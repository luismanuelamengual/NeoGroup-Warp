package example.persons;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Parameter;

import static org.neogroup.warp.Warp.getResource;
import static org.neogroup.warp.Warp.getResponse;

@ControllerComponent
public class PersonsController {

    @Get("pepe/:name/:lname/rata")
    public void tatch (@Parameter("name") String name, @Parameter("lname") String lastName) {
        getResponse().print("Hola ").print(name).print(" ").print(lastName);
    }

    @Get("/persons/:id")
    public Person getFirstPerson() {
        return getResource(Person.class).first();
    }
}
