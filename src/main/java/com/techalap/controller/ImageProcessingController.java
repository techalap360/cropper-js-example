package com.techalap.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techalap.model.Student;
import com.techalap.service.StudentService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Zubyaer Ahamed
 * @since 15-Jun-2020
 *
 */
@Slf4j
@Controller
@RequestMapping("/")
public class ImageProcessingController {

	private static final String FILE_STORE_DIR = "D://";

	@Autowired private StudentService service;

	@GetMapping
	public String loadPage() {
		log.debug("Loading home page");
		return "index";
	}

	@PostMapping
	public @ResponseBody Map<String, Object> savePhoto(Student student, HttpServletRequest request) {
		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");

		try {
			service.save(student, FILE_STORE_DIR);
		} catch (Exception e) {
			log.error("Error is : {}", e.getMessage(), e);
			response.put("status", "error");
		}

		return response;
	}
}
