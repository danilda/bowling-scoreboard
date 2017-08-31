package bowling.scoreboard

import commandObject.CommandUser
import commandObject.Roll

import static GameService.DATE_FORMAT

import java.text.SimpleDateFormat

class MainController {
    GameService gameService
    ScoreService scoreService
    GameDBService gameDBService

    def test() {
        Game game = new Game()
        User user = new User(name: "test", number: 0)
        def rollsOne = [2, 5, 10, 4, 8, 5]
        def rollsTwo = [8, 0, 0, 5, 0, 5]
        for (i in 0..rollsOne.size() - 1) {
            user.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i]))
        }
        User user2 = new User(name: "test2", number: 1)
        rollsOne = [2, 5, 10, 4, 8, 2]
        rollsTwo = [8, 0, 0, 5, 0, null]
        for (i in 0..rollsOne.size() - 1) {
            user2.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i]))
        }
        User user3 = new User(name: "test3", number: 2)
        rollsOne = [2, 5, 10, 4, 8]
        rollsTwo = [8, 0, 0, 5, 0]
        for (i in 0..rollsOne.size() - 1) {
            user3.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i]))
        }
        game.addToUsers(user)
        game.addToUsers(user2)
        game.addToUsers(user3)
        Roll roll = gameService.getNextStep(game)
        println roll
        render "test gsp"
    }

    def addUser(List<String> userNames) {
        if (userNames == null) {
            userNames = []
        } else if (userNames.size() == 6) {
            flash.put("error", "Maximum number of players 6!")
        } else {
            userNames.add("")
        }
        render view: "addUser", model: [users: userNames]
    }

    def addNewGame(List<String> userNames){
        List<User> users = []
        userNames.each {
            users.add(new User(number: userNames.indexOf(it), name: it))
        }
        Game game = gameDBService.addNewGame users
        showGame game //заработает ли оно?
    }

    def showGame(Game game){
        Map renderMap = gameService.getMapForRenderingFromGame game
        render view: "showGame", model: renderMap
    }

    //каждый раз ли будет перезаписывать? TODO проверить
    def saveRoll(Roll roll){
        Game game = gameDBService.addRollInGame roll
        showGame game //заработает ли оно?
    }

    def index() {
        respond new User(name: "Danil")
    }



    /*

    def newGame() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT)
        def game = new CommandGame()
        game.date = sdf.format(new Date())
        game.users = new ArrayList<>()
        addNewUserInGame(game)
        [game: game]
    }

    def showGame(Game game) {
        respond game: gameService.getRenderMapByGame(game)
    }

    def showAllGames() {
        respond new Game(date: new Date())
    }

    def newUser(CommandGame game) {
        if (game.users.size() == 6) {
            flash.put("error", "Maximum number of players 6!")
        } else {
            addNewUserInGame(game)
        }
        println game.users.size()
        render view: "newGame", model: [game: game]
    }

    def saveGame(CommandGame commandGame) {
        Game game = gameService.saveGameByCommandGame(commandGame)
        if (game.errors.iterator().size() == 0) {
            flash.put("error", "Incorrect inputted data")
            render view: "newGame", model: [game: commandGame]
        }

        Game.findAll().each {
            println it.getDate()
            println it.getId()
        }
//        redirect action: 'showGame', params: [game: game]
        render view: "showGame", model: [game: game]
    }

    private addNewUserInGame(CommandGame game) {
        game.users.add(new CommandUser())
        CommandUser user = game.users.get(game.users.size() - 1)
        user.frames = new ArrayList<>()
        for (i in 0..9) {
            user.frames.add(new CommandFrame(number: i))
        }
    }*/
}
