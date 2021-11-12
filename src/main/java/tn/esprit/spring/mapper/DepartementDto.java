package tn.esprit.spring.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;


public class DepartementDto {

	@JsonProperty("id")
    private int id;

  
    @JsonProperty("name")
    private String name;


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setId(int id) {
		this.id = id;
	}

    

}
