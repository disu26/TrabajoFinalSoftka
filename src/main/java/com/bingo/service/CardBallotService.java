package com.bingo.service;

import com.bingo.dao.CardBallotDao;
import com.bingo.domain.CardBallot;
import com.bingo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CardBallotService implements ICardBallotService{

    @Autowired
    private CardBallotDao cardBallotDao;

    @Override
    @Transactional(readOnly = true)
    public List<CardBallot> list() {
        return (List<CardBallot>) cardBallotDao.findAll();
    }

    @Override
    @Transactional
    public CardBallot save(CardBallot cardBallot) {
        return cardBallotDao.save(cardBallot);
    }

    @Transactional
    public void updateMarked(CardBallot cardBallot) {
        cardBallotDao.updateMarked(cardBallot.getCardBallotId(), true);
    }

    @Override
    @Transactional
    public void delete(CardBallot cardBallot) {
        cardBallotDao.delete(cardBallot);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CardBallot> findCardBallot(CardBallot cardBallot) {
        return cardBallotDao.findById(cardBallot.getCardBallotId());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<CardBallot> findByCardId(User user) {
        return cardBallotDao.findByCardId(user.getCardId());
    }

    @Transactional(readOnly = true)
    public Optional<CardBallot> findByCardAndBallotId(Long cardId, Long ballotId) {
        return cardBallotDao.findByCardAndBallotId(cardId, ballotId);
    }
}
