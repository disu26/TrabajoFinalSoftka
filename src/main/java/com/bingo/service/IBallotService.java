package com.bingo.service;

import com.bingo.domain.Ballot;

import java.util.List;
import java.util.Optional;

public interface IBallotService {

    public List<Ballot> list();

    public Optional<Ballot> findBallot(Ballot ballot);
}
