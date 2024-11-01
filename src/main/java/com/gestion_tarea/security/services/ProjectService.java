package com.gestion_tarea.security.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gestion_tarea.models.Project;
import com.gestion_tarea.models.Tenant;
import com.gestion_tarea.models.User;
import com.gestion_tarea.repository.ProjectRepository;
import com.gestion_tarea.repository.UserRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    
    
    
    

      
        public Project createProject(Project project, Tenant tenant) {
            // Verifica si el nombre del proyecto no es nulo o vacío
            if (project.getName() == null || project.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("The project name cannot be null or empty.");
            }

            // Verifica si ya existe un proyecto con el mismo nombre dentro del mismo Tenant
            Optional<Project> existingProject = projectRepository.findByNameAndTenantId(project.getName(), tenant.getId());

            if (existingProject.isPresent()) {
                throw new  IllegalArgumentException("A project with this name already exists in this tenant.");
            }

            // Si no existe, asigna el Tenant al proyecto y guárdalo en la base de datos
            project.setTenant(tenant);

            // Opcional: Establecer la fecha de creación si es necesario
            project.setStartDate(LocalDateTime.now());

            return projectRepository.save(project);
        }
    
    
//    
//    public Project addMemberToProject(Long projectId, String username) {
//        // Obtener el proyecto por su ID
//        Optional<Project> optionalProject = projectRepository.findById(projectId);
//        if (optionalProject.isEmpty()) {
//            throw new RuntimeException("Project not found with id " + projectId);
//        }
//        Project project = optionalProject.get();
//
//        // Obtener el usuario por su nombre de usuario
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new RuntimeException("User not found with username " + username);
//        }
//
//        // Añadir el usuario al proyecto si aún no está presente
//        if (!project.getMembers().contains(user)) {
//            project.getMembers().add(user);
//        }
//
//        // Guardar el proyecto actualizado en la base de datos
//        return projectRepository.save(project);
//    }

	public List<Project> getAllProjects(Long tenanId) {
		// TODO Auto-generated method stub
		return projectRepository.findByTenantId(tenanId);
	}
	
	public Project getProjectById(Long id,Long tenanId){
		return projectRepository.findByIdAndTenantId(id, tenanId)
				.orElseThrow(()->new RuntimeException("project np fkfds"));
				
	}
	public Project addMemberToProject(Long projectId, String username) {
	    // Buscar el proyecto por su ID
	    Optional<Project> projectOptional = projectRepository.findById(projectId);
	    if (!projectOptional.isPresent()) {
	        throw new RuntimeException("Project not found");
	    }

	    Project project = projectOptional.get();

	    // Buscar el usuario por su nombre de usuario
	    Optional<User> userOptional = userRepository.findByUsername(username);
	    if (!userOptional.isPresent()) {
	        throw new RuntimeException("User not found");
	    }

	    User user = userOptional.get();

	    // Validar que el usuario y el proyecto pertenezcan al mismo tenant
	    if (!user.getTenant().equals(project.getTenant())) {
	        throw new RuntimeException("User does not belong to the same tenant as the project");
	    }

	    // Añadir el usuario al proyecto si la validación es correcta
	    project.getMembers().add(user);

	    // Guardar el proyecto actualizado
	    return projectRepository.save(project);
	}
	
	public List<Project> getProjectsByMenber(Long tenantId,Long userId){
		return projectRepository.findByTenantIdAndMembers_Id(tenantId,userId);
	}
	
	public List<Project> getProjectsByResponsible(Long tenantId,Long userId){
		return projectRepository.findByTenantIdAndResponsible_Id(tenantId, userId);
	}

}
