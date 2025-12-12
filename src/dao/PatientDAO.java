
package dao;
import db.DB;
import model.Patient;
import java.sql.*;
import java.util.ArrayList;

public class PatientDAO {

public static void insert(Patient p) throws Exception {
        String sql = "INSERT INTO patients (name, phone, diagnosis) VALUES (?, ?, ?)";

        Connection con = DB.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, p.getName());
        ps.setString(2, p.getPhone());
        ps.setString(3, p.getDiagnosis());

        ps.executeUpdate();
    }


    public static ArrayList<Patient> getAll() throws Exception {
        ArrayList<Patient> list = new ArrayList<>();

        String sql = "SELECT * FROM patients";
        Connection con = DB.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Patient p = new Patient(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("phone"),
                rs.getString("diagnosis")
            );
            list.add(p);
        }

        return list;
    }
 
    public static void update(Patient p) throws Exception {
        Connection con=DB.getConnection();
    String sql = "UPDATE patients SET name=?, phone=?, diagnosis=? WHERE id=?";
    PreparedStatement ps = con.prepareStatement(sql);
    ps.setString(1, p.getName());
    ps.setString(2, p.getPhone());
    ps.setString(3, p.getDiagnosis());
    ps.setInt(4, p.getId());
    ps.executeUpdate();
}
    public static void delete(int id) throws Exception {
        Connection con=DB.getConnection();
    String sql = "DELETE FROM patients WHERE id=?";
    PreparedStatement ps = con.prepareStatement(sql);
    ps.setInt(1, id);
    ps.executeUpdate();
}

}
