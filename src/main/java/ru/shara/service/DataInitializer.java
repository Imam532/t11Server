package ru.shara.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.shara.model.User;
import ru.shara.model.UserRoleEnum;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

public class DataInitializer {

    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void run() {
        Set<UserRoleEnum> user = new HashSet<>();
        user.add(UserRoleEnum.USER);
        Set<UserRoleEnum> admin = new HashSet<>();
        admin.add(UserRoleEnum.USER);
        admin.add(UserRoleEnum.ADMIN);

//        userService.saveIfNotExists(new User("user", encoder.encode("user"), user));
//        userService.saveIfNotExists(new User("admin", encoder.encode("admin"), admin));
    }
}
