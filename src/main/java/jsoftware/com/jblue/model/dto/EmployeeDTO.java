package jsoftware.com.jblue.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO correspondiente a los datos generales, de identificación fiscal y
 * contacto de empleados (Employee).
 * <br>
 * Utiliza la estructura heredada dinámica de Map para transportar las cadenas
 * de texto de manera flexible entre los componentes de Swing y la capa DAO de
 * MySQL, apegándose estrictamente al estándar de diseño de JBlue.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2025-11-23
 * @version 1.0
 */
public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String rfc;
    private String curp;
    private String first_name;
    private String last_name1;
    private String last_name2;
    private int gender;
    private LocalDate birthdate;
    private String personal_email;
    private String personal_number;
    private int street1;
    private int street2;
    private String inside_number;
    private String outside_number;
    private int committee_id;
    private int status;
    private int last_employee_update;
    private LocalDateTime date_update;
    private LocalDateTime date_register;
    private LocalDateTime date_end;

    public EmployeeDTO() {
        this.id = -1;
        this.rfc = null;
        this.curp = null;
        this.first_name = null;
        this.last_name1 = null;
        this.last_name2 = null;
        this.gender = -1;
        this.birthdate = null;
        this.personal_email = null;
        this.personal_number = null;
        this.street1 = -1;
        this.street2 = -1;
        this.inside_number = null;
        this.outside_number = null;
        this.committee_id = -1;
        this.status = -1;
        this.last_employee_update = -1;
        this.date_update = null;
        this.date_register = null;
        this.date_end = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name1() {
        return last_name1;
    }

    public void setLast_name1(String last_name1) {
        this.last_name1 = last_name1;
    }

    public String getLast_name2() {
        return last_name2;
    }

    public void setLast_name2(String last_name2) {
        this.last_name2 = last_name2;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPersonal_email() {
        return personal_email;
    }

    public void setPersonal_email(String personal_email) {
        this.personal_email = personal_email;
    }

    public String getPersonal_number() {
        return personal_number;
    }

    public void setPersonal_number(String personal_number) {
        this.personal_number = personal_number;
    }

    public int getStreet1() {
        return street1;
    }

    public void setStreet1(int street1) {
        this.street1 = street1;
    }

    public int getStreet2() {
        return street2;
    }

    public void setStreet2(int street2) {
        this.street2 = street2;
    }

    public String getInside_number() {
        return inside_number;
    }

    public void setInside_number(String inside_number) {
        this.inside_number = inside_number;
    }

    public String getOutside_number() {
        return outside_number;
    }

    public void setOutside_number(String outside_number) {
        this.outside_number = outside_number;
    }

    public int getCommittee_id() {
        return committee_id;
    }

    public void setCommittee_id(int committee_id) {
        this.committee_id = committee_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLast_employee_update() {
        return last_employee_update;
    }

    public void setLast_employee_update(int last_employee_update) {
        this.last_employee_update = last_employee_update;
    }

    public LocalDateTime getDate_update() {
        return date_update;
    }

    public void setDate_update(LocalDateTime date_update) {
        this.date_update = date_update;
    }

    public LocalDateTime getDate_register() {
        return date_register;
    }

    public void setDate_register(LocalDateTime date_register) {
        this.date_register = date_register;
    }

    public LocalDateTime getDate_end() {
        return date_end;
    }

    public void setDate_end(LocalDateTime date_end) {
        this.date_end = date_end;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmployeeDTO other = (EmployeeDTO) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EmployeeDTO{");
        sb.append("id=").append(id);
        sb.append(", rfc=").append(rfc);
        sb.append(", curp=").append(curp);
        sb.append(", first_name=").append(first_name);
        sb.append(", last_name1=").append(last_name1);
        sb.append(", last_name2=").append(last_name2);
        sb.append(", gender=").append(gender);
        sb.append(", birthdate=").append(birthdate);
        sb.append(", personal_email=").append(personal_email);
        sb.append(", personal_number=").append(personal_number);
        sb.append(", street1=").append(street1);
        sb.append(", street2=").append(street2);
        sb.append(", inside_number=").append(inside_number);
        sb.append(", outside_number=").append(outside_number);
        sb.append(", committee_id=").append(committee_id);
        sb.append(", status=").append(status);
        sb.append(", last_employee_update=").append(last_employee_update);
        sb.append(", date_update=").append(date_update);
        sb.append(", date_register=").append(date_register);
        sb.append(", date_end=").append(date_end);
        sb.append('}');
        return sb.toString();
    }

}
