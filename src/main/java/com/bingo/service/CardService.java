package com.bingo.service;

import com.bingo.dao.CardDao;
import com.bingo.domain.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CardService implements ICardService{

    @Autowired
    private CardDao cardDao;

    @Override
    @Transactional(readOnly = true)
    public List<Card> list() {
        return (List<Card>) cardDao.findAll();
    }

    @Override
    @Transactional
    public Card save(Card card) {
        return cardDao.save(card);
    }

    @Override
    @Transactional
    public Card update(Long id, Card card) {
        card.setId(id);
        return cardDao.save(card);
    }

    @Transactional
    public void updateWinner(Long id, Card card) {
        cardDao.updateWinner(id, card.isWinner());
    }

    @Override
    @Transactional
    public void delete(Card card) {
        cardDao.delete(card);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Card> findCard(Card card) {
        return cardDao.findById(card.getId());
    }
}
