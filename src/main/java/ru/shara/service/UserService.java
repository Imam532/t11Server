package ru.shara.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.shara.model.User;
import ru.shara.repository.UserRepository;

import java.util.List;

@Service
@Transactional
@EnableTransactionManagement(proxyTargetClass = true)
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repo;

    public void save(User user) {
        repo.save(user);
    }

    public void saveIfNotExists(User user) {
        if (repo.getUserByName(user.getName()) == null) {
            repo.save(user);
        }
    }

    public List<User> listAll() {
        return repo.findAll();
    }

    public User get(Long id) {
        return repo.findById(id).get();
    }

    public User getUserByName(String name) {
        return repo.getUserByName(name);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<User> search(String keyword) {
        return repo.search(keyword);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        User user = getUserByName(name);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + name);
        }

        return user;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
