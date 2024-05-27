package org.ohara.restApi.security.services.impl;


import lombok.RequiredArgsConstructor;
import org.ohara.maVraiDep.data.security.data.entities.AppRole;
import org.ohara.maVraiDep.data.security.data.entities.AppUser;
import org.ohara.restApi.security.data.reporitories.RoleRepository;
import org.ohara.restApi.security.data.reporitories.UserRepository;
import org.ohara.restApi.security.services.SecurityService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService, UserDetailsService {
   private final UserRepository userRepository;
   private final RoleRepository roleRepository;
   private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public AppUser saveUser(String username, String password) {
        AppUser user = userRepository.findByUsername(username);
        if (user != null) throw new RuntimeException("User exist in app ");
        user=new AppUser(username, passwordEncoder.encode(password));
        return  userRepository.save(user);
    }

    @Override
    public AppRole saveRole(String roleName) {
         AppRole role= roleRepository.getByRoleName(roleName);
        if (role != null) throw new RuntimeException("Role exist");
        role= new AppRole(roleName,null);
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = userRepository.findByUsername(username);
        if (user == null) throw new RuntimeException("User not found ");
        AppRole role= roleRepository.getByRoleName(roleName);
        if (role == null) throw new RuntimeException("Role not found ");
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void removeRoleToUser(String username, String roleName) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username);
        if (appUser == null) throw new RuntimeException("User not found ");
        List<SimpleGrantedAuthority> authorities = appUser.getRoles()
                .stream()
                .map(appRole -> new SimpleGrantedAuthority(appRole.getRoleName())).toList();
           return new User(appUser.getUsername(), appUser.getPassword(),authorities);
    }
}
