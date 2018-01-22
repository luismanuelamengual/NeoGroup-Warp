package example;

import org.neogroup.warp.data.DataException;
import org.neogroup.warp.data.DataSource;
import org.neogroup.warp.data.DataSourceComponent;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

@DataSourceComponent
public class MainDataSource extends DataSource {

    private final PGSimpleDataSource dataSource;

    public MainDataSource() {

        dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPortNumber(5432);
        dataSource.setDatabaseName("testdb");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
    }

    @Override
    protected Connection requestConnection() {

        try {
            return dataSource.getConnection();
        }
        catch (SQLException ex) {
            throw new DataException(ex);
        }
    }
}
