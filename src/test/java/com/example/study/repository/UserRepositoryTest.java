package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        User user = new User();

        user.setAccount("User03");
        user.setEmail("a@naver.com");
        user.setPhoneNumber("010-1234-1234");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("User01");

        User newUser = userRepository.save(user);
        System.out.println(newUser);
    }

    @Test
    @Transactional
    public void read() {
        Optional<User> user = userRepository.findById(4L);

        user.ifPresent(selectUser -> {

            selectUser.getOrderDetailList().forEach(detail -> {
                Item item = detail.getItem();
                System.out.println(detail.getItem());

            });
        });

    }

    @Test
    @Transactional
    public void update() {
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(selectUser -> {
            selectUser.setAccount("pppp");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update method()");

            userRepository.save(selectUser);
        });
    }

    @Test
    // 메소드들은 실행이 되지만 데이터베이스에 적용되진 않는다.
    @Transactional
    public void delete() {
        Optional<User> user = userRepository.findById(3L);

        Assert.assertTrue(user.isPresent()); // true

        user.ifPresent(selectUser -> {
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(3L);

        Assert.assertFalse(deleteUser.isPresent()); // false

    }
}
