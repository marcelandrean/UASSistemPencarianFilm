package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Todo;
import model.User;

public class DatabaseController {
    static DatabaseHandler conn = new DatabaseHandler();

    public User loginUser(String email, String password) {
        User user = null;
        try {
            conn.connect();
            String query = "SELECT * FROM users WHERE email='" + email + "' AND password='" + password + "'";
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getInt("gender"));

                Date sqlBirthday = rs.getDate("birthday");
                LocalDate birthday = sqlBirthday.toLocalDate();
                user.setBirthday(birthday);

                user.setPhoto(rs.getString("photo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return user;
    }

    // INSERT
    public boolean insertNewUser(User user) {
        try {
            conn.connect();
            String query = "INSERT INTO users(name, email, password, gender, birthday, photo) VALUES(?,?,?,?,?,?)";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getGender());

            // Convert LocalDate to java.sql.Date
            LocalDate birthday = user.getBirthday();
            Date sqlBirthday = Date.valueOf(birthday);
            stmt.setDate(5, sqlBirthday);
            stmt.setString(6, user.getPhoto());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }
    }

    // SELECT USER
    public User getUser(int id) {
        User user = null;
        try {
            conn.connect();
            String query = "SELECT * FROM users WHERE id=" + id;
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getInt("gender"));

                Date sqlBirthday = rs.getDate("birthday");
                LocalDate birthday = sqlBirthday.toLocalDate();
                user.setBirthday(birthday);

                user.setPhoto(rs.getString("photo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return user;
    }

    // SELECT * FROM todo WHERE users
    public ArrayList<Todo> getAllTodos(int userId) {
        ArrayList<Todo> todos = new ArrayList<>();
        try {
            conn.connect();
            String query = "SELECT * FROM todo WHERE userId=" + userId;
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Todo todo = new Todo();
                todo.setId(rs.getInt("id"));
                todo.setTitle(rs.getString("title"));
                todo.setNote(rs.getString("note"));
                
                User user = getUser(rs.getInt("userId"));
                todo.setUser(user);
                
                todos.add(todo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return (todos);
    }
}