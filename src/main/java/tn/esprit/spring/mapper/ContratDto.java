package tn.esprit.spring.mapper;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;



public class ContratDto {

	@JsonProperty("reference")
    private int reference;

  
    @JsonProperty("typeContrat")
    private String typeContrat;

    @JsonProperty("salaire")
    private float salaire;

    @JsonProperty("dateDebut")
    private Date dateDebut;

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public String getTypeContrat() {
		return typeContrat;
	}

	public void setTypeContrat(String typeContrat) {
		this.typeContrat = typeContrat;
	}

	public float getSalaire() {
		return salaire;
	}

	public void setSalaire(float salaire) {
		this.salaire = salaire;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

    

    

    
    
}
