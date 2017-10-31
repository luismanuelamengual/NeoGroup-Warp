package example.users;

import org.neogroup.warp.Warp;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Route;
import org.neogroup.warp.models.CustomModel;

import java.util.Collection;

@ControllerComponent
public class UsersController {

    @Get("users")
    protected Route showUsers = (req, res) -> {

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
    };

    @Get("/users/addUser")
    protected Route addUser = (req, res) -> {

        CustomModel user = new CustomModel(UsersManager.MODEL_NAME);
        user.set(UsersManager.NAME_PARAMETER_NAME, req.getParameter("name"));
        user.set(UsersManager.LAST_NAME_PARAMETER_NAME, req.getParameter("lastName"));
        user.set(UsersManager.AGE_PARAMETER_NAME, Integer.parseInt(req.getParameter("age")));
        Warp.create(user);
        return showUsers.handle(req, res);
    };

    @Get("/users/:userId")
    protected Route getUserId = (req, res) -> req.getParameter("userId");
}
