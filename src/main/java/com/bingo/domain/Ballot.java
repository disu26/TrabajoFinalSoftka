package com.bingo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ballot")
public class Ballot implements Serializable{
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bal_id")
    private Long id;

    @Column(name = "bal_letter")
    private String letter;

    @Column(name = "bal_number")
    private String number;

    @Column(name = "bal_out")
    private boolean out;

}
