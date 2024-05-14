package com.example.be3rdproject.cafe.service;

import com.example.be3rdproject.cafe.repository.cafes.Cafes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class CafeCrawler {
    public static void main(String[] args) {
        // ChromeDriver 경로 설정 (본인의 환경에 맞게 수정)
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        // WebDriver 인스턴스 생성
        WebDriver driver = new ChromeDriver();

        // 검색할 키워드와 해당 키워드에 대한 카페 목록
        Map<String, String[]> keywordCafeMap = new HashMap<>();
        keywordCafeMap.put("과제하기 좋은", new String[]{"조용", "스터디", "넓은 공간"});
        keywordCafeMap.put("수다떨기 좋은", new String[]{"친구", "모임", "수다"});
        keywordCafeMap.put("사진찍기 좋은", new String[]{"포토존", "인테리어", "인생샷"});
        keywordCafeMap.put("로스팅 직접 하는", new String[]{"로스터리", "로스팅카페", "로스팅"});
        keywordCafeMap.put("깔끔한", new String[]{"깔끔", "꺠끗", "청결"});

        // 각 키워드별로 카페 검색 및 정보 추출
        for (String keyword : keywordCafeMap.keySet()) {
            System.out.println("[" + keyword + "]");
            String[] cafes = keywordCafeMap.get(keyword);
            for (String cafe : cafes) {
                try {
                    // 카카오맵 검색 페이지로 이동
                    driver.get("https://map.kakao.com/");

                    // 검색어 입력
                    WebElement searchBox = driver.findElement(By.id("search.keyword.query"));
                    searchBox.sendKeys(cafe);

                    // 검색 버튼 클릭
                    WebElement searchButton = driver.findElement(By.className("btn_search"));
                    searchButton.click();

                    // 카페 상세 페이지로 이동
                    WebElement cafeLink = driver.findElement(By.className("link_search"));
                    cafeLink.click();

                    // 해당 카페의 정보를 추출하여 객체에 설정하는 로직을 추가

                    //  카페의 주소 추출
                    WebElement addressElement = driver.findElement(By.className("cafe-address"));
                    String address = addressElement.getText();

                    // 카페의 전화번호 추출
                    WebElement phoneElement = driver.findElement(By.className("cafe-phone"));
                    String phone = phoneElement.getText();

                    // 카페의 영업시간 추출
                    WebElement timeElement = driver.findElement(By.className("cafe-time"));
                    String time = timeElement.getText();

                    // 추출한 정보를 카페 객체의 필드에 설정
                    Cafes newCafe = new Cafes();
                    newCafe.setCafeName(cafe);
                    newCafe.setCafeAddress(address);
                    newCafe.setCafePhone(phone);
                    newCafe.setCafeTime(time);

                    // 설정된 카페 객체를 데이터베이스에 저장하거나, 다른 로직에 활용할 수 있음

                } catch (Exception e) {
                    System.err.println("카페 정보를 크롤링하는 중 오류가 발생했습니다: " + e.getMessage());
                }
            }
            System.out.println();
        }

        // WebDriver 종료
        driver.quit();
    }
}
