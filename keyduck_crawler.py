import time
from selenium import webdriver
from bs4 import BeautifulSoup
import re
import json

options = webdriver.ChromeOptions()
options.add_argument("headless")
driver = webdriver.Chrome('/Users/guinness/Downloads/chromedriver', options = options)

driver.get('http://prod.danawa.com/list/?cate=1131635&15main_11_03')
time.sleep(2)

# 페이지 범위 배포전에 수정하기
# 테스트데이터는 2페이지(60개)까지만
for i in range(1,3):
    time.sleep(2)
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')
    urls = soup.select('#danawa_wrap > #danawa_container > .content_wrapper > #danawa_content > .product_list_wrap > .product_list_area > #productListArea > #productCodeListForContent')
    urls = str(urls)[60:-4].split(',')

    for code in urls:
        driver.get('http://prod.danawa.com/info/?pcode='+code+'&cate=1131635#bookmark_product_information')
        time.sleep(2)
        html = driver.page_source
        soup = BeautifulSoup(html, 'html.parser')
        
        model = soup.select(
            '.blog_wrap > #danawa_container >.blog_content > .summary_info > .top_summary > h3'
        )
        data = soup.select(
        '.blog_wrap > #danawa_container > .blog_content > .product_detail .detail_info > #productDescriptionArea > .detail_cont > .prod_spec > .spec_tbl > tbody > tr > td.dsc'
        )
        data.append(model)

        for d in range(len(data)):
            refine_data = re.sub("(<([^>]+)>)","",str(data[d]))
            data[d] = re.sub(r"[^a-zA-Z0-9가-힣○ ]","",refine_data)
            if data[d] == '':
                data[d] = 'default'


        request = {
                "model" : data[-1],
                "brand" : data[0][:-14],
                "connect" : data[2],
                "switchBrand" : data[11],
                "switchColor" : data[12],
                "hotswap" : data[51],
                "price" : 0,
                "keycap" : data[32],
                "keycapImprint" : data[34],
                "keycapProfile" : data[33],
                "led" : data[35],
                "arrangement" : data[5][:-1],
                "weight" : data[67][:-1],
                "cable" : data[36],
                "photo" : "default"
            }
    driver.get('http://prod.danawa.com/list/?cate=1131635&15main_11_03')
    next_p = driver.execute_script('movePage('+str(i+1)+')')
    driver.implicitly_wait(5)

driver.quit()


