package example.persons;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Parameter;

import java.util.Collection;

import static org.neogroup.warp.Warp.getResource;

@ControllerComponent
public class PersonsController {

    @Get("persons/:id")
    public Person getPerson(@Parameter("id") int personId) {
        return getResource(Person.class).where("id", personId).first();
    }

    @Get("persons")
    public Collection<Person> getPersons() {
        return getResource(Person.class).read();
    }
}