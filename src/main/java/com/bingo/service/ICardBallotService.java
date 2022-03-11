package com.bingo.service;

import com.bingo.domain.CardBallot;
import com.bingo.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ICardBallotService {

    public List<CardBallot> list();

    public CardBallot save(CardBallot cardBallot);

    public void delete(CardBallot cardBallot);

    public Optional<CardBallot> findCardBallot(CardBallot cardBallot);

    public Collection<CardBallot> findByCardId(User user);
}
