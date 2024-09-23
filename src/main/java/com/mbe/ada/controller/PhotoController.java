package com.mbe.ada.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mbe.ada.model.person.Person;
import com.mbe.ada.model.photo.Photo;
import com.mbe.ada.model.photo.dto.PhotoDTO;
import com.mbe.ada.repository.IPersonRepository;
import com.mbe.ada.repository.IPhotoRepository;

@RestController
@RequestMapping(value = "/photos")
public class PhotoController {

    @Autowired
    IPhotoRepository photoRepository;
    
    @Autowired
	IPersonRepository personRepos;


    @GetMapping
    public ResponseEntity<List<PhotoDTO>> index() {
    	
        List<Photo> data = photoRepository.findByActiveTrue();

        if (data.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        List<PhotoDTO> dataDTO = data.stream()
                .map(photo -> new PhotoDTO(photo))
                .toList();

        return new ResponseEntity<>(dataDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity create(
    		@RequestParam("personId") Long personId,
    		@RequestParam("isDefault") boolean isDefault,
    		@RequestPart("imageData") MultipartFile file) throws IOException {
    	
    	Optional<Person> person = personRepos.findById(personId);
        
        if (person.isEmpty()) 
        	return new ResponseEntity("Pessoa não encontrada", HttpStatus.NOT_FOUND);
    	
        Photo photoToCreate = new Photo(file, personId, isDefault);
        
        Photo savedPhoto = photoRepository.save(photoToCreate);
        PhotoDTO responseDTO = new PhotoDTO(savedPhoto);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
    	
        Optional<Photo> photo = photoRepository.findById(id);

        if (photo.isEmpty())
            return new ResponseEntity("Foto não encontrada", HttpStatus.NOT_FOUND);

        PhotoDTO photoDTO = new PhotoDTO(photo.get());
        return new ResponseEntity<>(photoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	
        Optional<Photo> photoOpt = photoRepository.findById(id);

        if (photoOpt.isEmpty())
            return new ResponseEntity("Foto não encontrada", HttpStatus.NOT_FOUND);

        Photo photoToDelete = photoOpt.get();
        photoToDelete.setActive(false);
        photoRepository.save(photoToDelete);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
