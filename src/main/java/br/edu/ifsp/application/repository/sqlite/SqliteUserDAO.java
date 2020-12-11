package br.edu.ifsp.application.repository.sqlite;

import br.edu.ifsp.domain.entities.user.Faculty;
import br.edu.ifsp.domain.entities.user.Student;
import br.edu.ifsp.domain.entities.user.User;
import br.edu.ifsp.domain.usecases.user.UserDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteUserDAO implements UserDAO {

    @Override
    public String create(User user) {
        String sql = "INSERT INTO User(id, name, email, phone, course, department) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, user.getInstitutionalId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());

            if(user instanceof Student)
                stmt.setString(5, ((Student)user).getCourse());
            else
                stmt.setNull(5, Types.VARCHAR);

            if(user instanceof Faculty)
                stmt.setString(6, ((Faculty)user).getDepartment());
            else
                stmt.setNull(6, Types.VARCHAR);

            stmt.execute();
            return user.getInstitutionalId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<User> findOne(String key) {
        return findOneByAttribute("id", key);
    }

    @Override
    public Optional<User> findOneByEmail(String email) {
        return findOneByAttribute("email", email);
    }

    private Optional<User> findOneByAttribute(String attribute, String value) {
        String sql = "SELECT * FROM User WHERE "+ attribute +"  = ?";
        User user = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, value);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                user = resultSetToEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    private User resultSetToEntity(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        String course = rs.getString("course");
        String department = rs.getString("department");

        if(course != null){
            return new Student(id, name, email, phone, course);
        }else{
            return new Faculty(id, name, email, phone, department);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                User user = resultSetToEntity(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean update(User user) {
        String sql = "UPDATE User SET name = ?, email = ?, phone = ?, course = ?, department = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());

            if(user instanceof Student)
                stmt.setString(4, ((Student)user).getCourse());
            else
                stmt.setNull(4, Types.VARCHAR);

            if(user instanceof Faculty)
                stmt.setString(5, ((Faculty)user).getDepartment());
            else
                stmt.setNull(5, Types.VARCHAR);

            stmt.setString(6, user.getInstitutionalId());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(String key) {
        String sql = "DELETE FROM User WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, key);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        if (user == null || user.getInstitutionalId() == null)
            throw new IllegalArgumentException("User and user ID must not be null.");
        return deleteByKey(user.getInstitutionalId());
    }
}
