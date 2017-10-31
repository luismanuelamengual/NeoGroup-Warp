package example.users;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.Request;
import org.neogroup.warp.controllers.Response;
import org.neogroup.warp.controllers.routing.Before;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Route;

@ControllerComponent
public class UserController {

    @Before("users")
    public boolean beforeShowUsers (Request req, Response res) {
        System.out.println ("accessing users !!");
        return true;
    }

    @Get("/users/")
    public Object showUsers (Request req, Response res) {
        return "chess optus !! yessss ....";
    }

    @Get("/camote/*")
    public Object camote (Request req, Response res) {
        return "Hello camote: " + req.getParameter("name");
    }

    @Get("/users/:userId/rama")
    public Object getUserId (Request req, Response res) {
        return "The user id is: " + req.getParameter("userId");
    }

    @Route("cumpli")
    public Object cumpli (Request req, Response res) {
        return 21 / 0;
    }
}
