package example.sources;

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
        dataSource.setServerName("192.168.1.123");
        dataSource.setPortNumber(5432);
        dataSource.setDatabaseName("db3g");
        dataSource.setUser("postgres");
        dataSource.setPassword("post2250sitrack");
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
