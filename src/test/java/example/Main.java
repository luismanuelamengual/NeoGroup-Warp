package example;

import org.neogroup.warp.WarpApplication;

public class Main {

    public static void main(String[] args) throws Exception {

        // QueryParser parser = new DefaultQueryParser();
        // Query query = parser.parseQuery(" SELECT rama, SUM(para) , timpa AS cartuli  FROM ponti   WHERE(rama  like 'tam \\'rama\\' su\"per') AND pepe.ramuli  = 3 LIMIT 10  START 70");

        WarpApplication application = new WarpApplication(8080, true);
        application.start();
    }
}
