package example;

import org.neogroup.warp.Request;
import org.neogroup.warp.Response;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.*;
import org.neogroup.warp.data.Data;
import org.neogroup.warp.data.DataObject;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;

import static org.neogroup.warp.Warp.*;

@ControllerComponent
public class RoutesController {


    @Get("pepe/:name/:lname/rata")
    public void tatch (@Parameter("name") String name, @Parameter("lname") String lastName) {
        getResponse().print("Hola ").print(name).print(" ").print(lastName);
    }

    @Get(value="users", priority = RoutingPriority.HIGH)
    public String getUsers (Request req) {
        //return getResource("user").first();
        return "Hola " + req.get("name");
    }

    @Get("/users/showName")
    public String showName (@Parameter("name") String name) {
        return MessageFormat.format("My name is \"{0}\"", name);
    }

    @Put("users/")
    public String insertUser () {
        return "";
    }

    @Get("/users/show")
    public String show (@Parameter(value = "name", required=false) String name) {
        return MessageFormat.format("My name is \"{0}\"", name);
    }

    @Get("elem")
    public DataObject getElement() {
        return Data.object()
            .set("name", "Luis")
            .set("lastName", "Amengual")
            .set("projects", Data.list(
                "Ramach",
                "Pepech",
                124.46,
                true))
            .set("rulex", "pepperonni")
            .set("direction", Data.object()
                .set("country", "Argentina")
                .set("province", "Mendoza"))
            .set("age", 36);
    }

    @Get("rama")
    public void showRama (Response response) {
        response.print("superv ").print(125);
    }


    @Get(value="pepe", priority = RoutingPriority.LOW)
    public void pepe3 (Response response) {
        response.print("PEPE 3 ");
    }

    @Get(value="pepe", priority = RoutingPriority.HIGH)
    public void pepe1 (Response response) {
        response.print("PEPE 1 ");
    }

    @Get("pepe")
    public void pepe2 (Response response) {
        response.print("PEPE 2 ");
    }

    @Route(value = "*", auxiliary = true, priority = RoutingPriority.VERY_HIGH)
    public void aux () {
        getLogger().info("Validando sesión");
    }


    @Get("tata")
    public Object[] rama () {
        return new Object[] {"asklj", 12354, new Double[] {65.0, 256.25}};
    }

    @Get("dbinsert")
    public void dbInsert (Response response) {

        getTable("person")
            .set("name", "pimba")
            .set("lastname", "tuke")
            .set("age", 27)
            .insert();

        response.print("Persona insertada !!");
    }

    @Get("dbupdate")
    public void dbUpdate () {
        getTable("person")
            .set("name", "pimba")
            .set("lastname", "ruke")
            .where("personid", 6)
            .update();

        getResponse().print("Persona actualizada");
    }

    @Get("dbdelete")
    public void dbDelete () {

        getTable("person").where("personid", 7).delete();

        getResponse().print("Persona borrada");
    }

    @Get("db")
    public Collection<DataObject> dbTest () {
         return getTable("person").find();
    }

    @Get("resinsert")
    public void insertCountry (@Parameter("name") String name) {
        getResource("country").set("name", name).insert();
        getResponse().print("Pais \"" + name + "\" insertado !!");
    }

    @Get("res")
    public Collection<DataObject> getCountries () {
        //return ((ResourceProxy<DataObject>)getResource("country")).orderBy("name", SortDirection.DESC).find();
        return null;
    }

    @Get("mes")
    public void messagesTest (Request request, Response response) {
        getContext().setLocale(Locale.forLanguageTag("es_AR"));

        String text = request.get("text");
        response.print(getMessage(text));
    }

    @Post("urlencoded")
    public String processUrlEncoded(@Parameter("lastName") String name) {
        return "Hola " + name;
    }

    @Post("multipart")
    public String processMultipart(@Parameter("name") String name) {
        return name;
    }



}
