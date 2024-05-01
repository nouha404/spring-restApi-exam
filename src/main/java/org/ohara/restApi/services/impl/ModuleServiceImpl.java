package org.ohara.restApi.services.impl;

import lombok.RequiredArgsConstructor;
import org.ohara.maVraiDep.data.entitties.Module;
import org.ohara.maVraiDep.data.web.dto.request.ModuleRequestDto;
import org.ohara.restApi.repositories.ModuleRepository;
import org.ohara.restApi.services.ModuleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;

    @Override
    public void addModule(ModuleRequestDto dto) {
        Module module = dto.TransformToEntity();
        module.setLibelle(module.getLibelle());
        module.setIsActive(true);
        moduleRepository.save(module);

    }
}
