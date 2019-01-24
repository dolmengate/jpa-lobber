package info.sroman;

import java.io.IOException;

public class Application {

    public static void main(String[] args )
    {
        System.out.println("Lobber at Application starting...");
        JPALobber lobber = null;
        try {
            lobber = new JPALobber(args);
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        lobber.lob();
    }
}
