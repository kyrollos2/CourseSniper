from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.common.exceptions import StaleElementReferenceException
from selenium.webdriver.support import expected_conditions as EC
import time

#Find a way to grab elements in a way that is browser independent
#Insert Headless Mode
chrome_options = Options()
chrome_options.add_argument("--headless=new")

driver = webdriver.Chrome(options=chrome_options)
driver.get("https://selfserv.middlesexcc.edu/Student/Student/Courses")

action = ActionChains(driver)
f = open("output.txt", "w")
print("id,section_name,title,start_date,end_date,available_seats", file=f)

def main_page_navigator():
    search_button = driver.find_element(by=By.ID, value="submit-search-form")
    search_button.click()   

def page_scraper(id):
    curr_page_table = driver.find_elements(by=By.XPATH, value="//tbody[@class='esg-table-body']/tr")
    for row in curr_page_table:
        #Grab more fields!!!!!!!!!!!!!!
        section_name = row.find_element(by=By.XPATH, value=".//td[@data-role='Section Name']/div/a").text
        title = row.find_element(by=By.XPATH, value=".//td[@data-role='Title']/div").text
        dates = row.find_element(by=By.XPATH, value=".//td[@data-role='Dates']/div").text.split("-")
        start_date = dates[0]
        end_date = dates[1]
        availability = row.find_element(by=By.XPATH, value=".//td[@data-role='Availability']/div").text.split("/")
        available_seats = availability[0]
        print(f"{id},{section_name},{title},{start_date},{end_date},{available_seats}", file=f)
        id=id+1
    return id

def course_list():
    main_page_navigator()
    WebDriverWait(driver, 10).until(EC.element_to_be_clickable((By.ID, "course-catalog-hide-filters-button"))).click()
    num_pages = int(driver.find_element(by=By.ID, value="course-results-total-pages").text)
    curr_page = int(driver.find_element(by=By.ID, value="course-results-current-page").get_attribute("value"))
    curr_id = 1
    while(curr_page != num_pages):
        curr_id = page_scraper(curr_id)
        next_page_button = driver.find_element(by=By.ID, value="course-results-next-page")
        action.move_to_element(next_page_button).click().perform()
        curr_page=curr_page+1

#search_filters("Computer Science", "Winter 2024", "106", "IN2")
course_list()
time.sleep(5)
driver.quit()
