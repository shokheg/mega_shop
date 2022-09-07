//package com.amr.project.service.testdata;
//
//import com.amr.project.model.entity.*;
//import com.amr.project.model.enums.Roles;
//
//import javax.persistence.EntityManager;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import static com.amr.project.service.testdata.RandomValues.*;
//
///***
// * Класс предназначен для автоматизации установления связей между объектами
// */
//public class SetupObjects {
//
//
//    // Генерируем список городов, взаимно связываем города со страной.
//    // Генерируем список наборов адресов для каждого города, взаимно связываем адреса с городами.
//    // Собираем готовые адреса в единый список и возвращаем для дальнейшего связывания с пользователями.
//    public static List<Address> setupCountry(Country country, int howManyCities, int howManyAddress) {
//        List<Address> allAddresses = new ArrayList<>();
//
//        List<List<Address>> addressesList = new ArrayList<>();
//        List<City> cities = randomCityList(howManyCities);
//        country.setCities(cities);
//        cities.forEach(city -> city.setCountry(country));
//
//        for (int i = 0; i < howManyCities; i++) {
//            final int j = i;
//            addressesList.add(randomAddressList(howManyAddress));
//            cities.get(i).setAddresses(addressesList.get(i));
//            addressesList.get(i).forEach(address -> address.setCity(cities.get(j)));
//        }
//        addressesList.forEach(allAddresses::addAll);
//        return allAddresses;
//    }
//
//    // Связываем пользователей и адреса друг с другом.
//    // Если пользователей больше, чем адресов, то присваиваем адреса по второму кругу
//    public static List<User> setupAddressesAndUsers(List<Address> addresses, List<User> users) {
//        int count = 0;
//        for (User user : users) {
//            if (count == addresses.size()) count = 0;
//            user.setAddress(addresses.get(count));
//
//            List<User> listUsersAtAddress = addresses.get(count).getUsers();
//            if (listUsersAtAddress == null) {
//                listUsersAtAddress = List.of(user);
//            } else {
//                listUsersAtAddress.add(user);
//            }
//            addresses.get(count).setUsers(listUsersAtAddress);
//            count++;
//        }
//        return users;
//    }
//
//    public static User setupUser(String username, Roles role) {
//        String folder = role == Roles.ADMIN ? "admins" : "users";
//        User user = new User();
//        user.setUsername(username + randomInt(1800, 2000));
//        user.setPassword("100");
//        user.setFirstName(randomName());
//        user.setLastName(randomLastname());
//        user.setEmail(user.getUsername() + "@mail.ru");
//        user.setPhone(randomShopPhone());
//        user.setAge(randomInt(20, 80));
//        user.setGender(randomGender());
//        user.setBirthday(randomBirthday());
//        user.setActivate(true);
//        user.setActivationCode("1234");
//        user.setImages(List.of(randomImageFromFolder(folder)));
//        user.setRole(role);
//        user.setUsing2FA(false);
//        user.setSecret(user.getUsername() + "_" + randomInt(1000, 9999));
//        user.setPersonalData(randomPersonalData());
//
//        return user;
//    }
//
//    public static void setupShop(User user, List<Address> addresses, int howManyItems, EntityManager em) {
//        List<Item> items = randomItemList(howManyItems);
//        Category category = randomItemCategory();
//        items.forEach(item -> item.setCategory(category));
//        category.setItems(items);
//        em.persist(category);
//
//        Shop shop = randomShop();
//        shop.setItems(items);
//        items.forEach(item ->item.setShop(shop));
//        shop.setUser(user);
//        user.setShops(Set.of(shop));
//        shop.setAddress(randomAddressFromList(addresses));
//        em.persist(shop);
//        items.forEach(item -> item.setUser(user));
//        items.forEach(em::persist);
//    }
//}
