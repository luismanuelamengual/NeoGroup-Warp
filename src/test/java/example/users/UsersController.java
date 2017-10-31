package example.users;

import org.neogroup.warp.Warp;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Route;

import java.util.Collection;

@ControllerComponent
public class UsersController {

    @Get("users")
    protected Route showUsers = (req, res) -> {

        Collection<User> users = Warp.retrieveModels(User.class);
        StringBuilder response = new StringBuilder();
        response.append("<body>");
        for (User user : users) {
            response.append("id: ");
            response.append(user.getId());
            response.append(" ");
            response.append("name: ");
            response.append(user.getName());
            response.append("<br>");
        }

        response.append("</body>");
        return response.toString();
    };

    @Get("/users/addUser")
    protected Route addUser = (req, res) -> {

        User user = new User();
        user.setName(req.getParameter("name"));
        user.setLastName(req.getParameter("lastName"));
        user.setAge(Integer.parseInt(req.getParameter("age")));
        Warp.createModel(user);
        return showUsers.handle(req, res);
    };

    @Get("/users/:userId")
    protected Route getUserId = (req, res) -> req.getParameter("userId");
}
