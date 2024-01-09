'''This purpose of the script is to pull out information from the course websites'''
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.common.exceptions import StaleElementReferenceException
from selenium.webdriver.support import expected_conditions as EC
import time

web_options = Options()
web_options.add_argument("--headless=new")

driver = webdriver.Chrome(options=web_options)
driver.get("https://selfserv.middlesexcc.edu/Student/Student/Courses")
driver.maximize_window()

action = ActionChains(driver)

#Create a json object that uses key value pairs
f = open("output.txt", "w")

def main_page_navigator():
    #Curl command (that has nothing to do with selenium)
    search_button = driver.find_element(by=By.ID, value="submit-search-form")
    search_button.click()  

def print_course_list():
    main_page_navigator()

    #Add Exception for two cases main page not working
    WebDriverWait(driver, 10).until(EC.element_to_be_clickable((By.ID, "course-catalog-hide-filters-button"))).click()

    num_pages = int(driver.find_element(by=By.ID, value="course-results-total-pages").text)
    curr_page = int(driver.find_element(by=By.ID, value="course-results-current-page").get_attribute("value"))
    curr_id = 1

    while(curr_page != num_pages+1):
        curr_id = page_scraper(curr_id)
        print("Next Page Loop")
        next_page_button = driver.find_element(by=By.ID, value="course-results-next-page")
        action.move_to_element(next_page_button).click().perform()
        curr_page=curr_page+1

def page_scraper(id):
    line_list = []
    try:
        curr_page_table = driver.find_elements(by=By.XPATH, value="//tbody[@class='esg-table-body']/tr")
        print("Grabbed current table")
        for row in curr_page_table:
            term = row.find_element(by=By.XPATH, value=".//td[@data-role='Term']/div").text
            #status = term = row.find_element(by=By.XPATH, value=".//td[@data-role='Status']/div")
            section_name = row.find_element(by=By.XPATH, value=".//td[@data-role='Section Name']/div/a").text
            title = row.find_element(by=By.XPATH, value=".//td[@data-role='Title']/div").text
            dates = row.find_element(by=By.XPATH, value=".//td[@data-role='Dates']/div").text.split("-")
            start_date = dates[0]
            end_date = dates[1]
            #location = row.find_element(by=By.XPATH, value=".//td[@data-role='Location']/div")
            #faculty = row.find_element(by=By.XPATH, value=".//td[@data-role='Faculty']/div")
            availability = row.find_element(by=By.XPATH, value=".//td[@data-role='Availability']/div").text.split("/")
            available_seats = availability[0]
            line = str(id) + ","+ term + "," + section_name + "," + title + "," + start_date + ","
            line += end_date + "," + available_seats
            line_list.append(line)
            id=id+1
        list_printer(line_list)
        return id
    #Insert Timeout Exception
    except:
        line_list = []
        print("Exception caught!")
        WebDriverWait(driver, 10).until(EC.visibility_of_all_elements_located((By.XPATH, "//tbody[@class='esg-table-body']/tr/td")))
        curr_page_table = driver.find_elements(by=By.XPATH, value="//tbody[@class='esg-table-body']/tr")
        print("Grabbed exception table")
        for row in curr_page_table:
            term = row.find_element(by=By.XPATH, value=".//td[@data-role='Term']/div").text
            #status = term = row.find_element(by=By.XPATH, value=".//td[@data-role='Status']/div")
            section_name = row.find_element(by=By.XPATH, value=".//td[@data-role='Section Name']/div/a").text
            title = row.find_element(by=By.XPATH, value=".//td[@data-role='Title']/div").text
            dates = row.find_element(by=By.XPATH, value=".//td[@data-role='Dates']/div").text.split("-")
            start_date = dates[0]
            end_date = dates[1]
            #location = row.find_element(by=By.XPATH, value=".//td[@data-role='Location']/div")
            #faculty = row.find_element(by=By.XPATH, value=".//td[@data-role='Faculty']/div")
            availability = row.find_element(by=By.XPATH, value=".//td[@data-role='Availability']/div").text.split("/")
            available_seats = availability[0]
            line = str(id) + "," + term + "," + section_name + "," + title + "," + start_date + ","
            line += end_date + "," + available_seats
            line_list.append(line)
            id=id+1
        list_printer(line_list)
        return id
    
def list_printer(list):
    for item in list:
        print(item, file=f)
        

def get_row_info():
    pass

print_course_list()