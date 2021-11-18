package tn.esprit.spring.services;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.repository.MissionRepository;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeServiceImplTest {

	@Autowired
	IEmployeService iemployeservice;

	@Autowired
	EmployeRepository empRepo;

	@Autowired
	ContratRepository contratRepoistory;

	@Autowired
	DepartementRepository deptRepoistory;

	@Autowired
	ITimesheetService itimesheetService;
	
	@Autowired
	IEntrepriseService iEntrepriseService;

	@Autowired
	EntrepriseRepository entRepository;

	@Autowired
	MissionRepository missionRepository;

	@Test
	public void testAjouterEmploye() {
		Employe employe = new Employe( "Ahmed", "Sallemi", "Ahmed.sellami@esprit.tn");
		int a = iemployeservice.ajouterEmploye(employe);
		assertEquals(employe.getId(), a);
	}

	@Test
	public void testajouterContrat() {
		Contrat contrat = new Contrat(1, new Date(), "contrat", 2500);
		int i = iemployeservice.ajouterContrat(contrat);
		assertEquals(contrat.getReference(), i);
	}

	@Test
	public void testgetEmployePrenomById() {
		Employe employe1 = new Employe("Ahmed", "Sallemi", "Ahmed.sellami@esprit.tn");
		Employe employe2 = empRepo.save(employe1);
		Optional<Employe> optional = empRepo.findById(employe2.getId());
		assertEquals(employe2.getId(), optional.get().getId());
		assertEquals(employe2.getPrenom(), optional.get().getPrenom());
	}

	@Test
	public void testdeleteEmployeById() {
		Employe employe1 = new Employe("Ahmed", "Sallemi", "Ahmed.sellami@esprit.tn");
		int id_emp = iemployeservice.ajouterEmploye(employe1);
		iemployeservice.deleteEmployeById(id_emp);
		Optional optional = empRepo.findById(id_emp);
		assertEquals(Optional.empty(), optional);
			
	}

	@Test
	public void testdeleteContratById() {
		Contrat contrat = new Contrat(1, new Date(), "contrat", 2500);
		Contrat cont = contratRepoistory.save(contrat);
		iemployeservice.deleteContratById(cont.getReference());
		assertNotEquals(cont.getReference(), contrat.getReference());
	}
/*
	@Test
	public void testmettreAjourEmailByEmployeId() {
		Employe employe = new Employe("Ahmed", "Sallemi", "Ahmed.sellami@esprit.tn");
		Employe emp = empRepo.save(employe);
		emp.setEmail("sallami31@gmail.com");
		iemployeservice.mettreAjourEmailByEmployeId(emp.getEmail(), emp.getId());
		assertEquals(employe.getId(), emp.getId());
		assertNotEquals(employe.getEmail(), emp.getEmail());
	}
	*/

	@Test
	public void testaffecterEmployeADepartement() {
		List<Employe> list = new ArrayList<>();
		Employe employe1 = new Employe("Ahmed", "Sallemi", "Ahmed.sellami@esprit.tn");
		list.add(employe1);
		Departement dep1 = new Departement(1, "info", list);
		iemployeservice.affecterEmployeADepartement(employe1.getId(), dep1.getId());
		assertEquals(list, dep1.getEmployes());
	}

	@Test
	public void testaffecterContratAEmploye() {
		Contrat contrat1 = new Contrat(1, new Date(), "contract", 33500);
		Employe employe1 = new Employe("Ahmed", "Sallemi", "Ahmed.sellami@esprit.tn");
		employe1.setContrat(contrat1);
		iemployeservice.affecterContratAEmploye(employe1.getId(), contrat1.getReference());
		assertEquals(contrat1, employe1.getContrat());
	}

	@Test
	public void testdesaffecterEmployeDuDepartement() {
		List<Employe> list = new ArrayList<>();
		Employe employe1 = new Employe("Ahmed", "Sallemi", "Ahmed.sellami@esprit.tn");
		list.add(employe1);
		Departement dep1 = new Departement(1, "info");
		iemployeservice.desaffecterEmployeDuDepartement(employe1.getId(), dep1.getId());
		assertNotEquals(dep1.getEmployes(), list);
	}

	@Test
	public void testGetNombreEmployeJPQL() {
		int count = iemployeservice.getNombreEmployeJPQL();
		assertNotEquals(0, count);
	}

	@Test
	public void testGetAllEmployeNamesJPQL() {
		List<Employe> list = (List<Employe>) empRepo.findAll();
		List<String> noms = new ArrayList<>();
		for (Employe emp : list) {
			noms.add(emp.getNom());
		}
		System.out.println(noms);
		List<String> empJPQL = iemployeservice.getAllEmployeNamesJPQL();
		System.out.println(empJPQL);
		assertEquals(empJPQL.size(), list.size());
	}

	@Test
	public void testGetAllEmployeByEntreprise() {
		Entreprise ent = new Entreprise("LEP", "123");
		entRepository.save(ent);

		Employe employe = new Employe("Ahmed", "Sallemi", "Ahmed.sellami@esprit.tn");
		int id_emp = iemployeservice.ajouterEmploye(employe);
		
		Departement dep = new Departement("dev");
		dep.setEntreprise(ent);
		List<Employe> emps = new ArrayList<>();
		emps.add(empRepo.findById(id_emp).get());
		dep.setEmployes(emps);
		deptRepoistory.save(dep);
		List<Entreprise> Lesentreprise = iEntrepriseService.getAllEntreprise();
		Entreprise entrepriseBDD= Lesentreprise.get(Lesentreprise.size()-1);
		Employe empBDD = empRepo.findById(id_emp).get();
		List<Employe> lesEmployes = iemployeservice.getAllEmployeByEntreprise(entrepriseBDD);
		Employe comparedEmp = lesEmployes.get(0);
		assertEquals(empBDD.getId(), comparedEmp.getId());
	}

	@Test
	public void testMettreAjourEmailByEmployeIdJPQL() {
		Employe employe = new Employe("Ahmed", "Sallemi", "Ahmed.sellami@esprit.tn");
		int id_emp = iemployeservice.ajouterEmploye(employe);
		String newMail = "sallami31@gmail.com";
		iemployeservice.mettreAjourEmailByEmployeIdJPQL(newMail, id_emp);
		Employe updatedEmp = empRepo.findById(id_emp).get();
		assertEquals(updatedEmp.getEmail(), newMail);
	}

	@Test
	public void testDeleteAllContratJPQL() {
		iemployeservice.deleteAllContratJPQL();
		List<Contrat> contrats = (List<Contrat>) contratRepoistory.findAll();
		assertEquals(0,contrats.size());
	}

	@Test
	public void testGetSalaireByEmployeIdJPQL() {
		Employe employe = new Employe("Ahmed", "Sallemi", "Ahmed.sellami@esprit.tn");
		int idEmp = iemployeservice.ajouterEmploye(employe);
		Contrat contrat = new Contrat(new Date(), "CIVP", 2500);
		contrat.setEmploye(employe);
		contratRepoistory.save(contrat);
		float salaire = iemployeservice.getSalaireByEmployeIdJPQL(idEmp);
		assertEquals(contrat.getSalaire(), salaire, 0.0f);
	}

	@Test
	public void testGetSalaireMoyenByDepartementId() {
		Employe employe1 = new Employe("amine", "sahbi", "hello@esprit.com");
		int id_emp = iemployeservice.ajouterEmploye(employe1);
		Departement dep = new Departement("recherche");
		deptRepoistory.save(dep);
		List<Departement> lesDepart= (List<Departement>) deptRepoistory.findAll();
		Departement dep1=lesDepart.get(lesDepart.size()-1);
	
		iemployeservice.affecterEmployeADepartement(id_emp, dep1.getId());

		Contrat contrat1 = new Contrat(new Date(), "contrat", 21700);
		contrat1.setEmploye(employe1);
		contratRepoistory.save(contrat1);

		float salairejpql = Float.parseFloat(iemployeservice.getSalaireMoyenByDepartementId(dep1.getId()).toString());
		assertEquals(contrat1.getSalaire(), salairejpql, 0.0f);
	}

	@Test
	public void testGetTimesheetsByMissionAndDate() throws ParseException {
		Employe employe = new Employe("Ahmed", "Sallemi", "Ahmed.sellami@esprit.tn");
		int id_emp = iemployeservice.ajouterEmploye(employe);

		Mission miss = new Mission("Recrutement", "recherche d'un stagiaire!");
		missionRepository.save(miss);

		String sDate1 = "01/11/2021";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		String sDate2 = "31/11/2021";
		Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);

		List<Mission> lesMissions= (List<Mission>) missionRepository.findAll();
		Mission comparedMission = (lesMissions).get(lesMissions.size()-1);

		itimesheetService.ajouterTimesheet(comparedMission.getId(), id_emp, date1, date2);
		Employe comparedEmp = empRepo.findById(id_emp).get();

		List<Timesheet> tsBDD = iemployeservice.getTimesheetsByMissionAndDate(comparedEmp, comparedMission, date1,
				date2);
		assertNotEquals(0, tsBDD.size());
	}

	@Test
	public void testGetAllEmployes() {
		List<Employe> employes = iemployeservice.getAllEmployes();
		assertNotEquals(0, employes.size());
	}

}