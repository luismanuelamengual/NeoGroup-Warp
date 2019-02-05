package example.customers;

import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.routing.Get;
import org.neogroup.warp.resources.ResourceItem;

import static org.neogroup.warp.Warp.getResource;

@ControllerComponent
public class CustomersController {

    @Get("customers")
    public ResourceItem getCustomers() {
        return getResource("customer").first();
    }
}
