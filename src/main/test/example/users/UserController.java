package example.users;

import org.neogroup.warp.Controller;
import org.neogroup.warp.routing.Get;
import org.neogroup.warp.routing.Route;

public class UserController extends Controller {

    @Get("/users/")
    public Route getShowUsersRoute () {
        return (req, res) -> {
            return "yes optus !! yessss ....";
        };
    }
}
