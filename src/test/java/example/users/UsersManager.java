package example.users;

import org.neogroup.warp.models.ModelManager;
import org.neogroup.warp.models.ModelManagerComponent;
import org.neogroup.warp.models.ModelQuery;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@ModelManagerComponent
public class UsersManager extends ModelManager<User> {

    private static Map<Integer, User> data;
    private static AtomicInteger idGenerator;

    static {
        idGenerator = new AtomicInteger();
        data = new HashMap<>();

        User user;
        int userId;

        userId = idGenerator.incrementAndGet();
        user = new User();
        user.setId(userId);
        user.setName("Luis");
        user.setLastName("Amengual");
        user.setAge(35);
        data.put(userId, user);

        userId = idGenerator.incrementAndGet();
        user = new User();
        user.setId(userId);
        user.setName("Jessica");
        user.setLastName("Alba");
        user.setAge(35);
        data.put(userId, user);
    }

    @Override
    public User create(User model, Object... params) {
        int modelId = idGenerator.incrementAndGet();
        model.setId(modelId);
        data.put(modelId, model);
        return model;
    }

    @Override
    public User update(User model, Object... params) {
        data.put(model.getId(), model);
        return model;
    }

    @Override
    public User delete(User model, Object... params) {
        return data.remove(model.getId());
    }

    @Override
    public User retrieve(Object id, Object... params) {
        return data.get(id);
    }

    @Override
    public Collection<User> retrieve(ModelQuery query, Object... params) {
        return data.values();
    }
}
