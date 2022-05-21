package com.example.advancedproject.Services;

import com.example.advancedproject.Entities.Role;
import com.example.advancedproject.Entities.UserEntity;
import com.example.advancedproject.Exceptions.EmailAlreadyInUseException;
import com.example.advancedproject.Exceptions.UserNotFoundException;
import com.example.advancedproject.Repos.RoleRepository;
import com.example.advancedproject.Repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service @Transactional @Slf4j @RequiredArgsConstructor
public class UserService implements UserDetailsService{
    @Autowired
    private final UserRepository userRepo;
    @Autowired
    private final RoleRepository roleRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByEmail(email);
        System.out.println("email = " + email);
        if(user == null){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }else{
            log.info("User found: {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("User"));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public Role saveRole(Role role){
        log.info("Saving role: {}", role.getName());
        return roleRepo.save(role);
    }

    public void addRoleToUser(String email, String name){
        log.info("Adding role {} to {}", name, email);
        UserEntity user = userRepo.findByEmail(email);
        Role role = roleRepo.findByName(name);
        user.getRoles().add(role);
    }

    public UserEntity register(UserEntity user) throws EmailAlreadyInUseException {
        if(userRepo.findByEmail(user.getEmail()) != null){
            throw new EmailAlreadyInUseException("Email is already in use");
        }
        log.info("Saving user: {}", userRepo.findByEmail(user.getEmail()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepo.findByName("ROLE_USER");
        user.getRoles().add(role);
        return userRepo.save(user);
    }

    public UserEntity getOne(Long user_id) throws UserNotFoundException {
        UserEntity user = userRepo.findById(user_id).get();
        Optional<UserEntity> optionalUser = userRepo.findById(user_id);
        if(optionalUser.isPresent()){
            log.info("Fetching user: {}", userRepo.findById(user_id).get());
            return user;
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }

    public String deleteUser(Long user_id){
        userRepo.deleteById(user_id);
        System.out.println(user_id);
        return "User " + user_id + " has been successfully deleted";
    }

    public boolean editUser(UserEntity user) throws UserNotFoundException {
        UserEntity oldUser = userRepo.findByEmail(user.getEmail());
        boolean exists = userRepo.existsById(oldUser.getUser_id());
        if(!exists){
            throw new UserNotFoundException("User not found");
        }
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(oldUser);
        return true;
    }

    public List<UserEntity> getUsers(){
        log.info("Fetching All users");
        return userRepo.findAll();
    }

    public UserEntity getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }
}
