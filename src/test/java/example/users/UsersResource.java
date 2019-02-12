package example.users;

import org.neogroup.warp.data.Data;
import org.neogroup.warp.data.DataCollection;
import org.neogroup.warp.data.DataObject;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ResourceComponent("user")
public class UsersResource extends Resource<DataObject> {

    @Override
    public Collection<DataObject> find(SelectQuery query) {

        List<DataObject> collection = new ArrayList<>();

        collection.add(Data.object()
            .set("name", "Pepech")
            .set("lastName", "Candulich")
            .set("age", 35));

        collection.add(Data.object()
            .set("name", "Ratata")
            .set("lastName", "Pipo")
            .set("age", 23));

        return collection;
    }
}