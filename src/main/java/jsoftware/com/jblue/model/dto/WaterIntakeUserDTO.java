package jsoftware.com.jblue.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO correspondiente a la vinculación y estado de tomas de agua por usuario
 * (WaterIntakeUser).
 * <br>
 * Utiliza la estructura interna dinámica de Map para transportar las cadenas de
 * texto de manera flexible entre los componentes de Swing y la capa DAO de
 * MySQL.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-05-30
 * @version 1.0
 */
public class WaterIntakeUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int process_id;
    private int employee_id;
    private int user_id;
    private int user_type_id;
    private int address_id;
    private int water_intake_id;
    private int water_intake_type_id;
    private boolean is_consumer;
    private int office_id;
    private String user_name;
    private String description;
    private String observation;
    private int fiscal_year;
    private int last_month_paid;
    private BigDecimal last_amount_paid;
    private int last_process_id;
    private int last_employee_update;
    private int status;
    private LocalDateTime date_update;
    private LocalDateTime date_register;
    private LocalDateTime date_delete;

    public WaterIntakeUserDTO() {
        this.id = -1;
        this.process_id = -1;
        this.employee_id = -1;
        this.user_id = -1;
        this.user_type_id = -1;
        this.address_id = -1;
        this.water_intake_id = -1;
        this.water_intake_type_id = -1;
        this.is_consumer = false;
        this.office_id = -1;
        this.user_name = null;
        this.description = null;
        this.observation = null;
        this.fiscal_year = -1;
        this.last_month_paid = -1;
        this.last_amount_paid = null;
        this.last_process_id = -1;
        this.last_employee_update = -1;
        this.status = -1;
        this.date_update = null;
        this.date_register = null;
        this.date_delete = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProcess_id() {
        return process_id;
    }

    public void setProcess_id(int process_id) {
        this.process_id = process_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(int user_type_id) {
        this.user_type_id = user_type_id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getWater_intake_id() {
        return water_intake_id;
    }

    public void setWater_intake_id(int water_intake_id) {
        this.water_intake_id = water_intake_id;
    }

    public int getWater_intake_type_id() {
        return water_intake_type_id;
    }

    public void setWater_intake_type_id(int water_intake_type_id) {
        this.water_intake_type_id = water_intake_type_id;
    }

    public boolean isIs_consumer() {
        return is_consumer;
    }

    public void setIs_consumer(boolean is_consumer) {
        this.is_consumer = is_consumer;
    }

    public int getOffice_id() {
        return office_id;
    }

    public void setOffice_id(int office_id) {
        this.office_id = office_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getFiscal_year() {
        return fiscal_year;
    }

    public void setFiscal_year(int fiscal_year) {
        this.fiscal_year = fiscal_year;
    }

    public int getLast_month_paid() {
        return last_month_paid;
    }

    public void setLast_month_paid(int last_month_paid) {
        this.last_month_paid = last_month_paid;
    }

    public BigDecimal getLast_amount_paid() {
        return last_amount_paid;
    }

    public void setLast_amount_paid(BigDecimal last_amount_paid) {
        this.last_amount_paid = last_amount_paid;
    }

    public int getLast_process_id() {
        return last_process_id;
    }

    public void setLast_process_id(int last_process_id) {
        this.last_process_id = last_process_id;
    }

    public int getLast_employee_update() {
        return last_employee_update;
    }

    public void setLast_employee_update(int last_employee_update) {
        this.last_employee_update = last_employee_update;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public LocalDateTime getDate_delete() {
        return date_delete;
    }

    public void setDate_delete(LocalDateTime date_delete) {
        this.date_delete = date_delete;
    }

    @Override
    public String toString() {
        return getUser_name();
    }
}
