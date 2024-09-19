package com.mbe.ada.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mbe.ada.model.person.Person;
import com.mbe.ada.model.person.dto.PersonDTO;
import com.mbe.ada.model.user.User;
import com.mbe.ada.repository.IPersonRepository;
import com.mbe.ada.repository.IUserRepository;
import com.mbe.ada.utils.Utils;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {
	
	@Autowired
	IPersonRepository personRepos;

	@Autowired
	IUserRepository userRepos;
	
	@GetMapping
	public ResponseEntity<List<PersonDTO>>  index() {		
		
		List<Person> data = personRepos.findByIsActiveTrue();
		
		if(data.size() == 0)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		List<PersonDTO> dataDTO = data.stream()
		.map(person -> new PersonDTO(person))
		.toList();
		
		return new ResponseEntity<List<PersonDTO>>(dataDTO, HttpStatus.OK);
	}
	
    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO data) {
       
    	Person personToCreate = new Person(data);

    	// Verify User existence
        if(data.userId() != null && data.userId()> 0) {
        	
        	Optional<User> user = userRepos.findById(data.userId());
        	
        	if(user.isEmpty())
        		return new ResponseEntity("Usuário relacionado à Pessoa não encontrado", HttpStatus.NOT_FOUND);	
        	
        }
        
        Person savedPerson = personRepos.save(personToCreate);
        PersonDTO responseDTO = new PersonDTO(savedPerson);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> get(@PathVariable Long id) {
        
    	Optional<Person> person = personRepos.findById(id);
        
        if (person.isEmpty()) 
        	return new ResponseEntity("Pessoa não encontrada", HttpStatus.NOT_FOUND);
        
        PersonDTO personDTO = new PersonDTO(person.get());
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable Long id, @RequestBody PersonDTO data) {
    	
        Optional<Person> personToUpdt = personRepos.findById(id);
        
        if (personToUpdt.isEmpty())
        	return new ResponseEntity("Pessoa não encontrada", HttpStatus.NOT_FOUND);
        
        Person personToUpdate = personToUpdt.get();
        

    	// Verify User existence
        if(data.userId() != null && data.userId()> 0) {
        	
        	Optional<User> user = userRepos.findById(data.userId());
        	
        	if(user.isEmpty())
        		return new ResponseEntity("Usuário relacionado à Pessoa não encontrado", HttpStatus.NOT_FOUND);	
        	
        }
        
        personToUpdate.updateValues(data);
        Person updatedPerson = personRepos.save(personToUpdate);
        PersonDTO updatedDTO = new PersonDTO(updatedPerson);
        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	
        Optional<Person> personOpt = personRepos.findById(id);
        
        if (personOpt.isEmpty())
        	return new ResponseEntity("Pessoa não encontrada", HttpStatus.NOT_FOUND);

        Person personToDelete = personOpt.get();
        personToDelete.setIsActive(false);
        personRepos.save(personToDelete);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

