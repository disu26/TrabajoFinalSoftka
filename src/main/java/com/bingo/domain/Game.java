package com.bingo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "game")
public class Game implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gam_id")
    private Long id;

    @Column(name = "gam_inProgress")
    private boolean inProgress;

    @Column(name = "gam_started")
    private boolean started;

    @Column(name = "gam_finished")
    private boolean finished;

    @Column(name = "gam_start_timer")
    private Date start_timer;
}
