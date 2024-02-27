package page;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DebitPage {
    private final SelenideElement cardNumber = $(".input__control[placeholder='0000 0000 0000 0000']");
    private final SelenideElement month = $(".input__control[placeholder='08']");
    private final SelenideElement year = $(".input__control[placeholder='22']");
    private final SelenideElement name = $(byText("Владелец")).parent()
            .find(By.tagName("input"));
    private final SelenideElement cvc = $(".input__control[type='text'][placeholder='999']");
    private final SelenideElement errorNotification = $(".input__sub");
    private final SelenideElement press = $(byText("Продолжить"));
    private final SelenideElement bank = $(".notification__content");

    public void verifyErrorNotification(String expectedText) {
        errorNotification.shouldHave(exactText(expectedText)).shouldBe(visible);
    }
    public void validCard(DataHelper.CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        name.setValue(cardInfo.getName());
        cvc.setValue(cardInfo.getCvc());
        press.click();
    }
    public void answerBank(String expectedText) {
        bank.shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText(expectedText));
    }

}