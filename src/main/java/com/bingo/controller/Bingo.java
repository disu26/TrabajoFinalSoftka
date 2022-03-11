package com.bingo.controller;

import com.bingo.domain.*;
import com.bingo.service.*;
import com.bingo.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.PATCH})
public class Bingo {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private CardService cardService;

    @Autowired
    private BallotService ballotService;

    @Autowired
    private CardBallotService cardBallotService;

    private Response response = new Response();

    @GetMapping(path = "/card/{mongoId}")
    public ResponseEntity<Response> cardValue(User user){
        var usu = userService.findUserByMongoId(user);
        int value[][]  = new int[5][5];
        if (usu.isPresent()){
            var card = cardBallotService.findByCardId(usu.get());
            for (Iterator<CardBallot> iterator = card.iterator(); iterator.hasNext();){
                for (int i=0; i < 5; i++){
                    for (int j=0; j < 5; j++){
                        if (i == 2 && j==2){
                            j = 3;
                        }
                        var item = iterator.next();
                        value[j][i] = Math.toIntExact(item.getBalId());
                        log.info(item.getBalId().toString());
                    }
                }
            }
            response.data = value;
        }else {
            response.message = "Usuario no encontrado";
            response.error = true;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/game/{id}")
    public ResponseEntity<Response> createGame(@PathVariable("id") String mongoId){
        try {
            Game game = new Game();
            gameService.save(game);
            response.data = game;
            Card card = new Card();
            cardService.save(card);

            User user = new User();
            user.setMongoId(mongoId);
            /**
             * Creación de las diferentes balotas, cada vez que se cree una nueva partida estos
             * deben volver a ser inicializados, ya que se tienen atributos como si la balota ya salió
             * que son únicos de cada partida
             */
            for (int i = 1; i <= 15; i++) {
                Ballot ballot = new Ballot();
                ballot.setLetter("B");
                ballot.setNumber(String.valueOf(i));
                ballotService.save(ballot);
            }

            for (int i = 16; i <= 30; i++) {
                Ballot ballot = new Ballot();
                ballot.setLetter("I");
                ballot.setNumber(String.valueOf(i));
                ballotService.save(ballot);
            }

            for (int i = 31; i <= 45; i++) {
                Ballot ballot = new Ballot();
                ballot.setLetter("N");
                ballot.setNumber(String.valueOf(i));
                ballotService.save(ballot);
            }

            for (int i = 46; i <= 60; i++) {
                Ballot ballot = new Ballot();
                ballot.setLetter("G");
                ballot.setNumber(String.valueOf(i));
                ballotService.save(ballot);
            }

            for (int i = 61; i <= 75; i++) {
                Ballot ballot = new Ballot();
                ballot.setLetter("O");
                ballot.setNumber(String.valueOf(i));
                ballotService.save(ballot);
            }

            /**
             * Asignacion de las balotas a las diferentes tarjetas,
             * esto se realiza apoyandonos en el hecho de que los ids de las
             * balotas están en orden del 1 al 75, lo que quiere decir que del 1 al 15
             * tendremos la letra B, del 16 al 30 la letra I y así sucesivamente.
             */
            generateCard(card.getId(), 1);

            generateCard(card.getId(), 15);

            generateCard(card.getId(), 30);

            generateCard(card.getId(), 45);

            generateCard(card.getId(), 60);

            user.setGameId(game.getId());
            user.setCardId(card.getId());
            userService.save(user);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception exc){
            response.status = exc.getCause().toString();
            response.error = true;
            if (Pattern.compile("(user.use_mongo_id_UNIQUE)").matcher(exc.getMessage()).find()){
                response.message = "el usuario ya está en la partida";
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }else {
                response.message = exc.getMessage();
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PostMapping(path = "/game/{gameId}/{id}")
    public ResponseEntity<Response> addUser(@PathVariable("gameId") Long gameId, @PathVariable("id") String mongoId){
        try {
            Card card = new Card();
            cardService.save(card);

            User user = new User();
            user.setMongoId(mongoId);

            generateCard(card.getId(), 1);

            generateCard(card.getId(), 15);

            generateCard(card.getId(), 30);

            generateCard(card.getId(), 45);

            generateCard(card.getId(), 60);

            user.setGameId(gameId);
            user.setCardId(card.getId());
            userService.save(user);
            response.data = user;
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception exc){
            response.status = exc.getCause().toString();
            response.error = true;
            if (Pattern.compile("(user.use_mongo_id_UNIQUE)").matcher(exc.getMessage()).find()){
                response.message = "el usuario ya está en la partida";
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }else {
                response.message = exc.getMessage();
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PutMapping(path = "/game/winner/{mongoId}")
    public ResponseEntity<Response> winner(User user){
        var usu = userService.findUserByMongoId(user);
        if (usu.isPresent()){
            var usuario = usu.get();
            userService.updateWinner(usuario);
            response.data = usuario;
        }else {
            response.message = "Usuario no encontrado";
            response.error = true;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void generateCard(Long cardId, int min){
        /**
         * Los números son generados aleatoriamente.
         */
        Set<Integer> generated = new HashSet<>();
        Random random = new Random();
        if(min == 30){
            for (int i = 0; i < 4; i++) {
                CardBallot cardBallot = new CardBallot();
                boolean gen = false;
                Long value = Long.valueOf(0);
                while (!gen) {
                    int possible;
                    if (min != 1) {
                        possible = random.nextInt(15 + 1) + min;
                    } else {
                        possible = random.nextInt(14 + 1) + min;
                    }
                    if (!generated.contains(possible)) {
                        generated.add(possible);
                        value = Long.valueOf(possible);
                        gen = true;
                    }
                    }
                cardBallot.setBalId(value);
                cardBallot.setCardId(cardId);
                cardBallotService.save(cardBallot);
            }
        }else {
            for (int i = 0; i < 5; i++) {
                CardBallot cardBallot = new CardBallot();
                boolean gen = false;
                Long value = Long.valueOf(0);
                while (!gen) {
                    int possible;
                    if (min != 1) {
                        possible = random.nextInt(15 + 1) + min;
                    } else {
                        possible = random.nextInt(14 + 1) + min;
                    }
                    if (!generated.contains(possible)) {
                        generated.add(possible);
                        value = Long.valueOf(possible);
                        gen = true;
                    }
                }
                cardBallot.setBalId(value);
                cardBallot.setCardId(cardId);
                cardBallotService.save(cardBallot);
            }
        }
    }

}
