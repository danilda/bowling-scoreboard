package bowling.scoreboard


import grails.gorm.transactions.Transactional
import commandObject.Roll

@Transactional
class GameService {
    public static final DATE_FORMAT = "MMM d, yyyy HH:mm:ss SSS"
    public static final Closure SORT_BY_NUMBER = { current, next -> current.number <=> next.number }
    ScoreService scoreService

    def getMapForRenderingFromGame(Game game) {
        def map = [game: game.getId()]
        map << [users: getUserListForRendering(game)]
        map
    }

    private getUserListForRendering(Game game) {
        List<User> users = game.getUsers().toList().sort SORT_BY_NUMBER
        List<List> resultUserList = new LinkedList<>()
        users.each { user ->
            resultUserList.add(getFrameListFromUser(user))
        }
        resultUserList
    }

    private getFrameListFromUser(User user) {
        List<Frame> frames = user.frames.toList().sort SORT_BY_NUMBER
        List<Map> resultFrameList = new LinkedList<>()
        frames.each { frame ->
            resultFrameList.add(getRollMapFromFrame(frame))
        }
        resultFrameList
    }

    private getRollMapFromFrame(Frame frame) {
        def rolls = null
        if (frame != null && frame.rollOne != null) {
            rolls = [score: frame.score]
            if (ScoreService.isStrike(frame)) {
                rolls << [rollOne: "X", rollTwo: ""]
            } else {
                def rollOne = frame.rollOne == 0 ? "-" : frame.rollOne.toString()
                def rollTwo = frame.rollTwo == 0 ? "-" : frame.rollTwo.toString()
                if (ScoreService.isSpare(frame)) {
                    rollTwo = "/"
                }
                rolls << [rollOne: rollOne, rollTwo: rollTwo]
            }
        }
        rolls
    }


    def getNextStep(Game game) {
        if (isGameEnded(game)) {
            return null
        }
        Roll roll = getNextRoll(game)
        roll.setGameId(game?.getId())
        roll
    }

    private getNextRoll(Game game) {
        List users = game.getUsers().toList().sort SORT_BY_NUMBER
        for (i in 0..users.size() - 2) {
            if (users[i].getFrames().size() > users[i + 1].getFrames().size()) {
                return getRollFromFramesWithDifferenceSize(users, i)
            }
        }
        User lastUser = users[users.size()-1]
        if(isFrameEnded(getLastUserFrame(lastUser))){
            return new Roll(userNumber: 0, frameNumber: getLastUserFrame(users[0]).getNumber(), rollNumber: 0)
        } else {
            Roll roll = new Roll(userNumber: users.size()-1)
            roll.setFrameNumber(getLastUserFrame(lastUser).getNumber())
            roll.setRollNumber(getRollNumberForNotEndedFrame(getLastUserFrame(lastUser)))
            return roll
        }
    }

    private getRollFromFramesWithDifferenceSize(List<User> users, int i){
        Frame lastFrame = getLastUserFrame(users[i])
        if (isFrameEnded(lastFrame)) {
            return new Roll(userNumber: users[i + 1].getNumber(), frameNumber: lastFrame.number, rollNumber: 0)
        }
        def roll = new Roll(userNumber: users[i].getNumber(), frameNumber: lastFrame.number)
        roll.setRollNumber(getRollNumberForNotEndedFrame(lastFrame))
        return roll
    }

    private getRollNumberForNotEndedFrame(Frame frame){
        if (frame.rollOne == null) {
            return 0
        } else if (frame.rollTwo == null) {
            return 1
        } else {
            return 2
        }
    }

    def isGameEnded(Game game) {
        def result = true
        game.getUsers().each { user ->
            if (user.getFrames().size() != 10) {
                result = false
            }
            user.getFrames().each { frame ->
                if (frame.number == 9 && frame.rollOne == null) {
                    result = false
                }
            }
        }
        result
    }

    def isFrameEnded(Frame frame) {
        if (frame.rollOne == null || frame.rollTwo == null || (frame.number == 9 && frame.rollThree == null)) {
            return false
        }
        true
    }

    def getLastUserFrame (User user){
        return user.getFrames().sort(SORT_BY_NUMBER).get(user.getFrames().size() - 1)
    }
}
