package example.users;

import org.neogroup.warp.controllers.Controller;
import org.neogroup.warp.routing.Get;
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
}
