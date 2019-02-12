package example.countries;

import org.neogroup.warp.data.Data;
import org.neogroup.warp.data.DataCollection;
import org.neogroup.warp.data.DataObject;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ResourceComponent("country")
public class CountriesResource extends Resource<DataObject> {

    public Collection<DataObject> find(SelectQuery query) {

        List<DataObject> collection = new ArrayList<>();
        collection.add(Data.object()
            .set("id", 1)
            .set("name", "Argentina"));
        collection.add(Data.object()
            .set("id", 2)
            .set("name", "Brazil"));
        collection.add(Data.object()
            .set("id", 3)
            .set("name", "Chile"));
        collection.add(Data.object()
            .set("id", 4)
            .set("name", "Uruguay"));

        return collection;
    }
}
