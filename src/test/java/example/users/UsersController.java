package example.users;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Parameter;
import org.neogroup.warp.data.DataObject;

import java.util.Collection;

import static org.neogroup.warp.Warp.getResource;

@ControllerComponent
public class UsersController {

    @Get("users/:id")
    public DataObject getUser(@Parameter("id") int customerId) {
        return getResource("user").where("id", customerId).first();
    }

    @Get("users")
    public Collection<DataObject> getUsers() {
        return getResource("user").find();
    }
}