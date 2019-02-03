package example.users;

import org.neogroup.warp.Request;
import org.neogroup.warp.Response;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Parameter;
import org.neogroup.warp.controllers.routing.Put;

import java.text.MessageFormat;

import static org.neogroup.warp.Warp.getResource;

@ControllerComponent
public class UsersController {

    @Get("users")
    public String getUsers (Request req) {
        //return getResource("user").first();
        return "Hola " + req.getParameter("name");
    }

    @Get("/users/showName")
    public String showName (@Parameter("name") String name) {
        return MessageFormat.format("My name is \"{0}\"", name);
    }

    @Put("users/")
    public String insertUser () {
        return "";
    }

    @Get("/users/show")
    public String show (@Parameter(value = "name", required=false) String name) {
        return MessageFormat.format("My name is \"{0}\"", name);
    }


    @Get("rama")
    public void showRama (Response response) {
        response.print("superv ").print(125);
    }
}