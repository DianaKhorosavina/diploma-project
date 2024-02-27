package test;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.SelectPage;
import data.DataHelper;
import data.SQLHelper;
import static com.codeborne.selenide.Selenide.open;


public class BuyingTourTest {
    SelectPage selectPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        selectPage = open("http://localhost:8080", SelectPage.class);
    }

    @Test
    @DisplayName("Purchasing a tour with a valid debit card")
    void purchasingATourWithAValidDebitCard() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateApprovedCardInfo();
        debitPage.validCard(validCard);
        debitPage.answerBank("Операция одобрена Банком.");
        var statusCard = SQLHelper.getStatusCard();
        String status = statusCard.getStatusCard();
        String expectedStatus = "APPROVED";
        Assertions.assertEquals(expectedStatus, status);
    }

    @Test
    @DisplayName("Purchasing a tour with a valid credit card")
    void purchasingATourWithAValidCreditCard() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateApprovedCardInfo();
        creditPage.validCardCredit(validCard);
        creditPage.answerBankCredit("Операция одобрена Банком.");
        var statusCard = SQLHelper.getStatusCard();
        String status = statusCard.getStatusCard();
        String expectedStatus = "APPROVED";
        Assertions.assertEquals(expectedStatus, status);
    }

    @Test
    @DisplayName("Purchasing a tour with an invalid debit card")
    void purchasingATourWithAnInvalidDebitCard() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateDeclinedCardNumber();
        debitPage.validCard(validCard);
        debitPage.answerBank("Ошибка! Банк отказал в проведении операции.");
        var statusCard = SQLHelper.getStatusCard();
        String status = statusCard.getStatusCard();
        String expectedStatus = "DECLINED";
        Assertions.assertEquals(expectedStatus, status);
    }

    @Test
    @DisplayName("Purchasing a tour with an invalid credit card")
    void purchasingATourWithAnInvalidCreditCard() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateDeclinedCardNumber();
        creditPage.validCardCredit(validCard);
        creditPage.answerBankCredit("Ошибка! Банк отказал в проведении операции.");
        var statusCard = SQLHelper.getStatusCard();
        String status = statusCard.getStatusCard();
        String expectedStatus = "DECLINED";
        Assertions.assertEquals(expectedStatus, status);
    }

    @Test
    @DisplayName("Send an empty request to purchase a tour by debit card")
    void allDebitFieldEmptyRequest() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateAllFieldsEmpty();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send an empty request to purchase a tour by credit card")
    void allCreditFieldEmptyRequest() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateAllFieldsEmpty();
        creditPage.validCardCredit(validCard);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by debit card with cyrillic name")
    void cyrillicNameRequest() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateCyrillicName();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by credit card with cyrillic name")
    void cyrillicNameCreditRequest() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateCyrillicName();
        creditPage.validCardCredit(validCard);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by debit card with last year")
    void useLustYearDebitCard() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateLastYearCard();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Send request to purchase a tour by credit card with last year")
    void useLustYearCreditCard() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateLastYearCard();
        creditPage.validCardCredit(validCard);
        creditPage.verifyErrorNotification("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Send request to purchase a tour by debit card with incomplete CVC card")
    void useIncompleteCVCDebit() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateIncompleteCVCCard();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by credit card with incomplete CVC card")
    void useIncompleteCVCCredit() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateIncompleteCVCCard();
        creditPage.validCardCredit(validCard);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by last month debit card")
    void useLastMonthDebitCard() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateLastMonthCard();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Send request to purchase a tour by last month credit card")
    void useLastMonthCreditCard() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateLastMonthCard();
        creditPage.validCardCredit(validCard);
        creditPage.verifyErrorNotification("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Send request to purchase a tour by debit card with empty card number")
    void requestWithEmptyDebitCardNumber() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateEmptyCardNumber();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by credit card with empty card number")
    void requestWithEmptyCreditCardNumber() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateEmptyCardNumber();
        creditPage.validCardCredit(validCard);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by debit card with empty month field")
    void requestWithEmptyMonthDebitCard() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateEmptyMonth();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by debit card with empty month field")
    void requestWithEmptyMonthCreditCard() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateEmptyMonth();
        creditPage.validCardCredit(validCard);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by debit card with empty year field")
    void requestWithEmptyMontDebitCard() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateEmptyYear();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by credit card with empty year field")
    void requestWithEmptyMontCreditCard() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateEmptyYear();
        creditPage.validCardCredit(validCard);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by debit card with empty name field")
    void requestWithEmptyYearDebitCard() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateNoName();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Send request to purchase a tour by credit card with empty name field")
    void requestWithEmptyYearCreditCard() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateNoName();
        creditPage.validCardCredit(validCard);
        creditPage.verifyErrorNotification("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Send request to purchase a tour by debit card with empty CVC field")
    void requestWithEmptyCVCDebitCard() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateEmptyCvc();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by credit card with empty CVC field")
    void requestWithEmptyCVCCreditCard() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateEmptyCvc();
        creditPage.validCardCredit(validCard);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by debit card with numbers in name field")
    void requestWithNumbersInNameFieldDebitCard() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateNumbersName();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by credit card with numbers in name field")
    void requestWithNumbersInNameFieldCreditCard() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateNumbersName();
        creditPage.validCardCredit(validCard);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by debit card with special symbols in name field")
    void requestWithSymbolsInNameFieldDebitCard() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateSymbolsName();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by credit card with special symbols in name field")
    void requestWithSymbolsInNameFieldCreditCard() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateSymbolsName();
        creditPage.validCardCredit(validCard);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("Send request to purchase a tour by debit card not registered in system")
    void requestWithNotRegisteredDebitCard() {
        var debitPage = selectPage.debitBuy();
        var validCard = DataHelper.generateRandom();
        debitPage.validCard(validCard);
        debitPage.answerBank("Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    @DisplayName("Send request to purchase a tour by credit card not registered in system")
    void requestWithNotRegisteredCreditCard() {
        var creditPage = selectPage.creditBuy();
        var validCard = DataHelper.generateRandom();
        creditPage.validCardCredit(validCard);
        creditPage.answerBankCredit("Ошибка! Банк отказал в проведении операции.");
    }
}

    












