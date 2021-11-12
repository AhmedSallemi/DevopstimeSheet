package tn.esprit.spring.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import tn.esprit.spring.entities.Role;



public class EmployeDto {

	@JsonProperty("id")
    private int id;

  
    @JsonProperty("email")
    private String email;

    @JsonProperty("prenom")
    private String prenom;

    @JsonProperty("name")
    private String name;

    @JsonProperty("isActif")
    private boolean isActif;
    
    @JsonProperty("role")
    private Role role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActif() {
		return isActif;
	}

	public void setActif(boolean isActif) {
		this.isActif = isActif;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
    
    
}
