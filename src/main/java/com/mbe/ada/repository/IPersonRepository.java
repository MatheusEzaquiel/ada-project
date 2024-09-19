package com.mbe.ada.repository;

import java.util.List;

import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mbe.ada.model.person.Person;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Long>{
	
	List<Person> findByIsActiveTrue();

	
}