package example;

import org.neogroup.warp.WarpApplication;

public class Main {

    public static void main(String[] args) throws Exception {

        WarpApplication application = new WarpApplication(8080);
        application.start();
    }
}
