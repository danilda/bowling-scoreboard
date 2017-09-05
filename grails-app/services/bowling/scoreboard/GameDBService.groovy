package bowling.scoreboard

import commandObject.RollCommand
import grails.gorm.transactions.Transactional
import static enums.RollsEnum.ROLL_ONE
import static enums.RollsEnum.ROLL_TWO
import static ScoreService.ALL_BOWLS
import static ScoreService.LAST_FRAME
import static GameService.SORT_BY_NUMBER

@Transactional
class GameDBService {
    ScoreService scoreService

    def addNewGame(List<User> users) {
        new Game(date: new Date(), users: users ).save()
    }

    def addRollInGame(RollCommand roll) {
        Game game = Game.get(roll.game)
        User currentUser = game.users.find { it.number == roll.userNumber}
        addRollInUser(currentUser, roll)
        scoreService.calculateFrames currentUser
        game.save()
    }

    private addRollInUser(User user, RollCommand roll) {
        def currentFrame = user.frames.find {it.number == roll.frameNumber}
        if (roll.rollNumber == ROLL_ONE.id) {
            addRollOne(user, roll)
        } else if (roll.rollNumber == ROLL_TWO.id){
            addRollTwo(currentFrame, roll)
        } else {
            addRollThree(currentFrame, roll)
        }
    }

    private addRollOne(User user, RollCommand roll) {
        if (roll.value == ALL_BOWLS && roll.frameNumber != LAST_FRAME) {
            user.addToFrames(new Frame(number: roll.frameNumber, rollOne: roll.value, rollTwo: 0))
        } else {
            user.addToFrames(new Frame(number: roll.frameNumber, rollOne: roll.value))
        }
    }

    private addRollTwo(Frame frame, RollCommand roll) {
        frame.rollTwo = roll.value
        if(frame.number == LAST_FRAME && (frame.rollOne + frame.rollTwo) < ALL_BOWLS){
            frame.rollThree = 0
        }
    }

    private addRollThree(Frame frame, RollCommand roll) {
        frame.rollThree = roll.value
    }

}
