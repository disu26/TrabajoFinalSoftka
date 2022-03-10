package com.bingo.service;

import com.bingo.domain.Card;

import java.util.List;
import java.util.Optional;

public interface ICardService {

    public List<Card> list();

    public Card save(Card card);

    public Card update(Long id, Card card);

    public void delete(Card card);

    public Optional<Card> findContact(Card card);
}
