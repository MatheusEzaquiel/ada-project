package com.mbe.ada.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbe.ada.model.photo.Photo;


public interface IPhotoRepository extends JpaRepository<Photo, Long> {
	List<Photo> findByActiveTrue();
}
