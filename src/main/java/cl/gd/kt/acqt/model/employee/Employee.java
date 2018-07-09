package cl.gd.kt.acqt.model.employee;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private String email;
    private String gender;
    private Integer phoneCountry;
    private Integer phoneRegion;
    private Integer phoneNumber;
    private String country;
    private String state;
    private String city;
    private String address;
    private String category;
    private String level;
    private Boolean isActive;
    
}