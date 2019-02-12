package example.users;

import org.neogroup.warp.data.Data;
import org.neogroup.warp.data.DataCollection;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;

@ResourceComponent("user")
public class UsersResource extends Resource {

    @Override
    public DataCollection find(SelectQuery query) {

        DataCollection collection = Data.collection();

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