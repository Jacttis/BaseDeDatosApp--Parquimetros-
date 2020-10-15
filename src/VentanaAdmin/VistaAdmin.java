package VentanaAdmin;

import quick.dbtable.DBTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Types;

public class VistaAdmin extends JFrame {

    private JPanel contentPane;
    private JTextArea txtConsulta;
    private JButton btnBorrar;
    private JButton btnEjecutar;
    private DBTable tabla;
    private JList listaTablas;
    private DefaultListModel<String> dListaTabla;
    private JList<String> listaColumnas;
    private DefaultListModel<String> dListaColumnas;
    private Admin admin;


    public VistaAdmin(DBTable tabla){
        admin=new Admin(tabla);
        setVisible(true);
        setResizable(false);
        setPreferredSize(new Dimension(1028, 600));
        this.setBounds(600, 300, 1028, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel
        contentPane=new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        //Creo el area a escribir comandos
        txtConsulta=new JTextArea();
        txtConsulta.setBounds(0,3,600,70);
        contentPane.add(txtConsulta);

        //Boton Ejecutar
        btnEjecutar =new JButton("Ejecutar");
        btnEjecutar.setBounds(601,0,100,35);
        contentPane.add(btnEjecutar);

        //Boton Ejecutar
        btnBorrar =new JButton("Borrar");
        btnBorrar.setBounds(601,35,100,35);
        contentPane.add(btnBorrar);
        btnEjecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEjecutarActionPerformed(e);
            }
        });

        //Tabla
        this.tabla=tabla;
        tabla.setBounds(0,100,701,460);
        contentPane.add(tabla);

       //Lista con todas las tablas
        listaColumnas=new JList<String>();
        listaColumnas.setBounds(868,100,162,262);
        contentPane.add(listaColumnas);



        //Lista con todas las tablas
        dListaTabla=admin.crearLista("SHOW TABLES","Tables_in_parquimetros");
        listaTablas=new JList(dListaTabla);
        listaTablas.setBounds(705,100,162,262);
        listaTablas.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        contentPane.add(listaTablas);

        listaTablas.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                        System.out.println(dListaTabla.getElementAt(e.getLastIndex()));
                        dListaColumnas=admin.crearLista("describe "+dListaTabla.getElementAt(e.getLastIndex()),"Field");
                        listaColumnas.setModel(dListaColumnas);
                        listaTablas.clearSelection();
                }
                //admin.crearLista("Show "+listaTablas.getSelectedValue());
            }
        });






    }
    private void btnEjecutarActionPerformed(ActionEvent evt) {
        this.refrescarTabla();
    }


    private void refrescarTabla()
    {
        try
        {
            // seteamos la consulta a partir de la cual se obtendr�n los datos para llenar la tabla
            tabla.setSelectSql(this.txtConsulta.getText().trim());

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