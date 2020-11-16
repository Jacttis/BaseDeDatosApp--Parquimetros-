package VentanaInspector;

import quick.dbtable.DBTable;

import java.sql.*;
import java.util.Date;

public class Inspector {
    public static final int FUERA_TURNO = -1;
    private int dni;
    private String nombre;
    private String apellido;
    private int legajo;
    private String calleSeleccionado;
    private int alturaSeleccionado;

    public Inspector(int dni, String nombre, String apellido, int legajo) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.legajo = legajo;
        calleSeleccionado=null;
        alturaSeleccionado=0;
    }


    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }


    /**
     * Obtiene el id_asociado_con que se pide en cierto dia y turno con este inspector
     * @param tabla
     * @param dia
     * @param turno
     * @return
     */
    public int getID(DBTable tabla,String dia,String turno){
        int id=0;
        try {
            Connection conexion=tabla.getConnection();
            Statement statement= conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id_asociado_con FROM asociado_con WHERE legajo="+legajo+" and calle='"+calleSeleccionado+
                    "' and altura="+alturaSeleccionado+" and dia='"+dia+"' and turno='"+turno+"';");
            if(rs.next()){
                id=rs.getInt("id_asociado_con");
            }
            else{
                id=FUERA_TURNO;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;

    }

    public String getCalleSeleccionado() {
        return calleSeleccionado;
    }

    public void setCalleSeleccionado(String calleSeleccionado) {
        this.calleSeleccionado = calleSeleccionado;
    }

    public int getAlturaSeleccionado() {
        return alturaSeleccionado;
    }

    public void setAlturaSeleccionado(int alturaSeleccionado) {
        this.alturaSeleccionado = alturaSeleccionado;
    }
}
