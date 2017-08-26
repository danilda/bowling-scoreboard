package bowling.scoreboard

import commandObject.CommandFrame
import commandObject.CommandGame
import commandObject.CommandUser

class MainController {

    def index() {
        respond new User(name: "Danil")
    }

    def newGame() {
        def game = new CommandGame()
        game.date = new Date()
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

}
