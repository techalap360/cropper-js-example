package com.techalap.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techalap.model.Student;
import com.techalap.repositiory.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentService {

	@Autowired private StudentRepository repository;

	@Transactional
	public Student save(Student student, String storeLocation) {
		if(student == null) return null;
		try {
			if(!student.getFile().isEmpty()) {
				student.setPhoto(saveImageAndGetLocation(student.getFile(), storeLocation));
			}

			repository.save(student);
		} catch (Exception e) {
			log.error("Error is : {}", e.getMessage(), e);
			return null;
		}
		return student;
	}

	private String saveImageAndGetLocation(MultipartFile file, String storeLocation) {
		if(file.isEmpty()) return null;

		String fileName = generateImageName(file.getOriginalFilename());
		if(fileName == null) return null;

		try {
			// Create a directory if not exist
			File dir = new File(storeLocation);
			if(!dir.exists()) dir.mkdirs();

			// Upload file into server location
			Files.copy(file.getInputStream(), Paths.get(storeLocation, fileName));
		} catch (IOException e) {
			log.error("Error is : {}", e.getMessage(), e);
			return null;
		}
		return fileName;
	}

	private String generateImageName(String originalFileName) {
		if(originalFileName == null || originalFileName.isEmpty()) return null;

		String extension = null;
		int j = originalFileName.lastIndexOf('.');
		if (j > 0) {
			extension = originalFileName.substring(j + 1);
		}
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String fileName = UUID.randomUUID() +  timeStamp + "." + extension;
		log.debug("File name is now: {}", fileName);
		return fileName;
	}
}
