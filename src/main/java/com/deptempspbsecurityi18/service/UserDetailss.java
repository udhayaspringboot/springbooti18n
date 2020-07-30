package com.deptempspbsecurityi18.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.deptempspbsecurityi18.model.User;



public class UserDetailss implements UserDetails {

	
	private String password;
	private String roles;
	private String userName;
	private boolean active;
		List<GrantedAuthority> gcrantedAuthority;
	public UserDetailss(User user)
	{
		this.userName=user.getUserName();
		this.password = user.getPassword();
		this.roles = user.getRoles();
		this.gcrantedAuthority = Arrays.stream(user.getRoles().split(",")).
				map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	public UserDetailss()
	{
		
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return gcrantedAuthority;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		// TODO Auto-generated method stub
		return true;
	}

}
