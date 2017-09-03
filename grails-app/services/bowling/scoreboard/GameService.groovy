package bowling.scoreboard


import grails.gorm.transactions.Transactional
import commandObject.Roll
import static enums.RollsEnum.ROLL_ONE
import static enums.RollsEnum.ROLL_TWO
import static enums.RollsEnum.ROLL_THREE
import static ScoreService.FIRS_FRAME
import static ScoreService.LAST_FRAME
import static ScoreService.ALL_BOWLS
import static ScoreService.MAX_NUMBER_OF_FRAMES
import static ScoreService.FIRST_USER


@Transactional
class GameService {
    public static final Closure SORT_BY_NUMBER = { current, next -> current.number <=> next.number }

    def getMapForRenderingFromGame(Game game) {
        def map = [game: game.getId()]
        map << [users: getUserListForRendering(game)]
        map
    }

    private getUserListForRendering(Game game) {
        List<User> users = game.getUsers().toList().sort SORT_BY_NUMBER
        List<Map> resultUserList = new LinkedList<>()
        users.each { user ->
            resultUserList.add([name: user.getName(), frames: getFrameListFromUser(user)])
        }
        resultUserList
    }

    private getFrameListFromUser(User user) {
        List<Map> resultFrameList = new LinkedList<>()
        if (user.frames != null) {
            List<Frame> frames = user.frames.toList().sort SORT_BY_NUMBER
            frames.each { frame ->
                resultFrameList.add(getRollMapFromFrame(frame))
            }
        }
        resultFrameList
    }

    private getRollMapFromFrame(Frame frame) {
        def rolls = null
        if (frame != null && frame.rollOne != null) {
            rolls = [score: frame.score]
            if (frame.number != LAST_FRAME) {
                processNotLastFrame(frame, rolls)
            } else {
                processLastFrame(frame, rolls)
            }
        }
        rolls
    }

    private processNotLastFrame(Frame frame, Map rolls) {
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

    private processLastFrame(Frame frame, Map rolls) {
        println frame
        def rollOne = frame.rollOne == 0 ? "-" : frame.rollOne.toString()
        def rollTwo = frame.rollTwo == 0 ? "-" : frame.rollTwo.toString()
        def rollThree = (frame.rollThree == 0) ? "" : frame.rollThree.toString()
        rolls << [rollOne: rollOne, rollTwo: rollTwo, rollThree: rollThree]
        if (ScoreService.isStrike(frame)) {
            rolls << [rollOne: "X"]
            if (frame.rollTwo == ALL_BOWLS) {
                rolls << [rollTwo: "X"]
                if (frame.rollThree == ALL_BOWLS) {
                    rolls << [rollThree: "X"]
                }
            } else {
                def a = frame.rollTwo ?: 0
                def b = frame.rollThree ?: 0
                if ((a + b) == ALL_BOWLS) {
                    rolls << [rollThree: "/"]
                }
            }
        } else if (ScoreService.isSpare(frame)) {
            rolls << [rollTwo: "/"]
            if (frame.rollThree == ALL_BOWLS) {
                rolls << [rollThree: "X"]
            }
            if (frame.rollThree == 0) {
                rolls << [rollThree: "-"]
            }
        }
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
        List<User> users = game.getUsers().toList().sort SORT_BY_NUMBER
        users.each { println it }
        if (!isAnyUserHasFrame(game)) {
            return new Roll(userNumber: FIRST_USER, frameNumber: FIRS_FRAME, rollNumber: ROLL_ONE.id)
        }

        for (int i = 0; i < users.size() - 1; i++) {
            if (users[i].frames?.size() > users[i + 1].frames?.size()) {
                return getRollByProcessingNotLastUser(users, i)
            }
        }
        return getRollByProcessingLastUser(users)
    }

    private isAnyUserHasFrame(Game game) {
        for (i in FIRS_FRAME..<game.users.size()) {
            if (game.users[i].frames.size() > 0) {
                return true
            }
        }
        false
    }

    private getRollByProcessingNotLastUser(List<User> users, int i) {
        Frame lastFrame = getLastUserFrame(users[i])
        if (lastFrame == null) {
            return new Roll(userNumber: i, frameNumber: FIRS_FRAME, rollNumber: ROLL_ONE.id)
        }
        if (isFrameEnded(lastFrame)) {
            return new Roll(userNumber: users[i + 1].getNumber(), frameNumber: lastFrame.number, rollNumber: ROLL_ONE.id)
        }
        def roll = new Roll(userNumber: users[i].getNumber(), frameNumber: lastFrame.number)
        roll.setRollNumber(getRollNumberForNotEndedFrame(lastFrame))
        return roll
    }

    private getRollByProcessingLastUser(List<User> users) {
        User lastUser = users[users.size() - 1]
        if (isFrameEnded(getLastUserFrame(lastUser))) {
            return new Roll(userNumber: FIRS_FRAME,
                    frameNumber: getLastUserFrame(users[FIRST_USER]).number + 1, rollNumber: ROLL_ONE.id)
        } else {
            Roll roll = new Roll(userNumber: users.size() - 1)
            roll.setFrameNumber(getLastUserFrame(lastUser).getNumber())
            roll.setRollNumber(getRollNumberForNotEndedFrame(getLastUserFrame(lastUser)))
            return roll
        }
    }


    private getRollNumberForNotEndedFrame(Frame frame) {
        if (frame.rollOne == null) {
            return ROLL_ONE
        } else if (frame.rollTwo == null) {
            return ROLL_TWO
        } else {
            return ROLL_THREE
        }
    }

    //TODO спросить почему нельзя просто ретурнить
    def isGameEnded(Game game) {
        def result = true
        game.getUsers().each { user ->
            if (user.getFrames()?.size() != MAX_NUMBER_OF_FRAMES) {
                result = false
            }
            user.getFrames().each { frame ->
                if (!isFrameEnded(frame)) {
                    result = false
                }
            }
        }
        result
    }

    def isFrameEnded(Frame frame) {
        if (frame.rollOne == null || frame.rollTwo == null || (frame.number == LAST_FRAME && frame.rollThree == null)) {
            return false
        }
        true
    }

    def getLastUserFrame(User user) {
        if (user.getFrames() != null && user.getFrames().size() != 0) {
            return user.getFrames().sort(SORT_BY_NUMBER).get(user.getFrames()?.size() - 1)
        }
        return null
    }
}
