package com.mbe.ada.model.photo.dto;


import com.mbe.ada.model.photo.Photo;

public record PhotoDTO(
		Long id,
	    String name,
	    byte[] imageData,
	    boolean isDefault,
	    Long personId) {
	

	    // Construtor que aceita uma entidade Photo
	    public PhotoDTO(Photo photo) {
	        this(
	            photo.getId(),
	            photo.getName(),
	            photo.getImageData(),
	            photo.isDefault(),
	            photo.getPersonId()
	        );
	    }

}
