<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/style.css">
</head>
<body>
	<img alt="banner" src="/images/gen.jpg">
	<%@include file="nav-bar1.jsp"%>
	<h3>Application Form for Admission into the Industrial Training
		Institute for the session 2021(Phase-1)</h3>
	<h5>
		Note:: Applicant has to register his/her details first by clicking <a
			href="open_application_entry">Student Registration</a> note your
		registration number, then click Apply for ITI
	</h5>
	
	<div id="form-section">
	<form>
	<h6>Student Apply For ITI Form</h6>
		<div class="row">
			<div class="col-3"><label for="SSC HallTicket Number" class="form-label">SSC HallTicket Number</label></div>
			<div class="col-3"><input type="text" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-3"> <label for="Date of Birth*" class="form-label">Date of Birth*</label></div>
			<div class="col-3"><input type="date" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-3"><label for="Registration Id" class="form-label">Registration Id*</label></div>
			<div class="col-3"><input type="text" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-3"><label for="Captcha" class="form-label">Captcha*</label></div>
			<div class="col-3"><input type="text" class="form-control"></div>
		</div>
		<div class="row"> 
				<div class="col-3"></div>
				<div class="col-4">
					<a type="button" class="btn btn-primary" href="login">Home</a>
					<a type="button" class="btn btn-success" href="Editviewpage">Proceed</a>
					<a type="button" class="btn btn-warning">Reset</a>
				</div>
				<div class="col-4"></div>
			</div>
	</form>
	</div>

</body>
</html>