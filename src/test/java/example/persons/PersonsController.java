package example.persons;

import org.neogroup.warp.Warp;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Route;
import org.neogroup.warp.data.DataObject;
import org.neogroup.warp.data.DataTable;

import java.util.List;

@ControllerComponent
public class PersonsController {

    @Get("persons")
    protected Route showPersons = (req, res) -> {

        //List<DataObject> users = Warp.getConnection().executeQuery("SELECT * FROM usuario WHERE accesonombre = ? LIMIT 10", "lamengual");

        DataTable usersTable = Warp.getConnection().getDataTable("usuario");
        usersTable.addWhere("accesonombre", "lamengual");
        usersTable.setLimit(1);
        List<DataObject> users = usersTable.findAll();

        return "rama";
    };
}
