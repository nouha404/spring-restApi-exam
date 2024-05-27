package org.ohara.restApi.security.services;


import org.ohara.maVraiDep.data.security.data.entities.AppRole;
import org.ohara.maVraiDep.data.security.data.entities.AppUser;

public interface SecurityService {
    AppUser getUserByUsername(String username);
    AppUser saveUser(String username,String password);
    AppRole saveRole(String roleName);
    void addRoleToUser(String username,String roleName);
    void removeRoleToUser(String username,String roleName);
}
