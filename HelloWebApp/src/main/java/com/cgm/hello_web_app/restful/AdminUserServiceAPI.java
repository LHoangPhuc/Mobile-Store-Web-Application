package com.cgm.hello_web_app.restful;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cgm.hello_web_app.dao.AdminUserDAO;
import com.cgm.hello_web_app.eitities.AdminUser;

@Path("/AdminUsers")
public class AdminUserServiceAPI {
    private AdminUserDAO adminUserDao = new AdminUserDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdminUsers() {
        List<AdminUser> adminUsers = adminUserDao.getAdminUserList();
        return Response.ok(adminUsers).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAdminUser(AdminUser adminUser) throws URISyntaxException {
        if (adminUser == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid AdminUser data").build();
        }

        boolean success = adminUserDao.addAdminUser(adminUser);
        if (success) {
            return Response.created(new URI("/adminUsers/" + adminUser.getIdAdminUser())).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add AdminUser").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateAdminUser(@PathParam("id") int id, AdminUser adminUser) {
        adminUser.setIdAdminUser(id);
        if (adminUserDao.updateAdminUser(adminUser)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteAdminUser(@PathParam("id") int id) {
        if (adminUserDao.deleteAdminUser(id)) {
            return Response.noContent().build();
        } else {
            return Response.notModified().build();
        }
    }
}
