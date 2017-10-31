package example.persons;

import org.neogroup.warp.Warp;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Route;

import java.util.Collection;

@ControllerComponent
public class PersonsController {

    @Get("persons")
    protected Route showPersons = (req, res) -> {

        Collection<Person> persons = Warp.retrieveModels(Person.class);
        return "hola";
    };
}
