package example.users;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Param;
import org.neogroup.warp.data.DataObject;

import java.util.Collection;

import static org.neogroup.warp.Warp.getResource;

@ControllerComponent("users")
public class UsersController {

    @Get()
    public String pepe() {
        return "estamos en el reaiz";
    }

    @Get("/insert/:id")
    public DataObject getUser(@Param("id") int userId) {
        return getResource("user").where("id", userId).first();
    }

    @Get("papas")
    public Collection<DataObject> getUsers() {
        return getResource("user").find();
    }

    @Get("/ramas")
    public String getUsers2() {
        return "Ramas";
    }
}