package example.persons;

import org.neogroup.warp.utils.json.Json;
import org.neogroup.warp.utils.json.JsonElement;
import org.neogroup.warp.utils.json.JsonSerializable;

public class Person implements JsonSerializable {

    private String name;
    private String lastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public JsonElement toJson() {
        return Json.object()
            .set("name", name)
            .set("lastName", lastName);
    }
}
