package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {

	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	public int ajouterEntreprise(Entreprise entreprise) {
		entrepriseRepoistory.save(entreprise);
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		deptRepoistory.save(dep);
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		//Le bout Master de cette relation N:1 est departement  
				//donc il faut rajouter l'entreprise a departement 
				// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
				//Rappel : la classe qui contient mappedBy represente le bout Slave
				//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
				
				Optional<Entreprise> entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId);
				Optional<Departement> depManagedEntity = deptRepoistory.findById(depId);
				if (entrepriseManagedEntity.isPresent() && depManagedEntity.isPresent()) {
					Entreprise value = entrepriseManagedEntity.get();
					Departement value1 = depManagedEntity.get();
					value1.setEntreprise(value);
					deptRepoistory.save(value1);
				}

	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Optional<Entreprise> optional = entrepriseRepoistory.findById(entrepriseId);
		if (optional.isPresent()) {
			Entreprise value = optional.get();
			List<String> depNames = new ArrayList<>();
			for(Departement dep : value.getDepartements()){
				depNames.add(dep.getName());
			}
			
			return depNames;
		}
		return getAllDepartementsNamesByEntreprise(entrepriseId);
			
	}
	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		Optional<Entreprise> optional = entrepriseRepoistory.findById(entrepriseId);
		if (optional.isPresent()) {
			Entreprise value = optional.get();
			entrepriseRepoistory.delete(value);
		}
	}

	@Transactional
	public void deleteDepartementById(int depId) {	
		Optional<Departement> optional = deptRepoistory.findById(depId);
		if (optional.isPresent()) {
			Departement value = optional.get();
			deptRepoistory.delete(value);
		}
	}

	

	public Entreprise getEntrepriseById(int entrepriseId) {
		Optional<Entreprise> optional = entrepriseRepoistory.findById(entrepriseId);
		if (optional.isPresent()) {
			
		return optional.get();
		
		}
		return getEntrepriseById(entrepriseId);
	}
	
	public List<Entreprise> getAllEntreprise() {
		return (List<Entreprise>) entrepriseRepoistory.findAll();
	}


}
