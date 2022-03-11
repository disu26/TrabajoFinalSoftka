package com.bingo.service;

import com.bingo.domain.Ballot;
import com.bingo.domain.Card;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IBallotService {

    public List<Ballot> list();

    public Ballot save(Ballot ballot);

    public Optional<Ballot> findBallot(Ballot ballot);
}
