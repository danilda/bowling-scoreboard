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
        Game game = Game.get(roll.getGameId())
        List<User> users = game.getUsers().toList().sort { current, next -> current.number <=> next.number }
        User currentUser = users[(int) roll.getUserNumber()]
        addRollInUser(currentUser, roll)
        if(roll.rollNumber in [1, 2]){
            scoreService.calculateFrames currentUser
        }
        game.save()
        game
    }

    private addRollInUser(User user, Roll roll){
        if (roll.getRollNumber() == 0) {
            user.addToFrames(new Frame(number: roll.getFrameNumber(), rollOne: roll.getValue()))
        } else {
            List frames = user.getFrames().toList().sort { current, next -> current.number <=> next.number }
            currentFrame = frames[(int) roll.getFrameNumber()]
            if(roll.getRollNumber() == 1 ){
                currentFrame.setRollOne(roll.getValue())
            } else {
                currentFrame.setRollTwo(roll.getValue())
            }
        }

    }
}
