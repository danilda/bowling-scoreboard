package bowling.scoreboard

import grails.converters.JSON

class MainController {

    def index() {
        respond new User(name: "Danil")
    }

    def newGame() {
        respond new Game(date: new Date())
    }

    def show(Game game) {
        respond new Game(date: new Date())
    }

    def showAllGames() {
        respond new Game(date: new Date())
    }

    def newUser() {

        println params.game
        Game game = new Game(JSON.parse(params.game))
        respond game
    }

}
