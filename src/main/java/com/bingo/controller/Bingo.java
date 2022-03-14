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

    /**
     * Variables para utilizar los servicios de las diferentes tablas.
     */
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

    /**
     * Variable de tipo Response, utilizada para retornar errores e información.
     */
    private Response response;

    /**
     * Método para retorar el valor de la tarjeta de un usuario en forma de matriz,
     * esta matriz es utilizada para mostrarla en el FrontEnd.
     * @param user
     * @return un objeto de tipo Response, que en su campo response.data lleva la matriz de la tarjeta
     */
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

    /**
     * Método que retorna la lista de jugadores en una partida como un objeto,
     * para mostrarlo posteriormente en el FrontEnd.
     * @return un objeto de tipo Response, que en su campo response.data contiene un objeto de tipo User.
     */
    @GetMapping(path = "/players")
    public ResponseEntity<Response> playersList(){
        response = new Response();
        try {
            List<User> players = new ArrayList<>();
            var gameStarted = gameService.findGameStarted();
            if (gameStarted.isPresent()) {
                var game = gameStarted.get();
                var users = userService.findUserByGameId(game.getId());
                for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
                    var item = iterator.next();
                    players.add(item);
                }
                response.data = players;
            } else {
                throw new Exception("Juego no encontrado");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception exc){
            response.message = exc.getMessage();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/game/started")
    public ResponseEntity<Response> started(){
        response = new Response();
        var gameStarted = gameService.findGameStarted();
        if (gameStarted.isPresent()){
            response.data = true;
        }else {
            response.message = "No hay ningún juego iniciado";
            response.data = false;
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/game/inProgress")
    public ResponseEntity<Response> inProgress(){
        response = new Response();
        var gameInProgress = gameService.findGameInProgress();
        if (gameInProgress.isPresent()){
            response.data = true;
        }else {
            response.message = "No hay ningún juego en progreso";
            response.data = false;
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "game/startTime")
    public ResponseEntity<Response> startTime(){
        response = new Response();
        var gameStarted = gameService.findGameStarted();
        if (gameStarted.isPresent()){
            var game = gameStarted.get();
            response.data = game.getStart_timer();
        }else {
            response.message = "No hay ningún juego iniciado";
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "game/userAdmin/{mongoId}")
    public ResponseEntity<Response> userAdmin(User user){
        response = new Response();
        var us = userService.findUserByMongoId(user);
        if(us.isPresent()){
            var admin =  us.get();
            if(admin.isAdmin()){
                response.data = "true";
            }else {
                response.data = "false";
            }

        }else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "game/ballotsOut")
    public ResponseEntity<Response> ballotsOut(){
        response = new Response();
        List ballotsOut = ballotService.outList();
        response.data = ballotsOut;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "game/isWinner/{mongoId}")
    public ResponseEntity<Response> isWinner(User user){
        response = new Response();
        var us = userService.findUserByMongoId(user);
        if(us.isPresent()){
            int [] cardWinner = new int[24];
            var probWinner = us.get();
            boolean winner = false;
            Collection<CardBallot> cW = cardBallotService.findByCardId(probWinner);
            for (Iterator<CardBallot> iterator = cW.iterator(); iterator.hasNext(); ) {
                for (int i=0 ; i < 24; i++){
                    var item = iterator.next();
                    /**
                     * Se llena un vector de 23 posiciones, en el que se pondra un 1 si esa balota está marcada,
                     * este vector después se valida teniendo en cuanta que las balotas se almacenan de a 5 por letra
                     * y 4 en la letra N.
                     */
                    if(item.isMarked()){
                        cardWinner[i] = 1;
                    }else {
                        cardWinner[i] = 0;
                    }
                }
            }

            /**
             * Verificación si el usuario ganó o no.
             */
            if(cardWinner[0] == 1 && cardWinner[6] == 1 && cardWinner[17] == 1 && cardWinner[23] == 1){
                winner = true;
            }else if (cardWinner[4] == 1 && cardWinner[8] == 1 && cardWinner[15] == 1 && cardWinner[19] == 1){
                winner = true;
            }else  if(cardWinner[0] == 1 && cardWinner[1] == 1 && cardWinner[2] == 1 && cardWinner[3] == 1 && cardWinner[4] == 1){
                winner = true;
            }else if(cardWinner[5] == 1 && cardWinner[6] == 1 && cardWinner[7] == 1 && cardWinner[8] == 1 && cardWinner[9] == 1){
                winner = true;
            }else if(cardWinner[10] == 1 && cardWinner[11] == 1 && cardWinner[12] == 1 && cardWinner[13] == 1 ){
                winner = true;
            }else if(cardWinner[14] == 1 && cardWinner[15] == 1 && cardWinner[16] == 1 && cardWinner[17] == 1 && cardWinner[18] == 1){
                winner = true;
            }else if(cardWinner[19] == 1 && cardWinner[20] == 1 && cardWinner[21] == 1 && cardWinner[22] == 1 && cardWinner[23] == 1){
                winner = true;
            }else if(cardWinner[0] == 1 && cardWinner[5] == 1 && cardWinner[10] == 1 && cardWinner[14] == 1 && cardWinner[19] == 1){
                winner = true;
            }else if(cardWinner[1] == 1 && cardWinner[6] == 1 && cardWinner[11] == 1 && cardWinner[15] == 1 && cardWinner[20] == 1){
                winner = true;
            }else if(cardWinner[2] == 1 && cardWinner[7] == 1 && cardWinner[12] == 1 && cardWinner[16] == 1 && cardWinner[21] == 1){
                winner = true;
            }else if(cardWinner[3] == 1 && cardWinner[8] == 1 && cardWinner[13] == 1 && cardWinner[17] == 1 && cardWinner[22] == 1){
                winner = true;
            }else if(cardWinner[4] == 1 && cardWinner[9] == 1 && cardWinner[14] == 1 && cardWinner[18] == 1 && cardWinner[23] == 1){
                winner = true;
            }
            if(winner){
                userService.updateWinner(probWinner);
                response.data = true;
                response.message = "El usuario ganó";
            }else {
                response.data = false;
                response.message = "El usuario no ganó";
            }
        }else {
            response.message = "Usuario no encontrado";
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Cuando ingresa la primera persona, se crea un juego y se le asigna la tarjeta a esta persona.
     * @param mongoId
     * @return Objeto de tipo response que muestra una cosa u otra dependiendo de si la creación es exitosa o no.
     */
    @PostMapping(path = "/game/{mongoId}")
    public ResponseEntity<Response> createGame(@RequestBody @PathVariable("mongoId") String mongoId){
        response = new Response();
        try {
            var gameStarted = gameStarted();
            if(gameStarted.getBody().data.equals(false)) {
                Card card = new Card();
                cardService.save(card);
                /**
                 * El usuario que crea el juego es asignado como admin y este tendrá la labor de
                 * generar las balotas.
                 */
                User user = new User();
                user.setAdmin(true);
                user.setMongoId(mongoId);
                var firstGame = gameService.findAnyGame();

                if(!firstGame.isPresent()) {
                    /**
                     * Creación de las diferentes balotas si es la primera vez que se ejecuta la aplicación.
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
                }

                /**
                 * Después de generar las balotas, se crea el primer juego.
                 */
                Game game = new Game();
                Date startTime = new Date();
                gameService.save(game);
                gameService.updateStarted(game.getId(), game, true);
                gameService.updateStartTime(game.getId(), game, startTime);
                response.data = game;

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
            }else{
                throw new Exception("Ya hay una partida creada");
            }
        }catch (Exception exc){
            response.status = "ERROR";
            response.error = true;
            if (Pattern.compile("(user.use_game_mongoid_UNIQUE)").matcher(exc.getMessage()).find()){
                response.message = "el usuario ya está en la partida";
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }else if(exc.getMessage().equals("Ya hay una partida creada")){
                response.message = exc.getMessage();
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }else {
                response.message = exc.getMessage();
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Cuando ingresa una persona y ya hay una partida iniciada pero no en progreso,
     * la persona que entre se añade a esta.
     * @param mongoId
     * @return
     */
    @PostMapping(path = "/game/addUser/{mongoId}")
    public ResponseEntity<Response> addUser(@RequestBody @PathVariable("mongoId") String mongoId){
        response = new Response();
        try {
            var gameInProgressBool = gameInProgress();
            if(gameInProgressBool.getBody().data.equals(false)) {
                var gameStarted = gameService.findGameStarted().get();
                Card card = new Card();
                cardService.save(card);

                User user = new User();
                user.setMongoId(mongoId);

                generateCard(card.getId(), 1);

                generateCard(card.getId(), 15);

                generateCard(card.getId(), 30);

                generateCard(card.getId(), 45);

                generateCard(card.getId(), 60);

                user.setGameId(gameStarted.getId());
                user.setCardId(card.getId());
                userService.save(user);

                response.data = user;
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }else {
                throw new Exception("Ya hay una partida en progreso, intente de nuevo más tarde");
            }
        }catch (Exception exc){
            response.status = "ERROR";
            response.error = true;
            if (Pattern.compile("(user.use_game_mongoid_UNIQUE)").matcher(exc.getMessage()).find()){
                response.message = "el usuario ya está en la partida";
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }else {
                response.message = exc.getMessage();
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PutMapping(path = "ballot/out")
    public ResponseEntity<Response> ballotOut(){
        Random random = new Random();
        int balOut = random.nextInt(74 + 1) + 1;
        var bal = ballotService.findBallotById((long) balOut);
        if (bal.isPresent()){
            var ballot = bal.get();
            if(ballot.isOut()){
                ballot = posibleBallot(ballot);
            }
            ballotService.updateOut(ballot);
            response.data = ballot;
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "balcard/{mongoId}/{balId}")
    public ResponseEntity<Response> balCardMarked(@RequestBody @PathVariable("mongoId") String mongoId,
                                                  @RequestBody @PathVariable("balId") Long balId)
    {
        response = new Response();
        var us = userService.findUserByMongoId(mongoId);
        if(us.isPresent()) {
            var userCard = us.get();
            var cB = cardBallotService.findByCardAndBallotId(userCard.getCardId(), balId);
            if (cB.isPresent()) {
                var cardBallot = cB.get();
                cardBallotService.updateMarked(cardBallot);
                response.data = cardBallot;
            } else {
                response.message = "Esa balota no se encuentra en esa tarjeta";
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public Ballot posibleBallot(Ballot ballot){
        Random random = new Random();
        if(ballot.isOut()){
            int balOut = random.nextInt(74 + 1) + 1;
            var bal = ballotService.findBallotById((long) balOut);

            if (bal.isPresent()){
                var posibleBalot = bal.get();
                if(!posibleBalot.isOut()){
                    return posibleBalot;
                }else {
                    posibleBallot(posibleBalot);
                }
            }
        }
        return ballot;
    }

    @PutMapping(path = "/game/winner/{mongoId}")
    public ResponseEntity<Response> winner(User user){
        response = new Response();
        log.info(user.getMongoId());
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

    @PutMapping(path = "/game/upInProgress")
    public ResponseEntity<Response> updateInProgress(){
        var gameStarted = gameService.findGameStarted();
        if(gameStarted.isPresent()){
            var game = gameStarted.get();
            gameService.updateInProgress(game.getId(),game, true);
            response.message = "Actualizado Correctamente";
        }else {
            response.message = "No hay ningún juego iniciado";
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "/game/finish")
    public ResponseEntity<Response> finish(){
        response = new Response();
        var gameInProgress = gameService.findGameInProgress();
        if(gameInProgress.isPresent()){
            var game = gameInProgress.get();
            gameService.updateFinished(game.getId(), game , true);
            gameService.updateInProgress(game.getId(), game, false);
            gameService.updateStarted(game.getId(), game, false);
            ballotService.restartBallot();
            response.data = game;
        }else {
            response.message = "No hay ningún juego en progreso";
            return new ResponseEntity<>(response,HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Metodo para verificar si existe o no una partida en progreso.
     * @return Objeto response que en su campo response.data lleva un boolean diciendo
     * que indica si hay o no una partida en progreso
     */
    public ResponseEntity<Response> gameInProgress(){
        response = new Response();
        var gameInProgress = gameService.findGameInProgress();
        if (gameInProgress.isPresent()){
            response.data = true;
        }else {
            response.data = false;
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Response> gameStarted(){
        response = new Response();
        var gameStarted = gameService.findGameStarted();
        if (gameStarted.isPresent()){
            response.data = true;
        }else {
            response.data = false;
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
