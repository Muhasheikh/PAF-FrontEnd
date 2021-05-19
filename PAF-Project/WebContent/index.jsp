<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@page import="model.Payment"%>
    
    
    
    
    
<!DOCTYPE html>
<html lang="en">
    <head>
       
        
        <link href="Css/styles.css" rel="stylesheet" />
        <link href="Bootstrap/css/bootstrap.css" rel="stylesheet"/>
        
        	
    	
    </head>
    <body>
        






<div class = "container"> 
	<div class="row">
		<div class="col">

		<h1>Payment Managemet</h1>
		
	<form id="formItem" name="formItem"  >
		User Name:
		<input id="userName" name="userName" type="text" class="form-control form-control-sm"><br>
		 Mobile No:
		<input id="userMobile" name="userMobile" type="text" class="form-control form-control-sm"><br> 
		Card No:
		<input id="cardNo" name="cardNo" type="text" class="form-control form-control-sm"><br>
		 Amount:
		<input id="amount" name="amount" type="text" class="form-control form-control-sm"><br>
	<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
		<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
	</form>
    
    <div id="alertSuccess" class="alert alert-success"></div>
     <div id="alertError" class="alert alert-danger"></div>
    
    <br>
	<div id="divItemsGrid">
	<%
	Payment pay = new Payment();
	out.print(pay.readItems());
	%>
	</div>

<!-- -------------------------------------------------------------------------------------------------------------- -->

	<script src="Components/jquery.js"></script>
       <script src="Components/main.js"></script>
	
 </div>
 </div>
</div>
    


	
</body>
</html>