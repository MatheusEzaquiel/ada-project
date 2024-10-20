package com.mbe.ada.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mbe.ada.model.person.Person;
import com.mbe.ada.model.photo.Photo;
import com.mbe.ada.repository.IPersonRepository;
import com.mbe.ada.repository.IPhotoRepository;

import jakarta.transaction.Transactional;

@Service
public class PhotoService {

	@Autowired
	IPhotoRepository photoRepos;
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	IPersonRepository personRepos;
	
	@Transactional
	public String getImageDataByPersonId(@PathVariable Long personId) {
		
		Optional<Photo> photo = photoRepos.findByPersonId(personId);
		
		if(photo.isPresent()) {
			return photo.get().getImageData();
		}
		
		return null;
		
	}
	
	public Photo create(MultipartFile file, Long personId, Boolean isDefault) {

    	Optional<Person> person = personRepos.findById(personId);
        
        if (person.isEmpty()) 
        	System.out.println("Pessoa não encontrada");
    	

        String newFileName;
        
		try {
			
			newFileName = imageService.saveImage(file.getBytes(), file.getOriginalFilename());
			  
	        //file -> byte[] -> base64
	        String photoBase64 = Base64.getEncoder().encodeToString(file.getBytes());
	        

	        Photo photoToCreate = new Photo(newFileName, photoBase64, person.get(), isDefault);
	        
	        if(!isDefault)
	        	photoToCreate.setImageData(null);
	        
	        return photoRepos.save(photoToCreate);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
      
        return null;
        
  
	}

}
