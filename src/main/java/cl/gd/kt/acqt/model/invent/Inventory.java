package cl.gd.kt.acqt.model.invent;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.core.json.JsonObject;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Inventory {

    private Long seq;
    private Long serialNumber;
    private String description;
    private String category;
    private Boolean assigned;
    private Long employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private Instant employeeDtAssignment;
    private Instant dtAdmission;
    private Instant dtEgress;
    private Boolean enable;
    
    public Inventory(JsonObject json) {
    	this.seq = json.getLong("seq");
    	this.serialNumber = json.getLong("serial_number");
    	this.description = json.getString("description");
    	this.category = json.getString("category");
    	this.assigned = json.getBoolean("assigned");
    	this.employeeId = json.getLong("employee_id");
    	this.employeeFirstName = json.getString("employee_first_name");
    	this.employeeLastName = json.getString("employee_last_name");
    	this.employeeDtAssignment = json.getInstant("employee_dt_assignment");
    	this.dtAdmission = json.getInstant("dt_admission");
    	this.dtEgress = json.getInstant("dt_egress");
    	this.enable = json.getBoolean("enable");
    }
 
}
