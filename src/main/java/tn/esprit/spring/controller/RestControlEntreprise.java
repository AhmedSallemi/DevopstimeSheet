package tn.esprit.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.mapper.DepartementDto;
import tn.esprit.spring.mapper.EntrepriseDto;
import tn.esprit.spring.mapper.MapStructMapperImpl;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;

@RestController
public class RestControlEntreprise {

	private static final Logger logger=LoggerFactory.getLogger(RestControlEntreprise.class);
	@Autowired
	IEmployeService iemployeservice;
	@Autowired
	IEntrepriseService ientrepriseservice;
	@Autowired
	ITimesheetService itimesheetservice;
	
	private MapStructMapperImpl mapperImpl;
	
	 @Autowired
	    public RestControlEntreprise(
	    		MapStructMapperImpl mapperImpl
	    ) {
	        this.mapperImpl = mapperImpl;
	    }
	
	// Ajouter Entreprise : http://localhost:8081/SpringMVC/servlet/ajouterEntreprise
	
	@PostMapping("/ajouterEntreprise")
    public ResponseEntity<Void> ajouterEntreprise(
            @Valid @RequestBody EntrepriseDto entrDto
    ) {
		ientrepriseservice.ajouterEntreprise(mapperImpl.entreprisePostDtoToEntreprise(entrDto));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
	
	// http://localhost:8081/SpringMVC/servlet/affecterDepartementAEntreprise/1/1
    @PutMapping(value = "/affecterDepartementAEntreprise/{iddept}/{identreprise}") 
	public void affecterDepartementAEntreprise(@PathVariable("iddept")int depId, @PathVariable("identreprise")int entrepriseId) {
		ientrepriseservice.affecterDepartementAEntreprise(depId, entrepriseId);
	}
    
    // http://localhost:8081/SpringMVC/servlet/deleteEntrepriseById/1
    @DeleteMapping("/deleteEntrepriseById/{identreprise}") 
	@ResponseBody 
	public void deleteEntrepriseById(@PathVariable("identreprise")int entrepriseId)
	{
    	try{
        	logger.trace("deleteEntrepriseById accessed");
        	logger.debug("In deleteEntrepriseById ");
        	logger.info("begin delete");
		ientrepriseservice.deleteEntrepriseById(entrepriseId);
		logger.debug("End delete");
    	logger.info("deleteEntrepriseById succeded");
    	logger.error("error");
    	}
    	catch(Exception e){
    		logger.error("Error : ",e);
    	}
	}
    
    // http://localhost:8081/SpringMVC/servlet/getEntrepriseById/1
    @GetMapping(value = "/getEntrepriseById/{identreprise}")
    @ResponseBody
	public Entreprise getEntrepriseById(@PathVariable("identreprise") int entrepriseId) {
    	logger.info("begin getEntrepriseById");
    	logger.trace("getEntrepriseById accessed");
    	Entreprise e= ientrepriseservice.getEntrepriseById(entrepriseId);
    	logger.trace("getEntrepriseById Succeded");
    	logger.info("end getEntrepriseById");
    	return e;	}
    // http://localhost:8081/SpringMVC/servlet/ajouterDepartement
 	
 	@PostMapping("/ajouterDepartement")
    public ResponseEntity<Void> ajouterDepartement(
            @Valid @RequestBody DepartementDto depDto
    ) {
		ientrepriseservice.ajouterDepartement(mapperImpl.departementPostDtoToDepartement(depDto));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
	
 	 // http://localhost:8081/SpringMVC/servlet/getAllDepartementsNamesByEntreprise/1
    @GetMapping(value = "getAllDepartementsNamesByEntreprise/{identreprise}")
    @ResponseBody
	public List<String> getAllDepartementsNamesByEntreprise(@PathVariable("identreprise") int entrepriseId) {
		return ientrepriseservice.getAllDepartementsNamesByEntreprise(entrepriseId);
	}

    // URL : http://localhost:8081/SpringMVC/servlet/deleteDepartementById/3
    @DeleteMapping("/deleteDepartementById/{iddept}") 
	@ResponseBody 
	public void deleteDepartementById(@PathVariable("iddept") int depId) {
    	try{
        	logger.trace("deleteDepartementById accessed");
        	logger.debug("In deleteDepartementById ");
        	logger.info("begin delete");
		ientrepriseservice.deleteDepartementById(depId);
		logger.debug("End delete");
    	logger.info("deleteDepartementById succeded");
    	}
    	catch(Exception e){
    		logger.error("Error : ",e);
    	}
	}
    
    }
