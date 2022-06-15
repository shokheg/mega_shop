package com.amr.project.service.testdata;

import com.amr.project.model.entity.Address;
import com.amr.project.model.entity.Country;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Roles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static com.amr.project.service.testdata.SetupObjects.*;

/***
 * Класс для загрузки тестовых данных в бд.
 * Одна страна, 5 городов, по 5 адресов в каждом
 * 5 юзеров, два админа, 2 магазина, 5 итемов в каждом
 ***/
@Component
public class TestDataLoadService implements CommandLineRunner {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        testDataLoad();
    }

    public void testDataLoad() {

        Country russia = Country.builder().name("Россия").build();
        List<Address> addresses = setupCountry(russia, 5, 5);
        em.persist(russia);

        //Список пользователей, генерируем по имени пользователя и роли
        List<User> users = new ArrayList<>();
        users.add(setupUser("gogol", Roles.USER));
        users.add(setupUser("gertcen", Roles.USER));
        users.add(setupUser("usavich", Roles.USER));
        users.add(setupUser("petrovich", Roles.USER));
        users.add(setupUser("monroe", Roles.USER));

        users.add(setupUser("stranger", Roles.ADMIN));
        users.add(setupUser("pushkin", Roles.ADMIN));

        // Связываем юзеров и магазины, задаём юзера, список адресов и em,
        // всё само генерируется и вяжется.
        setupShop(users.get(0), addresses, 5, em);
        setupShop(users.get(1), addresses, 5, em);

        //Добавляем всех юзеров в бд
        setupAddressesAndUsers(addresses, users);
        users.forEach(user -> em.persist(user));

    }
}
