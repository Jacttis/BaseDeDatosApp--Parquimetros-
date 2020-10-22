package VentanaInspector;

import quick.dbtable.DBTable;

import javax.swing.*;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class InspectorLogica {

    private DefaultListModel<String> listaPatentes;
    private LinkedList<String> listaPatentesM;
    private Inspector inspector;
    private DBTable tabla;

    public InspectorLogica(Inspector inspector,DBTable tabla){
        this.inspector=inspector;
        this.tabla=tabla;
        listaPatentesM=new LinkedList<String>();
        listaPatentes=new DefaultListModel<String>();
    }

    /**
     *
     * @param patente
     */
    public void agregarPatente(String patente){
        listaPatentes.addElement(patente);
        listaPatentesM.add(patente);
    }


   private boolean verificarPermiso(String ubicacion,int altura,Date fecha,Time hora){
        


    }

    /**
     * elimina las patentes que no van a ser multadas
     * @param ubicacion
     * @param altura
     */
    private void patentesAMultar(String ubicacion,int  altura){
        try {
            Connection conexion=tabla.getConnection();
            Statement statement= conexion.createStatement();
            String alturaS=String.valueOf(altura);
            ResultSet rs=statement.executeQuery("SELECT patente FROM estacionados WHERE calle='"+ubicacion+"' and altura="+altura+";");
            while(rs.next()){
                String patente=rs.getString("patente");
                listaPatentesM.remove(patente);
            }

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Verifica si el inspector puede realizar la multa en esa ubicacion
     * Controla la lista de patentes multar y las agrega a la BD Multas
     * @param calle
     * @param altura
     */
    public void agregarMultas(String calle, int altura){
        try {
           // verificarPermiso();
            patentesAMultar(calle,altura);
            Date fecha=null;
            Time hora=null;
            inspector.setAlturaSeleccionado(altura);
            inspector.setCalleSeleccionado(calle);

            Connection conexion=tabla.getConnection();
            Statement statement= conexion.createStatement();
            ResultSet rsFecha= statement.executeQuery("SELECT CURDATE(),CURTIME();");
            if(rsFecha.next()) {
                fecha = rsFecha.getDate("CURDATE()");
                hora = rsFecha.getTime("CURTIME()");
            }
            int id_asociado_con=inspector.getID(tabla);

            for(String p:listaPatentesM) {
                int rs = statement.executeUpdate("INSERT INTO multa(fecha,hora,patente,id_asociado_con) VALUE ('"+fecha+ "','" +hora+ "','"+p+"',"+id_asociado_con+");");

            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


}
