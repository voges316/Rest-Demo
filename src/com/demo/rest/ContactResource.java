package com.demo.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.demo.notes.Contact;
import com.demo.notes.ContactMgr;

@Path("/contacts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

	/* not thread-safe */
	private static ContactMgr contactMgr = ContactMgr.getInstance();
	
	@POST
    public Response create(Contact contact) {
		// Validate
		if (contact == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Invalid contact provided").build();
		}
		
        Contact result = contactMgr.create(contact);
        ResponseBuilder builder = Response.ok(result);        
        return builder.build();
    }
	
	@GET
    @Path("{id}")
    public Response find (
            @PathParam("id") int id ) {
		Contact result = contactMgr.get(id);
		if (result == null) {
			return Response.noContent().build();
		}
		
		return Response.ok(result).build();
    }
	
    @POST
    @Path("{id}")
    public Response update(
    		@PathParam("id") int id,
    		Contact contact) {
    	// Validate
		if (contact == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Invalid contact provided").build();
		}
		
		if (id != contact.getId()) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Ids must match").build();
		}
		
		Contact c = contactMgr.get(id);
    	
		if (c == null) {
			return Response.status(Status.NOT_FOUND)
					.entity("Could not update contact. Id doesn't exist").build();
		}
    	
        Contact result = contactMgr.update(contact);
        
        return Response.ok(result).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(
    		@PathParam("id") int id) {
    	Contact c = contactMgr.get(id);
    	
    	if (c == null) {
			return Response.status(Status.NOT_FOUND)
					.entity("Could not delete contact. Id doesn't exist").build();
		}
    	
    	Contact result = contactMgr.delete(id);
    	
    	return Response.ok(result).build();
    }
	
    @GET
	public Response findAll() {
		List<Contact> contacts = contactMgr.getContacts();
		GenericEntity<List<Contact>> entity = new GenericEntity<List<Contact>>(contacts) {};
        ResponseBuilder builder = Response.ok(entity);
		
		return builder.build();
	}
}
