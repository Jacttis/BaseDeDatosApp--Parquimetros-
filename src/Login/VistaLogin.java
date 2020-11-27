package Login;

import VentanaAdmin.VistaAdmin;
import VentanaInspector.Inspector;
import VentanaInspector.VistaInspector;
import VentanaParquimetro.VistaParquimetro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VistaLogin extends JFrame {


    private JPanel contentPane;
    private JTextField user;
    private JPasswordField password;
    private JButton connection;
    private JLabel labelUser;
    private JLabel ayuda;
    private JLabel jLabelPassword;
    private Login login;
    private JButton goToParquimetros;


    public VistaLogin(){
        super("Login");
        setVisible(true);
        setBounds(600, 300, 300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        login =new Login();

        // Panel del JFRAME
        contentPane=new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(Color.lightGray);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        //Texto Usuario y label de usuario y el label de ayuda
        labelUser = new JLabel("Usuario - ");
        labelUser.setBounds(10,100,70,20);

        //label de ayuda
        ayuda=new JLabel("?");
        ayuda.setBounds(250,100,10,10);
        ayuda.setToolTipText("El usuario es el legajo para los inspectores");

        //Texto Usuario
        user=new JTextField();
        user.setBounds(90,100,150,20);
        contentPane.add(user);
        contentPane.add(labelUser);
        contentPane.add(ayuda);

        //label Contrasenia
        jLabelPassword = new JLabel("Contrasenia - ");
        jLabelPassword.setBounds(10,130,120,20);

        //Contrasenia Field
        password=new JPasswordField();
        password.setBounds(90,130,150,20);
        contentPane.add(password);
        contentPane.add(jLabelPassword);

        //Boton Conectar
        connection= new JButton("Conectar");
        connection.setBounds(10,160,100,20);
        contentPane.add(connection);
        connection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnConectar(evt,user.getText(),String.valueOf(password.getPassword()));
            }
        });

        goToParquimetros=new JButton("Conexion parquimetro");
        goToParquimetros.setBounds(10,190,180,20);
        contentPane.add(goToParquimetros);
        goToParquimetros.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnParquimetro(evt);
            }
        });
    }

    /**
     * Listener del btnConectar.
     * Verifica el usuario si es admin controla con la base de datos, si no es admin llama a verificarUsuario
     * de la logica del login par verificar si es inspector para conectarse
     *
     * @param evt
     * @param user
     * @param password
     */
    private void btnConectar(ActionEvent evt,String user,String password){
        boolean conecto=false;
        try{

            if(user.equals("admin")){
                login.conectarBD(user,password);
                VistaAdmin vistaAdmin=new VistaAdmin(login.getTabla());
                this.dispose();
            }
            else{
                Inspector inspector = login.verificarInspector(user,password);
                if(inspector!=null){
                    VistaInspector inspectorV=new VistaInspector(inspector, login.getTabla());
                    conecto=true;
                }
                else{
                    JOptionPane.showMessageDialog(
                            this,
                            "Legajo/Contrasenia incorrecta.\n",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

            if(conecto){
                this.dispose();
            }
        }

        catch (SQLException ex){
            JOptionPane.showMessageDialog(this,
                    "Se produjo un error al intentar conectarse a la base de datos.\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void btnParquimetro(ActionEvent evt){
        try {
            login.conectarBD("parquimetro","parq");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        VistaParquimetro vistaParquimetro=new VistaParquimetro(login.getTabla());
        this.dispose();
    }



}
