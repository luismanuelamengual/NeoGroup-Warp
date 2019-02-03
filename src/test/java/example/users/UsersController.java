package example.users;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Route;

@ControllerComponent
public class UsersController {

    @Get("users")
    protected Route showUsers = (req, res) -> new String("candulich");

    @Get("/users/addUser")
    protected Route addUser = (req, res) -> "ramach";

    @Get("/users/:userId")
    protected Route getUserId = (req, res) -> req.getParameter("userId");

    @Get("/pepe/:name/:lastName/tito")
    protected Route pepeAction = (req, res) -> "Hola " + req.getParameter("name") + " "+ req.getParameter("lastName");

    @Get("tata")
    protected Route rama = (req, res) -> {
        return "";
    };




}