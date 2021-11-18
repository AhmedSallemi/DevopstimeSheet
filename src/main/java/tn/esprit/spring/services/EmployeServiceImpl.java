package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	private static final Logger l = LogManager.getLogger(EmployeServiceImpl.class);
	
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	public int ajouterEmploye(Employe employe) {
		try {
			l.info("in ajouter Employe");
			l.debug("Je vais commencer l'ajout");
			employeRepository.save(employe);
			l.info("ou ajouter Employe");
			return employe.getId();
		}
		catch (Exception e) { l.error("Erreur dans ajouterEmploye() : " , e); }
		
		return employe.getId();	
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		
		try {
			l.info("in  mettreAjourEmailByEmployeId");
			l.debug("Je vais commencer la mise à jour");
			Optional<Employe> optional = employeRepository.findById(employeId);
			if (optional.isPresent()) {
				Employe value = optional.get();
				value.setEmail(email);
				employeRepository.save(value);
			}
			l.info("out Mise à jour ");
			}
		catch (Exception e) { l.error("Erreur dans mettreAjourEmailByEmployeId() : " , e); }
	}

	@Transactional
	public void affecterEmployeADepartement(int employeId, int depId) {

		Optional<Departement> depManagedEntity = deptRepoistory.findById(depId);
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		try {
			l.info("in  affecterEmployeADepartement");
			l.debug("Je vais commencer l'affectation");
			if (depManagedEntity.isPresent() && employeManagedEntity.isPresent()) {
				Departement value = depManagedEntity.get();
				Employe value1 = employeManagedEntity.get();

				if (value.getEmployes() == null) {

					List<Employe> employes = new ArrayList<>();
					employes.add(value1);
					value.setEmployes(employes);
				} else {

					value.getEmployes().add(value1);

				}
			}
			l.info("out affecterEmployeADepartement ");
			}
			catch (Exception e) { l.error("Erreur dans affecterEmployeADepartement() : " , e); }
			
	}


	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		Optional<Departement> dep = deptRepoistory.findById(depId);
		try {
			l.info("in  desaffecterEmployeDuDepartement");
			l.debug("Je vais commencer la desaffectation");
			if (dep.isPresent()) {
				Departement value = dep.get();

				int employeNb = value.getEmployes().size();
				for (int index = 0; index < employeNb; index++) {
					if (value.getEmployes().get(index).getId() == employeId) {
						value.getEmployes().remove(index);
						break;
					}
				}
			}
			l.info("out de desaffecterEmployeDuDepartement ");
			}
			catch (Exception e) { l.error("Erreur dans desaffecterEmployeDuDepartement() : " , e); }
		
	}

	public int ajouterContrat(Contrat contrat) {
		
		try {
			l.info("in ajouter Contrat");
			l.debug("Je vais commencer l'ajout");
			contratRepoistory.save(contrat);
			l.info("out ajouter Contrat ");
			return contrat.getReference();
			}
			catch (Exception e) { l.error("Erreur dans ajouterContrat() : " , e); }
		
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		Optional<Contrat> contratManagedEntity = contratRepoistory.findById(contratId);
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		
		try {
			l.info("in  affecterContratAEmploye");
			l.debug("Je vais commencer l'affectation");
			if (contratManagedEntity.isPresent() && employeManagedEntity.isPresent()) {
				Contrat value = contratManagedEntity.get();
				Employe value1 = employeManagedEntity.get();
				value.setEmploye(value1);
				contratRepoistory.save(value);
			}
			l.info("out affecterContratAEmploye ");
			}
			catch (Exception e) { l.error("Erreur dans affecterContratAEmploye() : " , e); }

	}

	public Employe getEmployePrenomById(int employeId) {

		Optional<Employe> optional = employeRepository.findById(employeId);
		
 		try {
 			l.info("in  getEmployePrenomById");
 			l.debug("je vais récuperer l'emp");
 			if (optional.isPresent()) {
 				Employe value = optional.get();
 				 value.getPrenom();
 			}
 			l.info("out de getEmployePrenomById ");
 			}
 			catch (Exception e) { l.error("Erreur dans getEmployePrenomById() : " , e); }
		
 		return null;
	}

	public void deleteEmployeById(int employeId) {
		Optional<Employe> optional = employeRepository.findById(employeId);
		try {
			l.info("in  deleteEmployeById");
			l.debug("Je vais commencer la supression");
			if (optional.isPresent()) {
				Employe value = optional.get();
				for (Departement dep : value.getDepartements()) {
					dep.getEmployes().remove(value);
				}

				employeRepository.delete(value);
			}
			l.info("out de deleteEmployeById ");
			}
			catch (Exception e) { l.error("Erreur dans deleteEmployeById() : " , e); }

	}

	public void deleteContratById(int contratId) {
		Optional<Contrat> optional = contratRepoistory.findById(contratId);
		try {
			l.info("in  deleteContratById");
			l.debug("Je vais commencer la supression");
			if (optional.isPresent()) {
				Contrat value = optional.get();
				contratRepoistory.delete(value);
			}
			l.info("out de deleteContratById ");
			}
			catch (Exception e) { l.error("Erreur dans deleteContratById() : " , e); }

	}

	public int getNombreEmployeJPQL() {
		
		try {
			l.info("in  getNombreEmployeJPQL");
			l.debug("je vais recuperer le nombre d'emp");
			l.info("out de getNombreEmployeJPQL ");
			return employeRepository.countemp();
			}
			catch (Exception e) { l.error("Erreur dans getNombreEmployeJPQL() : " , e); }
		return employeRepository.countemp();
	}

	public List<String> getAllEmployeNamesJPQL() {
		
		
		try {
			l.info("in  getAllEmployeNamesJPQL");
			l.debug("je vais recuperer all employes names with jpql");
			l.info("out de getAllEmployeNamesJPQL ");
			return employeRepository.employeNames();
			}
			catch (Exception e) { l.error("Erreur dans getAllEmployeNamesJPQL() : " , e); }
		return employeRepository.employeNames();
	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		
		try {
			l.info("in  getAllEmployeByEntreprise");
			l.debug("je vais recuperer all employes names avec employe");
			l.info("out de getAllEmployeByEntreprise ");
			return employeRepository.getAllEmployeByEntreprisec(entreprise);
			}
			catch (Exception e) { l.error("Erreur dans getAllEmployeByEntreprise() : " , e); }
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		
		try {
 			l.info("in  mettreAjourEmailByEmployeIdJPQL");
 			l.debug("Je vais commencer la mise à jour");
 			employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);
 			l.info("out Mise à jour ");
 			}
 			catch (Exception e) { l.error("Erreur dans mettreAjourEmailByEmployeIdJPQL() : " , e); }

	}

	public void deleteAllContratJPQL() {
		
		try {
			l.info("in  deleteAllContratJPQL");
			l.debug("Je vais commencer la suppression");
			employeRepository.deleteAllContratJPQL();
			l.info("out deleteAllContratJPQL ");
			}
			catch (Exception e) { l.error("Erreur dans deleteAllContratJPQLsq() : " , e); }
		
	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		
		try {
			l.info("in  getSalaireByEmployeIdJPQL");
			l.debug("je vais récuperer salaire de l'emp with jpql");
			l.info("out de getSalaireByEmployeIdJPQL ");
			return employeRepository.getSalaireByEmployeIdJPQL(employeId);
			}
			catch (Exception e) { l.error("Erreur dans getSalaireByEmployeIdJPQL() : " , e); }
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		
		
		try {
			l.info("in  getSalaireMoyenByDepartementId");
			l.debug("je vais recuperer le salaire moyen du departement");
			l.info("out de getSalaireMoyenByDepartementId ");
			return employeRepository.getSalaireMoyenByDepartementId(departementId);
			}
			catch (Exception e) { l.error("Erreur dans getSalaireMoyenByDepartementId() : " , e); }
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {

		try {
			l.info("in  getTimesheetsByMissionAndDate");
			l.debug("je vais recuperer timesheet by mission and date");
			l.info("out de getTimesheetsByMissionAndDate ");
			return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
			}
			catch (Exception e) { l.error("Erreur dans getTimesheetsByMissionAndDate() : " , e); }
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {

		try {
			l.info("in  getAllEmployes");
			l.debug("je vais recuperer la liste des emps");
			l.info("out de getAllEmployes ");
			return (List<Employe>) employeRepository.findAll();
			}
			catch (Exception e) { l.error("Erreur dans getAllEmployes() : " , e); }
		return (List<Employe>) employeRepository.findAll();
	}

}
