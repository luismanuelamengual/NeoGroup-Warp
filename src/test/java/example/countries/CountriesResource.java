package example.countries;

import org.neogroup.warp.data.Data;
import org.neogroup.warp.data.DataCollection;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;

@ResourceComponent("country")
public class CountriesResource extends Resource {

    public DataCollection find(SelectQuery query) {
        return Data.collection()
            .add(Data.object()
                .set("id", 1)
                .set("name", "Argentina"))
            .add(Data.object()
                .set("id", 2)
                .set("name", "Brazil"))
            .add(Data.object()
                .set("id", 3)
                .set("name", "Chile"))
            .add(Data.object()
                .set("id", 4)
                .set("name", "Uruguay"));
    }
}
