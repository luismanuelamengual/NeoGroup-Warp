package example.persons;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Route;
import org.neogroup.warp.data.query.Select;
import org.neogroup.warp.data.query.conditions.Operator;

import java.util.Map;

import static org.neogroup.warp.Warp.*;

@ControllerComponent
public class PersonsController {

    @Get("persons")
    protected Route showPersons = (req, res) -> {
        /*Collection<Person> persons = retrieveModels(Person.class);
        View personsView = createView("persons");
        personsView.setParameter("persons", persons);*/

/*
        try (DataConnection connection = getDataConnection()) {
            SelectQuery select = new SelectQuery("persona");
            select.addReturnFields("nombre", "apellido", new QueryReturnField("usuario", "usuarioid"));
            select.addWhere("personaid", "=", 123);
            select.addJoin("usuario", "persona.usuarioid", "usuario.usuarioid");
            ResultSet resultSet = connection.executeQuery(select);

        }*/

/*
        try (Connection connection = getDataSource("test").getConnection()) {

            SelectQuery select = new SelectQuery("persona");
            select.addReturnFields("nombre", "apellido", new QueryReturnField("usuario", "usuarioid"));
            select.addWhere("personaid", "=", 123);
            select.addJoin("usuario", "persona.usuarioid", "usuario.usuarioid");
            connection.select(select);
        }
        catch (Exception ex) {}
*/

        Select personsQuery = new Select("person");
        personsQuery.addSelectFields("nombre", "apellido", "dirección");
        personsQuery.addWhere("nombre", "Luis");
        personsQuery.addWhere("apellido", "Amengual");
        personsQuery.addWhere("age", Operator.GREATER_THAN, 10);
        personsQuery.addJoin("usuario", "persona.usuarioid", "usuario.usuarioid");




        return "rama";
    };

    @Get("persons2")
    protected Route showPersons2 = (req, res) -> createView("persons", Map.of("persons", retrieveModels(Person.class)));
}
