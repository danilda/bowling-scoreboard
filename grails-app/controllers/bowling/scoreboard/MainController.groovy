package bowling.scoreboard

import commandObject.CommandFrame
import commandObject.CommandGame
import commandObject.CommandUser
import static GameService.DATE_FORMAT

import java.text.SimpleDateFormat

class MainController {
    GameService gameService
    def testid1
    def testid2

    def index() {
        def rollsOne = [10, 10, 10, 7, 8, 0, 10, 7, 9, 10]
        def rollsTwo = [0, 0, 0, 2, 2, 9, 0, 3, 0, 10]
        def rollsThree = [null, null, null, null, null, null, null, null, null, 8]
        User user = new User(name: "Sumy", totalScore: 1)
        for(int i in 0..9){
            user.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i], rollThree: rollsThree[i], score: 1))
        }
        Game game = new Game(date: new Date()).addToUsers(user)
        game.save(flush:true)
        game.errors.allErrors.each {
            println it
        }
        testid1 = game.getId()
        testid2 = user.getId()
        println user.getName()
        println testid1
        println testid2
        respond new User(name: "Danil")
    }

    def test(){
        println Game.get(testid1)
        render "Hello world"
    }

    def newGame() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT)
        def game = new CommandGame()
        game.date = sdf.format(new Date())
        game.users = new ArrayList<>()
        addNewUserInGame(game)
        [game : game]
    }

    def show(Game game) {
        respond new Game(date: new Date())
    }

    def showAllGames() {
        respond new Game(date: new Date())
    }

    //ошибку для минимального количества игроков
    def newUser(CommandGame game) {
        if(game.users.size() == 6){
            flash.put("error", "Maximum number of players 6!")
        } else {
            addNewUserInGame(game)
        }
        println game.users.size()
        render view: "newGame", model: [game: game]
    }

    private addNewUserInGame(CommandGame game){
        game.users.add(new CommandUser())
        CommandUser user =  game.users.get(game.users.size() - 1)
        user.frames = new ArrayList<>()
        for(i in 0..9){
            user.frames.add(new CommandFrame())
        }
    }

    def saveGame(CommandGame commandGame){
        Game game = gameService.saveGameByCommandGame(commandGame)
        if(game.errors.iterator().size() == 0){
            flash.put("error", "Incorrect inputted data")
            render view: "newGame", model: [game: commandGame]
        }

        Game.findAll().each {
            println it.getDate()
            println it.getId()
        }
        render "Game saved"

    }

}
