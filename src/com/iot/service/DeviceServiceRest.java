package com.iot.service;

import com.iot.domain.Device;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.annotation.security.RolesAllowed; // For JAX-RS Role-based Security

@Path("/device")
public interface DeviceServiceRest {

    @POST
    @Path("/addDevice")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ROLE_USER") 
    Device addDevice(Device device); 

    @GET
    @Path("/getDevicesByUserId/{userId}") 
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ROLE_USER") 
    List<Device> getDevicesByUserId(@PathParam("userId") Long userId); 
}
