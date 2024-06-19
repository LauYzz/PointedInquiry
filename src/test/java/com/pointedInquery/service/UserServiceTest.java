package com.pointedInquery.service;

import com.pointedInquery.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pointedInquery.PointedInqueryApplication;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PointedInqueryApplication.class)
@Transactional
@Rollback
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void changePasswd() {
        // Test if the password is changed
        String newPasswd = "newPasswd";
        String userId = "1";
        int result = userService.changePasswd(newPasswd, userId);
        assertEquals(1, result);
    }

    @Test
    void changePasswdWithNullNewPasswd() {
        // Test if the password is changed with a null new password
        String newPasswd = null;
        String userId = "1";
        assertThrows(IllegalArgumentException.class, () -> {
            userService.changePasswd(newPasswd, userId);
        });
    }

    @Test
    void changePasswdWithEmptyNewPasswd() {
        // Test if the password is changed with an empty new password
        String newPasswd = "";
        String userId = "1";
        assertThrows(IllegalArgumentException.class, () -> {
            userService.changePasswd(newPasswd, userId);
        });
    }

    @Test
    void changePasswdWithNullUserId() {
        // Test if the password is changed with a null user ID
        String newPasswd = "newPasswd";
        String userId = null;
        assertThrows(IllegalArgumentException.class, () -> {
            userService.changePasswd(newPasswd, userId);
        });
    }

    @Test
    void changePasswdWithEmptyUserId() {
        // Test if the password is changed with an empty user ID
        String newPasswd = "newPasswd";
        String userId = "";
        assertThrows(IllegalArgumentException.class, () -> {
            userService.changePasswd(newPasswd, userId);
        });
    }

    @Test
    void changePasswdWithNonexistentUserId() {
        // Test if the password is changed with a nonexistent user ID
        String newPasswd = "newPasswd";
        String userId = "userId";
        int result = userService.changePasswd(newPasswd, userId);
        assertEquals(0, result);
    }

    @Test
    void isExpert() {
        // Test if the user is an expert
        String userId = "1";
        boolean result = userService.IsExpert(userId);
        assertTrue(result);
    }

    @Test
    void isExpertWithNullUserId() {
        // Test if the user is an expert with a null user ID
        String userId = null;
        assertThrows(IllegalArgumentException.class, () -> {
            userService.IsExpert(userId);
        });
    }

    @Test
    void isExpertWithEmptyUserId() {
        // Test if the user is an expert with an empty user ID
        String userId = "";
        assertThrows(IllegalArgumentException.class, () -> {
            userService.IsExpert(userId);
        });
    }

    @Test
    void isExpertWithNonexistentUserId() {
        // Test if the user is an expert with a nonexistent user ID
        String userId = "userId";
        boolean result = userService.IsExpert(userId);
        assertFalse(result);
    }

    @Test
    void beExpert() {
        // Test if the user becomes an expert
        String userId = "1";
        boolean result = userService.beExpert(userId);
        assertTrue(result);
    }

    @Test
    void beExpertWithNullUserId() {
        // Test if the user becomes an expert with a null user ID
        String userId = null;
        assertThrows(IllegalArgumentException.class, () -> {
            userService.beExpert(userId);
        });
    }

    @Test
    void beExpertWithEmptyUserId() {
        // Test if the user becomes an expert with an empty user ID
        String userId = "";
        assertThrows(IllegalArgumentException.class, () -> {
            userService.beExpert(userId);
        });
    }

    @Test
    void beExpertWithNonexistentUserId() {
        // Test if the user becomes an expert with a nonexistent user ID
        String userId = "userId";
        boolean result = userService.beExpert(userId);
        assertFalse(result);
    }

    @Test
    void checkCollectDir() {
        // Test if the directory is collected
        String userId = "1";
        String expertId = "1";
        int result = userService.checkCollectDir(userId, expertId);
        assertEquals(1, result);
    }

    @Test
    void checkCollectDirWithNullUserId() {
        // Test if the directory is collected with a null user ID
        String userId = null;
        String expertId = "1";
        assertThrows(IllegalArgumentException.class, () -> {
            userService.checkCollectDir(userId, expertId);
        });
    }

    @Test
    void checkCollectDirWithEmptyUserId() {
        // Test if the directory is collected with an empty user ID
        String userId = "";
        String expertId = "1";
        assertThrows(IllegalArgumentException.class, () -> {
            userService.checkCollectDir(userId, expertId);
        });
    }

    @Test
    void checkCollectDirWithNonexistentUserId() {
        // Test if the directory is collected with a nonexistent user ID
        String userId = "userId";
        String expertId = "1";
        int result = userService.checkCollectDir(userId, expertId);
        assertEquals(0, result);
    }

    @Test
    void checkCollectDirWithNullExpertId() {
        // Test if the directory is collected with a null expert ID
        String userId = "1";
        String expertId = null;
        assertThrows(IllegalArgumentException.class, () -> {
            userService.checkCollectDir(userId, expertId);
        });
    }

    @Test
    void checkCollectDirWithEmptyExpertId() {
        // Test if the directory is collected with an empty expert ID
        String userId = "1";
        String expertId = "";
        assertThrows(IllegalArgumentException.class, () -> {
            userService.checkCollectDir(userId, expertId);
        });
    }

    @Test
    void checkCollectDirWithNonexistentExpertId() {
        // Test if the directory is collected with a nonexistent expert ID
        String userId = "1";
        String expertId = "expertId";
        int result = userService.checkCollectDir(userId, expertId);
        assertEquals(0, result);
    }

    @Test
    void checkCollectDirWithNullUserIdAndExpertId() {
        // Test if the directory is collected with a null user ID and a null expert ID
        String userId = null;
        String expertId = null;
        assertThrows(IllegalArgumentException.class, () -> {
            userService.checkCollectDir(userId, expertId);
        });
    }

    @Test
    void checkCollectDirWithEmptyUserIdAndExpertId() {
        // Test if the directory is collected with an empty user ID and an empty expert ID
        String userId = "";
        String expertId = "";
        assertThrows(IllegalArgumentException.class, () -> {
            userService.checkCollectDir(userId, expertId);
        });
    }

    @Test
    void checkCollectDirWithNonexistentUserIdAndExpertId() {
        // Test if the directory is collected with a nonexistent user ID and a nonexistent expert ID
        String userId = "userId";
        String expertId = "expertId";
        int result = userService.checkCollectDir(userId, expertId);
        assertEquals(0, result);
    }

    @Test
    void checkCollectDirWithNullUserIdAndEmptyExpertId() {
        // Test if the directory is collected with a null user ID and an empty expert ID
        String userId = null;
        String expertId = "";
        assertThrows(IllegalArgumentException.class, () -> {
            userService.checkCollectDir(userId, expertId);
        });
    }

    @Test
    void checkCollectDirWithEmptyUserIdAndNullExpertId() {
        // Test if the directory is collected with an empty user ID and a null expert ID
        String userId = "";
        String expertId = null;
        assertThrows(IllegalArgumentException.class, () -> {
            userService.checkCollectDir(userId, expertId);
        });
    }

    @Test
    void checkCollectDirWithNonexistentUserIdAndEmptyExpertId() {
        // Test if the directory is collected with a nonexistent user ID and an empty expert ID
        String userId = "userId";
        String expertId = "";
        int result = userService.checkCollectDir(userId, expertId);
        assertEquals(0, result);
    }

    @Test
    void checkCollectDirWithEmptyUserIdAndNonexistentExpertId() {
        // Test if the directory is collected with an empty user ID and a nonexistent expert ID
        String userId = "";
        String expertId = "expertId";
        int result = userService.checkCollectDir(userId, expertId);
        assertEquals(0, result);
    }

    @Test
    void checkCollectDirWithNullUserIdAndNonexistentExpertId() {
        // Test if the directory is collected with a null user ID and a nonexistent expert ID
        String userId = null;
        String expertId = "expertId";
        assertThrows(IllegalArgumentException.class, () -> {
            userService.checkCollectDir(userId, expertId);
        });
    }

    @Test
    void checkCollectDirWithNonexistentUserIdAndNullExpertId() {
        // Test if the directory is collected with a nonexistent user ID and a null expert ID
        String userId = "userId";
        String expertId = null;
        assertThrows(IllegalArgumentException.class, () -> {
            userService.checkCollectDir(userId, expertId);
        });
    }
}
