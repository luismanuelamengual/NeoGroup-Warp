package example.persons;

import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ResourceComponent("person")
public class PersonsResource extends Resource<Person> {

    @Override
    public Collection<Person> find(SelectQuery query) {

        List<Person> persons = new ArrayList<>();

        Person person1 = new Person();
        person1.setId(1);
        person1.setName("Luis");
        person1.setLastName("Amengual");
        person1.setAge(36);

        persons.add(person1);
        return persons;
    }
}
