package example.persons;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Parameter;
import org.neogroup.warp.data.DataCollection;
import org.neogroup.warp.data.DataObject;

import static org.neogroup.warp.Warp.getResource;

@ControllerComponent
public class PersonsController {

    @Get("persons/:id")
    public DataObject getPerson(@Parameter("id") int personId) {
        return getResource("person").where("id", personId).first();
    }

    @Get("persons")
    public DataCollection getPersons() {
        return getResource("person").find();
    }
}