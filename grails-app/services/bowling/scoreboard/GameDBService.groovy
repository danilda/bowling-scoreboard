package bowling.scoreboard

import commandObject.RollCommand
import grails.gorm.transactions.Transactional
import static enums.RollsEnum.ROLL_ONE
import static enums.RollsEnum.ROLL_TWO
import static constants.GameConstants.ALL_BOWLS
import static constants.GameConstants.LAST_FRAME
import static constants.GameConstants.ROLL_TWO_VALUE_FOR_STRIKE
import static constants.GameConstants.ROLL_TREE_DEFAULT_VALUE

@Transactional
class GameDBService {
    ScoreService scoreService

    def addNewGame(List<User> users) {
        new Game(date: new Date(), users: users).save()
    }

    def addRollInGame(RollCommand roll) {
        Game game = roll.user.game
        addRollInUser(roll)
        scoreService.calculateFrames roll.user
        game.save()
    }

    private addRollInUser(RollCommand roll) {
        if (roll.rollNumber == ROLL_ONE.id) {
            addRollOne(roll)
        } else if (roll.rollNumber == ROLL_TWO.id){
            addRollTwo(roll)
        } else {
            addRollThree(roll)
        }
    }

    private addRollOne(RollCommand roll) {
        roll.frame.rollOne = roll.value
        roll.user.addToFrames(roll.frame)
        if (roll.value == ALL_BOWLS && roll.frame.number != LAST_FRAME) {
            roll.frame.rollTwo = ROLL_TWO_VALUE_FOR_STRIKE
        }
    }

    private addRollTwo(RollCommand roll) {
        roll.frame.rollTwo = roll.value
        if(roll.frame.number == LAST_FRAME && (roll.frame.rollOne + roll.frame.rollTwo) < ALL_BOWLS){
            roll.frame.rollThree = ROLL_TREE_DEFAULT_VALUE
        }
    }

    private addRollThree(RollCommand roll) {
        roll.frame.rollThree = roll.value
    }

}
