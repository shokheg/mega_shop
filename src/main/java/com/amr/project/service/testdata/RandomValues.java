package com.amr.project.service.testdata;

import com.amr.project.model.entity.*;
import com.amr.project.model.enums.Gender;
import com.amr.project.model.enums.PersonalDataStatus;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/***
 * Классический класс случайных значений.
 */
public class RandomValues {
    private static final String[] names = {
        "Александр", "Николай", "Василий", "Дмитрий", "Фёдор",
        "Иван", "Пётр", "Павел", "Андрей", "Алексей", "Евгений"
    };

    private static final String[] lastNames = {
            "Пушкин", "Гоголь", "Достоевский", "Лермонтов", "Герцен",
            "Маяковский", "Блок", "Ленин", "Брежнев", "Троцкий"
    };

    private static final String[] streetNames = {
            "Земляничная", "Кружевная", "Краснознамённая",
            "Колотушкина", "Ивановская", "Дерибасовская",
            "Вязов", "Ленина", "Звёздная", "Любимая",
            "Пушкинская", "Мирная", "Тихая", "Яблочная",
            "Добрая", "Светлая", "Огурцовая"
    };

    private static final String[] cityNames = {
            "Москва", "Санкт-Петербург", "Владимир",
            "Томск", "Владивосток", "Хабаровск",
            "Нижний Новгород", "Благовещенск", "Иркутск", "Мурманск",
            "Челябинск", "Екатеринбург"
    };

    private static final String[] categoryNames = {
            "Бижутерия", "Автомобили", "Стулья", "Носки",
            "Люди", "Игрушки", "Телефоны", "Одежда", "Стройматериалы",
            "Бижутерия", "Сумки"
    };

    private static final String[] shopNames = {
            "Для Милых Дам", "Красивое место", "Дивный Мир", "Рай на колёсах",
            "Галактика стульев", "Домашний", "Детский мир", "Сад и Огород"
    };

    private static final String[] itemNames = {
            "Спички", "Стул", "Автомобиль", "Человек", "Чёрные носки", "Цветные носки",
            "Футболка", "Мобильный телефон", "Стационарный телефон", "Сумка",
            "Блестяшка", "Доски", "Большая сумка"
    };

    // Всё, что связано с адресами и именами
    public static List<Address> randomAddressList(int howManyAddressesWeWant) {
        Set<Address> addresses = new HashSet<>();
        while (addresses.size() != howManyAddressesWeWant) {
            addresses.add(randomAddress());
        }
        return new ArrayList<>(addresses);
    }

    public static Address randomAddress() {
        return Address.builder().cityIndex(randomCityIndex())
                .house(randomHouseNumber())
                .street(randomStreetName())
                .build();
    }

    public static Address randomAddressFromList(List<Address> addresses) {
        return addresses.get(randomInt(0, addresses.size() - 1));
    }

    public static List<City> randomCityList(int howManyCitiesWeWant) {
        Set<City> citySet = new HashSet<>();
        while (citySet.size() != howManyCitiesWeWant) {
            citySet.add(randomCity());
        }
        return new ArrayList<>(citySet);
    }

    public static City randomCity() {
        return City.builder().name(randomCityName()).build();
    }

    public static String randomStreetName() {
        int randomStreetNumber = randomInt(0, streetNames.length - 1);
        return streetNames[randomStreetNumber];
    }

    public static String randomHouseNumber() {
        int number = randomInt(1, 99);
        return number + "";
    }

    public static String randomCityIndex() {
        int index = randomInt(100000, 999999);
        return index + "";
    }

    public static String randomCityName() {
        return cityNames[randomInt(0, cityNames.length - 1)];
    }

    public static String randomName() {
        return names[randomInt(0, names.length - 1)];
    }

    public static String randomLastname() {
        return lastNames[randomInt(0, lastNames.length - 1)];
    }

    // Всё, что связано с генерацией случайных чисел
    public static int randomInt(int minIncluded, int maxIncluded) {
        return ThreadLocalRandom.current().nextInt(minIncluded, maxIncluded + 1);
    }

    public static long randomLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

    // Всё что связано с датами и персональными данными
    public static LocalDate randomBirthday() {
        long randomDay = ThreadLocalRandom.current().nextLong(LocalDate.of(1950, Month.JANUARY, 1).toEpochDay(),
                LocalDate.of(2000, Month.DECEMBER, 31).toEpochDay());
        return LocalDate.ofEpochDay(randomDay);
    }

