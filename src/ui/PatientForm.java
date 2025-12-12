
package ui;
import model.Patient;
import dao.PatientDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.time.LocalTime;
import java.util.ArrayList;
public class PatientForm extends JFrame {
    
    private JTextField txtName;
    private JTextField txtPhone;
    

    private JButton btnAdd;
    private JButton btnRefresh;
    private JButton btnExport;
    private JButton btnEdit;
    private JButton btnDelete;

    private JComboBox<String> comboDiag;
    
    private JLabel lblClock;
    private JLabel lblCount;
    private JTable table;
    private ArrayList<Patient> patients;

    public  PatientForm() {
        setTitle("Patients Management");
        setSize(600, 400);
        setLayout(null);

        JLabel lblName = new JLabel("Name");
        JLabel lblPhone = new JLabel("Phone");
        JLabel lblDiag = new JLabel("Diagnosis");
        
        txtName = new JTextField();
        txtPhone = new JTextField();
        
        String[] diagnoses = {"Flu", "Diabetes", "Allergy", "Checkup", "Hypertension"};
        comboDiag = new JComboBox<>(diagnoses);

        btnAdd = new JButton("Add");
        btnRefresh = new JButton("Refresh");
        btnExport = new JButton("Export");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");

        lblClock = new JLabel();
        
        lblCount = new JLabel("Total Patients: 0");
        
        lblName.setBounds(20, 20, 100, 25);
        txtName.setBounds(20, 45, 200, 25);

        lblPhone.setBounds(20, 80, 100, 25);
        txtPhone.setBounds(20, 105, 200, 25);

        lblDiag.setBounds(20, 140, 100, 25);
        comboDiag.setBounds(20, 165, 200, 25);
        

        btnAdd.setBounds(20, 210, 200, 30);
        btnEdit.setBounds(20, 250, 200, 30);
        btnDelete.setBounds(20, 290, 200, 30);
        btnRefresh.setBounds(20, 330, 200, 30);
        btnExport.setBounds(20, 370, 200, 30);
        
        
        
        lblClock.setBounds(450, 10, 150, 25);
        lblCount.setBounds(250, 330, 200, 25);
        
        add(lblName);
        add(lblPhone);
        add(lblDiag);
        add(lblCount);
        
        add(txtName);
        add(txtPhone);
        add(comboDiag);
        add(btnAdd);
        add(btnRefresh);
        add(btnExport);
        add(btnEdit);
        add(btnDelete);
        add(lblClock);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[]{"ID", "Name", "Phone", "Diagnosis"}, 0));

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(250, 20, 320, 300);
        add(sp);

        btnAdd.addActionListener(e -> addPatient());
        btnRefresh.addActionListener(e -> loadTable());
        btnExport.addActionListener(e -> exportData());
        btnEdit.addActionListener(e -> updatePatient());
        btnDelete.addActionListener(e -> deletePatient());
        
        
        startClock();
        loadTable();
        table.getSelectionModel().addListSelectionListener(e -> fillFields());
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void updatePatient() {
    int row = table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Select a row first!");
        return;
    }

    try {
        int id = (int) table.getValueAt(row, 0);

        Patient p = new Patient(
                id,
                txtName.getText(),
                txtPhone.getText(),
                comboDiag.getSelectedItem().toString()
        );

        PatientDAO.update(p);

        JOptionPane.showMessageDialog(this, "Updated!");
        loadTable();

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}
    
    private void deletePatient() {
    int row = table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Select a row first!");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure to delete this patient?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) return;

    try {
        int id = (int) table.getValueAt(row, 0);
        PatientDAO.delete(id);

        JOptionPane.showMessageDialog(this, "Deleted!");
        loadTable();

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}
    
    
    
    private void fillFields() {
    int row = table.getSelectedRow();
    if (row == -1) return;

    txtName.setText(table.getValueAt(row, 1).toString());
    txtPhone.setText(table.getValueAt(row, 2).toString());
    comboDiag.setSelectedItem(table.getValueAt(row, 3).toString());
}

    private void startClock() {
        new Thread(() -> {
            while (true) {
                lblClock.setText(LocalTime.now().toString());
                try { Thread.sleep(1000); } catch (Exception ignored) {}
            }
        }).start();
    }

    public void addPatient() {
        try {
            Patient p = new Patient(
                    txtName.getText(),
                    txtPhone.getText(),
                    comboDiag.getSelectedItem().toString()
            );

            PatientDAO.insert(p);
            JOptionPane.showMessageDialog(this, "Saved!");
            loadTable();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

   public void loadTable() {
    try {
        patients = PatientDAO.getAll();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Patient p : patients) {
            model.addRow(new Object[]{
                    p.getId(), p.getName(), p.getPhone(), p.getDiagnosis()
            });
        }

        final int count = model.getRowCount();
        SwingUtilities.invokeLater(() -> lblCount.setText("Total Patients: " + count));

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

   public void exportData() {
    try {
        java.io.FileWriter fw = new java.io.FileWriter("patients.txt");

        for (Patient p : patients) {
            fw.write("ID: " + p.getId() + "\n");
            fw.write("Name: " + p.getName() + "\n");
            fw.write("Phone: " + p.getPhone() + "\n");
            fw.write("Diagnosis: " + p.getDiagnosis() + "\n");
            fw.write("-----------------------------\n");
        }

        fw.close();
        JOptionPane.showMessageDialog(this, "Exported!");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage());
        ex.printStackTrace();
    }
}  
   
   
    
    
    
    
}

