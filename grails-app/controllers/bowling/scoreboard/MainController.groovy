package bowling.scoreboard


import commandObject.UsersNames
import commandObject.Roll

class MainController {
    GameService gameService
    GameDBService gameDBService

    def test() {
        render "test gsp"
    }

    def addUser(UsersNames users) {
        if (users == null || users.getNames() == null) {
            users = new UsersNames(names: [])
        }
        if (users.getNames().size() == 6) {
            flash.put("error", "Maximum number of players 6!")
        } else {
            users.getNames().add("")
        }
        render view: "addUser", model: [users: users]
    }

    def removeUser(UsersNames users) {

        if (users.getNames().size() == 1) {
            flash.put("error", "There must be at least one player in the game")
        } else {
            users.getNames().remove(users.names.size()-1)
        }
        render view: "addUser", model: [users: users]
    }

    def addNewGame(UsersNames users) {
        //TODO добавить норм проверки
        if (users.getNames().size() == 1 && users.getNames().get(0) == "") {
            flash.put("error", "Please clarify name")
            render view: "addUser", model: [users: users]
            return
        }

        List<User> usersForSave = []
        users.getNames().each {
            usersForSave.add(new User(number: users.getNames().indexOf(it), name: it))
        }
        Game game = gameDBService.addNewGame usersForSave
        redirect action: 'showGame', params: [id: game.id]
    }

    def showGame(String id) {
        println "showGame, params: game - " + id
        def game = Game.get(id)
        game.getUsers().each {
            println it
        }
        Map renderMap = gameService.getMapForRenderingFromGame game
        println renderMap
        println gameService.getNextStep(game)
        render view: "showGame", model: [renderMap: renderMap, nextRoll: gameService.getNextStep(game)]
    }

    def saveRoll(Roll roll) {
        println roll
        Game game = gameDBService.addRollInGame roll
        redirect action: 'showGame', params: [id: game.id]
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

    //ошибку для минимального количества игроков
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
        game.users.add(new UsersNames())
        UsersNames user = game.users.get(game.users.size() - 1)
        user.frames = new ArrayList<>()
        for (i in 0..9) {
            user.frames.add(new CommandFrame(number: i))
        }
    }*/
}
