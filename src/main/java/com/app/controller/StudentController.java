package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.entity.Student_Registrations;
import com.app.repo.MyRepository;
import com.app.service.Student_Registrations_service;

@Controller
public class StudentController {
	
	
	@Autowired private MyRepository myRepository;
	@Autowired private Student_Registrations_service service;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	 
	@RequestMapping("/open_application_entry")
	public String show1(@ModelAttribute("stdregs1") Student_Registrations stdregs1, Model model) {
		
		List<Object> boardNames = myRepository.getBoards();
		System.out.println("Board names- >"+boardNames);
		
		model.addAttribute("boardNames", boardNames);
		
		Student_Registrations student_Registrations= service.saveRegistration(stdregs1);
		System.out.println("Student SSCHallTicketNumber-> "+student_Registrations.getSscHallTicketNo());
		
		return "open_application_entry";
	}
	@RequestMapping("/open_application-step1")
	public String show6() {
		return "open_application-step1";
	}
	
	@RequestMapping("/nav-bar1")
	public String show2() {
		return "nav-bar1";
	}
	@RequestMapping("/open_editview_form")
	public String show3() {
		return "open_editview_form";
	}
	@RequestMapping("/open_edit_form")
	public String show4() {
		return "open_edit_form";
	}
	@RequestMapping("/forgotregid")
	public String show5() {
		return "forgotregid";
	}
	
	@RequestMapping("/Editviewpage")
	public String show7() {
		return "Editviewpage";
	}
	@RequestMapping("/Editviewpagesuccess")
	public String show8() {
		return "Editviewpagesuccess";
	}
	@RequestMapping("/forgotregidsuccess")
	public String show9() {
		return "forgotregidsuccess";
	}

}
