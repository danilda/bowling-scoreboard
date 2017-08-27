package bowling.scoreboard

import commandObject.CommandFrame
import commandObject.CommandGame
import commandObject.CommandUser
import static GameService.DATE_FORMAT

import java.text.SimpleDateFormat

class MainController {
    GameService gameService

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
        Game game = gameService.commandGameInGame(commandGame)
        if(game.errors.iterator().size() == 0){
            flash.put("error", "Incorrect inputted data")
            render view: "newGame", model: [game: commandGame]
        }


    }

}
