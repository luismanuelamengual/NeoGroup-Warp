package example.users;

import org.neogroup.warp.data.Data;
import org.neogroup.warp.data.DataElement;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ResourceComponent("user")
public class UsersResource extends Resource<DataElement> {

    @Override
    public Collection<DataElement> find(SelectQuery query) {
        List<DataElement> list = new ArrayList<>();
        list.add(Data.object()
            .set("name", "Pepech")
            .set("lastName", "Candulich")
            .set("age", 35));

        list.add(Data.object()
            .set("name", "Ratata")
            .set("lastName", "Pipo")
            .set("age", 23));
        return list;
    }
}