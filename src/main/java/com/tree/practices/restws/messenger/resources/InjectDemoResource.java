package com.tree.practices.restws.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

	@GET
	@Path("annotations")
	public String getParamsUsingAnnotation(@MatrixParam("param") String matrixParam,
			@HeaderParam("customHeaderValue") String customHeaderValue,
			@CookieParam("cookieParam") String cookieParam) {
		return "Including the matrix parameter " + matrixParam + " . Also a Header Parameter: " + customHeaderValue
				+ ". And we have also a cookie parameter: " + cookieParam;
	}

	@GET
	@Path("context")
	public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers) {
		String absolutePath = uriInfo.getAbsolutePath().toString();
		String path = uriInfo.getPath();
		String cookies = headers.getCookies().toString();
		return "Anbsolute Path: " + absolutePath + ", Path: " + path
				+", Cookies: " + cookies;
	}

}
