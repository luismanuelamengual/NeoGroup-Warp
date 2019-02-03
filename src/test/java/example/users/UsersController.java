package example.users;

import org.neogroup.warp.Request;
import org.neogroup.warp.controllers.Controller;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Parameter;
import org.neogroup.warp.controllers.routing.Put;

import java.text.MessageFormat;

import static org.neogroup.warp.Warp.getResource;

@Controller
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
}