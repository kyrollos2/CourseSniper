"""This is a modular version of Ivans script,
I was having trouble with some bugs late at night"""
import time
import json
import os
from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import StaleElementReferenceException, NoSuchElementException

def setup_driver():
    web_options = Options()
    web_options.add_argument("--headless")
    driver = webdriver.Chrome(options=web_options)
    driver.get("https://selfserv.middlesexcc.edu/Student/Courses")
    driver.maximize_window()
    return driver

def main_page_navigator(driver):
    try:
        search_button = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.ID, "submit-search-form")))
        search_button.click()
    except NoSuchElementException:
        print("Check the page layout.")

def page_scraper(driver, json_dict, table_id):
    WebDriverWait(driver, 10).until(EC.visibility_of_all_elements_located((By.XPATH, "//tbody[@class='esg-table-body']/tr/td")))

    curr_page_table = driver.find_elements(by=By.XPATH, value="//tbody[@class='esg-table-body']/tr")

    for row in curr_page_table:
        try:
            section_name = row.find_element(by=By.XPATH, value=".//td[@data-role='Section Name']/div/a").text
            title = row.find_element(by=By.XPATH, value=".//td[@data-role='Title']/div").text
            dates_element = row.find_element(by=By.XPATH, value=".//td[@data-role='Dates']/div")
            dates = dates_element.text.split("-")
            start_date = dates[0].strip() if dates else ""
            end_date = dates[1].strip() if len(dates) > 1 else ""

            # Extract the available seats
            try:
                available_seats = row.find_element(by=By.XPATH, value=".//td[@data-role='Available Seats']/div").text
            except NoSuchElementException:
                available_seats = "N/A"  # Handle the case when available seats information is not present

            row_json = {
                "section_name": section_name,
                "title": title,
                "start_date": start_date,
                "available_seats": available_seats,  # Include available seats in the JSON data
            }

            json_dict.update({table_id: row_json})
            table_id += 1

        except Exception as e:
            print(f"An unexpected error occurred while scraping row {table_id}: {e}")
            continue

    return table_id

def print_course_list(driver, file):
    json_dict = {}
    main_page_navigator(driver)
    WebDriverWait(driver, 10).until(EC.element_to_be_clickable((By.ID, "course-catalog-hide-filters-button"))).click()

    num_pages = int(driver.find_element(by=By.ID, value="course-results-total-pages").text)
    curr_page = 1
    curr_id = 1

    while curr_page <= num_pages:
        print(f"Page {curr_page} out of {num_pages}")
        curr_id = page_scraper(driver, json_dict, curr_id)
        print("Scrape complete!")
        if curr_page < num_pages:
            next_page_button = driver.find_element(by=By.ID, value="course-results-next-page")
            ActionChains(driver).move_to_element(next_page_button).click().perform()
        curr_page += 1

    json.dump(json_dict, file, indent=4)

if __name__ == "__main__":
    driver = setup_driver()
    try:
        start_time = time.time()

        # Specify the directory where you want to save the JSON file
        output_directory = "coursniper-dboperations/data"  # Replace with your desired directory path
        output_file_path = os.path.join(output_directory, "courses.json")

        with open(output_file_path, "w") as course_file:
            print_course_list(driver, course_file)

        print(f"Scraping completed in {time.time() - start_time:.2f} seconds.")
    finally:
        driver.quit()
