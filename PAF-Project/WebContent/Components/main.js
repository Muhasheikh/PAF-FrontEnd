$(document).ready(function()
{
if ($("#alertSuccess").text().trim() == "")
 {
 $("#alertSuccess").hide();
 }
 $("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
		{
		// Clear alerts---------------------
			$("#alertSuccess").text("");
			$("#alertSuccess").hide();
			$("#alertError").text("");
			$("#alertError").hide();
			
			// Form validation-------------------
			var status = validateItemForm();
			if (status != true)
			{
				$("#alertError").text(status);
				$("#alertError").show();
				return;
			}
		
		// If valid------------------------
		var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
		$.ajax(
		{
			url : "paymentAPI",
			type : type,
			data : $("#formItem").serialize(),
			dataType : "text",
			complete : function(response, status)
		{
			onItemSaveComplete(response.responseText, status);
		}
		});
	});


function onItemSaveComplete(response, status)
{
	if (status == "success")
	{
	var resultSet = JSON.parse(response);
	if (resultSet.status.trim() == "success")
	{
	$("#alertSuccess").text("Successfully saved.");
	$("#alertSuccess").show();
	$("#divItemsGrid").html(resultSet.data);
	} else if (resultSet.status.trim() == "error")
	{
	$("#alertError").text(resultSet.data);
	$("#alertError").show();
	}
	} else if (status == "error")
	{
	$("#alertError").text("Error while saving.");
	$("#alertError").show();
	} else
	{
	$("#alertError").text("Unknown error while saving..");
	$("#alertError").show();
	}
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}

$(document).on("click", ".btnUpdate", function(event)
		{
		$("#hidItemIDSave").val($(this).data("paymentid"));
		 $("#userName").val($(this).closest("tr").find('td:eq(0)').text());
		 $("#userMobile").val($(this).closest("tr").find('td:eq(1)').text());
		 $("#cardNo").val($(this).closest("tr").find('td:eq(2)').text());
		 $("#amount").val($(this).closest("tr").find('td:eq(3)').text());
		});



$(document).on("click", ".btnRemove", function(event)
		{
		 $.ajax(
		 {
		 url : "paymentAPI",
		 type : "DELETE",
		 data : "paymentID=" + $(this).data("paymentid"),
		 dataType : "text",
		 complete : function(response, status)
		 {
		 onItemDeleteComplete(response.responseText, status);
		 }
		 });
		});




// UPDATE==========================================



function onItemDeleteComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully deleted.");
 $("#alertSuccess").show();
 $("#divItemsGrid").html(resultSet.data);
 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while deleting.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while deleting..");
 $("#alertError").show();
 }
}

// CLIENT-MODEL================================================================
function validateItemForm()
{
// CODE
if ($("#userName").val().trim() == "")
 {
 return "User Name Required.";
 }
// NAME
if ($("#userMobile").val().trim() == "")
 {
 return "Mobile No Required.";
 } 
9
// PRICE-------------------------------
if ($("#cardNo").val().trim() == "")
 {
 return "Card Number Required.";
 }
// is numerical value
var tmpPrice = $("#amount").val().trim();
if (!$.isNumeric(tmpPrice))
 {
 return "Insert amount in numbers.";
 }
// convert to decimal price
 $("#amount").val(parseFloat(tmpPrice).toFixed(2));
// DESCRIPTION------------------------

return true;
}
