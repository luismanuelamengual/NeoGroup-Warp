package example.users;

import org.neogroup.warp.controllers.Controller;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.resources.ResourceItem;

import java.util.Collection;

import static org.neogroup.warp.Warp.getResource;
import static org.neogroup.warp.Warp.getResponse;

@Controller
public class UsersController {

    @Get("users")
    public String getUsers () {
        return "lksjfl";
    }
}