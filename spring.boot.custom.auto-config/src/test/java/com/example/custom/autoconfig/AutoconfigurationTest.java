package com.example.custom.autoconfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.custom.autoconfig.Testing.MyUser;
import com.example.custom.autoconfig.Testing.MyUserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AutoconfigurationApplication.class)
@EnableJpaRepositories(basePackages = { "com.example.custom.autoconfig" })
public class AutoconfigurationTest {
 
    @Autowired
    private MyUserRepository userRepository;
 
    @Test
    public void whenSaveUser_thenOk() {
        MyUser user = new MyUser("user@email.com");
        userRepository.save(user);
    }
}
