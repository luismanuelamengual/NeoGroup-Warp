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
    public Collection<Person> read(SelectQuery query) {

        List<Person> persons = new ArrayList<>();
        Person person1 = new Person();
        person1.setName("Luis");
        person1.setLastName("Amengual");
        persons.add(person1);

        Person person2 = new Person();
        person2.setName("Jessica");
        person2.setLastName("Alba");
        person2.setAge(35);
        persons.add(person2);
        return persons;
    }
}
