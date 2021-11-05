package example.persons;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Param;
import org.neogroup.warp.data.DataObject;

import java.util.Collection;

import static org.neogroup.warp.Warp.getResource;

@ControllerComponent
public class PersonsController {

    @Get("persons/:id")
    public Person getPerson(@Param("id") int personId) {
        return getResource(Person.class).where("id", personId).first();
    }

    @Get("persons")
    public Collection<Person> getPersons() {
        return getResource(Person.class).find();
    }

    @Get("persons2")
    public Collection<DataObject> getPersons2() {
        return getResource("person").find();
    }
}