package com.example.AuthService.service;

import com.example.AuthService.entities.UserInfo;
import com.example.AuthService.eventProducer.UserInfoProducer;
import com.example.AuthService.model.UserInfoDto;
import com.example.AuthService.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@Data
public class UserDetailsServiceImpl implements UserDetailsService
{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserInfoProducer userInfoProducer;


    public UserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                  UserInfoProducer userInfoProducer){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userInfoProducer = userInfoProducer;
    }

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

        log.debug("Entering in loadUserByUsername Method...");
        UserInfo user = userRepository.findByUsername(username);
        if(user == null){
            log.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        log.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExist(UserInfoDto userInfoDto){

        return userRepository.findByUsername(userInfoDto.getUsername());
    }

    public Boolean signupUser(UserInfoDto userInfoDto){
        //        ValidationUtil.validateUserAttributes(userInfoDto);
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExist(userInfoDto))){
            return false;
        }
        String userId = UUID.randomUUID().toString();
        userRepository.save(new UserInfo(userId, userInfoDto.getUsername(), userInfoDto.getPassword(), new HashSet<>()));
        // pushEventToQueue
        userInfoProducer.sendEventToKafka(userInfoDto);
        return true;
    }
}
