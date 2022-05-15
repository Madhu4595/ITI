<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/bootstrap-5.1.3-dist/css/bootstrap.min.css">
<script type="text/javascript" src="/bootstrap-5.1.3-dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<img alt="banner" src="/images/gen.jpg"/>
	<%@include file="nav-bar1.jsp"%><br>

	<div class="row">
		<div class="col-4">
			<div class="card bg-warning">
				<div class="card-header">Student Area</div>
				<div class="card-body">
					<div><a href="open_application_entry">1. Student Registration (Registration Only)</a></div>
					<div><a href="open_editview_form">2. Application to apply for ITI</a></div>
					If Student is not Registered both Steps 1 and 2 are Mandatory for Applying to ITI
					<div><a href="open_edit_form">3. Edit Registration</a></div>
					<div><a href="forgotregid">4. Forgot Registration ID</a></div>
				</div>
			</div>
		</div>
		<div class="col-4"><img alt="ap image" src="/images/ap.jpg"></div>
		<div class="col-4">
			<div class="card bg-warning">
				<div class="card-header">For Officials</div>
				<div class="card-body">
					<h3>Login Here</h3>
					<form>
						User Name: <input type="text"/><br>
						Password:  <input type="password"/><br>
						Captcha:   <input type="text"/><br>
						<input type="submit" value="Login"/>
					</form>
				</div>
			</div>
		</div>
	</div>


</body>
</html>