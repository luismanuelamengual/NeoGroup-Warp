package example;

import org.neogroup.warp.data.DataSource;
import org.neogroup.warp.data.DataSourceComponent;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

@DataSourceComponent("main")
public class MainDataSource extends DataSource {

    private PGSimpleDataSource dataSource;

    public MainDataSource() {
        dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPortNumber(5432);
        dataSource.setDatabaseName("testdb");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
    }

    @Override
    protected Connection requestConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
