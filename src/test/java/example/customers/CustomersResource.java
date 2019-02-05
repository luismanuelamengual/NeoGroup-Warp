package example.customers;

import org.neogroup.warp.data.Data;
import org.neogroup.warp.data.DataElement;
import org.neogroup.warp.query.Query;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ResourceComponent("customer")
public class CustomersResource extends Resource<DataElement> {

    @Override
    public Collection<DataElement> find(Query query) {
        List<DataElement> list = new ArrayList<>();
        list.add(Data.object()
            .set("name", "Pepech")
            .set("lastName", "Candulich")
            .set("age", 35));
        return list;
    }
}
