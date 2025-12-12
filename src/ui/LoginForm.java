
package ui;
import java.awt.TextField;
import javax.swing.*;
public class LoginForm extends JFrame {
    
    JLabel labUser =new JLabel("UserName");
    JLabel labPass =new JLabel("Password");
    JTextField txtUser = new JTextField();
    JPasswordField txtPass = new JPasswordField();
    JButton btnLogin = new JButton("Login");

    public LoginForm() {
        setTitle("Clinic Login");
        setSize(300,200);
        setLayout(null);
        labUser.setBounds(50, 20, 200, 25);
        txtUser.setBounds(50, 45, 200, 25);
        labPass.setBounds(50, 80, 200, 25);
        txtPass.setBounds(50, 105, 200, 25);
        btnLogin.setBounds(50, 140, 200, 30);
        
        add(labUser);
        add(labPass);
        add(txtUser);
        add(txtPass);
        add(btnLogin);

        btnLogin.addActionListener(e -> login());

        setLocationRelativeTo(null);
        setVisible(true);
    }

   public void login() {
    String user = txtUser.getText();
    String pass = new String(txtPass.getPassword());

    if (user.equals("admin") && pass.equals("123")) {
        try {
            new PatientForm().setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "Wrong Login!");
    }
}
    
    
}
