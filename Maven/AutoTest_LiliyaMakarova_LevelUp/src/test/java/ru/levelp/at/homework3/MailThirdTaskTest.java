package ru.levelp.at.homework3;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class MailThirdTaskTest extends BaseMailTest {

    private static final String SUBJECT = "Задание 3";
    private static final String MESSAGE_TEXT = "Задание 3";

    @Test
    void sendMessageInboxFolderAndDelMessage() {

        // 1. Войти в почту
        driver.get("https://mail.ru/");
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.cssSelector("[data-testid='enter-mail-primary']"))).click();
        WebElement frame = driver.findElement(By.cssSelector("iframe[class='ag-popup__frame__layout__iframe']"));
        driver.switchTo().frame(frame);

        var emailTextBox = wait
            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        emailTextBox.sendKeys(MAIL_RU_LOGIN);
        emailTextBox.sendKeys(Keys.ENTER);

        var passwordTextBox = wait
            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='password']")));
        passwordTextBox.sendKeys(MAIL_RU_PASSWORD);
        passwordTextBox.sendKeys(Keys.ENTER);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ph-project-promo-close-icon")))
                .click();
        } catch (TimeoutException te) {
            //ignore
        }

        // 2. Assert, что вход выполнен успешно
        String actualUserLogin = wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.cssSelector(RIGHT_MENU))).getAttribute("alt");
        assertThat(actualUserLogin).isEqualTo(MAIL_RU_LOGIN);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_INBOX))).click();
        wait.until(ExpectedConditions.titleContains(PAGE_TITLE_INBOX));
        final int receivedMessagesBefore = countTestLetterItems(LETTER_LIST_ITEM);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_TRASH))).click();
        wait.until(ExpectedConditions.titleContains(PAGE_TITLE_TRASH));
        final int deletedMessagesBefore = wait.until(ExpectedConditions
            .presenceOfAllElementsLocatedBy(By.cssSelector(LETTER_LIST_ITEM))).size();

        // 3. Создать новое письмо (заполнить адресата (самого себя), тему письма и тело)
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.compose-button")))
            .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .xpath("//div[@data-type='to']//input"))).sendKeys(MAIL_RU_LOGIN);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='Subject']")))
            .sendKeys(SUBJECT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='textbox']")))
            .sendKeys(MESSAGE_TEXT);

        // 4. Отправить письмо
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='send']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button2_close"))).click();

        // 5. Verify, что письмо появилось в папке Входящие
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_INBOX))).click();
        wait.until(ExpectedConditions.titleContains(PAGE_TITLE_INBOX));
        wait.until(ExpectedConditions.numberOfElementsToBe(By
            .cssSelector(LETTER_LIST_ITEM), receivedMessagesBefore + 1));

        // 6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        wait.until(ExpectedConditions.elementToBeClickable(By
            .cssSelector(LETTER_LIST_ITEM + ":nth-of-type(1)"))).click();

        var actualAddressee = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector(".letter__recipients > .letter-contact"))).getAttribute("title");
        Assertions.assertThat(actualAddressee).isEqualTo(MAIL_RU_LOGIN);

        var actualSubject = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("h2.thread-subject"))).getText();
        Assertions.assertThat(actualSubject).isEqualTo(SUBJECT);

        var actualMessageText = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("div.js-readmsg-msg > div > div > div > :first-child"))).getText();
        Assertions.assertThat(actualMessageText).isEqualTo(MESSAGE_TEXT);

        // 7. Удалить письмо
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button2_delete"))).click();

        // 8. Verify что письмо появилось в папке Корзина
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_TRASH))).click();
        wait.until(ExpectedConditions.titleContains(PAGE_TITLE_TRASH));
        wait.until(ExpectedConditions.numberOfElementsToBe(By
            .cssSelector(LETTER_LIST_ITEM), deletedMessagesBefore + 1));

        // 9. Выйти из учётной записи
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(RIGHT_MENU))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("[data-testid='whiteline-account-exit']"))).click();
    }
}
