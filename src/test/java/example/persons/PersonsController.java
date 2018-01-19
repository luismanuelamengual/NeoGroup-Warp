package example.persons;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Route;
import org.neogroup.warp.data.DataObject;

import static org.neogroup.warp.Warp.getConnection;

@ControllerComponent
public class PersonsController {

    @Get("persons")
    protected Route showPersons = (req, res) -> {

        /*
        DataObject doUsers = getConnection().getDataObject("person");
        doUsers.addSelectField("accesonombre", "nombresuli");
        doUsers.addWhere("accesonombre", Operator.CONTAINS,  "amen");
        doUsers.addJoin("persona", "usuario.personaid", "persona.personaid");
        doUsers.setOffset(10);
        doUsers.setLimit(10);
        doUsers.find();
        while (doUsers.fetch()) {
            System.out.println ("Accessname: " + doUsers.getField("nombresuli"));
        }
        */

        /*
        DataObject doPerson = getConnection().getDataObject("person");
        doPerson.setField("name", "Vane");
        doPerson.addWhere("personid", 44);
        doPerson.update();
        */

        DataObject doPerson = getConnection().getDataObject("person");
        doPerson.find();
        StringBuilder data = new StringBuilder();
        while (doPerson.fetch()) {
            data.append("<br>");
            data.append((String)doPerson.getField("name"));
            data.append("|");
            data.append((String)doPerson.getField("lastname"));
            data.append("|");
            data.append((Integer)doPerson.getField("age"));
        }

        return data.toString();
    };
}
