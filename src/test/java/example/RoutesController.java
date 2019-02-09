package example;

import org.neogroup.warp.Request;
import org.neogroup.warp.Response;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.*;
import org.neogroup.warp.data.Data;
import org.neogroup.warp.data.DataElement;

import java.text.MessageFormat;

import static org.neogroup.warp.Warp.getLogger;
import static org.neogroup.warp.Warp.getResponse;

@ControllerComponent
public class RoutesController {


    @Get("pepe/:name/:lname/rata")
    public void tatch (@Parameter("name") String name, @Parameter("lname") String lastName) {
        getResponse().print("Hola ").print(name).print(" ").print(lastName);
    }

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

    @Get("elem")
    public DataElement getElement() {
        return Data.object()
            .set("name", "Luis")
            .set("lastName", "Amengual")
            .set("projects", Data.list()
                .add("Ramach")
                .add("Pepech")
                .add(124.46)
                .add(true))
            .set("rulex", "pepperonni")
            .set("direction", Data.object()
                .set("country", "Argentina")
                .set("province", "Mendoza"))
            .set("age", 36);
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


    @Get("tata")
    public Object[] rama () {
        return new Object[] {"asklj", 12354, new Double[] {65.0, 256.25}};
    }
}
