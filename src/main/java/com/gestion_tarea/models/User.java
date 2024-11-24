package com.gestion_tarea.models;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users", 
    uniqueConstraints = { 
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email") 
    })
public class User {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    @Size(max = 20)
	    private String username;

	    @NotBlank
	    @Size(max = 50)
	    @Email
	    private String email;

	    @NotBlank
	    @Size(max = 120)
	    private String password;

	    @ManyToOne
	    @JoinColumn(name = "tenant_id", nullable = true)
	    @JsonIgnore
	    private Tenant tenant;
//
	    @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(
	        name = "user_roles",
	        joinColumns = @JoinColumn(name = "user_id"),
	        inverseJoinColumns = @JoinColumn(name = "role_id")
	    )
	    private Set<Role> roles = new HashSet<>();

	    @ManyToMany(mappedBy = "members")
	    @JsonBackReference
	    private Set<Project> projects = new HashSet<>();
    // Getters y setters


  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

}
