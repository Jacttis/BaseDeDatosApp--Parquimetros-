package Login;

import quick.dbtable.DBTable;

import javax.swing.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login {
    private DBTable tabla;

    public Login(){
        tabla=new DBTable();
    }

    /**
     * Devuelve la tabla de la base de datos
     * @return DBTable
     */
    public DBTable getTabla() {
        return tabla;
    }

    /**
     * Establece la tabla de la base de datos
     * @param tabla
     */
    public void setTabla(DBTable tabla) {
        this.tabla = tabla;
    }

    /**
     * Conecta la base de datos a travez de un usuario y contrasenia conectandose en localhost
     *
     * @param usuario
     * @param clave
     * @throws SQLException
     */
    public void conectarBD(String usuario, String clave) throws SQLException{
        try {
            String driver ="com.mysql.cj.jdbc.Driver";
            String servidor = "localhost:3306";
            String baseDatos = "parquimetros";
            String uriConexion = "jdbc:mysql://" + servidor + "/" +
                    baseDatos +"?serverTimezone=America/Argentina/Buenos_Aires";

            //establece una conexi�n con la  B.D. "parquimetros"  usando directamante una tabla DBTable
            tabla.connectDatabase(driver, uriConexion, usuario, clave);
            System.out.println("Success");

        }
        catch (SQLException ex) {

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

    /**
     * Recibe como parametro un legajo y contrasenia y verifica si existe un inspector con esos
     * datos si existe devuelve verdadero sino devuelve falso
     * @param usuario
     * @param contrasenia
     * @return boolean
     * @throws SQLException
     */
    public boolean verificarUsuario(String usuario , String contrasenia) throws SQLException {
        boolean verificado=false;
        this.conectarBD("inspector","inspector");
        Connection connection= tabla.getConnection();
        Statement statement=connection.createStatement();
        ResultSet rs=statement.executeQuery("SELECT * FROM inspectores WHERE legajo="+usuario+" and password=MD5("+contrasenia+")");

        if(rs.next()){
            String dni=rs.getString("dni");
            if(dni!=null){
                verificado=true;
            }
        }
        rs.close();
        statement.close();
        return verificado;

    }

}
