package sg.edu.nus.iss.miniproject.models;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class User implements Serializable {

    private String userId;

    @NotNull(message="First Name is mandatory")
    @Size(min=3, max=50, message="Character length is out of bound")
    private String firstName;

    @NotNull(message="Last Name is mandatory")
    @Size(min=3, max=50, message="Character length is out of bound")
    private String lastName;

    @NotNull(message="Date of Birth is mandatory")
    @Past(message="Birth Date cannot be a date from the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;


    @NotNull(message="Email is mandatory")
    @Email(message="Email format is incorrect")
    private String email;

    @NotNull(message="Password is mandatory")
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message="minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character")
    private String password;

    public User(){}

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public Date getDob() {return dob;}
    public void setDob(Date dob) {this.dob = dob;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

}
