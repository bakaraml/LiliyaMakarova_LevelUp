package ru.levelp.at.homework3;

import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseMailTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected static final String MAIL_RU_LOGIN = "liloek_2002@mail.ru";
    protected static final String MAIL_RU_PASSWORD = "deqvif-9riqdy-comqyM";

    protected static final String LETTER_LIST_ITEM = "a.js-letter-list-item";
    protected static final String RIGHT_MENU = "img.ph-avatar-img";
    protected static final String LEFT_MENU_DRAFTS = "[href='/drafts/?']";
    protected static final String LEFT_MENU_SENT = "[href='/sent/?']";
    protected static final String LEFT_MENU_TEST = "[href='/2/?']";
    protected static final String LEFT_MENU_INBOX = "[href='/inbox/?']";
    protected static final String LEFT_MENU_TRASH = "[href='/trash/?']";
    protected static final String PAGE_TITLE_DRAFT = "Черновики - Почта Mail.ru";
    protected static final String PAGE_TITLE_SENT = "Отправленные - Почта Mail.ru";
    protected static final String PAGE_TITLE_TEST = "Тест - Почта Mail.ru";
    protected static final String PAGE_TITLE_INBOX = "Входящие - Почта Mail.ru";
    protected static final String PAGE_TITLE_TRASH = "Корзина - Почта Mail.ru";

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    public int countTestLetterItems(String cssItem) {
        try {
            return wait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.cssSelector(cssItem))).size();
        } catch (TimeoutException te) {
            return 0;
        }
    }
}
