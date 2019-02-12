package example.persons;

import org.neogroup.warp.data.Data;
import org.neogroup.warp.data.DataCollection;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;

@ResourceComponent("person")
public class PersonsResource extends Resource {

    @Override
    public DataCollection find(SelectQuery query) {

        return Data.collection()
            .add(Data.object()
                .set("name", "Luis")
                .set("lastName", "Amengual"))
            .add(Data.object()
                .set("name", "Jessica")
                .set("lastName", "Alba"));
    }
}
