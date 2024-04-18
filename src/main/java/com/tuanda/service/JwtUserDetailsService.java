package com.tuanda.service;

import com.tuanda.dto.request.UserRequestDTO;
import com.tuanda.entity.User;
import com.tuanda.mapper.UserMapper;
import com.tuanda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private UserMapper userMapper;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = this.userRepository.findByEmail(username);
//
//        if (Objects.isNull(user)) throw new UsernameNotFoundException("User not found with username: " + username);
//
//        //generate role from database
//        List<RoleSecurity> roles = this.generateRole(user.getRole());
//
//        return new org.springframework.security.core.userdetails.User(user.getEmail(),
//                bcryptEncoder.encode(user.getPassword()),
//                roles);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            authorities.add(new SimpleGrantedAuthority(user.getRole()));

            UserDetails principal = new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), authorities);

            return principal;
        }
        return null;
    }

    public User save(UserRequestDTO userDto) {
        String password = this.bcryptEncoder.encode(userDto.getPassword());
        User user = this.userMapper.mapToUser(userDto, password);
        return this.userRepository.save(user);
    }

    public User findByUsername(String userName) {
        return this.userRepository.findByEmail(userName);
    }
}
