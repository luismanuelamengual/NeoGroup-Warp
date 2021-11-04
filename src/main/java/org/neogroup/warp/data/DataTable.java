package org.neogroup.warp.data;

import org.neogroup.warp.data.query.QueryObject;

import java.util.Collection;

public class DataTable extends QueryObject<DataTable> {

    private DataConnection connection;

    public DataTable(DataConnection connection, String table) {
        super(table);
        this.connection = connection;
    }

    public Collection<DataObject> find() {
        return connection.query(createSelectQuery());
    }

    public int insert () {
        return connection.execute(createInsertQuery());
    }

    public int update () {
        return connection.execute(createUpdateQuery());
    }

    public int delete () {
        return connection.execute(createDeleteQuery());
    }

    public DataObject first () {
        Collection<DataObject> objects = limit(1).find();
        return !objects.isEmpty()? objects.iterator().next() : null;
    }
}