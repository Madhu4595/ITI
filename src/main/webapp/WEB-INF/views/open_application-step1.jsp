<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/bootstrap-5.1.3-dist/css/bootstrap.min.css">
<script type="text/javascript" src="/bootstrap-5.1.3-dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="/css/style.css">
</head>
<body>
<img alt="banner" src="/images/gen.jpg">
<%@include file="nav-bar1.jsp"%><br>
	<h2>Registration Form for Admission into the industrial training Institute for the session 2021(Phase-1)</h2>
	
	
	<div id="form-section">
	<h4>Attention: Enter data carefully, If any information found wrong in Marks/GPA, Caste, etc. , the authority has the right to disqualify for admissions.</h4>
		<div id="inner-div">
		<br>
		<div class="row">
			<div class="col-2"><label for="" class="form-label">Hall Ticket Number* :</label></div>
			<div class="col-3"><input type="text" class="form-control"/></div>
			<div class="col-2"><label for="" class="form-label">Board :</label></div>
			<div class="col-3"><input type="text" class="form-control"/></div>
		</div>
		<div class="row">
			<div class="col-2"><label for="" class="form-label">Year of Pass* :</label></div>
			<div class="col-3"><input type="text" class="form-control"/></div>
			<div class="col-2"><label for="" class="form-label">Month of Pass* :</label></div>
			<div class="col-3"><select class="form-control"><option>-select-</option></select></div>
		</div>
		<div class="row">
			<div class="col-2"><label for="" class="form-label">Applicants Name:*</label></div>
			<div class="col-3"><input type="text" class="form-control"/></div>
			<div class="col-2"><label for="" class="form-label">Mobile Number*:</label></div>
			<div class="col-3"><input type="text" class="form-control"/></div>
		</div>
		<div class="row">
			<div class="col-2"><label for="" class="form-label">Date of Birth(dd-mm-yyyy):*</label></div>
			<div class="col-3"><input type="date" class="form-control"/></div>
			<div class="col-2"><label for="" class="form-label">Aadhar Card Number:*</label></div>
			<div class="col-3"><input type="text" class="form-control"/></div>
		</div>
		<div class="row">
			<div class="col-2"><label for="" class="form-label">Gender:*</label></div>
			<div class="col-3"><input type="radio">Male <input type="radio">Female</div>
			<div class="col-2"><label for="" class="form-label">Category:*</label></div>
			<div class="col-3"><select class="form-control"><option>-select-</option></select></div>
		</div>
		<div class="row">
			<div class="col-2"><label for="" class="form-label">Father Name*:</label></div>
			<div class="col-3"><input type="text" class="form-control"/></div>
			<div class="col-2"><label for="" class="form-label">Physically Challenged:*</label></div>
			<div class="col-3"><input type="radio">Yes <input type="radio">No</div>
		</div>
		<div class="row">
			<div class="col-2"><label for="" class="form-label">mothers Name*:</label></div>
			<div class="col-3"><input type="text" class="form-control"/></div>
			<div class="col-2"><label for="" class="form-label">PWD Category:*</label></div>
			<div class="col-3"><select class="form-control"><option>-select-</option></select></div>
		</div>
		<div class="row">
			<div class="col-2"><label for="" class="form-label">Ex-Servicemen:*</label></div>
			<div class="col-3"><input type="radio">Yes <input type="radio">No</div>
			<div class="col-2"><label for="" class="form-label">Qualification:*</label></div>
			<div class="col-3"><select class="form-control"><option>-select-</option></select></div>
		</div>
		<div class="row">
			<div class="col-2"><label for="" class="form-label">Address:*</label></div>
			<div class="col-3"><textarea class="form-control" rows="5" id="comment"></textarea></div>
			<div class="col-2"><label for="" class="form-label">Local/Non Local:*</label></div>
			<div class="col-3"><select class="form-control"><option>-select-</option></select></div>
		</div>
		<div class="row">
			<div class="col-2"><label for="" class="form-label">Pincode:</label></div>
			<div class="col-3"><input type="text" class="form-control"/></div>
			<div class="col-2"><label for="" class="form-label">Email:</label></div>
			<div class="col-3"><input type="text" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-3"><label for="" class="form-label">Economic Weaker Section:*</label></div>
			<div class="col-2"><input type="radio">Yes <input type="radio">No</div>
		</div>
		</div>
		<div id="inner-div">
		<div class="row">
			<div class="col-4"></div>
			<div class="col-4"><label class="form-label">Number of Languages*<input type="radio">two <input type="radio">three</label></div>
			<div class="col-4"></div>
		</div>
		<div class="row">
			<div class="col-3"></div>
			<div class="col-1">SNO</div>
			<div class="col-2">Marks</div>
			<div class="col-2"></div>
		</div>
		<div class="row">
			<div class="col-3"></div>
			<div class="col-1">1</div>
			<div class="col-2">First language</div>
			<div class="col-2"><input type="text" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-3"></div>
			<div class="col-1">2</div>
			<div class="col-2">Second language</div>
			<div class="col-2"><input type="text" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-3"></div>
			<div class="col-1">3</div>
			<div class="col-2">Third language: English</div>
			<div class="col-2"><input type="text" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-3"></div>
			<div class="col-1">4</div>
			<div class="col-2">Mathematics</div>
			<div class="col-2"><input type="text" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-3"></div>
			<div class="col-1">5</div>
			<div class="col-2">General Science</div>
			<div class="col-2"><input type="text" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-3"></div>
			<div class="col-1">6</div>
			<div class="col-2">Social Studies</div>
			<div class="col-2"><input type="text" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-3"></div>
			<div class="col-1"></div>
			<div class="col-2">Total</div>
			<div class="col-2"><input type="text" class="form-control"></div>
		</div>
		</div> 
		<div id="inner-div">
			<div class="row">
				<div class="col-3">Upload Photo<br><input type="file" class="form-control"><br>File size less than 200kb and Must be jpg,png,jpeg images</div>
				<div class="col-1"></div>
				<div class="col-7"><input type="checkbox">  Above information is correct to the best of my knowledge and I hereby give my consent to the use of Aadhaar Number provided in the application to carryout identity verification for admission process. If any information found wrong, the authority may cancel my admission at any time without any prior notice.</div>
			</div>
			<div class="row">
				<div class="col-5"></div>
				<div class="col-1"><input type="submit"></div>
				<div class="col-7"></div>
			</div>
		</div>
	</div>
</body>
</html>