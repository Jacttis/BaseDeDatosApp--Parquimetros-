package VentanaParquimetro;

import VentanaInspector.InspectorLogica;
import quick.dbtable.DBTable;

import javax.swing.*;
import java.awt.*;

public class VistaParquimetro extends JFrame {

    private JPanel contentPane;
    private JComboBox<String> calles;
    private JComboBox<Integer> parquimetros;
    private JComboBox<Integer> tarjetas;
    private JLabel jLabelubicacion;
    private JLabel jLabelParqu;
    private DBTable tabla;

    public VistaParquimetro(DBTable tabla){
        super("Conexion Tarjetas");
        setVisible(true);
        setResizable(false);
        setPreferredSize(new Dimension(1028, 600));
        this.setBounds(600, 300, 1028, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.tabla=tabla;
    }
}
