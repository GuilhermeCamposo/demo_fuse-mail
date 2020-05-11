package com.redhat.fuse.boosters.rest.http;

import javax.ws.rs.core.MediaType;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * A simple Camel REST DSL route that implements the greetings service.
 *
 */
@Component
public class CamelRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        createRestRoute();
        createMailRoute();
    }

    private void createMailRoute() {
    	from("direct:email-route")
    	.routeId("email-route")
	    	.setHeader("To",constant("guilhermecamposo60@gmail.com"))
	    	.setHeader("Subject",jsonpath("$.subject"))
	    	.setBody(jsonpath("$.message"))
	    	.setHeader("From",constant("Guilherme Camposo <guilhermecamposo60@gmail.com>"))
	    	.setHeader("contentType", constant(MediaType.TEXT_PLAIN))
    	.to("smtps:s{{mail.host}}:{{mail.port}}?username={{mail.username}}&password={{mail.password}}")
    	.log("EMAIL ENVIADO");
    }

	private void createRestRoute() {

        restConfiguration()
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Email REST API")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiProperty("base.path", "camel/")
                .apiProperty("api.path", "/")
                .apiProperty("host", "")
                .apiContextRouteId("doc-api")
            .component("servlet")
            .bindingMode(RestBindingMode.json);

        rest("/email")
        	.description("Send a email")
            .post()
            	.consumes(MediaType.APPLICATION_JSON)
            	.produces(MediaType.TEXT_PLAIN)
	            .outType(String.class)
	            .route()
	            	.routeId("rest-api")
	                .to("direct:email-route")
	                .setBody(constant("EMAIL ENVIADO"));
	}

}
