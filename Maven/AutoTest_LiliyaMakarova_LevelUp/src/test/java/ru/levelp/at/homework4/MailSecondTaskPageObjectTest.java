package ru.levelp.at.homework4;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;


public class MailSecondTaskPageObjectTest extends BaseMailTest {

    private static final String SUBJECT = "Задание 2. Тест";
    private static final String MESSAGE_TEXT = "Задание 2";

    @Test
    void sendMessageTestFolder() {

        // Предварительо создать папку "Тест"
        // Создать правило, по которому все письма, содержащие «Тест» в теме письма, отправляются в папку Тест
        // 1. Войти в почту
        LoginRegistrationPage loginRegistrationPage = new LoginRegistrationPage(driver);

        loginRegistrationPage.open();
        loginRegistrationPage.clickOnLoginButton();

        loginRegistrationPage.enterUsernameToUsernameField(MAIL_RU_LOGIN);
        loginRegistrationPage.sendEnterKeyToUsernameField();

        loginRegistrationPage.enterPasswordToPasswordField(MAIL_RU_PASSWORD);
        loginRegistrationPage.sendEnterKeyToPasswordField();

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);

        mainPage.closePromoPopup();

        // 2. Assert, что вход выполнен успешно
        Assertions.assertThat(mainPage.getActualUsername()).isEqualTo(MAIL_RU_LOGIN);

        mainPage.clickLeftMenuSentButton();
        final int sentMessagesBefore = mainPage.getListOfLettersCount();

        mainPage.clickLeftMenuTestButton();
        final int testMessagesBefore = mainPage.getListOfLettersCount();

        // 3. Создать новое письмо (заполнить адресата, тему письма и тело)
        mainPage.clickComposeLetterButton();

        mainPage.enterAddresseeToAddresseeField(MAIL_RU_LOGIN);
        mainPage.enterSubjectToSubjectField(SUBJECT);
        mainPage.enterMessageTextToMessageTextField(MESSAGE_TEXT);

        // 4. Отправить письмо
        mainPage.clickSendButton();
        mainPage.clickLetterSentCloseButton();

        // 5. Verify, что письмо появилось в папке отправленные
        mainPage.clickLeftMenuSentButtonAndWaitForNumberOfLettersIncreaseByOne(sentMessagesBefore);

        // 6. Verify, что письмо появилось в папке «Тест»
        mainPage.clickLeftMenuTestButtonAndWaitForNumberOfLettersIncreaseByOne(testMessagesBefore);

        // 7. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        mainPage.clickOnFirstLetterInListOfLetters();

        Assertions.assertThat(mainPage.getActualAddressee()).isEqualTo(MAIL_RU_LOGIN);
        Assertions.assertThat(mainPage.getActualSubject()).isEqualTo(SUBJECT);
        Assertions.assertThat(mainPage.getActualMessageText()).isEqualTo(MESSAGE_TEXT);

        // 8. Выйти из учётной записи
        mainPage.clickRightMenuButton();
        mainPage.clickLogoutButton();
    }
}
