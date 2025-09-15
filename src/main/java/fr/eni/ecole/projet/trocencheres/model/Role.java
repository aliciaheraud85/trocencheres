package fr.eni.ecole.projet.trocencheres.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLES")
public class Role {
    private String role;
    private int isAdmin;

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public int getIsAdmin() { return isAdmin; }
    public void setIsAdmin(int isAdmin) { this.isAdmin = isAdmin; }
}
