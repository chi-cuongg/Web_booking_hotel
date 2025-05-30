package org.codewithcuong.hamora.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.codewithcuong.hamora.model.User;

import java.util.List;

@Repository
public class UserRepo {
    @Autowired
    private JdbcTemplate jdbc;

    public UserRepo(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void saveUser(User user) {
        String sql = "INSERT INTO Users (full_name,email, password_hash) VALUES (?, ?, ?)";
        jdbc.update(sql, user.getFullname(), user.getEmail(), user.getPassword());
    }
    public void saveUserFromGoogle(User user) {
        String sql = "INSERT INTO Users (full_name, email) VALUES (?, ?)";
        jdbc.update(sql, user.getFullname(), user.getEmail());
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try {
            return jdbc.queryForObject(sql, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getString("user_id"));
                user.setFullname(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password_hash")); // map đúng
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                user.setActive(rs.getBoolean("is_active"));
                return user;
            }, email);
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> getAllUser() {
        String sql = "SELECT * FROM Users";
        return jdbc.query(sql, (rs, rowNum) -> new User(
                rs.getString("email"),
                rs.getString("password_hash")
        ));
    }

    // Lưu token vào bảng PasswordResetTokens
    public void savePasswordResetToken(String userId, String token) {
        String sql = "INSERT INTO Tokens (user_id, token, expiry_date) VALUES (?, ?, DATEADD(MINUTE, 30, GETDATE()))";
        jdbc.update(sql, userId, token);
    }

    // Tìm user thông qua token còn hiệu lực
    public User findUserByToken(String token) {
        String sql = """
            SELECT u.* FROM Users u
            JOIN Tokens t ON u.user_id = t.user_id
            WHERE t.token = ? AND t.expiry_date > GETDATE()
        """;
        try {
            return jdbc.queryForObject(sql, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getString("user_id"));
                user.setFullname(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password_hash"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                user.setActive(rs.getBoolean("is_active"));
                return user;
            }, token);
        } catch (Exception e) {
            return null;
        }
    }

    // Xóa token đã dùng hoặc hết hạn
    public void deleteToken(String token) {
        String sql = "DELETE FROM Tokens WHERE token = ?";
        jdbc.update(sql, token);
    }

    // Cập nhật mật khẩu mới
    public void updatePassword(String userId, String hashedPassword) {
        String sql = "UPDATE Users SET password_hash = ? WHERE user_id = ?";
        jdbc.update(sql, hashedPassword, userId);
    }
}


