package com.amr.project.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Long.class)

//TODO мне кажется нужно удалить этот класс, так как он урезанная версия UserDto
public class UserInfoDto {
    private Long id;
    private String phone;
    private String firstName;
    private String lastName;
    private int age;
    private LocalDate birthday;
    private GenderDto gender;
    private UserDto user;
}
