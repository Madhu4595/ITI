package com.app.service;

import java.util.Optional;

import com.app.entity.Student_Registrations;

public interface Student_Registrations_service {
	
	public Student_Registrations saveRegistration(Student_Registrations registrations); 
	public Student_Registrations updateRegistration(Student_Registrations registrations);
	public void deleteBySscHallTicketNo(Long sscHallTicketNo);
	public Optional<Student_Registrations> getBySscHallTicketNo(Long sscHallTicketNo);

}
