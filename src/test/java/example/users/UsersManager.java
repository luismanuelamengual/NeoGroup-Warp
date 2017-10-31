package example.users;

import org.neogroup.warp.models.CustomModel;
import org.neogroup.warp.models.CustomModelManager;
import org.neogroup.warp.models.ModelManagerComponent;
import org.neogroup.warp.models.ModelQuery;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@ModelManagerComponent
public class UsersManager extends CustomModelManager {

    public static final String MODEL_NAME = "User";
    public static final String ID_PARAMETER_NAME = "id";
    public static final String NAME_PARAMETER_NAME = "name";
    public static final String LAST_NAME_PARAMETER_NAME = "lastName";
    public static final String AGE_PARAMETER_NAME = "age";

    private static Map<Integer, CustomModel> data;
    private static AtomicInteger idGenerator;

    static {
        idGenerator = new AtomicInteger();
        data = new HashMap<>();

        CustomModel model;
        int modelId;

        modelId = idGenerator.incrementAndGet();
        model = new CustomModel(MODEL_NAME);
        model.set(ID_PARAMETER_NAME, modelId);
        model.set(NAME_PARAMETER_NAME, "Luis");
        model.set(LAST_NAME_PARAMETER_NAME, "Amengual");
        model.set(AGE_PARAMETER_NAME, 35);
        data.put(modelId, model);

        modelId = idGenerator.incrementAndGet();
        model = new CustomModel(MODEL_NAME);
        model.set(ID_PARAMETER_NAME, modelId);
        model.set(NAME_PARAMETER_NAME, "Jessica");
        model.set(LAST_NAME_PARAMETER_NAME, "Alba");
        model.set(AGE_PARAMETER_NAME, 36);
        data.put(modelId, model);
    }

    public UsersManager() {
        super(MODEL_NAME);
    }

    @Override
    public CustomModel create(CustomModel model, Object... params) {
        int modelId = idGenerator.incrementAndGet();
        model.set(ID_PARAMETER_NAME, modelId);
        data.put(modelId, model);
        return model;
    }

    @Override
    public CustomModel update(CustomModel model, Object... params) {
        int modelId = (int)model.get(ID_PARAMETER_NAME);
        data.put(modelId, model);
        return model;
    }

    @Override
    public CustomModel delete(CustomModel model, Object... params) {
        int modelId = (int)model.get(ID_PARAMETER_NAME);
        return data.remove(modelId);
    }

    @Override
    public Collection<CustomModel> retrieve(ModelQuery query, Object... params) {
        return data.values();
    }
}
