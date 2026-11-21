package jsoftware.com.jblue.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO que transporta los estados, marcas de tiempo y auditoría de los procesos
 * del sistema de cobros. Implementa el estándar de mapeo flexible mediante
 * cadenas.
 *
 * @author Juan P. Campos C.
 * @since 2026-07-18
 * @version 1.0
 */
public class ProcessDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //-- DATOS DEL TRAMITE --//
    private int id;
    private int sequence;
    private int process_type;

    //-- DATOS DEL FLUJO --//
    private int employee_start;
    private LocalDateTime date_start;
    private int employee_valid;
    private LocalDateTime date_valid;
    private int employee_mov;
    private LocalDateTime date_mov;
    private int employee_payment;
    private LocalDateTime date_payment;
    private int employee_finalize;
    private LocalDateTime date_finalize;
    private int employee_print;
    private LocalDateTime date_print;

    //-- DATOS DE AUDITORIA--//
    private int administration_start_id;
    private int administration_end_id;
    private String current_db_user;
    private int status;
    private int committee_id;
    private int office_id;
    private int last_employee_update;
    private LocalDateTime date_update;
    private LocalDateTime date_register;
    private LocalDateTime date_end;

    public ProcessDTO() {
        this.id = -1;
        this.sequence = -1;
        this.process_type = -1;
        this.employee_start = -1;
        this.date_start = null;
        this.employee_valid = -1;
        this.date_valid = null;
        this.employee_mov = -1;
        this.date_mov = null;
        this.employee_payment = -1;
        this.date_payment = null;
        this.employee_finalize = -1;
        this.date_finalize = null;
        this.employee_print = -1;
        this.date_print = null;
        this.administration_start_id = -1;
        this.administration_end_id = -1;
        this.current_db_user = null;
        this.status = -1;
        this.committee_id = -1;
        this.office_id = -1;
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

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getProcess_type() {
        return process_type;
    }

    public void setProcess_type(int process_type) {
        this.process_type = process_type;
    }

    public int getEmployee_start() {
        return employee_start;
    }

    public void setEmployee_start(int employee_start) {
        this.employee_start = employee_start;
    }

    public LocalDateTime getDate_start() {
        return date_start;
    }

    public void setDate_start(LocalDateTime date_start) {
        this.date_start = date_start;
    }

    public int getEmployee_valid() {
        return employee_valid;
    }

    public void setEmployee_valid(int employee_valid) {
        this.employee_valid = employee_valid;
    }

    public LocalDateTime getDate_valid() {
        return date_valid;
    }

    public void setDate_valid(LocalDateTime date_valid) {
        this.date_valid = date_valid;
    }

    public int getEmployee_mov() {
        return employee_mov;
    }

    public void setEmployee_mov(int employee_mov) {
        this.employee_mov = employee_mov;
    }

    public LocalDateTime getDate_mov() {
        return date_mov;
    }

    public void setDate_mov(LocalDateTime date_mov) {
        this.date_mov = date_mov;
    }

    public int getEmployee_payment() {
        return employee_payment;
    }

    public void setEmployee_payment(int employee_payment) {
        this.employee_payment = employee_payment;
    }

    public LocalDateTime getDate_payment() {
        return date_payment;
    }

    public void setDate_payment(LocalDateTime date_payment) {
        this.date_payment = date_payment;
    }

    public int getEmployee_finalize() {
        return employee_finalize;
    }

    public void setEmployee_finalize(int employee_finalize) {
        this.employee_finalize = employee_finalize;
    }

    public LocalDateTime getDate_finalize() {
        return date_finalize;
    }

    public void setDate_finalize(LocalDateTime date_finalize) {
        this.date_finalize = date_finalize;
    }

    public int getEmployee_print() {
        return employee_print;
    }

    public void setEmployee_print(int employee_print) {
        this.employee_print = employee_print;
    }

    public LocalDateTime getDate_print() {
        return date_print;
    }

    public void setDate_print(LocalDateTime date_print) {
        this.date_print = date_print;
    }

    public int getAdministration_start_id() {
        return administration_start_id;
    }

    public void setAdministration_start_id(int administration_start_id) {
        this.administration_start_id = administration_start_id;
    }

    public int getAdministration_end_id() {
        return administration_end_id;
    }

    public void setAdministration_end_id(int administration_end_id) {
        this.administration_end_id = administration_end_id;
    }

    public String getCurrent_db_user() {
        return current_db_user;
    }

    public void setCurrent_db_user(String current_db_user) {
        this.current_db_user = current_db_user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCommittee_id() {
        return committee_id;
    }

    public void setCommittee_id(int committee_id) {
        this.committee_id = committee_id;
    }

    public int getOffice_id() {
        return office_id;
    }

    public void setOffice_id(int office_id) {
        this.office_id = office_id;
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
        int hash = 3;
        hash = 37 * hash + this.id;
        hash = 37 * hash + this.sequence;
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
        final ProcessDTO other = (ProcessDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        return this.sequence == other.sequence;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProcessDTO{");
        sb.append("id=").append(id);
        sb.append(", sequence=").append(sequence);
        sb.append(", process_type=").append(process_type);
        sb.append(", employee_start=").append(employee_start);
        sb.append(", date_start=").append(date_start);
        sb.append(", employee_valid=").append(employee_valid);
        sb.append(", date_valid=").append(date_valid);
        sb.append(", employee_mov=").append(employee_mov);
        sb.append(", date_mov=").append(date_mov);
        sb.append(", employee_payment=").append(employee_payment);
        sb.append(", date_payment=").append(date_payment);
        sb.append(", employee_finalize=").append(employee_finalize);
        sb.append(", date_finalize=").append(date_finalize);
        sb.append(", employee_print=").append(employee_print);
        sb.append(", date_print=").append(date_print);
        sb.append(", administration_start_id=").append(administration_start_id);
        sb.append(", administration_end_id=").append(administration_end_id);
        sb.append(", current_db_user=").append(current_db_user);
        sb.append(", status=").append(status);
        sb.append(", committee_id=").append(committee_id);
        sb.append(", office_id=").append(office_id);
        sb.append(", last_employee_update=").append(last_employee_update);
        sb.append(", date_update=").append(date_update);
        sb.append(", date_register=").append(date_register);
        sb.append(", date_end=").append(date_end);
        sb.append('}');
        return sb.toString();
    }

}
