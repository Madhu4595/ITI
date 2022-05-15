<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/style.css">
<script type="text/javascript">
	function demo(x) {
		alert(x.value);
	}
</script>
</head>
<body>
	<img alt="banner" src="/images/gen.jpg">
	<%@include file="nav-bar1.jsp"%><br>
	<h3>Registration Form for Admission into the Industrial Training
		Institute for the session 2021(Phase-1)</h3>
	<div id="form-section">
		<h5>Registration Form Step-1</h5>
		<form:form action = "open_application_entry" modelAttribute="stdregs1" method = "POST">
			<div class="row">
				<div class="col-3">
					<label class="form-label">SSC/8th
						Board:</label>
				</div>
				<div class="col-3">
					<form:select path = "boardName" class="form-control" onchange="demo(this)" id="board">
						<form:option value="">-select-</form:option>
						<c:forEach items ="${boardNames}" var="boardNames">
							<form:option value = "${boardNames}">${boardNames}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="row">
				<div class="col-3">
					<label class="form-label">SSC/8th
						Hall Ticket Number:</label>
				</div>
				<div class="col-3">
					<form:input type="text" path = "sscHallTicketNo" class="form-control"/>
				</div>
			</div>
			<div class="row">
				<div class="col-3">
					<label class="form-label">Year Of
						Passing*:</label>
				</div>
				<div class="col-3">
					<form:input type="text" path="sscYearOfPass" class="form-control"/>
				</div>
			</div>
			<div class="row">
				<div class="col-3">
					<label for="Result Type" class="form-label">Result Type*:</label>
				</div>
				<div class="col-3">
					<form:select path = "sscResultType" class="form-control">
						<form:option value="">-select-</form:option>
						<form:option value="Grades">Grades</form:option>
						<form:option value="Marks">Marks</form:option>
					</form:select>
				</div>
			</div>
			<div class="row">
				<div class="col-3"></div>
				<div class="col-4">
					<a type="button" class="btn btn-primary" href="login">Home</a> <a
						type="button" class="btn btn-success"
						href="open_application-step1">Proceed</a> <a type="button"
						class="btn btn-warning">Reset</a>
				</div>
				<div class="col-4"></div>
			</div>
		</form:form>
	</div>
</body>
</html>