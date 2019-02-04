package example.persons;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Parameter;
import org.neogroup.warp.resources.ResourceProxy;

import static org.neogroup.warp.Warp.getResource;
import static org.neogroup.warp.Warp.getResponse;

@ControllerComponent
public class PersonsController {

    @Get("pepe/:name/:lname/rata")
    public void tatch (@Parameter("name") String name, @Parameter("lname") String lastName) {
        getResponse().print("Hola ").print(name).print(" ").print(lastName);
    }

    @Get("/persons/:id")
    public Person getPerson(@Parameter("id") String personId) {
        return (Person)getResource(Person.class).where("personid", personId).first();
    }
}