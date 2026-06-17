package com.example.auth_app_backend.user.service;

import com.example.auth_app_backend.common.util.UserHelper;
import com.example.auth_app_backend.common.config.APPConstants;
import com.example.auth_app_backend.user.dto.UserDto;
import com.example.auth_app_backend.auth.entity.Provider;
import com.example.auth_app_backend.user.entity.Role;
import com.example.auth_app_backend.user.entity.User;
import com.example.auth_app_backend.common.exception.ResourceNotFoundException;
import com.example.auth_app_backend.user.repository.RoleRepository;
import com.example.auth_app_backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if(userDto.getEmail()==null|| userDto.getEmail().isBlank()){
            throw new IllegalArgumentException("Email is required");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email Already Existed");
        }
       User user= modelMapper.map(userDto, User.class);
        user.setId(null);   // MOST IMPORTANT LINE
        user.setParentId(userDto.getParentId()!=null?userDto.getParentId():"#");
        user.setProvider(userDto.getProvider()!=null?userDto.getProvider(): Provider.LOCAL);
        user.setCreateAt(Instant.now());
        //role assign here to user  for authorization
        Role role = roleRepository.findByName("ROLE_" + APPConstants.GUEST_ROLE)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User savedUser=userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
       User user= userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User Not Found with given email id"));
       return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        UUID uuid=UserHelper.parseUUID(userId);
        User existingUser=userRepository.findById(uuid).orElseThrow(()->new ResourceNotFoundException(("User not existing with this id ")));
        // We are not  going to change email id for this project
        if(userDto.getName()!=null){
            existingUser.setName(userDto.getName());
        }
        if(userDto.getProvider()!=null){
            existingUser.setProvider(userDto.getProvider());
        }
        if(userDto.getPhone()!=null){
            existingUser.setPhone(userDto.getPhone());
        }
        if(userDto.getEmail()!=null){
            existingUser.setEmail(userDto.getEmail());
        }
        if(userDto.getPassword()!=null){
            existingUser.setPassword(userDto.getPassword());
        }
        if (userDto.getCompany() != null) {
            existingUser.setCompany(userDto.getCompany());
        }
        //TODO: change password updation logic ...
        existingUser.setEnable(userDto.isEnable());
        existingUser.setUpdateAt(Instant.now());
        User updatedUser=userRepository.save(existingUser);
        return modelMapper.map(updatedUser,UserDto.class);

    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        UUID uuid= UserHelper.parseUUID(userId);
      User user= userRepository.findById(uuid).orElseThrow(()->new ResourceNotFoundException("User Not Found with given id "));
      userRepository.delete(user);

    }

    @Override
    @Transactional
    public Iterable<UserDto> getAllUser() {
        return userRepository.findAll().stream().map(
                 user ->modelMapper.map(user,UserDto.class)).toList();
    }

    @Override
    public UserDto getUserById(String userId) {
        UUID uuid = UserHelper.parseUUID(userId);

        User user = userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User Not Found with given id "));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                Collections.emptyList()
        );
    }
}
