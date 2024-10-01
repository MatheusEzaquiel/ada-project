package com.mbe.ada.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ImageService {
	
	private Path folderPath = Path.of("/opt/images");
	
	public String saveImage(byte[] fileData, String filename) throws IOException {
		
		String extension = getExtensionFile(filename);
		
		String fileNameUUID = UUID.randomUUID().toString(); 
		String newFileName = fileNameUUID + "." + extension;
		File file = new File(folderPath + File.separator + newFileName);
		
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(fileData);
		}
		
		return newFileName;
		
	}
	
	public byte[] converFileToBytes(File file) throws IOException {
		
		byte[] fileData = new byte[(int) file.length()];
		
		try(FileInputStream fis = new FileInputStream(file)) {
			fis.read(fileData);
		}
		return fileData;
		
	}
	
	public String getExtensionFile(String filename) {
		String[] part = filename.split("\\.");
		int lastItem = part.length - 1;
		return part[lastItem];
	}
	
	public String getNameFile(String filename) {
		String[] part = filename.split("\\.");
		return part[0];
	}
	
}
