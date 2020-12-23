//package com.uni.common.springboot.service;
//
//import com.uni.common.springboot.model.SecurityUserDetails;
//import com.uni.common.springboot.model.Users;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@AllArgsConstructor
//@Slf4j
//@Service
//public class JwtUserDetailsService implements UserDetailsService  {
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        List<GrantedAuthority> authorityList = new ArrayList<>();
//        Users users = new Users();
//
//        // todo 写入自己业务系统的用户角色代码逻辑
//
//        return new SecurityUserDetails(users, authorityList);
//    }
//}
