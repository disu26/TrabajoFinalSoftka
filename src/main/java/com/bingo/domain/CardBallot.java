package com.bingo.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "card_has_ballot")
public class CardBallot implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_bal_id")
    private Long cardBallotId;

    @Column(name = "card_car_id")
    private Long cardId;

    @Column(name = "ballot_bal_id")
    private Long balId;

    @Column(name = "card_bal_marked")
    private boolean marked;

}
