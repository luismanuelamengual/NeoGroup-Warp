package example.customers;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.controllers.routing.Parameter;
import org.neogroup.warp.data.DataElement;

import java.util.Collection;

import static org.neogroup.warp.Warp.getResource;

@ControllerComponent
public class CustomersController {

    @Get("customers/:id")
    public DataElement getCustomer(@Parameter("id") int customerId) {
        return getResource("customer").where("id", customerId).first();
    }

    @Get("customers")
    public Collection<DataElement>getCustomers() {
        return getResource("customer").find();
    }
}
