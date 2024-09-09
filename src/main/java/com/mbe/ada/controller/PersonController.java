package com.mbe.ada.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbe.ada.model.person.Person;
import com.mbe.ada.model.person.dto.PersonDTO;
import com.mbe.ada.repository.IPersonRepository;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {
	
	@Autowired
	IPersonRepository personRepos;
	
	@GetMapping
	public ResponseEntity<List<PersonDTO>>  index() {		
		
		List<Person> data = personRepos.findAll();
		
		if(data.size() == 0)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		List<PersonDTO> dataDTO = data.stream()
		.map(person -> new PersonDTO(person))
		.toList();
		
		return new ResponseEntity<List<PersonDTO>>(dataDTO, HttpStatus.OK);
	}

}

