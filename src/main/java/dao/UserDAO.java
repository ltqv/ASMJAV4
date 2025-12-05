package dao;

import java.util.List;

import entity.User;

public interface UserDAO {
	User findById(String id);

	User findByEmail(String email);

	List<User> findAll();

	void create(User user);

	void update(User user);

	void delete(String id);
}
