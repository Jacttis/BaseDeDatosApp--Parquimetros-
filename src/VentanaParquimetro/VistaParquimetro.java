package VentanaParquimetro;

import Login.VistaLogin;
import VentanaInspector.InspectorLogica;
import quick.dbtable.DBTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.LinkedList;

public class VistaParquimetro extends JFrame {

    private JPanel contentPane;
    private JComboBox<String> calles;
    private JComboBox<Integer> parquimetros;
    private JComboBox<Integer> tarjetas;
    private JLabel jLabelubicacion;
    private JLabel jLabelParqu;
    private JLabel jLabelTarjeta;
    private DBTable tabla;
    private JButton menuPrincipal;
    private JButton conectarTarjeta;

    public VistaParquimetro(DBTable tabla){
        super("Conexion Tarjetas");
        setVisible(true);
        setResizable(false);
        setPreferredSize(new Dimension(1028, 600));
        this.setBounds(600, 300, 1028, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.tabla=tabla;


        contentPane=new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        jLabelubicacion = new JLabel("Elegir Ubicacion -");
        jLabelubicacion.setBounds(300,35,150, 35);
        contentPane.add(jLabelubicacion);

        jLabelParqu = new JLabel("Elegir Parquimetro -");
        jLabelParqu.setBounds(285,73,150, 35);
        contentPane.add(jLabelParqu);

        jLabelTarjeta = new JLabel("Elegir Tarjeta -");
        jLabelTarjeta.setBounds(285,105,150, 35);
        contentPane.add(jLabelTarjeta);

        //ComboBox parquimetros
        parquimetros=new JComboBox<Integer>();
        parquimetros.setBounds(400,73,180,35);
        contentPane.add(parquimetros);

        //ComboBox Calles
        calles=new JComboBox<String>();
        calles.setBounds(400,35,180,35);
        calles.addItem(" ");
        agregarCalles();
        contentPane.add(calles);

        calles.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED && !e.getItem().equals(" ")){
                    String calle = (String) e.getItem();
                    String[] ca=calle.split(" ");
                    agregarParquimetros(ca[0],Integer.parseInt(ca[1]));
                }
            }
        });
        //ComboBox Calles
        tarjetas=new JComboBox<Integer>();
        tarjetas.setBounds(400,105,180,35);
        contentPane.add(tarjetas);
        agregarTarjetas();

        //tabla
        this.tabla.setBounds(100,150,700,400);
        contentPane.add(this.tabla);

        //Boton menuPrincipal
        menuPrincipal = new JButton("Menu Principal");
        menuPrincipal.setBounds(800, 70,180, 35);
        menuPrincipal.setEnabled(true);
        menuPrincipal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VistaLogin vistaL = new VistaLogin();
                try {
                    tabla.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                dispose();
            }
        });
        contentPane.add(menuPrincipal);

        conectarTarjeta=new JButton("Conectar Tarjeta");
        conectarTarjeta.setBounds(620,70,180,35);
        conectarTarjeta.setEnabled(true);
        conectarTarjeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refrescarTabla();
            }
        });
        contentPane.add(conectarTarjeta);

    }

    /**
     * Agrega las calles al combo box el cual tiene asignado dicho inspector.
     */
    private void agregarCalles(){
        Connection connection= tabla.getConnection();
        Statement statement= null;
        LinkedList<String> verificacion=new LinkedList<String>();
        try {
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery("SELECT calle,altura FROM ubicaciones;");

            while(rs.next()){
                String c=rs.getString("calle");
                int a=rs.getInt("altura");
                if(!verificacion.contains(c+" "+a)) {
                    calles.addItem(c + " " + a);
                    verificacion.add(c+" "+a);
                }
            }
            rs.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    private void agregarTarjetas(){
        Connection connection= tabla.getConnection();
        Statement statement= null;
        LinkedList<Integer> verificacion=new LinkedList<Integer>();
        try {
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery("SELECT id_tarjeta FROM tarjetas;");

            while(rs.next()){
                int a=rs.getInt("id_tarjeta");
                if(!verificacion.contains(a)) {
                    tarjetas.addItem(a);
                    verificacion.add(a);
                }
            }
            rs.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    /**
     * Agrega las calles al combo box el cual tiene asignado dicho inspector.
     */
    private void agregarParquimetros(String calle,int altura){
        parquimetros.removeAllItems();
        Connection connection= tabla.getConnection();
        Statement statement= null;
        LinkedList<Integer> verificacion=new LinkedList<Integer>();
        try {
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery("SELECT id_parq FROM parquimetros WHERE calle='"+calle+"' and altura="+altura);

            while(rs.next()){
                int id_parq=rs.getInt("id_parq");
                if(!verificacion.contains(id_parq)) {
                    parquimetros.addItem(id_parq);
                    verificacion.add(id_parq);
                }
            }
            rs.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }

    private void refrescarTabla()
    {
            try {
                Integer id_t = (Integer) tarjetas.getSelectedItem();
                Integer id_p=(Integer) parquimetros.getSelectedItem();
                if(id_p!=null && id_t!=null) {
                    String sql = "CALL conectar(" + id_t + "," + id_p + ")";
                    Connection c = tabla.getConnection();
                    Statement st = c.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    tabla.refresh(rs);
                    for (int i = 0; i < tabla.getColumnCount(); i++) {
                        if (tabla.getColumn(i).getType() == Types.TIME) {
                            tabla.getColumn(i).setType(Types.CHAR);
                        }
                        if (tabla.getColumn(i).getType() == Types.DATE) {
                            tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Por favor elija una tarjeta y un parquimetro");
                }

            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n",
                        "Error al ejecutar la consulta.", JOptionPane.ERROR_MESSAGE);

            }

    }
}
