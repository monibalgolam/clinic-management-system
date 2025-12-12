
package model;

public class Patient {
    
    private int id;
    private String name;
    private String phone;
    private String diagnosis;

    public Patient(int id, String name, String phone, String diagnosis) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.diagnosis = diagnosis;
    }

    public Patient(String name, String phone, String diagnosis) {
        this.name = name;
        this.phone = phone;
        this.diagnosis = diagnosis;
    }

    public int getId() {
        return id; 
    }
    public String getName() {
        return name; 
    }
    public String getPhone() { 
        return phone;
    }
    public String getDiagnosis() { 
        return diagnosis;
    }
}