    //Случайная дата окончания срока действия паспорта
    public static LocalDate randomDateOfIssue() {
        long fromDate = LocalDate.now().toEpochDay();
        long toDate = LocalDate.of(2029, 12,31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(fromDate, toDate);
        return LocalDate.ofEpochDay(randomDay);
    }

    public static PersonalData randomPersonalData() {
        return PersonalData.builder().passport(randomPassport())
                .authority("default_authority")
                .placeOfBirth("гор. " + randomCityName())
                .dateOfIssue(randomDateOfIssue())
                .comment("default")
                .status(PersonalDataStatus.CONFIRMED)
                .build();
    }

    public static long randomPassport() {
        return randomLong(1000000000, 9_999_999_999L);
    }

    public static Gender randomGender() {
        int i = Gender.values().length - 1;
        return Gender.values()[randomInt(0, i)];
    }

    // Конфигурация Item
    public static BigDecimal randomItemBasePrice() {
        return new BigDecimal(randomInt(500, 5000));
    }
    public static BigDecimal randomItemPrice(BigDecimal basePrice) {
        return basePrice.multiply(new BigDecimal("1.3"));
    }
    public static int randomCount() {
        return randomInt(0, 20);
    }
    public static double randomRating() {
        double res = ThreadLocalRandom.current().nextDouble(0D, 5D);
        BigDecimal bd = BigDecimal.valueOf(res);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static String randomItemName() {
        return itemNames[randomInt(0, itemNames.length - 1)];
    }
    public static String randomItemDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Исключительный товар: ").append(randomItemName())
                .append(" родом из города ").append(randomCityName())
                .append(". Наивысшее качество среди всех товаров категории \"")
                .append(randomItemCategoryName()).append("\". Спешите купить!");
        return sb.toString();
    }
    public static String randomItemCategoryName() {
        return categoryNames[randomInt(0, categoryNames.length-1)];
    }

    public static Category randomItemCategory() {
        return Category.builder().name(randomItemCategoryName()).build();
    }

    public static Item randomItem() {
        BigDecimal base = randomItemBasePrice();

        return Item.builder()
                .name(randomItemName())
                .basePrice(base)
                .price(randomItemPrice(base))
                .count(randomCount())
                .rating(randomRating())
                .description(randomItemDescription())
                .discount(0)
                .category(randomItemCategory())
                .images(List.of(randomImageFromFolder("items"))).build();
    }

    public static List<Item> randomItemList(int count) {
        List<Item> list = new ArrayList<>();
        while (list.size() != count) {
            list.add(randomItem());
        }
        return list;
    }

    // Shop конфигурация
    public static String randomShopName() {
        return shopNames[randomInt(0, shopNames.length - 1)] + "_" + randomInt(1, 1000);
    }
    public static String randomShopEmail() {
        return "shop_" + randomInt(100000, 1000000) + "@mail.ru";
    }
    public static String randomShopPhone() {
        return "+79" + randomInt(100000000, 999999999);
    }
    public static String randomShopDescription() {
        return "Магазин замечательных вещей";
    }
    public static Shop randomShop() {
        return Shop.builder().name(randomShopName())
                .email(randomShopEmail())
                .phone(randomShopPhone())
                .description(randomShopDescription())
                .rating(randomRating())
                .logo(randomImageFromFolder("logos")).build();
    }

    // Работа с изображениями, возвращаем случайную картинку из указанной папки
    public static Image randomImageFromFolder(String folderName) {
        byte[] picture;
        File folder = new File("src/main/resources/static/image/" + folderName);
//        File folder = new File("classes/static/image/" + folderName); //данный код нужен для упаковки приложения в jar файл.
        File[] files = folder.listFiles();

        if (files == null) {
            throw new RuntimeException("Проблема с указанной папкой " + folderName + " : нет файлов");
        }
        int i = randomInt(0, files.length - 1);
        File file = files[i];
        try {
            picture = Files.readAllBytes(Path.of(files[i].getPath()));
            System.out.printf("Image from folder %s with name %s successfully loaded. \n",
                    folderName.toUpperCase(Locale.ROOT), file.getName().toUpperCase(Locale.ROOT));
        } catch (IOException e) {
            System.out.printf("Ошибка загрузки файла %s из папки %s : ",
                    file.getName().toUpperCase(Locale.ROOT), folderName.toUpperCase(Locale.ROOT));
            System.out.println(e);
            throw new RuntimeException(e);
        }

        return Image.builder().isMain(false).picture(picture).build();
    }
}
