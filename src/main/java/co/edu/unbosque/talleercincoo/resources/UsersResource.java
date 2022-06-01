package co.edu.unbosque.talleercincoo.resources;

import co.edu.unbosque.talleercincoo.dtos.ExceptionMessage;
import co.edu.unbosque.talleercincoo.dtos.User;
import co.edu.unbosque.talleercincoo.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

//import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Path("/users")
public class UsersResource {

    @Context
    ServletContext context;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/TallerCinco";

    static final String USER = "postgres";
    static final String PASS= "123456";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        Connection conn = null;
        List<User> users = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService userService = new UserService(conn);

            users = userService.getUsers();
            conn.close();
        }catch (SQLException s){
            s.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) conn.close();
            }catch (SQLException s){
                s.printStackTrace();
            }
        }
        return Response.ok().entity(users).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user) {
        String contextPath = context.getRealPath("") + File.separator;

        try {
            user = UserService.createUser(user.getUsername(), user.getEmail(), user.getPassword(), user.getRole(), contextPath);

            return Response.created(UriBuilder.fromResource(UsersResource.class).path(user.getUsername()).build()).entity(user).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("username") String username) {
        try {
            List<User> users = UserService.getUsers();

            User user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);

            if (user != null) {
                return Response.ok().entity(user).build();
            } else {
                return Response.status(404).entity(new ExceptionMessage(404, "co.edu.unbosque.TalleerCincoo.dtos.User not found")).build();
            }
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }
}
