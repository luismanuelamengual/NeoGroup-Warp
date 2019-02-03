package example;

import org.neogroup.warp.data.DataSourceComponent;
import org.postgresql.ds.PGSimpleDataSource;

@DataSourceComponent("main")
public class MainDataSource extends PGSimpleDataSource {

    public MainDataSource() {
        setServerName("localhost");
        setPortNumber(5432);
        setDatabaseName("testdb");
        setUser("postgres");
        setPassword("postgres");
    }
}
