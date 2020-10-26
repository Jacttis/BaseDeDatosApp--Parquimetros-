package VentanaInspector;

import quick.dbtable.DBTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class VistaInspector extends JFrame {
    JPanel contentPane;
    JComboBox<String> calles;
    JButton crearMulta;
    DBTable tabla;
    JButton agregarPatente;
    Inspector inspector;
    InspectorLogica logica;
    JLabel ubicacion;
    JLabel multasL;

    public VistaInspector(Inspector inspector, DBTable tabla) {

        super("Unidad Personal");
        setVisible(true);
        setResizable(false);
        setPreferredSize(new Dimension(1028, 600));
        this.setBounds(600, 300, 1028, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.tabla=tabla;
        this.inspector=inspector;
        logica=new InspectorLogica(this.inspector,this.tabla);

        contentPane=new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //Label Ubicacion y MultasL

        ubicacion = new JLabel("Elejir Ubicacion -");
        ubicacion.setBounds(300,35,150, 35);
        contentPane.add(ubicacion);

        multasL = new JLabel("Multas Labradas :");
        multasL.setBounds(100,120,150,35);
        contentPane.add(multasL);

        //Boton patente
        agregarPatente = new JButton("Agregar Patente");
        agregarPatente.setBounds(100, 35, 180, 35);
        agregarPatente.setEnabled(true);
        agregarPatente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarNuevaPatente(e);
            }
        });
        contentPane.add(agregarPatente);

        //ComboBox Calles
        calles=new JComboBox<String>();
        calles.setBounds(400,35,180,35);
        agregarCalles();
        contentPane.add(calles);

        //tabla
        this.tabla.setBounds(100,150,700,400);
        contentPane.add(this.tabla);


        //Boton multa
        crearMulta=new JButton("Generar Multas");
        crearMulta.setBounds(100,70,180,35);
        crearMulta.setEnabled(false);
        crearMulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(generarMultas(e)){
                    JOptionPane.showMessageDialog(null,"Se crearon las multas");
                }
                else{
                    JOptionPane.showMessageDialog(null,"No tiene permiso");
                }
            }
        });
        contentPane.add(crearMulta);




    }

    private void agregarNuevaPatente(ActionEvent event){
        crearMulta.setEnabled(true);
        agregarPatente.setEnabled(false);
        boolean pvalida = false;
        while(!pvalida){
            String nombrePatente = JOptionPane.showInputDialog("Ingrese el nombre de la patente");
            if(nombrePatente.length() != 6){
                JOptionPane.showMessageDialog(null,"Error, ingresar una patente de 6 caracteres");
            }
            else{
                pvalida = true;
                logica.agregarPatente(nombrePatente);
            }
        }
        agregarPatente.setEnabled(true);

    }


    private boolean generarMultas(ActionEvent e){
        String calleAltura= (String) calles.getSelectedItem();
        String[] ca=calleAltura.split(" ");
        boolean ret=logica.agregarMultas(ca[0],Integer.parseInt(ca[1]));
        if(ret){
            refrescarTabla();
        }
        return ret;
    }


    private void agregarCalles(){
        Connection connection= tabla.getConnection();
        Statement statement= null;
        try {
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery("SELECT calle,altura FROM asociado_con WHERE legajo="+inspector.getLegajo()+";");

            while(rs.next()){
                String c=rs.getString("calle");
                int a=rs.getInt("altura");
                calles.addItem(c+" "+a);
            }
            rs.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    /**
     * Refresca la tabla con la consulta obtenida en el JTextArea
     */
    private void refrescarTabla()
    {
        try
        {
            // seteamos la consulta a partir de la cual se obtendr�n los datos para llenar la tabla
            tabla.setSelectSql("SELECT numero,patente,fecha,hora,calle,altura,legajo " +
                                "FROM multa NATURAL JOIN asociado_con " +
                                "WHERE fecha='"+logica.getDiaMulta()+"' and hora='"+logica.getHoraMulta()+"' and legajo="+inspector.getLegajo()+";");

            // obtenemos el modelo de la tabla a partir de la consulta para
            // modificar la forma en que se muestran de algunas columnas
            tabla.createColumnModelFromQuery();
            for (int i = 0; i < tabla.getColumnCount(); i++)
            { // para que muestre correctamente los valores de tipo TIME (hora)
                if	 (tabla.getColumn(i).getType()== Types.TIME)
                {
                    tabla.getColumn(i).setType(Types.CHAR);
                }
                // cambiar el formato en que se muestran los valores de tipo DATE
                if	 (tabla.getColumn(i).getType()==Types.DATE)
                {
                    tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
                }
            }
            // actualizamos el contenido de la tabla.
            tabla.refresh();
            // No es necesario establecer  una conexi�n, crear una sentencia y recuperar el
            // resultado en un resultSet, esto lo hace autom�ticamente la tabla (DBTable) a
            // patir de la conexi�n y la consulta seteadas con connectDatabase() y setSelectSql() respectivamente.



        }
        catch (SQLException ex)
        {
            // en caso de error, se muestra la causa en la consola
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                    ex.getMessage() + "\n",
                    "Error al ejecutar la consulta.",
                    JOptionPane.ERROR_MESSAGE);

        }

    }
}
