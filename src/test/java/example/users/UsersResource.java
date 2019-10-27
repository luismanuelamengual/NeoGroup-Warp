package example.users;

import org.neogroup.warp.data.Data;
import org.neogroup.warp.data.DataObject;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;

import java.util.Arrays;
import java.util.Collection;

@ResourceComponent("user")
public class UsersResource extends Resource<DataObject> {

    @Override
    public Collection<DataObject> find(SelectQuery query) {
        return Arrays.asList(
            Data.object()
                .set("name", "Pepech")
                .set("lastName", "Candulich")
                .set("age", 35),
            Data.object()
                .set("name", "Ratata")
                .set("lastName", "Pipo")
                .set("age", 23)
        );
    }
}