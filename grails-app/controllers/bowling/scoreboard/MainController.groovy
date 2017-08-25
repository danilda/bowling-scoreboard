package bowling.scoreboard


import commandObject.NewGame
import grails.converters.JSON

class MainController {

    def index() {
        respond new User(name: "Danil")
    }

    def newGame() {
        def d = new Date()
        [date : d, usersCount: 0]
    }

    def show(Game game) {
        respond new Game(date: new Date())
    }

    def showAllGames() {
        respond new Game(date: new Date())
    }

    def newUser(NewGame model) {
        println model
        respond model.date
    }


}
