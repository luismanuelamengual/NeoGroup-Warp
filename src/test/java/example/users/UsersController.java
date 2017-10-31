package example.users;

import org.neogroup.warp.Warp;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.Request;
import org.neogroup.warp.controllers.Response;
import org.neogroup.warp.controllers.routing.Before;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Route;
import org.neogroup.warp.models.CustomModel;

import java.util.Collection;

@ControllerComponent
public class UsersController {

    @Get("/users/")
    public Object showUsers (Request req, Response res) {

        Collection<CustomModel> users = Warp.retrieve(UsersManager.MODEL_NAME);
        StringBuilder response = new StringBuilder();
        response.append("<body>");
        for (CustomModel user : users) {
            response.append("id: ");
            response.append(user.get(UsersManager.ID_PARAMETER_NAME));
            response.append(" ");
            response.append("name: ");
            response.append(user.get(UsersManager.NAME_PARAMETER_NAME));
            response.append("<br>");
        }
        response.append("</body>");
        return response.toString();
    }

    @Get("/users/addUser")
    public Object addUser (Request req, Response res) {

        CustomModel user = new CustomModel(UsersManager.MODEL_NAME);
        user.set(UsersManager.NAME_PARAMETER_NAME, req.getParameter("name"));
        user.set(UsersManager.LAST_NAME_PARAMETER_NAME, req.getParameter("lastName"));
        user.set(UsersManager.AGE_PARAMETER_NAME, Integer.parseInt(req.getParameter("age")));
        Warp.create(user);
        return showUsers(req, res);
    }

    @Get("/users/:userId")
    public Object getUser (Request req, Response res) {

        return null;
    }
}
