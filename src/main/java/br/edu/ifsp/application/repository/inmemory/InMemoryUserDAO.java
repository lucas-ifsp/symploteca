package br.edu.ifsp.application.repository.inmemory;

import br.edu.ifsp.domain.entities.user.User;
import br.edu.ifsp.domain.usecases.user.UserDAO;

import java.util.*;

public class InMemoryUserDAO implements UserDAO {

    private static final Map<String, User> db = new LinkedHashMap<>();

    @Override
    public String create(User user) {
        db.put(user.getInstitutionalId(), user);
        return user.getInstitutionalId();
    }

    @Override
    public Optional<User> findOne(String key) {
        if (db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public Optional<User> findOneByEmail(String email) {
        return db.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(User user) {
        String id = user.getInstitutionalId();
        if (db.containsKey(id)) {
            db.replace(id, user);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(String key) {
        if (db.containsKey(key)) {
            db.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        return deleteByKey(user.getInstitutionalId());
    }
}
