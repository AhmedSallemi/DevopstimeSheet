package tn.esprit.spring.mapper;



import com.fasterxml.jackson.annotation.JsonProperty;



public class EntrepriseDto {

	@JsonProperty("id")
    private int id;

  
    @JsonProperty("name")
    private String name;

    @JsonProperty("raisonSocial")
    private String raisonSocial;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRaisonSocial() {
		return raisonSocial;
	}

	public void setRaisonSocial(String raisonSocial) {
		this.raisonSocial = raisonSocial;
	}



}
