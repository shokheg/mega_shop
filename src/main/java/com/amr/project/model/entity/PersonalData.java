package com.amr.project.model.entity;

import com.amr.project.model.enums.PersonalDataStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@ToString
public class PersonalData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "passport",nullable = false, unique = true)
    private Long passport;

    @Column(name = "dateOfIssue", nullable = false)
    private Date dateOfIssue;

    @Column(name = "authority", nullable = false)
    private String authority;

    @Column(name = "placeOfBirth", nullable = false)
    private String placeOfBirth;

    @Column(name = "comment")
    private String comment;


    @OneToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH},
            orphanRemoval = true)

    @ToString.Exclude
    private List<Image> listOfImages;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "personalDataStatus")
    private PersonalDataStatus status;

}
