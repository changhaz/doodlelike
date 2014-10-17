package com.lbc.doodlelike;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class HelloWs {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Hello web service";
	}

	@POST
	@Path("/id")
	@Produces(MediaType.TEXT_HTML)
	public Response page() throws URISyntaxException {
		return Response
				.created(
						new URI(
								"/Users/lbchen/Documents/workspace/doodlelike/WebContent/pages/emailcontent.html"))
				.build();
	}
}
