package example.users;

import org.neogroup.warp.controllers.Controller;
import org.neogroup.warp.controllers.routing.Get;

@Controller
public class UsersController {

    @Get("users/*")
    public String getUsers () {
        return "ramach";
    }
}