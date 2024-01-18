'''
This script uses selenium to read the html off of Middlesex College course catalog.

In order to do this, we have to access the following website:
    https://selfserv.middlesexcc.edu/Student/Courses
We use this website in order to get to the actual catalogue itself:
    https://selfserv.middlesexcc.edu/Student/Courses/Search

1.A    main_page_navigator():
The first link is NOT the catalogue of courses itself, but the /main page/ before the course
catalogue. The reason we use this page instead of using the catalogue link itself is because
of the way that the website is structured.

The website has two views that can only be accessed on the main page:
- Catalog Listing
- Section Listing

When accessing the catalog directly via the second link, the default view is the catalog
listing. This is problematic because the data is not visible under many drop downs, and is also
unorganized. This makes the coding process harder and the code also take longer to run.

'''

import time
import json

from selenium import webdriver

from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options

from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

from selenium.common.exceptions import StaleElementReferenceException

web_options = Options()
web_options.add_argument("--headless=new")

driver = webdriver.Chrome(options=web_options)
driver.get("https://selfserv.middlesexcc.edu/Student/Courses")
driver.maximize_window()

action = ActionChains(driver)

#Create a json object that uses key value pairs


#Explanation for why this exists 
def main_page_navigator():
    #Curl command (that has nothing to do with selenium)
    search_button = driver.find_element(by=By.ID, value="submit-search-form")
    search_button.click()  

def print_course_list(file):
    json_dict = {

    }

    main_page_navigator()

    #Add Exception for two cases main page not working
    WebDriverWait(driver, 10).until(EC.element_to_be_clickable((By.ID, "course-catalog-hide-filters-button"))).click()

    num_pages = int(driver.find_element(by=By.ID, value="course-results-total-pages").text)
    curr_page = int(driver.find_element(by=By.ID, value="course-results-current-page").get_attribute("value"))
    curr_id = 1

    while(curr_page < num_pages+1):
        print(f"Page {curr_page} out of {num_pages}")
        curr_id = page_scraper(json_dict, curr_id)
        print("Scrape complete!")
        next_page_button = driver.find_element(by=By.ID, value="course-results-next-page")
        action.move_to_element(next_page_button).click().perform()
        curr_page=curr_page+1

    json.dump(json_dict, file, indent=4)

def page_scraper(json_dict, table_id):
    #Waits for all the rows of the course catalog table to be visible
    WebDriverWait(driver, 10).until(EC.visibility_of_all_elements_located((By.XPATH, "//tbody[@class='esg-table-body']/tr/td")))

    #The variable below is a list of every course row on the current page of the course catalog
    curr_page_table = driver.find_elements(by=By.XPATH, value="//tbody[@class='esg-table-body']/tr")

    #Loops through the list and grabs the text value of all the fields from the rows
    for row in curr_page_table:
        #term = row.find_element(by=By.XPATH, value=".//td[@data-role='Term']/div").text
        #status = term = row.find_element(by=By.XPATH, value=".//td[@data-role='Status']/div")
        section_name = row.find_element(by=By.XPATH, value=".//td[@data-role='Section Name']/div/a").text
        title = row.find_element(by=By.XPATH, value=".//td[@data-role='Title']/div").text
        dates = row.find_element(by=By.XPATH, value=".//td[@data-role='Dates']/div").text.split("-")
        start_date = dates[0]
        end_date = dates[1]
        course_location = row.find_element(by=By.XPATH, value=".//td[@data-role='Location']/div")
        course_faculty = row.find_element(by=By.XPATH, value=".//td[@data-role='Faculty']/div")
        availability = row.find_element(by=By.XPATH, value=".//td[@data-role='Availability']/div").text.split("/")
        available_seats = availability[0].strip()
        course_credits = row.find_element(by=By.XPATH, value=".//td[@data-role='Credits']/div").text

        #Formats the data to be printed out to the json file
        row_json = {
            "section_name" : section_name,
            "title" : title,
            "start_date" : start_date,
            "available_seats" : available_seats
        }

        json_dict.update({table_id: row_json})
        table_id += 1
    return table_id

start_time = time.time()

with open("courses.json", "w") as course_file:
    print_course_list(course_file)

print("--- %s seconds ---" % (time.time() - start_time))