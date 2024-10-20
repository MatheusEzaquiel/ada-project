package com.mbe.ada.model.person.dto;

import java.time.LocalDate;

import com.mbe.ada.model.person.Person;

public record DetailPersonDTO(
		Long id,
		String name,
		String lastname,
		String email,
		String cpf,
		LocalDate birthDate,
		Boolean isTeacher,
		Long userId,
		String photo
		) {
	
	public DetailPersonDTO(Person p, String photo) {
		this(p.getId(), p.getName(), p.getLastname(), p.getEmail(), p.getCpf(), p.getBirthDate(), p.getIsTeacher(), p.getUserId(), photo);
	}

}