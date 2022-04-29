package com.example.restwo.resource;

import com.example.restwo.service.Service;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import org.json.JSONObject;

import static jakarta.ws.rs.core.Response.Status.OK;


@Path("/")
public class Resource {

    private final Service service = new Service();

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello, World!";
    }


    @GET
    @Path("/ip")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public String getIp(){
        return service.getIps();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Path("/domain")
    public String getDomain(){
        String domains = service.getDomains();
        System.out.println(domains);
        return domains;
    }



//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/url")
//    public String getUrl(){
//        return service.getUrls();
//    }

    @POST
    @Path("/form")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response formData(String inStream){
        JSONObject jsonObject = new JSONObject(inStream);
        String type = jsonObject.get("type").toString();
        String input = jsonObject.get("input").toString();
        System.out.println("\n" + type + " : " + input);
        return Response.status(OK).entity(service.search(type, input)).build();
    }



}


