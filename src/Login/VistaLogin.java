package Login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;

public class VistaLogin extends JFrame {


   private JPanel contentPane;
   private JTextField user;
   private JPasswordField password;
   private JButton connection;
   private JLabel username;
    private JLabel jLabelPassword;
    private Login login;


   public VistaLogin(){
       setVisible(true);
       setBounds(600, 300, 800, 500);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setResizable(false);
       login =new Login();

       // Panel
       contentPane=new JPanel();
       contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
       contentPane.setLayout(null);
       setContentPane(contentPane);

       //Texto Usuario y label
       username = new JLabel("Usuario - ");
       username.setBounds(260,140,70,20);
       user=new JTextField();
       user.setBounds(330,140,150,20);
       contentPane.add(user);
       contentPane.add(username);

       //Texto Usuario y label
       jLabelPassword = new JLabel("Contrasenia - ");
       jLabelPassword.setBounds(260,240,100,20);
       password=new JPasswordField();
       password.setBounds(330,240,150,20);
       contentPane.add(password);
       contentPane.add(jLabelPassword);

       //Boton Conectar
       connection= new JButton("Conectar");
       connection.setBounds(330,340,100,20);
       contentPane.add(connection);
       connection.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
               btnConectar(evt,user.getText(),String.valueOf(password.getPassword()));
           }
       });
   }

    private void btnConectar(ActionEvent evt,String user,String password)
    {

        try{
            login.conectarBD(user,password);
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(this,
                    "Se produjo un error al intentar conectarse a la base de datos.\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



}
