package bowling.scoreboard

import commandObject.Roll
import grails.gorm.transactions.Transactional
import static enums.RollsEnum.ROLL_ONE
import static enums.RollsEnum.ROLL_TWO
import static ScoreService.ALL_BOWLS
import static ScoreService.LAST_FRAME

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
        scoreService.calculateFrames currentUser
        game.save()
        println "GameDBService" + game
        game.getUsers().each {
            println "GameDBService" + it
            it?.getFrames()?.each {
                println "GameDBService" + it
            }
        }
        game
    }

    private addRollInUser(User user, Roll roll) {
        List frames = user.getFrames().toList().sort { current, next -> current.number <=> next.number }
        def currentFrame = frames[(int) roll.getFrameNumber()]
        if (roll.getRollNumber() == ROLL_ONE.id) {
            addRollOne(user, roll)
        } else if (roll.getRollNumber() == ROLL_TWO.id){
            addRollTwo(currentFrame, roll)
        } else {
            addRollThree(currentFrame, roll)
        }
    }

    private addRollOne(User user, Roll roll) {
        if (roll.getValue() == ALL_BOWLS && roll.frameNumber != LAST_FRAME) {
            user.addToFrames(new Frame(number: roll.getFrameNumber(), rollOne: roll.getValue(), rollTwo: 0))
        } else {
            user.addToFrames(new Frame(number: roll.getFrameNumber(), rollOne: roll.getValue()))
        }
    }

    //TODO правильно ли тут сетить третий?
    private addRollTwo(Frame frame, Roll roll) {
        println "addRollTwo " + roll
        frame.rollTwo = roll.value
        if(frame.number == LAST_FRAME && (frame.rollOne + frame.rollTwo) < ALL_BOWLS){
            frame.rollThree = 0
        }
    }

    private addRollThree(Frame frame, Roll roll) {
        frame.rollThree = roll.value
    }

}
