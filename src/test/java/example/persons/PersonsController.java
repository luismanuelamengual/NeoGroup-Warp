package example.persons;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Route;

import java.util.Collection;

import static org.neogroup.warp.Warp.retrieveModels;

@ControllerComponent
public class PersonsController {

    @Get("persons")
    protected Route showPersons = (req, res) -> {

        Collection<Person> persons = retrieveModels(Person.class);
        return "hola";
    };
}
