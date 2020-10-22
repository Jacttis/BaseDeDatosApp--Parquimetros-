package VentanaInspector;

import quick.dbtable.DBTable;

import javax.swing.*;
import java.awt.*;

public class VistaInspector extends JFrame {


    public VistaInspector(Inspector inspector, DBTable tabla) {
        super("Inspector");
        setVisible(true);
        setResizable(false);
        setPreferredSize(new Dimension(1028, 600));
        this.setBounds(600, 300, 1028, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
