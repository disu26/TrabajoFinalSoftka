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
@Table(name = "user")
public class User implements Serializable{

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "use_id")
    private Long id;

    @Column(name = "use_mongo_id")
    private String mongoId;

    @Column(name = "game_gam_id")
    private Long gameId;

    @Column(name = "card_car_id")
    private Long cardId;

    @Column(name = "use_winner")
    private boolean winner;
}
