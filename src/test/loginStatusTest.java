package test;

import main.item.loginStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Note: loginStatus.java login() method should turn the || in if condition to &&
class loginStatusTest {

    private loginStatus loginInstance;

    @BeforeEach
    void setUp() {
        loginInstance = loginStatus.getInstance();
        // Make sure to logout before each test to start fresh
        loginInstance.logout();
    }

    @AfterEach
    void tearDown() {
        // Clear state after each test
        loginInstance.logout();
    }

    // Singleton Pattern Test
    @Test
    void testSingletonPattern() {
        // ensure that multiple calls to getInstance return the same object
        loginStatus instance1 = loginStatus.getInstance();
        loginStatus instance2 = loginStatus.getInstance();

        assertSame(instance1, instance2);
    }

    // Initial State Test
    @Test
    void testInitialLoginStatus() {
        // initail login status should be false
        assertFalse(loginInstance.getLoginStatus());
    }

    // Login Method Tests
    @Test
    void testLogin_Success() {
        // testing successful login
        loginInstance.login("admin", "admin");

        assertTrue(loginInstance.getLoginStatus());
    }

    @Test
    void testLogin_Failure_WrongUsername() {
        // testing wrong username login failure
        loginInstance.login("wronguser", "admin");

        assertFalse(loginInstance.getLoginStatus());
    }

    @Test
    void testLogin_Failure_WrongPassword() {
        // testing wrong password login failure
        loginInstance.login("admin", "wrongpass");

        assertFalse(loginInstance.getLoginStatus());
    }

    @Test
    void testLogin_Failure_BothWrong() {
        // testing both username and password wrong login failure
        loginInstance.login("wronguser", "wrongpass");

        assertFalse(loginInstance.getLoginStatus());
    }

    @Test
    void testLogin_EmptyCredentials() {
        // testing empty string credentials login
        loginInstance.login("", "");

        assertFalse(loginInstance.getLoginStatus());
    }

    // Note: Missing null handling condition in loginStatus.java
    @Test
    void testLogin_NullCredentials() {
        // testing null credentials login
        loginInstance.login(null, null);

        assertFalse(loginInstance.getLoginStatus());
    }

    @Test
    void testLogout_WhenLoggedIn() {
        // testing logout after a successful login
        loginInstance.login("admin", "admin");
        assertTrue(loginInstance.getLoginStatus());

        loginInstance.logout();
        assertFalse(loginInstance.getLoginStatus());
    }

    @Test
    void testLogout_WhenAlreadyLoggedOut() {
        // testing logout when already logged out
        assertFalse(loginInstance.getLoginStatus());

        loginInstance.logout();
        assertFalse(loginInstance.getLoginStatus());
    }

    // test edge cases
    @Test
    void testCaseSensitivity() {
        // testing case sensitivity of username and password
        loginInstance.login("ADMIN", "admin");
        assertFalse(loginInstance.getLoginStatus());

        loginInstance.login("Admin", "admin");
        assertFalse(loginInstance.getLoginStatus());
    }

    @Test
    void testWhitespaceInCredentials() {
        // testing whitespace handling in usrname and password
        loginInstance.login(" admin ", " admin ");
        assertFalse(loginInstance.getLoginStatus());

        loginInstance.login("admin", "admin ");
        assertFalse(loginInstance.getLoginStatus());
    }

    // test status persistence across instances
    @Test
    void testStatusPersistence() {

        loginInstance.login("admin", "admin");

        // get a new instance
        loginStatus newInstance = loginStatus.getInstance();

        // status should be the same
        assertTrue(newInstance.getLoginStatus());
        assertEquals(loginInstance.getLoginStatus(), newInstance.getLoginStatus());
    }

}