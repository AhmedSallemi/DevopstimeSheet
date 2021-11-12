package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	public int ajouterEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Optional<Employe> optional = employeRepository.findById(employeId);
		if (optional.isPresent()) {
			Employe value = optional.get();
			value.setEmail(email);
			employeRepository.save(value);
		}
	}

	@Transactional
	public void affecterEmployeADepartement(int employeId, int depId) {

		Optional<Departement> depManagedEntity = deptRepoistory.findById(depId);
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);

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

	}

	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		Optional<Departement> dep = deptRepoistory.findById(depId);
		if (dep.isPresent()) {
			Departement value = dep.get();

			int employeNb = value.getEmployes().size();
			for (int index = 0; index < employeNb; index++) {
				if (value.getEmployes().get(index).getId() == employeId) {
					value.getEmployes().remove(index);
					break;// a revoir
				}
			}
		}
	}

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		Optional<Contrat> contratManagedEntity = contratRepoistory.findById(contratId);
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		if (contratManagedEntity.isPresent() && employeManagedEntity.isPresent()) {
			Contrat value = contratManagedEntity.get();
			Employe value1 = employeManagedEntity.get();
			value.setEmploye(value1);
			contratRepoistory.save(value);
		}

	}

	public Employe getEmployePrenomById(int employeId) {

		Optional<Employe> optional = employeRepository.findById(employeId);
		if (optional.isPresent()) {
			Employe value = optional.get();
			 value.getPrenom();
		}
		return null;
		

	}

	public void deleteEmployeById(int employeId) {
		Optional<Employe> optional = employeRepository.findById(employeId);
		if (optional.isPresent()) {
			Employe value = optional.get();
			for (Departement dep : value.getDepartements()) {
				dep.getEmployes().remove(value);
			}

			employeRepository.delete(value);
		}

	}

	public void deleteContratById(int contratId) {
		Optional<Contrat> optional = contratRepoistory.findById(contratId);
		if (optional.isPresent()) {
			Contrat value = optional.get();
			contratRepoistory.delete(value);
		}

	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}

	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}

	public void deleteAllContratJPQL() {
		employeRepository.deleteAllContratJPQL();
	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
		return (List<Employe>) employeRepository.findAll();
	}

}
