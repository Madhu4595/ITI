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
	<%@include file="nav-bar1.jsp"%><br>
	<h3>Application Form for Admission into the Industrial Training Institute for the session 2022 for Phase -II</h3>
	
	<div id="form-section">
	<h5>Forgot Registration Form</h5>
		<div class="row">
			<div class="col-3"><label for="" class="form-label">SSC HallTicket Number*</label></div>
			<div class="col-3"><input type="text" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-3"><label for="" class="form-label">Date of Birth*</label></div>
			<div class="col-3"><input type="date" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-3"><label for="" class="form-label">Captcha*</label></div>
			<div class="col-3"><input type="text" class="form-control"></div>
		</div>
		<div class="row"> 
				<div class="col-3"></div>
				<div class="col-3"><input type="text" class="form-control"></div>
			</div>
			<div class="row"> 
				<div class="col-3"></div>
				<div class="col-4">
					<a type="button" class="btn btn-primary" href="login">Home</a>
					<a type="button" class="btn btn-success" href="forgotregidsuccess">Proceed</a>
					<a type="button" class="btn btn-warning">Reset</a>
				</div>
				<div class="col-4"></div>
			</div>
	</div>
</body>
</html>