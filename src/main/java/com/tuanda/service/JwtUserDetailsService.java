package com.tuanda.service;

import com.tuanda.common.Constants;
import com.tuanda.custome_exception.InvalidFormatException;
import com.tuanda.dto.request.UserRequestDTO;
import com.tuanda.entity.User;
import com.tuanda.mapper.UserMapper;
import com.tuanda.repository.UserRepository;
import com.tuanda.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

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
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            authorities.add(new SimpleGrantedAuthority(user.getRole()));

            UserDetails principal = new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), authorities);

            return principal;
        }
        return null;
    }

    public User save(UserRequestDTO userDto) throws InvalidFormatException {
        validateInfo(userDto);
        String password = this.bcryptEncoder.encode(userDto.getPassword());
        User user = this.userMapper.mapToUser(userDto, password);
        return this.userRepository.save(user);
    }

    public User updateProfile(UserRequestDTO userDto) throws InvalidFormatException {
        boolean isChangePassword = StringUtils.isNotEmpty(userDto.getPassword());
        validateNewInfo(userDto);
        User old_user = this.userRepository.findById(userDto.getId()).orElse(null);
        if(Objects.isNull(old_user)) throw new InvalidFormatException(Constants.Message.USER_ID_IS_NOT_EXIST);
        // if change password
        String password = old_user.getPassword();
        if(isChangePassword)
            password = this.bcryptEncoder.encode(userDto.getPassword());
        User new_user = this.userMapper.mapToUser(userDto, password);
        return this.userRepository.save(new_user);
    }

    private void validateInfo(UserRequestDTO userDto) throws InvalidFormatException {
        if (!StringUtils.isValidEmail(userDto.getEmail()))
            throw new InvalidFormatException(Constants.Message.INVALID_EMAIL);
        if (!StringUtils.isValidUsername(userDto.getUsername()))
            throw new InvalidFormatException(Constants.Message.INVALID_USERNAME);
        if (!StringUtils.isValidPassword(userDto.getPassword()))
            throw new InvalidFormatException(Constants.Message.INVALID_PASSWORD);

        Long id = this.userRepository.findUserIdByUserName(userDto.getUsername());
        if (Objects.nonNull(id))
            throw new InvalidFormatException(String.format(Constants.Message.EXIST_USERNAME, userDto.getUsername()));

        String username = this.userRepository.findUsernameByEmail(userDto.getEmail());
        if (Objects.nonNull(username))
            throw new InvalidFormatException(String.format(Constants.Message.EXIST_EMAIL, userDto.getEmail()));
    }

    private void validateNewInfo(UserRequestDTO userDto) throws InvalidFormatException {
        boolean isUpdatePassword = StringUtils.isNotEmpty(userDto.getPassword());
        if (!StringUtils.isValidEmail(userDto.getEmail()))
            throw new InvalidFormatException(Constants.Message.INVALID_EMAIL);
        if (!StringUtils.isValidUsername(userDto.getUsername()))
            throw new InvalidFormatException(Constants.Message.INVALID_USERNAME);
        if (isUpdatePassword && !StringUtils.isValidPassword(userDto.getPassword()))
            throw new InvalidFormatException(Constants.Message.INVALID_PASSWORD);

        Long id = this.userRepository.findUserIdByUserNameExceptSelf(userDto.getUsername(), userDto.getId());
        if (Objects.nonNull(id))
            throw new InvalidFormatException(String.format(Constants.Message.EXIST_USERNAME, userDto.getUsername()));

        String username = this.userRepository.findUsernameByEmailExceptSelf(userDto.getEmail(), userDto.getId());
        if (Objects.nonNull(username))
            throw new InvalidFormatException(String.format(Constants.Message.EXIST_EMAIL, userDto.getEmail()));
    }

    public User findByUsername(String userName) {
        return this.userRepository.findUserByUsername(userName);
    }

    public String findUsernameByEmail(String email) {
        return this.userRepository.findUsernameByEmail(email);
    }

}
