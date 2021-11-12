package tn.esprit.spring.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;

@Component
public class MapStructMapperImpl {

	public Employe employePostDtoToEmploye(EmployeDto empDto) {
        if ( empDto == null ) {
            return null;
        }

        Employe emp = new Employe();

        emp.setId( empDto.getId() );
        emp.setEmail( empDto.getEmail() );
        emp.setNom(empDto.getName());
        emp.setPrenom(empDto.getPrenom());
        emp.setActif(empDto.isActif());
        emp.setRole(empDto.getRole());

        return emp;
    }
	
	public Contrat contratPostDtoToContrat(ContratDto contratDto) {
        if ( contratDto == null ) {
            return null;
        }

        Contrat contrat = new Contrat();

        contrat.setReference( contratDto.getReference() );
        contrat.setDateDebut(contratDto.getDateDebut());
        contrat.setSalaire(contratDto.getSalaire());
        contrat.setTypeContrat(contratDto.getTypeContrat());


        return contrat;
    }
	
	
	public Entreprise entreprisePostDtoToEntreprise(EntrepriseDto entrDto) {
        if ( entrDto == null ) {
            return null;
        }

        Entreprise entrp = new Entreprise();

        entrp.setId(entrDto.getId());
        entrp.setName(entrDto.getName());
        entrp.setRaisonSocial(entrDto.getRaisonSocial());



        return entrp;
    }
    
	
	public Departement departementPostDtoToDepartement(DepartementDto depDto) {
        if ( depDto == null ) {
            return null;
        }

        Departement dep = new Departement();

        dep.setId(depDto.getId());
        dep.setName(depDto.getName());



        return dep;
    }
	
	public Mission missionPostDtoToMission(MissionDto missDto) {
        if ( missDto == null ) {
            return null;
        }

        Mission miss = new Mission();

        miss.setId(missDto.getId());
        miss.setName(missDto.getName());
        miss.setDescription(missDto.getDescription());


        return miss;
    }
	
	public EmployeDto mapperToDtoEmp(Employe model) {
		EmployeDto dto = new EmployeDto();
        dto.setId(model.getId());
        dto.setEmail( model.getEmail() );
        dto.setName(model.getNom());
        dto.setPrenom(model.getPrenom());
        dto.setActif(model.isActif());
        dto.setRole(model.getRole());
        return dto;
	}
}
