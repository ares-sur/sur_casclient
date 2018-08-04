package org.ares.app.c5.security.rbac.spring;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class UserDetailsModel implements UserDetails {
	private String username;
	private String password;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;  
    private boolean isEnabled = true;
    private Collection<? extends GrantedAuthority> authorities=new HashSet<>();
}
