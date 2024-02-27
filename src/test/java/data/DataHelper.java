package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker();

    private DataHelper() {
    }

    public static String generateCardNumber() {

        return faker.numerify("#### #### #### ####");
    }


    public static String generateName(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generateMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String statusCard() {
        return "APPROVED";
    }
    public static String statusCardCredit() {
        return "APPROVED";
    }
    public static String generateCVC() {
        return faker.numerify("###");
    }

    public static CardInfo generateRandom() {
        return new CardInfo(generateCardNumber(), generateName("en"), generateMonth(), generateYear(), generateCVC());
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String name;
        String month;
        String year;
        String cvc;
    }
    @Value
    public static class StatusCard {
        String statusCard;
    }
    @Value
    public static class StatusCardCredit {
        String statusCardCredit;
    }
    public static String generateApprovedCard() {
        return "4444 4444 4444 4441";
    }
    public static String generateDeclinedCard() {
        return "4444 4444 4444 4442";
    }


    public static String generateLastYear() {
        return "23";
    }

    public static String generateIncompleteCVC() {

        return "56";
    }
    public static String generateLastMonth() {
        return "01";
    }
    public static String generateSpecialCharactersName() {
        return "@#@#$";
    }

    public static String generateNumbersInName() {
        return "5648648";
    }
    public static String generateFieldsEmpty() {
        return "";
    }
    public static CardInfo generateApprovedCardInfo() {
        return new CardInfo(generateApprovedCard(), generateName("en"), generateMonth(), generateYear(), generateCVC());
    }

    public static CardInfo generateDeclinedCardNumber() {
        return new CardInfo(generateDeclinedCard(), generateName("en"), generateMonth(), generateYear(), generateCVC());
    }
    public static CardInfo generateEmptyCardNumber() {
        return new CardInfo(generateFieldsEmpty(), generateName("en"), generateMonth(), generateYear(), generateCVC());
    }

    public static CardInfo generateLastYearCard() {
        return new CardInfo(generateApprovedCard(), generateName("en"), generateMonth(), generateLastYear(), generateCVC());
    }
    public static CardInfo generateEmptyYear() {
        return new CardInfo(generateApprovedCard(), generateName("en"), generateMonth(), generateFieldsEmpty(), generateCVC());
    }

    public static CardInfo generateIncompleteCVCCard() {
        return new CardInfo(generateApprovedCard(), generateName("en"), generateMonth(), generateYear(), generateIncompleteCVC());
    }
    public static CardInfo generateEmptyCvc() {
        return new CardInfo(generateApprovedCard(), generateName("en"), generateMonth(), generateYear(), generateFieldsEmpty());
    }

    public static CardInfo generateLastMonthCard() {
        return new CardInfo(generateApprovedCard(), generateName("en"), generateLastMonth(), generateYear(), generateCVC());
    }
    public static CardInfo generateEmptyMonth() {
        return new CardInfo(generateApprovedCard(), generateName("en"), generateFieldsEmpty(), generateYear(), generateCVC());
    }
    public static CardInfo generateSymbolsName() {
        return new CardInfo(generateApprovedCard(), generateSpecialCharactersName(), generateMonth(), generateYear(), generateCVC());
    }
    public static CardInfo generateCyrillicName() {
        return new CardInfo(generateApprovedCard(), generateName("ru"), generateMonth(), generateYear(), generateCVC());
    }
    public static CardInfo generateNumbersName() {
        return new CardInfo(generateApprovedCard(), generateNumbersInName(), generateMonth(), generateYear(), generateCVC());
    }
    public static CardInfo generateNoName() {
        return new CardInfo(generateApprovedCard(), generateFieldsEmpty(), generateMonth(), generateYear(), generateCVC());
    }
    public static CardInfo generateAllFieldsEmpty() {
        return new CardInfo(generateFieldsEmpty(), generateFieldsEmpty(), generateFieldsEmpty(), generateFieldsEmpty(), generateFieldsEmpty());
    }

}