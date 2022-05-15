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
	<br>
	<div id="form-section">
	<h5>Student Edit Form</h5>
		<form>
			<div class="row">
				<div class="col-3"><label class="form-label">SSC HallTicket Number*</label></div>
				<div class="col-3"><input type="text" class="form-control"></div>
			</div>
			<div class="row">
				<div class="col-3"><label class="form-label">Date of Birth*</label></div>
				<div class="col-3"><input type="date" class="form-control"></div>
			</div>
			<div class="row">
				<div class="col-3"><label class="form-label">Registration Id*</label></div>
				<div class="col-3"><input type="text" class="form-control"></div>
			</div>
			<div class="row">
				<div class="col-3"><label class="form-label">Captcha*</label></div>
				<div class="col-3"><input type="text" class="form-control"></div>
			</div>
			<div class="row">
				<div class="col-3" class="form-label"></div>
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