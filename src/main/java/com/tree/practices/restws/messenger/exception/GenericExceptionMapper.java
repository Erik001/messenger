package com.tree.practices.restws.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.tree.practices.restws.messenger.model.ErrorMessage;

//@Provider //It was commented to avoid to catch all the exception that are throw
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 500, "http://www.errors.com");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
	}

}
