package example.countries;

import org.neogroup.warp.data.Data;
import org.neogroup.warp.data.DataObject;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@ResourceComponent("country")
public class CountriesResource extends Resource<DataObject> {

    private Map<Integer, DataObject> store;

    public CountriesResource() {
        this.store = new HashMap<>();
        this.store.put(1, Data.object().set("id", 1).set("name", "Argentina"));
        this.store.put(2, Data.object().set("id", 2).set("name", "Brazil"));
        this.store.put(3, Data.object().set("id", 3).set("name", "Chile"));
        this.store.put(4, Data.object().set("id", 4).set("name", "Uruguay"));
    }

    @Override
    public DataObject find(Object id) {
        if (!(id instanceof Integer)) {
            id = Integer.parseInt(id.toString());
        }
        return store.get(id);
    }

    public Collection<DataObject> find(SelectQuery query) {
        return this.store.values();
    }
}
