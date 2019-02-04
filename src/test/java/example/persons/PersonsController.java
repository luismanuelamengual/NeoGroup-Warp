package example.persons;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Parameter;
import org.neogroup.warp.utils.json.Json;
import org.neogroup.warp.utils.json.JsonElement;

import static org.neogroup.warp.Warp.getResource;
import static org.neogroup.warp.Warp.getResponse;

@ControllerComponent
public class PersonsController {

    @Get("pepe/:name/:lname/rata")
    public void tatch (@Parameter("name") String name, @Parameter("lname") String lastName) {
        getResponse().print("Hola ").print(name).print(" ").print(lastName);
    }

    @Get("/persons/:id")
    public Person getPerson(@Parameter("id") int personId) {
        return getResource(Person.class).where("id", personId).first();
    }

    @Get("elem")
    public JsonElement getElement() {
        return Json.object()
            .set("name", "Luis")
            .set("lastName", "Amengual")
            .set("projects", Json.array()
                .add("Ramach")
                .add("Pepech")
                .add(124.46)
                .add(true))
            .set("rulex", "pepperonni")
            .set("direction", Json.object()
                .set("country", "Argentina")
                .set("province", "Mendoza"))
            .set("age", 36);
    }
}