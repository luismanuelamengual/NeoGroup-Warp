package example.persons;

import org.neogroup.warp.models.ModelManager;
import org.neogroup.warp.models.ModelManagerComponent;
import org.neogroup.warp.models.ModelQuery;

import java.util.Collection;

@ModelManagerComponent
public class PersonsManager extends ModelManager<Person> {

    @Override
    public Person create(Person model, Object... params) {
        return null;
    }

    @Override
    public Person update(Person model, Object... params) {
        return null;
    }

    @Override
    public Person delete(Person model, Object... params) {
        return null;
    }

    @Override
    public Person retrieve(Object id, Object... params) {
        return null;
    }

    @Override
    public Collection<Person> retrieve(ModelQuery query, Object... params) {
        return null;
    }
}
