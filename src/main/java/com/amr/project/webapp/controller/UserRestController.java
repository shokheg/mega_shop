package com.amr.project.webapp.controller;

import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.*;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.EmailSenderService;
import com.amr.project.service.abstracts.UserService;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@Api(tags = "User controller")
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;

    private final EmailSenderService emailSenderService;

    public UserRestController(UserService userService, UserMapper userMapper, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get user by id")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        UserDto userDto = userMapper.toDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "Update user")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        User user = userMapper.toModel(userDto);
        userService.update(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create new user")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        User user = userMapper.toModel(userDto);
        String message = String.format("Hello, %s \n" +
                "Welcome to Avito. Please visit: http://localhost:8080/api/users/activate/%s/%s",
                user.getUsername(),
                user.getId(),
                user.getActivationCode());
        emailSenderService.sendEmail(user.getEmail(), "Activation Code", message);
        userService.persist(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/activate/{id}/{code}")
    @ApiOperation(value = "Подтверждение почты пользователя")
    public ResponseEntity<UserDto> activateUser(@PathVariable Long id, @PathVariable String code) {
        if(userService.existsById(id)) {
            userService.activateUser(id, code);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete user by id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.existsById(id)) {
            userService.deleteByIdCascadeEnable(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
