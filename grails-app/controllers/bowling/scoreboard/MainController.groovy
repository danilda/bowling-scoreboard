package bowling.scoreboard

import commandObject.CommandFrame
import commandObject.CommandGame
import commandObject.CommandUser
import static GameService.DATE_FORMAT

import java.text.SimpleDateFormat

class MainController {
    GameService gameService
    ScoreService scoreService

    def test(){
        User user = new User()
        def testFrames = []
        for(i in 0..8){
            user.addToFrames new Frame(number: i, rollOne: 10, rollTwo: 0)
        }
        user.addToFrames  new Frame(number: 9, rollOne: 10, rollTwo: 10, rollThree: 10)
        scoreService.calculateFrames user
        testFrames = scoreService.getSortedValidListOfFramesFromUser user
        println testFrames.get(0).score == 30
        println testFrames.get(1).score == 60
        println testFrames.get(2).score == 90
        println testFrames.get(3).score == 120
        println testFrames.get(4).score == 150
        println testFrames.get(5).score == 180
        println testFrames.get(6).score == 210
        println testFrames.get(7).score == 240
        println testFrames.get(8).score == 270
        println testFrames.get(9).score == 300

        render "test gsp"
    }

    def index() {
        respond new User(name: "Danil")
    }

    def newGame() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT)
        def game = new CommandGame()
        game.date = sdf.format(new Date())
        game.users = new ArrayList<>()
        addNewUserInGame(game)
        [game : game]
    }

    def showGame(Game game) {
        respond game: gameService.getRenderMapByGame(game)
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
//        redirect action: 'showGame', params: [game: game]
        render view: "showGame", model: [game: game]
    }


    private addNewUserInGame(CommandGame game){
        game.users.add(new CommandUser())
        CommandUser user =  game.users.get(game.users.size() - 1)
        user.frames = new ArrayList<>()
        for(i in 0..9){
            user.frames.add(new CommandFrame(number: i))
        }
    }


}
