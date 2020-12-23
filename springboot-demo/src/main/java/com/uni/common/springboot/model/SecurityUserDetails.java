//package com.uni.common.springboot.model;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.experimental.Accessors;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
//@Setter
//@Getter
//@Accessors(chain = true)
//public class SecurityUserDetails extends Users implements UserDetails {
//
//    private Collection<? extends GrantedAuthority> authorities;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    public SecurityUserDetails(Users users, Collection<? extends GrantedAuthority> authorities){
//        this.setUsername(users.getUsername());
//        this.setPassword(users.getPassword());
//        this.setAuthorities(authorities);
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
