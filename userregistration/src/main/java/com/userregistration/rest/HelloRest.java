package com.userregistration.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
@Path("/hello")
public class HelloRest {
 
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey says: " + msg;
 
		return Response.status(200).entity(output).build();
 
	}	
 
}