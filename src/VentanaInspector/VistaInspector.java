package VentanaInspector;

import quick.dbtable.DBTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.LinkedList;

public class VistaInspector extends JFrame {
    JPanel contentPane;
    JComboBox<String> calles;
    JComboBox<Integer> parquimetros;
    JButton crearMulta;
    DBTable tabla;
    JButton agregarPatente;
    Inspector inspector;
    InspectorLogica logica;
    JLabel jLabelubicacion;
    JLabel multasL;
    JLabel jLabelParqu;

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

        jLabelubicacion = new JLabel("Elegir Ubicacion -");
        jLabelubicacion.setBounds(300,35,150, 35);
        contentPane.add(jLabelubicacion);

        jLabelParqu = new JLabel("Elegir Parquimetro -");
        jLabelParqu.setBounds(285,73,150, 35);
        contentPane.add(jLabelParqu);

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
                try {
                    if(!calles.getSelectedItem().equals(" ")) {
                        if (generarMultas(e)) {
                            JOptionPane.showMessageDialog(null, "Se crearon las multas");
                        } else {
                            JOptionPane.showMessageDialog(null, "No tiene permiso");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Seleccione una ubicacion");
                    }
                } catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(null,"Patente de automovil no reconocida\nvuelva a caargar las patentes correctamente");
                    logica.getListaPatentesModel().clear();
                    logica.getListaPatentesMulta().clear();

                }
            }
        });
        contentPane.add(crearMulta);




    }

    /**
     * LLama a la logica para agregar una nueva patente a verificar si esta multada
     * @param event
     */
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

    /**
     * LLama a la logica para generar las multas correspondientes
     * @param e Evento del action listener que se llama
     * @return
     */
    private boolean generarMultas(ActionEvent e) throws SQLException {
        String calleAltura= (String) calles.getSelectedItem();
        String[] ca=calleAltura.split(" ");
        int id_parq= (int) parquimetros.getSelectedItem();
        boolean ret=logica.agregarMultas(ca[0],Integer.parseInt(ca[1]));
        logica.marcarAcceso(id_parq);

        if(ret){
            refrescarTabla();
        }
        return ret;
    }

    /**
     * Agrega las calles al combo box el cual tiene asignado dicho inspector.
     */
    private void agregarCalles(){
        Connection connection= tabla.getConnection();
        Statement statement= null;
        LinkedList<String> verificacion=new LinkedList<String>();
        boolean primero=false;
        try {
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery("SELECT calle,altura FROM asociado_con WHERE legajo="+inspector.getLegajo()+";");

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

    /**
     * Refresca la tabla con la consulta obtenida en el JTextArea(Dado por la catedra)
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
