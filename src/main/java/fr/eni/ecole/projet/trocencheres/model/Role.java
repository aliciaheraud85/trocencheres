package fr.eni.ecole.projet.trocencheres.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLES")
public class Role {
    @Id
    @Column(name = "ROLE")
    private String role;

    @Column(name = "IS_ADMIN")
    private Integer isAdmin;

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Integer getIsAdmin() { return isAdmin; }
    public void setIsAdmin(Integer isAdmin) { this.isAdmin = isAdmin; }
}
