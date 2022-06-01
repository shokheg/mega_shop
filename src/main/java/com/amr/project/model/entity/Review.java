package com.amr.project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Objects;

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "dignity")
    private String dignity; //плюсы

    @Column(name = "flaw")
    private String flaw; //минусы

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private Date date;

    @Column(name = "rating")
    private int rating;


    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Shop shop;


    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Item item;


    private boolean isModerated;
    private boolean isModerateAccept;
    private String moderatedRejectReason;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Review review = (Review) o;
        return id != null && Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
