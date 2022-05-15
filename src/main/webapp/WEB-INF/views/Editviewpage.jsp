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
	<h1>Application Form for Admission into the industrial training
		Institute for the session 2021(Phase-1)</h1>
	<div id="form-section">
		<div id="inner-div"><br>
		 	<div class="row">
		 		<div class="col-2"></div>
		 		<div class="col-3">Selected Districts are:</div>
		 		<div class="col-3">
		 			<table class="table">
		 				<thead>
		 					<tr>
		 						<th>SNO</th>
		 						<th>District Name</th>
		 					</tr>
		 				</thead>
		 				<tbody>
		 					<tr>
		 						<td></td>
		 						<td></td>
		 					</tr>
		 				</tbody>
		 			</table>
		 		</div>
			</div>
			<div class="row">
				<div class="col-2"></div>
				<div class="col-3">Please Select District here:</div>
				<div class="col-2"><select class="form-control"><option>-select-</option></select></div>
				<div class="col-2"><input type="submit" value="add"></div>
			</div>
			</div>
			<div id="inner-div"><br>
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
		<div class="row">
			<div class="col-3"></div>
			<div class="col-8"><label class="checkbox-inline"><input type="checkbox" value="">I hereby consent to the use of Aadhaar Numbers provided in the application to carryout Identity Validation for ITI college admission process.</label></div>
		</div>
		<div class="row">
			<div class="col-5"></div>
			<div class="col-3"><a type="button" class="btn btn-success" href="Editviewpagesuccess">Submit</a></div>
		</div>
	</div>
</body>
</html>