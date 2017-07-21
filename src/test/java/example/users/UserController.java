package example.users;

import org.neogroup.warp.controllers.Controller;
import org.neogroup.warp.routing.Get;
import org.neogroup.warp.routing.Request;
import org.neogroup.warp.routing.Route;

public class UserController extends Controller {

    @Get("/users/")
    public Route showUsersRoute = (req, res) -> {
        return "chess optus !! yessss ....";
    };

    @Get("/camote/*")
    public Route camoteRoute = (req, res) -> {
        return "Hello camote: " + req.getParameter("name");
    };

    @Get("/users/:userId/rama")
    public Route getUserIdRoute = (req, res) -> {
        return "The user id is: " + req.getParameter("userId");
    };

    @Request("cumpli")
    public Route cumpliRoute = (req, res) -> {
        return "Hello cumpli !!";
    };
}
