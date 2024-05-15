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

import com.cgm.hello_web_app.dao.userDAO;
import com.cgm.hello_web_app.eitities.user;

@Path("/users")
public class userServiceAPI {
    private userDAO userDao = new userDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<user> users = userDao.getUserList();
        return Response.ok(users).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(user user) throws URISyntaxException {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user data").build();
        }

        boolean success = userDao.addUser(user);
        if (success) {
            // Thêm thành công, trả về status 201 và URI của người dùng mới
            return Response.created(new URI("/users/" + user.getIduser())).build();
        } else {
            // Nếu thất bại, trả về status 500
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add user").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, user user) {
        user.setIduser(id);
        if (userDao.updateUser(user)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        if (userDao.deleteUser(id)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }
}
