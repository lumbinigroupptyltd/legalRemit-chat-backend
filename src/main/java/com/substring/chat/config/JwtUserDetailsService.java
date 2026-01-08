package com.substring.chat.config;


import com.substring.chat.coresdk.IUserServiceSDK;
import com.substring.chat.coresdk.dto.PermissionCustomResponse;
import com.substring.chat.coresdk.dto.RolePermissionListResponse;
import com.substring.chat.coresdk.dto.UserRolePermissionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserServiceSDK iUserServiceSDK;

    //    public JwtUserDetailsService(RestTemplate restTemplate, UserRepository userRepository, IUserServiceSDK iUserServiceSDK) {
//        this.restTemplate = restTemplate;
//        this.userRepository = userRepository;
//        this.iUserServiceSDK = iUserServiceSDK;
//    }
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserRolePermissionResponse user = iUserServiceSDK.getUserByEmailIgnoreCaseAndIsActive(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (RolePermissionListResponse role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            for (PermissionCustomResponse userRoleToPrivilege : role.getPermission()) {
                authorities.add(new SimpleGrantedAuthority(userRoleToPrivilege.getPermissionName()));
            }
        }

        return new CustomUserDetails(user.getEmail(), user.getPassword(), user.getIsActive(), authorities);
//		Users user = userRepository.findByEmailIgnoreCase(email);
//		if (user == null) {
//			throw new UsernameNotFoundException("User not found with username: " + email);
//		}
//		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//				new ArrayList<>());
    }

}
