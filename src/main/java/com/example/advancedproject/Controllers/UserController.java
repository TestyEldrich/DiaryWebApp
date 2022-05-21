package com.example.advancedproject.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.advancedproject.Entities.DailyNotesEntity;
import com.example.advancedproject.Entities.Role;
import com.example.advancedproject.Entities.UserEntity;
import com.example.advancedproject.Exceptions.EmailAlreadyInUseException;
import com.example.advancedproject.Exceptions.UserNotFoundException;
import com.example.advancedproject.Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUsersPass")
    public ModelAndView getUsersPass(Model model){
        List<UserEntity> users = userService.getUsers();
        model.addAttribute("users", users);
        return new ModelAndView("admin");
    }

    @GetMapping("/getUsersDelete")
    public ModelAndView getUsersDelete(Model model){
        List<UserEntity> users = userService.getUsers();
        model.addAttribute("users", users);
        return new ModelAndView("admin2");
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> Register(@ModelAttribute UserEntity user) throws EmailAlreadyInUseException {
        userService.register(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/roleSave")
    public ResponseEntity<Role>saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/roleSave").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/roleAddToUser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getEmail(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getUser")
    public ResponseEntity GetOneUser(@RequestParam Long user_id){
        try{
            return ResponseEntity.ok(userService.getOne(user_id));
        } catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception E){
            return ResponseEntity.badRequest().body("Error occurred");
        }

    }

    @PostMapping("/editUser")
    public ResponseEntity edit(@ModelAttribute UserEntity requestUser) {
        try{
            userService.editUser(requestUser);
            return ResponseEntity.ok("Successfully edited");
        } catch(UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Error occurred");
        }
    }

    @PostMapping("/deleteUser/{user_id}")
    public ResponseEntity DeleteUser(@PathVariable Long user_id){
        try{
            return ResponseEntity.ok(userService.deleteUser(user_id));
        } catch(Exception E){
            return ResponseEntity.badRequest().body("Error occurred");
        }
    }

    @GetMapping("/checkEntity")
    public ResponseEntity CheckEntity(){
        try{
            return ResponseEntity.ok("Everything works ok");
        } catch(Exception E){
            return ResponseEntity.badRequest().body("Error occurred");
        }
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
            try{
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                System.out.println("passed");
                JWTVerifier verifier = JWT.require(algorithm).build();
                System.out.println(refresh_token);
                DecodedJWT decodedJWT =  verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                UserEntity user = userService.getUserByEmail(email);
                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception exception){
                log.error("Error logging in {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
    }

}
@Data
class RoleToUserForm{
    private String email;
    private String roleName;
}
