import time
from selenium import webdriver
from bs4 import BeautifulSoup
import re


def refine(data):
    refine_data = re.sub("(<([^>]+)>)","",str(data))
    data = re.sub(r"[^a-zA-Z0-9가-힣○ ]","",refine_data)
    return data

def is_key_exist(dict, key):
    if key in dict:
        return dict[key]
    else:
        return None



options = webdriver.ChromeOptions()
options.add_argument("headless")
driver = webdriver.Chrome('/Users/guinness/Downloads/chromedriver', options = options)

driver.get('http://prod.danawa.com/list/?cate=1131635&15main_11_03')
time.sleep(2)

# 페이지 범위 배포전에 수정하기
# 테스트데이터는 2페이지(60개)까지만
for i in range(1,2):
    time.sleep(2)
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')
    urls = soup.select('#danawa_wrap > #danawa_container > .content_wrapper > #danawa_content > .product_list_wrap > .product_list_area > #productListArea > #productCodeListForContent')
    urls = str(urls)[60:-4].split(',')

    for code in urls:
        option_dict = {}
        driver.get('http://prod.danawa.com/info/?pcode='+code+'&cate=1131635#bookmark_product_information')
        time.sleep(2)
        html = driver.page_source
        soup = BeautifulSoup(html, 'html.parser')
        
        model = soup.select(
            '.blog_wrap > #danawa_container >.blog_content > .summary_info > .top_summary > h3'
        )

        option = soup.select(
        '.blog_wrap > #danawa_container > .blog_content > .product_detail .detail_info > #productDescriptionArea > .detail_cont > .prod_spec > .spec_tbl > tbody > tr > th.tit'
        )

        data = soup.select(
        '.blog_wrap > #danawa_container > .blog_content > .product_detail .detail_info > #productDescriptionArea > .detail_cont > .prod_spec > .spec_tbl > tbody > tr > td.dsc'
        )
        
        for d in range(len(option)):
            if option[d] == '':
                continue
            # refine_data = re.sub("(<([^>]+)>)","",str(option[d]))
            # option[d] = re.sub(r"[^a-zA-Z0-9가-힣○ ]","",refine_data)
            option[d] = refine(option[d])
            data[d] = refine(data[d])
            
            option_dict[option[d]] = data[d]
        print(option_dict)


        request = {
                "model" : refine(model),
                "brand" : is_key_exist( option_dict ,"제조회사"),
                "connect" :is_key_exist(option_dict ,"연결 방식"),
                "switchBrand" : is_key_exist(option_dict ,"스위치"),
                "switchColor" : is_key_exist(option_dict ,"키 스위치"),
                "hotswap" : is_key_exist(option_dict ,"스위치 교체 가능"),
                "price" : None,
                "keycap" : is_key_exist(option_dict ,"키캡 재질"),
                "keycapImprint" : is_key_exist(option_dict ,"키캡 각인"),
                "keycapProfile" : is_key_exist(option_dict ,"스텝스컬쳐2"),
                "led" : is_key_exist(option_dict ,"LED 백라이트"),
                "arrangement" : is_key_exist(option_dict ,"키 배열"),
                "weight" : is_key_exist(option_dict ,"무게"),
                "cable" : is_key_exist(option_dict ,"직조(패브릭) 케이블"),
                "photo" :None
            }
        print(request)
    # driver.get('http://prod.danawa.com/list/?cate=1131635&15main_11_03')
    # next_p = driver.execute_script('movePage('+str(i+1)+')')
    # driver.implicitly_wait(5)

driver.quit()


