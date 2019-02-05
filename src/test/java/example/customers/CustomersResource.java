package example.customers;

import org.neogroup.warp.query.Query;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;
import org.neogroup.warp.resources.ResourceItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ResourceComponent("customer")
public class CustomersResource extends Resource<ResourceItem> {

    @Override
    public Collection<ResourceItem> find(Query query) {
        ResourceItem canduli1 = new ResourceItem()
            .set("name", "Pepech")
            .set("lastName", "Candulich")
            .set("age", 35);

        List<ResourceItem> list = new ArrayList<>();
        list.add(canduli1);
        return list;
    }
}
