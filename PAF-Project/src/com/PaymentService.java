package com;

import model.Payment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payments")
public class PaymentService {

	Payment pay = new Payment();

	// read operation through postman
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return pay.readItems();
	}

	// insert operation through postman
	@POST
	@Path("/insert/{OrderID}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(
			@PathParam("OrderID")String orderID,
			@FormParam("userName") String userName,
			@FormParam("userMobile") String userMobile,
			@FormParam("cardNo") String cardNo) 
	
	{
		String amt = getOrderID(orderID);
		//System.out.println(amt);
		String output = pay.insertItem(userName, userMobile, cardNo, amt);
		return output;
	}

	// update operation through postman
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String itemData) {
		// Convert the input string to a JSON object
		JsonObject payObject = new JsonParser().parse(itemData).getAsJsonObject();
		// Read the values from the JSON object
		String payID = payObject.get("paymentID").getAsString();
		String userN = payObject.get("userName").getAsString();
		String userM = payObject.get("userMobile").getAsString();
		String cardNo = payObject.get("cardNo").getAsString();
		String Amount = payObject.get("amount").getAsString();
		String output = pay.updateItem(payID, userN, userM, cardNo, Amount);
		return output;
	}

	// delete operations through postman

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String payID = doc.select("paymentID").text();
		String output = pay.deleteItem(payID);
		return output;
	}
	
	
	/////Getting the order Id from user service
	@GET
	@Path("/orderDetails/{OrderID}")
	@Produces(MediaType.APPLICATION_XML)
	public String getOrderID(@PathParam("OrderID") String OrderID)
	{
		
		String Path = "http://localhost:8082/PAFOrderManagement/OrderManagementService/OrderManagement/getAmount/";
		
		Client client = Client.create();
		
		WebResource target = client.resource(Path);
		
		//Client response = target.queryParam("ID",ID).accept("application/xml").get(Client.class);
		String response = target.queryParam("OrderID", OrderID).accept("application/xml").get(String.class);
		return response.toString();
		
		
	}
	
}
