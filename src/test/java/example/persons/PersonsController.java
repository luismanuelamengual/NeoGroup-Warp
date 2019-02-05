package example.persons;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Parameter;
import org.neogroup.warp.data.Data;
import org.neogroup.warp.data.DataElement;

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
    public DataElement getElement() {
        return Data.object()
            .set("name", "Luis")
            .set("lastName", "Amengual")
            .set("projects", Data.array()
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
}