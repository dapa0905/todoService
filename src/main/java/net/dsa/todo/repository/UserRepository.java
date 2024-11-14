package net.dsa.todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.dsa.todo.model.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, String> {
	


}
