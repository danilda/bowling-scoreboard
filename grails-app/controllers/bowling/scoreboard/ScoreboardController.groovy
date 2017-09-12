package bowling.scoreboard

import commandObject.RollCommand
import commandObject.UsersNamesCommand


import static org.springframework.http.HttpStatus.NOT_FOUND

class ScoreboardController {
    GameService gameService
    GameDBService gameDBService

    def addUsers() {
        render view: "addUsers"
    }

    def addNewGame(UsersNamesCommand users) {
        if (users.hasErrors()) {
            respond users.errors, view:'addUser', model:[users: users, error: true]
            return
        }
        List<User> usersForSave = []
        users.getNames().eachWithIndex {String name, int i ->
            usersForSave.add(new User(number: i, name: name))
        }
        Game game = gameDBService.addNewGame usersForSave
        redirect action: 'showGame', params: [id: game.id]
    }

    def showGame(String id) {
        Game game = Game.get(id)
        if (!game){
            render status: NOT_FOUND
        }
        Map renderMap = gameService.getModelForRendering game
        render view: "showGame", model: [renderMap: renderMap, nextRoll: gameService.getNextStep(game)]
    }

    def saveRoll(RollCommand roll) {
        // TODO to add validation
        Game game = gameDBService.addRollInGame roll
        redirect action: 'showGame', params: [id: game.id]
    }

    def index() {
    }

    def showGameList(){
        respond Game.list(params)
    }
}
