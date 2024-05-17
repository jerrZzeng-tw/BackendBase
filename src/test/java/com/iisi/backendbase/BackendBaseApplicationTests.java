package com.iisi.backendbase;

import com.iisi.backendbase.entity.User;
import com.iisi.backendbase.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Slf4j
class BackendBaseApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void userCRUD() {
        // Create
        User user1 = User.builder().username("user1").password("123456").email("user@mail.com").build();
        userRepository.save(user1);
        // Read
        Optional<User> result = userRepository.findById(user1.userId());
        assertNotNull(result.orElse(null));
        assertEquals(user1.username(), result.get().username());
        assertEquals(user1.password(), result.get().password());
        log.info("user1: {}", user1);
        // Update
        user1.password("654321");
        userRepository.save(user1);
        result = userRepository.findById(user1.userId());
        assertNotNull(result.orElse(null));
        assertEquals(user1.password(), result.get().password());
        // Delete
        userRepository.delete(user1);
        assertFalse(userRepository.existsById(user1.userId()));
    }
}