package VentanaInspector;

import quick.dbtable.DBTable;

import java.sql.SQLException;

public class Prueba {
    public static DBTable tabla=new DBTable();
    public static void conectarBD(String usuario, String clave) {
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
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        Inspector inspector=new Inspector(41097481,"Axel","Fontana",1);
        conectarBD("inspector","inspector");
        InspectorLogica inspectorLogica= new InspectorLogica(inspector,tabla);
        inspectorLogica.agregarPatente("PIV519");
        inspectorLogica.agregarPatente("PIV518");
        inspectorLogica.agregarMultas("Zapiola",1100);


    }
}
