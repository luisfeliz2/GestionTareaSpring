package com.gestion_tarea.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion_tarea.models.Project;


public interface ProjectRepository extends JpaRepository<Project, Long> {

	Optional<Project> findByName(String name);

	Optional<Project> findByNameAndTenantId(String name, Long id);
	List<Project> findByTenantId(Long id);
Optional<Project> findByIdAndTenantId(Long id,Long tenantId);
	


}