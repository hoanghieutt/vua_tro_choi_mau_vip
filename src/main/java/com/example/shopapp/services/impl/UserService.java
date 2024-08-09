package com.example.shopapp.services.impl;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Role;
import com.example.shopapp.models.User;
import com.example.shopapp.repositories.RoleRepository;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.zip.DataFormatException;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;
    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phonNumer = userDTO.getPhoneNumber();
        // kiểm tra xem số điện thoại đã tồn tại hay chưa
        if (userRepository.existsByPhoneNumber(phonNumer)){
            throw new DataIntegrityViolationException("phone number is already exists ");

        }
        // convert from userDTO => user
        User newuser = User.builder()
                .fullname(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAaccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("role not found"));
        newuser.setRole(role);

        // kiểm tra nếu có accountId , không yều cầu password
        if (userDTO.getFacebookAccountId()==0 && userDTO.getGoogleAccountId()==0){
            String password = userDTO.getPassword();
//            String encodedPassword =
        }
        return userRepository.save(newuser);
    }

    @Override
    public String login(String phonNumer, String passWord) {
        // liên quan đến phần scurity
        return null;
    }
}
