package com.app.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.entity.Student_Registrations;
import com.app.repo.Student_Registrations_repo;
import com.app.service.Student_Registrations_service;

@Service
public class Student_Registrations_service_impl implements Student_Registrations_service{
	
	@Autowired Student_Registrations_repo repo;

	@Override
	public Student_Registrations saveRegistration(Student_Registrations registrations) {
		return repo.save(registrations);
	}

	@Override
	public Student_Registrations updateRegistration(Student_Registrations registrations) {
		return repo.save(registrations);
	}

	@Override
	public void deleteBySscHallTicketNo(Long sscHallTicketNo) {
		repo.deleteById(sscHallTicketNo);
	}

	@Override
	public Optional<Student_Registrations> getBySscHallTicketNo(Long sscHallTicketNo) {
		return repo.findById(sscHallTicketNo);
	}

}
