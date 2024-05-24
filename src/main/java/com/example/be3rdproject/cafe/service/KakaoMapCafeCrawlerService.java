package com.example.be3rdproject.cafe.service;

import com.example.be3rdproject.cafe.repository.cafes.Cafes;
import com.example.be3rdproject.cafe.repository.cafes.CafesJpaRepository;
import com.example.be3rdproject.cafe.repository.menus.Menus;
import com.example.be3rdproject.cafe.repository.menus.MenusJpaRepository;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class KakaoMapCafeCrawlerService {

    @Autowired
    private CafesJpaRepository cafesJpaRepository;
    @Autowired
    private MenusJpaRepository menusJpaRepository;

    private WebDriver driver;
    private WebDriverWait wait;

    @PostConstruct
    public void initWebDriver() {
        driver = createWebDriver();
        // 전역 변수 설정
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        crawlCafes();
    }

    private WebDriver createWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Headless 모드
        options.addArguments("--disable-gpu"); // GPU 비활성화
        options.addArguments("--no-sandbox"); // 샌드박스 비활성화
        options.addArguments("--disable-dev-shm-usage"); // /dev/shm 사용 비활성화
        return new ChromeDriver(options);
    }

    public void crawlCafes() {
        try {
            navigateToSearchPage();

            // 페이지 넘기면서 크롤링
            for (int pageNum = 1; pageNum <= 20; pageNum++) {
                log.info("현재페이지: {}", pageNum);
                collectDataFromCurrentPage();
                if (pageNum % 5 == 0) { // 5의 배수마다 다음 페이지
                    clickNextButton();
                } else {
                    clickPageButton(pageNum); // 아니면 다음 번호 클릭
                }
            }
        } catch (WebDriverException e) {
            log.error("웹드라이버 오류: {}", e.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private void navigateToSearchPage() {
        driver.get("https://map.kakao.com/"); // 카카오맵 가져오기
        WebElement searchInput =
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search.keyword.query")));
        searchInput.sendKeys("서울 카페");
        searchInput.sendKeys(Keys.ENTER);
        WebElement morePlacesButton =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("info.search.place.more"))); // 장소 더보기
        morePlacesButton.sendKeys(Keys.ENTER);
    }

    private void collectDataFromCurrentPage() {
        // 검색 결과중 카페 리스트에서 모든 카페 요소 가져옴
        List<WebElement> cafes =
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("#info\\.search\\.place\\.list > li"), 10));

        List<Cafes> cafeList = new ArrayList<>(); // 추출 정보 담을 리스트 생성
        List<Menus> menuList = new ArrayList<>();
        for (WebElement cafeElement : cafes) {
            try {
                Cafes cafe = extractCafeInfo(cafeElement, menuList);
                if (cafe != null) {
                    cafeList.add(cafe);
                    log.info("Cafe 추출: {}", cafe); // 카페 추출 확인용
                } else {
                    log.warn("Cafe 데이터 없음 ...");
                }
            } catch (StaleElementReferenceException e) {
                log.warn("해당 요소가 없음: {}", e.getMessage());
            }
        }
        // DB에 저장
        cafesJpaRepository.saveAll(cafeList);
        menusJpaRepository.saveAll(menuList);
    }

    private void clickPageButton(int pageNum) throws InterruptedException {
        int buttonNum = pageNum % 5;
        WebElement pageButton =
                driver.findElement(By.id(String.format("info.search.page.no%d", buttonNum + 1)));
        pageButton.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
    }

    private void clickNextButton() throws InterruptedException {
        WebElement nextButton =
                driver.findElement(By.id("info.search.page.next"));
        nextButton.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
    }

    private Cafes extractCafeInfo(WebElement cafeElement, List<Menus> menuList) {
        try {
            String name = getElementText(cafeElement, By.className("link_name"));
            String address = getElementText(cafeElement, By.cssSelector(".info_item > .addr > p"));
            String phone = getElementText(cafeElement, By.className("phone"));
            String scoreString = getElementText(cafeElement, By.cssSelector(".rating.clickArea > span.score > em"));
            Float score = parseScore(scoreString);
            String openingHours = getElementText(cafeElement, By.cssSelector(".info_item > .openhour > p > a"));

            Cafes cafe = Cafes.builder()
                    .cafeName(name)
                    .cafeAddress(address)
                    .cafePhone(phone)
                    .cafeScore(score)
                    .openingHours(openingHours)
                    .build();

            extractDetailInfo(cafeElement, cafe, menuList);

            return cafe;
        } catch (NoSuchElementException e) {
            log.error("카페 요소가 없습니다.: {}", e.getMessage());
            return null;
        }
    }

    private void extractDetailInfo(WebElement cafeElement, Cafes cafe, List<Menus> menuList) {
        String originalWindow = driver.getWindowHandle();

        try {
            Thread.sleep(2000);
            WebElement moreViewLink = cafeElement.findElement(By.cssSelector("a.moreview"));
            moreViewLink.sendKeys(Keys.ENTER);

            // 새 창 전환
            Thread.sleep(2000);
            Set<String> allWindows = driver.getWindowHandles();
            String newWindow = allWindows.stream()
                    .filter(window -> !window.equals(originalWindow))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("새 창을 찾을 수 없음."));

            driver.switchTo().window(newWindow);
            Thread.sleep(2000);

            // 시설 정보 추출
            Boolean wifi = hasFacility("ico_wifi");
            Boolean animal = hasFacility("ico_animal");
            Boolean parking = hasFacility("ico_parking");

            cafe.setCafeWifi(wifi);
            cafe.setCafeAnimal(animal);
            cafe.setCafeParking(parking);

            // 메뉴 더보기 클릭 시도
            try {
                Thread.sleep(2000); // 대기
                WebElement moreMenuButton = driver.findElement(By.cssSelector("a.link_more"));
                moreMenuButton.sendKeys(Keys.ENTER);

                // 메뉴 정보 추출
                Thread.sleep(2000);
                List<WebElement> menuElements = driver.findElements(By.cssSelector("ul.list_menu > li"));
                for (WebElement menuElement : menuElements) {
                    try {
                        String menuName = getElementText(menuElement, By.cssSelector(".loss_word"));
                        String menuPrice = getElementText(menuElement, By.cssSelector(".price_menu"));

                        // 메뉴 이름의 길이 검사
                        if (menuName.length() > 50) {
                            log.warn("메뉴 이름 길이 초과.: {}", menuName);
                            menuName = menuName.substring(0, 255); // 길이를 초과하면 잘라냄
                        }

                        Menus menu = Menus.builder()
                                .menuName(menuName)
                                .menuPrice(menuPrice)
                                .cafe(cafe)
                                .build();
                        menuList.add(menu);
                        log.info("메뉴 추출: {}", menu);
                    } catch (NoSuchElementException e) {
                        log.warn("메뉴 요소가 없습니다.: {}", e.getMessage());
                    }
                }
            } catch (InterruptedException e) {
                log.warn("InterruptedException 발생: {}", e.getMessage());
            }

            driver.close();
            driver.switchTo().window(originalWindow);
        } catch (NoSuchElementException e) {
            log.error("상세정보 없음: {}", e.getMessage());
        } catch (InterruptedException e) {
            log.error("InterruptedException 발생: {}", e.getMessage());
        }
    }

    private String getElementText(WebElement parent, By by) {
        try {
            WebElement element = parent.findElement(by);
            return element.getText();
        } catch (NoSuchElementException e) {
            log.warn("요소를 찾을 수 없음.: {}", by);
            return "";
        }
    }

    private Float parseScore(String scoreString) {
        if (scoreString == null || scoreString.isEmpty()) {
            log.warn("Score 미제공");
            return null;
        }
        try {
            return Float.parseFloat(scoreString);
        } catch (NumberFormatException e) {
            log.error("Score parse 오류: {}", scoreString);
            return null;
        }
    }

    private Boolean hasFacility(String facilityClass) {
        try {
            WebElement facilityElement =
                    driver.findElement(By.cssSelector("ul.list_facility li span." + facilityClass));
            return facilityElement != null;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}