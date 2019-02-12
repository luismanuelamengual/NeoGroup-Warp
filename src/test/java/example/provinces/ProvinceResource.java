package example.provinces;

import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ResourceComponent("province")
public class ProvinceResource extends Resource<Province> {

    @Override
    public Collection<Province> find(SelectQuery query) {
        List<Province> provinces = new ArrayList<>();
        Province province = new Province();
        province.setId(1);
        province.setName("Mendoza");
        provinces.add(province);
        return provinces;
    }
}
