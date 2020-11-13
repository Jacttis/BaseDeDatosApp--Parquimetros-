package VentanaAdmin;

import quick.dbtable.DBTable;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Admin {
    private DBTable tabla;

    public Admin(DBTable tabla) {
        this.tabla = tabla;
    }

    /**
     * Crea una lista con la consulta y la columna pasada por parametro y devuelve una DefaultListModel de string
     * @param consulta
     * @param columna
     * @return DefaultListModel<String>
     */
    public DefaultListModel<String> crearLista(String consulta,String columna) {
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        try {
            Connection connection= tabla.getConnection();
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(consulta);
            while(rs.next()){
                String nombre=rs.getString(columna);
                listModel.addElement(nombre);
            }
            rs.close();
            statement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listModel;
    }
}