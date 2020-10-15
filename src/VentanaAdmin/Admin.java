package VentanaAdmin;

import quick.dbtable.DBTable;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Vector;

public class Admin {
    private DBTable tabla;

    public Admin(DBTable tabla) {
        this.tabla = tabla;
    }

    public DefaultListModel<String> crearLista(String consulta) {

        DefaultListModel<String> listModel = new DefaultListModel<String>();
        try {
            tabla.setSelectSql(consulta);
            tabla.createColumnModelFromQuery();
            tabla.refresh();
            Vector vector = tabla.getDataVector();

            for (int i = 0; i < vector.size(); i++) {

                listModel.addElement(vector.get(i).toString());

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listModel;
    }


}