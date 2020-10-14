package Main;

import java.awt.*;
import Login.VistaLogin;



public class Main {

    public static void main(String[] args) {


        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VistaLogin login= new VistaLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
