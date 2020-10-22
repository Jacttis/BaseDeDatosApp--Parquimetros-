package Login;

import VentanaAdmin.VistaAdmin;
import VentanaInspector.Inspector;
import VentanaInspector.VistaInspector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;

public class VistaLogin extends JFrame {


   private JPanel contentPane;
   private JTextField user;
   private JPasswordField password;
   private JButton connection;
   private JLabel username;
    private JLabel ayuda;
    private JLabel jLabelPassword;
    private Login login;


   public VistaLogin(){

       setVisible(true);
       setBounds(600, 300, 800, 600);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setResizable(false);
       login =new Login();

       // Panel del JFRAME
       contentPane=new JPanel();
       contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
       contentPane.setLayout(null);
       setContentPane(contentPane);

       //Texto Usuario y label de usuario y el label de ayuda
       username = new JLabel("Usuario - ");
       username.setBounds(260,140,70,20);
       ayuda=new JLabel("?");
       ayuda.setBounds(490,140,10,10);
       ayuda.setToolTipText("El usuario es el legajo para los inspectores");
       user=new JTextField();
       user.setBounds(330,140,150,20);
       contentPane.add(user);
       contentPane.add(username);
       contentPane.add(ayuda);

       //Texto Usuario y label
       jLabelPassword = new JLabel("Contrasenia - ");
       jLabelPassword.setBounds(240,170,120,20);
       password=new JPasswordField();
       password.setBounds(330,170,150,20);
       contentPane.add(password);
       contentPane.add(jLabelPassword);

       //Boton Conectar
       connection= new JButton("Conectar");
       connection.setBounds(330,200,100,20);
       contentPane.add(connection);
       connection.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
               btnConectar(evt,user.getText(),String.valueOf(password.getPassword()));
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



}
