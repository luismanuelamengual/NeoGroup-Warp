package example.users;

import org.neogroup.warp.Request;
import org.neogroup.warp.Response;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.*;

import java.text.MessageFormat;

import static org.neogroup.warp.Warp.getLogger;
import static org.neogroup.warp.Warp.getResource;

@ControllerComponent
public class UsersController {

    @Get(value="users", priority = RoutingPriority.HIGH)
    public String getUsers (Request req) {
        //return getResource("user").first();
        return "Hola " + req.get("name");
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


    @Get(value="pepe", priority = RoutingPriority.LOW)
    public void pepe3 (Response response) {
        response.print("PEPE 3 ");
    }

    @Get(value="pepe", priority = RoutingPriority.HIGH)
    public void pepe1 (Response response) {
        response.print("PEPE 1 ");
    }

    @Get("pepe")
    public void pepe2 (Response response) {
        response.print("PEPE 2 ");
    }

    @Route(value = "*", auxiliary = true, priority = RoutingPriority.VERY_HIGH)
    public void aux () {
        getLogger().info("Validando sesión");
    }

    @Get("cama")
    public Pepe getPepe () {
        return new Pepe("Luis", "Amengual");
    }


    class Pepe {
        private String name;
        private String lastName;

        public Pepe(String name, String lastName) {
            this.name = name;
            this.lastName = lastName;
        }

        public String getName() {
            return name;
        }

        public String getLastName() {
            return lastName;
        }
    }
}