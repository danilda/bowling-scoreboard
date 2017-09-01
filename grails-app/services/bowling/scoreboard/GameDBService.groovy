package bowling.scoreboard

import commandObject.Roll
import grails.gorm.transactions.Transactional

@Transactional
class GameDBService {

    ScoreService scoreService

    def addNewGame(List<User> users) {
        Game game = new Game(date: new Date())
        users.each { user ->
            game.addToUsers(user)
        }
        game.save()
        game
    }

    def addRollInGame(Roll roll) {
        println(roll)
        Game game = Game.get(roll.getGameId())
        println(game)
        List<User> users = game.getUsers().toList().sort { current, next -> current.number <=> next.number }
        User currentUser = users[(int) roll.getUserNumber()]
        addRollInUser(currentUser, roll)
        if (roll.rollNumber in [1, 2] || roll.value == 10) {
            scoreService.calculateFrames currentUser
        }
        game.save()
        println "GameDBService" + game
        game.getUsers().each {
            println "GameDBService" + it
            it?.getFrames().each{
                println "GameDBService" + it
            }
        }
        game
    }

    private addRollInUser(User user, Roll roll) {
        if (roll.getRollNumber() == 0) {
            addRollOne(user, roll)
        } else {
            addRollTwoAndThree(user, roll)
        }
    }

    private addRollOne(User user, Roll roll) {
        if (roll.getValue() == 10 && roll.frameNumber != 9) {
            user.addToFrames(new Frame(number: roll.getFrameNumber(), rollOne: roll.getValue(), rollTwo: 0))
        } else {
            user.addToFrames(new Frame(number: roll.getFrameNumber(), rollOne: roll.getValue()))
        }
    }

    private addRollTwoAndThree(User user, Roll roll) {
        List frames = user.getFrames().toList().sort { current, next -> current.number <=> next.number }
        def currentFrame = frames[(int) roll.getFrameNumber()]
        if (roll.getRollNumber() == 1) {
            currentFrame.setRollTwo(roll.getValue())
        } else {
            currentFrame.setRollThree(roll.getValue())
        }
    }

}
