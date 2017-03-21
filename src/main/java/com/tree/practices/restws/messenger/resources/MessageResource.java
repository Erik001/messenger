package com.tree.practices.restws.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.tree.practices.restws.messenger.model.Message;
import com.tree.practices.restws.messenger.resources.beans.MessageFilterBean;
import com.tree.practices.restws.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
//@Produces(value={MediaType.APPLICATION_JSON,MediaType.TEXT_XML}) //We can override this at method level
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	private MessageService messageService = new MessageService();

	@GET
	// @Produces(MediaType.APPLICATION_JSON)
	/*
	 * public List<Message> getMessages(@QueryParam(value = "year") int year,
	 * 
	 * @QueryParam(value = "start") int start,
	 * 
	 * @QueryParam(value = "size") int size) {
	 */
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJsonMessages(@BeanParam MessageFilterBean filterBean) {
		System.out.println("JSON method has been called!!");
		// if (year > 0) {
		if (filterBean.getYear() > 0) {
			// return messageService.getAllMessagesForYear(year);
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		/*
		 * if (start >= 0 && size > 0) { return
		 * messageService.getAllMessagesPaginated(start, size);
		 */
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXmlMessages(@BeanParam MessageFilterBean filterBean) {
		System.out.println("XML method has been called!!");
		// if (year > 0) {
		if (filterBean.getYear() > 0) {
			// return messageService.getAllMessagesForYear(year);
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		/*
		 * if (start >= 0 && size > 0) { return
		 * messageService.getAllMessagesPaginated(start, size);
		 */
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}

	@POST
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.APPLICATION_JSON)
	// public Message addMessage(Message message) {
	// public Response addMessage(Message message) throws URISyntaxException {In
	// order to get the uri programmatically
	public Response addJsonMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		// return Response.status(Status.CREATED).entity(newMessage).build(); Is
		// preferable to use created rather than status
		// System.out.println(uriInfo.getAbsolutePath());
		String newMessageId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newMessageId).build();
		// return Response.created(new URI("/messenger/webapi/messages/" +
		// newMessage.getId())).entity(newMessage).build();
		return Response.created(uri).entity(newMessage).build();
		// return messageService.addMessage(message);
	}
	
	@POST
	 @Consumes(MediaType.TEXT_XML)
	 @Produces(MediaType.TEXT_XML)
	// public Message addMessage(Message message) {
	// public Response addMessage(Message message) throws URISyntaxException {In
	// order to get the uri programmatically
	public Response addXmlMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		// return Response.status(Status.CREATED).entity(newMessage).build(); Is
		// preferable to use created rather than status
		// System.out.println(uriInfo.getAbsolutePath());
		String newMessageId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newMessageId).build();
		// return Response.created(new URI("/messenger/webapi/messages/" +
		// newMessage.getId())).entity(newMessage).build();
		return Response.created(uri).entity(newMessage).build();
		// return messageService.addMessage(message);
	}

	@PUT
	@Path("/{messageId}")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}

	@DELETE
	@Path("/{messageId}")
	// @Produces(MediaType.APPLICATION_JSON)
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}

	@GET
	@Path("/{messageId}")
	// @Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message) , "self");
		message.addLink(getUriForProfile(uriInfo, message) , "profile");
		message.addLink(getUriForComments(uriInfo, message) , "comments");
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder() ///It adds this section of the uri http://localhost:8080/messenger/webapi/
				.path(MessageResource.class)   ///It adds the section that corresponds to the resource /messages
				.path(MessageResource.class, "getCommentResource")   ///It adds the section that corresponds to the resource /{messageId}/comments
				.path(CommentResource.class)  /// doesn't hurt if we add it
				.resolveTemplate("messageId", message.getId()) //It will replace the messageId variable by the actual Id of the message, if not added the following exception its been thrown java.lang.IllegalArgumentException: The template variable 'messageId' has no value
				.build();
		return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder() ///It adds this section of the uri http://localhost:8080/messenger/webapi/
				.path(ProfileResource.class)   ///It adds the section that corresponds to the resource /profiles
				.path(message.getAuthor())  /// It adds the author name at the end of the uri /{authorName}
				.build();
		return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder() ///It adds this section of the uri http://localhost:8080/messenger/webapi/
		.path(MessageResource.class)   ///It adds the section that corresponds to the resource /messages
		.path(Long.toString(message.getId())) /// It adds the message Id at the end of the uri /{messageId}
		.build() //Generates the Uri
		.toString();
		return uri;
	}

	// @GET To avoid having all the methods and functionality in the same class
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
