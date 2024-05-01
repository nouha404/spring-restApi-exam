package org.ohara.restApi.repositories;

import org.ohara.maVraiDep.data.entitties.Module;
import org.ohara.maVraiDep.data.entitties.Professeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories(basePackageClasses = ModuleRepository.class)
public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findByLibelle(String moduleLibelle);
    //Optional<Module> findById(Long id);
    Optional<Module> findModuleByLibelle(String libelle);
}
