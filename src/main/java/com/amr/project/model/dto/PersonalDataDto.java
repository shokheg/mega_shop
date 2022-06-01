package com.amr.project.model.dto;


import com.amr.project.model.entity.Image;
import com.amr.project.model.enums.PersonalDataStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Long.class)
public class PersonalDataDto {
    private Long id;
    private Long passport;
    private Date dateOfIssue;
    private String authority;
    private String placeOfBirth;
    private List<Image> listOfImages;
    private PersonalDataStatus status;
}
