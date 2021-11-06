package example;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.*;
import org.neogroup.warp.data.Data;
import org.neogroup.warp.data.DataObject;
import org.neogroup.warp.http.Request;
import org.neogroup.warp.http.Response;

import java.text.MessageFormat;
import java.util.Collection;

import static org.neogroup.warp.Warp.*;

@ControllerComponent
public class RoutesController {

    @Get("cars/:id/")
    public String getCar (@Param("id") int carId) {
        return "car(" + carId + ")";
    }

    @Get("cars")
    public String getCar2 (@Param(name="id", required = false) Integer carId) {
        return "carz(" + carId + ")";
    }

    @Get("pepe/:name/:lname/rata")
    public void tatch (@Param("name") String name, @Param("lname") String lastName) {
        getResponse().print("Hola ").print(name).print(" ").print(lastName);
    }

    @Get(value="users", priority = RoutingPriority.HIGH)
    public String getUsers (Request req) {
        //return getResource("user").first();
        return "Hola " + req.get("name");
    }

    @Get("/users/showName")
    public String showName (@Param("name") String name) {
        return MessageFormat.format("My name is \"{0}\"", name);
    }

    @Put("users/")
    public String insertUser () {
        return "";
    }

    @Get("/users/show")
    public String show (@Param(value = "name", required=false) String name) {
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

    @Before("*")
    public void aux () {
        getResponse().print("Se valido la sesión\n");
        // stopRouting();
    }


    @Get("cata")
    public String cata(@Param("uno") String uno,
                       @Param(value = "dos", required = false) String dos,
                       @Param(value = "cinco", required = false) int cinco,
                       @Param(value = "seis", required = false) double seis,
                       @Header(value = "Connectionj", required = false) String connection) {

        System.out.println (uno);
        System.out.println (dos);
        System.out.println (cinco);
        System.out.println (seis);
        System.out.println (connection);
        return "done";
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
    public void insertCountry (@Param("name") String name) {
        getResource("country").set("name", name).insert();
        getResponse().print("Pais \"" + name + "\" insertado !!");
    }

    @Get("res")
    public Collection<DataObject> getCountries () {
        //return ((ResourceProxy<DataObject>)getResource("country")).orderBy("name", SortDirection.DESC).find();
        return null;
    }

    @Post("urlencoded")
    public String processUrlEncoded(@Param("lastName") String name) {
        return "Hola " + name;
    }

    @Post("multipart")
    public byte[] processMultipart(@Param("selfie") byte[] selfie) {
        return selfie;
    }

    @Post("multipart2")
    public String processMultipart2(@Param("lastName") String lastName) {
        return lastName;
    }
}
