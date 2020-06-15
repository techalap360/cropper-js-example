package com.techalap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@Entity
@Table(name = "ST")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "studentId", unique = true, nullable = false)
	private Long studentId;
	@Column(name = "studentName")
	private String studentName;
	@Column(name = "photo")
	private String photo;
	@Transient
	MultipartFile file;
}
