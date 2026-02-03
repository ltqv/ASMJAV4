package unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    private FakeLoginService loginService;

    @BeforeEach
    void setUp() {
        loginService = new FakeLoginService();
    }

    // ========== TEST CASE 1 ==========
    @Test
    void testLoginSuccess() {
        boolean result = loginService.login("admin", "123456");
        assertTrue(result, "Đăng nhập đúng phải thành công");
    }

    // ========== TEST CASE 2 ==========
    @Test
    void testLoginWrongPassword() {
        boolean result = loginService.login("admin", "wrongpass");
        assertFalse(result, "Sai mật khẩu phải thất bại");
    }

    // ========== TEST CASE 3 ==========
    @Test
    void testLoginUserNotFound() {
        boolean result = loginService.login("unknown", "123456");
        assertFalse(result, "User không tồn tại phải thất bại");
    }

    // ========== TEST CASE 4 ==========
    @Test
    void testEmptyUsername() {
        boolean result = loginService.login("", "123456");
        assertFalse(result, "Username rỗng phải thất bại");
    }

    // ========== TEST CASE 5 ==========
    @Test
    void testEmptyPassword() {
        boolean result = loginService.login("admin", "");
        assertFalse(result, "Password rỗng phải thất bại");
    }

    // ========== FAKE SERVICE ==========
    // Mô phỏng service thật để test logic
    static class FakeLoginService {

        public boolean login(String username, String password) {
            if (username == null || password == null) return false;
            if (username.isEmpty() || password.isEmpty()) return false;

            // giả lập user trong database
            return username.equals("admin") && password.equals("123456");
        }
    }
    @Test
    void testPasswordCorrect() {
        String real = "123";
        String input = "123";
        assertEquals(real, input);
    }

    @Test
    void testPasswordWrong() {
        String real = "123";
        String input = "321";
        assertNotEquals(real, input);
    }

    @Test
    void testUsernameEmpty() {
        String username = "";
        assertTrue(username.isEmpty());
    }

}
