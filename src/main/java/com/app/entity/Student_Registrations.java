package com.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class Student_Registrations {

	@Id @GeneratedValue(strategy = GenerationType.AUTO) private int sno;
	private String sscHallTicketNo;
	private String boardName;
	private String sscYearOfPass;
	private String sscResultType;
//	private String sscMmonth;
//	private String name;
//	private String phoneNumber;
//	private String dateOfBirth;
//	private String adhaarNumber;
//	private String gender;
//	private String category;
//	private String fatherName;
//	private String phc;
//	private String motherName;
//	private String exServiceMan;
//	private String qualification;
//	private String address;
//	private String localNonLocal;
//	private String pincode;
//	private String economicallyWS;
//	private String noOfLanguages;
//	private String firstLangMarks;
//	private String secondLangMarks;
//	private String engLangMarks;
//	private String mathsLangMarks;
//	private String scienceLangMarks;
//	private String socialLangMarks;
//	private String totalMarks;
//	private String photo;
	public Student_Registrations(String sscHallTicketNo, String boardName, String sscYearOfPass, String sscResultType) {
		super();
		this.sscHallTicketNo = sscHallTicketNo;
		this.boardName = boardName;
		this.sscYearOfPass = sscYearOfPass;
		this.sscResultType = sscResultType;
	}
	public Student_Registrations() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getSscHallTicketNo() {
		return sscHallTicketNo;
	}
	public void setSscHallTicketNo(String sscHallTicketNo) {
		this.sscHallTicketNo = sscHallTicketNo;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getSscYearOfPass() {
		return sscYearOfPass;
	}
	public void setSscYearOfPass(String sscYearOfPass) {
		this.sscYearOfPass = sscYearOfPass;
	}
	public String getSscResultType() {
		return sscResultType;
	}
	public void setSscResultType(String sscResultType) {
		this.sscResultType = sscResultType;
	}
	@Override
	public String toString() {
		return "Student_Registrations [sscHallTicketNo=" + sscHallTicketNo + ", boardName=" + boardName
				+ ", sscYearOfPass=" + sscYearOfPass + ", sscResultType=" + sscResultType + "]";
	}
	
	
	
}
