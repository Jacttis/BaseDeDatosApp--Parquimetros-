package Login;

import quick.dbtable.DBTable;

import javax.swing.*;
import java.sql.SQLException;

public class Login {
    private DBTable tabla;
    private VistaLogin GUI;
    
    public DBTable getTabla() {
        return tabla;
    }

    public void setTabla(DBTable tabla) {
        this.tabla = tabla;
    }



    public Login(){
        tabla=new DBTable();
    }

    public void conectarBD(String usuario, String clave) throws SQLException{

        try
        {
            String driver ="com.mysql.cj.jdbc.Driver";
            String servidor = "localhost:3306";
            String baseDatos = "parquimetros";
            String uriConexion = "jdbc:mysql://" + servidor + "/" +
                    baseDatos +"?serverTimezone=America/Argentina/Buenos_Aires";

            //establece una conexi�n con la  B.D. "batallas"  usando directamante una tabla DBTable
            tabla.connectDatabase(driver, uriConexion, usuario, clave);
            System.out.println("Success");

        }
        catch (SQLException ex)
        {

            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            throw ex;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }




}
