package org.ohara.restApi.services;

import org.ohara.maVraiDep.data.entitties.Module;
import org.ohara.maVraiDep.data.web.dto.request.ModuleRequestDto;

import java.util.List;

public interface ModuleService {
    void addModule(ModuleRequestDto dto);
    List<Module> getModule();
}
