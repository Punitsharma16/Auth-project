package com.example.auth_app_backend.services;

import com.example.auth_app_backend.Utils.UserHelper;
import com.example.auth_app_backend.config.APPConstants;
import com.example.auth_app_backend.dtos.AccountMaster;
import com.example.auth_app_backend.dtos.UserDto;
import com.example.auth_app_backend.entities.Provider;
import com.example.auth_app_backend.entities.Role;
import com.example.auth_app_backend.entities.User;
import com.example.auth_app_backend.exception.ResourceNotFoundException;
import com.example.auth_app_backend.repositories.AccountRepository;
import com.example.auth_app_backend.repositories.RoleRepository;
import com.example.auth_app_backend.repositories.UserRepository;
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
    AccountRepository accountRepository;
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
        user.setId(null);   // 🔥 MOST IMPORTANT LINE
        user.setProvider(userDto.getProvider()!=null?userDto.getProvider(): Provider.LOCAL);
        //role assign here to user  for authorization
        Role role = roleRepository.findByName("ROLE_" + APPConstants.GUEST_ROLE)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        User savedUser=userRepository.save(user);
        // generate dynamic account number
        Long accountNumber = 10000 + new Random().nextLong(90000);
        // ACCOUNT SAVE (SQL)
        AccountMaster account = AccountMaster.builder()
                .accountId("ACC_" + UUID.randomUUID())
                .accountNumber(accountNumber)
                .fullname(userDto.getName())
                .createBy(savedUser.getId().toString())
                .updateBy(savedUser.getId().toString())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .logo(userDto.getImage())
                .company("companyname")
                .active(true)
                .hasTwoStepAuthencation(false)
                .createAt(Instant.now())
                .updateAt(Instant.now())
                .user(savedUser)
                .build();

        accountRepository.save(account);

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
            existingUser.setName(userDto.getEmail());
        }
        if(userDto.getProvider()!=null){
            existingUser.setProvider(userDto.getProvider());
        }
        if(userDto.getPassword()!=null){
            existingUser.setPassword(userDto.getPassword());
        }
        //TODO: change password updation logic ...
        existingUser.setEnable(userDto.isEnable());
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
