package com.mbe.ada.repository;

import java.util.List;

import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mbe.ada.model.user.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
	
	@SQL("SELECT id, name, email, cpf, birth_date FROM users")
	List<User> findAll();

	
}