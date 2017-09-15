package bowling.scoreboard

import grails.gorm.transactions.Transactional
import commandObject.RollCommand
import static enums.RollsEnum.ROLL_ONE
import static enums.RollsEnum.ROLL_TWO
import static enums.RollsEnum.ROLL_THREE
import static constants.GameConstants.LAST_FRAME
import static constants.GameConstants.ALL_BOWLS
import static constants.GameConstants.FIRST_USER
import static constants.GameConstants.MAX_NUMBER_OF_FRAMES
import static constants.GameConstants.FIRS_FRAME


@Transactional
class GameService {
    public static final Closure SORT_BY_NUMBER = { current, next -> current.number <=> next.number }
    public static final String STRIKE = "X"
    public static final String SPARE = "/"
    public static final String MISS = "-"
    public static final String EMPTY = ""

    def getModelForRendering(Game game) {
        List<User> users = game.getUsers().sort SORT_BY_NUMBER
        List<Map> resultUserList = users.collect { user ->
            [name: user.getName(), frames: getFrameListFromUser(user)]
        }
        [game: game.getId(), users: resultUserList]
    }

    private getFrameListFromUser(User user) {
        if (user.frames == null) {
            return []
        }
        user.frames.sort(SORT_BY_NUMBER).collect { frame ->
            getRollMapFromFrame(frame)
        }
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
        if (frame.isStrike()) {
            rolls << [rollOne: STRIKE, rollTwo: EMPTY]
        } else {
            def rollTwo
            if (frame.isSpare()) {
                rollTwo = SPARE
            } else {
                rollTwo = defaultRollTwo(frame)
            }
            rolls << [rollOne: defaultRollOne(frame), rollTwo: rollTwo]
        }
    }

    private processLastFrame(Frame frame, Map rolls) {
        def rollOne = defaultRollOne frame
        def rollTwo = defaultRollTwo frame
        def rollThree = defaultRollThree frame
        rolls << [rollOne: rollOne, rollTwo: rollTwo, rollThree: rollThree]
        if (frame.isStrike()) {
            rolls << [rollOne: STRIKE]
            if (frame.rollTwo == ALL_BOWLS) {
                rolls << [rollTwo: STRIKE]
                if (frame.rollThree == ALL_BOWLS) {
                    rolls << [rollThree: STRIKE]
                }
            } else {
                def a = frame.rollTwo ?: 0
                def b = frame.rollThree ?: 0
                if ((a + b) == ALL_BOWLS) {
                    rolls << [rollThree: SPARE]
                }
            }
        } else if (frame.isSpare()) {
            rolls << [rollTwo: SPARE]
            if (frame.rollThree == ALL_BOWLS) {
                rolls << [rollThree: STRIKE]
            }
            if (frame.rollThree == 0) {
                rolls << [rollThree: MISS]
            }
        }
    }

    private defaultRollOne(Frame frame) {
        frame.rollOne == 0 ? MISS : frame.rollOne.toString()
    }

    private defaultRollTwo(Frame frame) {
        frame.rollTwo == 0 ? MISS : frame.rollTwo.toString()
    }

    private defaultRollThree(Frame frame) {
        frame.rollThree == 0 ? EMPTY : frame.rollThree.toString()
    }

    def getNextStep(Game game) {
        if (isGameEnded(game)) {
            return null
        }
        RollCommand roll = getNextRoll(game)
        calculateMaxValue(roll)
        roll
    }

    private getNextRoll(Game game) {
        List<User> users = game.getUsers().sort SORT_BY_NUMBER
        if (!isAnyUserHasFrame(game)) {
            return new RollCommand(user: users[FIRST_USER], frame: new Frame(number: FIRS_FRAME), rollNumber: ROLL_ONE.id)
        }
        for (int i = 0; i < users.size() - 1; i++) {
            if (users[i].frames?.size() > users[i + 1].frames?.size()) {
                return getRollByProcessingNotLastUser(users, i)
            }
        }
        return getRollByProcessingLastUser(users)
    }

    private isAnyUserHasFrame(Game game) {
        User.createCriteria().list{
            eq("game", game)
            sizeGt("frames", 0)
        }.size()
    }

    private getRollByProcessingNotLastUser(List<User> users, int i) {
        Frame lastFrame = getLastUserFrame(users[i])
        if (lastFrame == null) {
            return new RollCommand(user: users[i], frame: new Frame(number: FIRS_FRAME), rollNumber: ROLL_ONE.id)
        }
        if (isFrameEnded(lastFrame)) {
            return new RollCommand(user: users[i + 1], frame: new Frame(number:lastFrame.number), rollNumber: ROLL_ONE.id)
        }
        def roll = new RollCommand(user: users[i], frame: lastFrame)
        roll.rollNumber = getRollNumberForNotEndedFrame(lastFrame)
        return roll
    }

    private getRollByProcessingLastUser(List<User> users) {
        User lastUser = users[users.size() - 1]
        if (isFrameEnded(getLastUserFrame(lastUser))) {
            return new RollCommand(user: users[FIRST_USER],
                    frame: new Frame(number: getLastUserFrame(users[FIRST_USER]).number + 1), rollNumber: ROLL_ONE.id)
        } else {
            RollCommand roll = new RollCommand(user: users.last())
            roll.frame = getLastUserFrame(lastUser)
            roll.rollNumber = getRollNumberForNotEndedFrame(getLastUserFrame(lastUser))
            return roll
        }
    }


    private getRollNumberForNotEndedFrame(Frame frame) {
        if (frame.rollOne == null) {
            return ROLL_ONE.id
        } else if (frame.rollTwo == null) {
            return ROLL_TWO.id
        } else {
            return ROLL_THREE.id
        }
    }

    private calculateMaxValue(RollCommand roll) {
        if (roll.rollNumber == ROLL_ONE.id) {
            roll.maxValue = ALL_BOWLS
        } else {
            calculateMaxValueForRollTwoAndThree(roll)
        }
    }

    private calculateMaxValueForRollTwoAndThree( RollCommand roll) {
        if (roll.rollNumber == ROLL_TWO.id) {
            calculateMaxValueForRollTwo(roll)
        } else {
            calculateMaxValueForRollThree(roll)
        }
    }

    private calculateMaxValueForRollTwo(RollCommand roll) {
        def frame = roll.frame
        if (frame.number != LAST_FRAME) {
            roll.maxValue = ALL_BOWLS - frame.rollOne
        } else {
            if (frame.isStrike()) {
                roll.maxValue = ALL_BOWLS
            } else {
                roll.maxValue = ALL_BOWLS - frame.rollOne
            }
        }
    }

    private calculateMaxValueForRollThree(RollCommand roll) {
        def frame = roll.frame
        if (frame.isStrike()) {
            if (frame.rollTwo == ALL_BOWLS) {
                roll.maxValue = ALL_BOWLS
            } else {
                roll.maxValue = ALL_BOWLS - frame.rollTwo
            }
        } else {
            roll.maxValue = ALL_BOWLS
        }
    }

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
        !(frame.rollOne == null || frame.rollTwo == null || (frame.number == LAST_FRAME && frame.rollThree == null))
    }

    def getLastUserFrame(User user) {
        if (user.frames != null && user.frames.size() != 0) {
            return user.frames.sort(SORT_BY_NUMBER).last()
        }
        return null
    }
}
