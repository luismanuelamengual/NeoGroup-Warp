package example.users;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Parameter;
import org.neogroup.warp.data.DataCollection;
import org.neogroup.warp.data.DataObject;

import static org.neogroup.warp.Warp.getResource;

@ControllerComponent
public class UsersController {

    @Get("users/:id")
    public DataObject getUser(@Parameter("id") int userId) {
        return getResource("user").where("id", userId).first();
    }

    @Get("users")
    public DataCollection getUsers() {
        return getResource("user").find();
    }
}