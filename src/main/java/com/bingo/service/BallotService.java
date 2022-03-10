package com.bingo.service;

import com.bingo.dao.BallotDao;
import com.bingo.domain.Ballot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BallotService implements IBallotService{

    @Autowired
    private BallotDao ballotDao;

    @Override
    @Transactional(readOnly = true)
    public List<Ballot> list(){
        return (List<Ballot>) ballotDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ballot> findBallot(Ballot ballot) {
        return ballotDao.findById(ballot.getId());
    }
}
