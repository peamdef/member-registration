package com.test.registation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.registation.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

public class UserPrinciple implements UserDetails {
  private static final long serialVersionUID = 1L;
 
  private String  referenceCode;
 
    private String name;
 
    private String username;
 
    private String email;

    private String phoneNumber;
 
    @JsonIgnore
    private String password;
 
    private Collection<? extends GrantedAuthority> authorities;
 
    public UserPrinciple(String referenceCode, String name,
              String username, String email, String password,String phoneNumber) {
        this.referenceCode = referenceCode;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
 
    public static UserPrinciple build(User user) {
        return new UserPrinciple(
                user.getReferenceCode(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber()
        );
    }
 
    public String getReferenceCode() {
        return referenceCode;
    }
 
    public String getName() {
        return name;
    }
 
    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() { return phoneNumber; }
 
    @Override
    public String getUsername() {
        return username;
    }
 
    @Override
    public String getPassword() {
        return password;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(referenceCode, user.referenceCode);
    }
}
