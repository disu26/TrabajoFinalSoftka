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

    @Transactional(readOnly = true)
    public List<Ballot> outList(){
        return (List<Ballot>) ballotDao.findBallotOut(true);
    }

    @Override
    @Transactional
    public Ballot save(Ballot ballot){
        return ballotDao.save(ballot);
    }

    @Transactional
    public void updateOut(Ballot ballot){
        ballotDao.updateOut(ballot.getId(), true);
    }

    @Transactional
    public void updateLetter(Ballot ballot, String letter){
        ballotDao.updateLetter(ballot.getId(), letter);
    }

    @Transactional
    public void updateNumber(Ballot ballot, String number){
        ballotDao.updateNumber(ballot.getId(), number);
    }

    @Transactional
    public void restartBallot(){
        ballotDao.restartBallot(false);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ballot> findBallot(Ballot ballot) {
        return ballotDao.findById(ballot.getId());
    }

    @Transactional(readOnly = true)
    public Optional<Ballot> findBallotById(Long id) {
        return ballotDao.findById(id);
    }
}
