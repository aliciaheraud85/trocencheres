package fr.eni.ecole.projet.trocencheres.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SignUpRequest {

    @NotBlank(message = "Username is required")
    private final String username;
    @NotBlank(message = "Last Name is required")
    private final String lastName;
    @NotBlank(message = "First Name is required")
    private final String firstName;
    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email is required")
    private final String email;
    private final String phoneNumber;
    @NotBlank(message = "Street is required")
    private final String street;
    @NotBlank(message = "Postal is required")
    private final String postalCode;
    @NotBlank(message = "City is required")
    private final String city;
    @NotBlank(message = "Password is required")
    private final String password;

    public SignUpRequest(String username, String lastName, String firstName, String email, String phoneNumber, String street, String postalCode, String city, String password) {
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getPassword() {
        return password;
    }
}
