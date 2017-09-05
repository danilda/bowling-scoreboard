package bowling.scoreboard

import exception.FramesValidationException
import grails.gorm.transactions.Transactional
import static GameService.SORT_BY_NUMBER

@Transactional
class ScoreService {
    public static final FIRS_FRAME = 0
    public static final LAST_FRAME = 9
    public static final FIRST_USER = 0
    public static final MAX_NUMBER_OF_USERS = 6
    public static final MAX_NUMBER_OF_FRAMES = 10
    public static final ALL_BOWLS = 10
    public static final DEFAULT_SCORE_VALUE = 0


    static isStrike(Frame frame) {
        frame?.rollOne == ALL_BOWLS
    }

    static isSpare(Frame frame) {
        if(frame.rollOne != null && frame.rollTwo != null) {
            return  (frame.rollOne + frame.rollTwo) == ALL_BOWLS
        }
        false
    }

    static getSortedValidListOfFrames(User user) {
        List<Frame> list = user.getFrames().sort SORT_BY_NUMBER
        list.each {
            if (!it.validate(['number', 'rollOne', 'rollTwo', 'rollThree'])) {
                throw new FramesValidationException("Exception in " + it.toString())
            }
        }
        list
    }

    def calculateFrames(User user) throws FramesValidationException {
        List<Frame> frames = getSortedValidListOfFrames(user)
        for (Frame frame: frames) {
            frame.score = DEFAULT_SCORE_VALUE
        }
        for (int i in FIRS_FRAME..frames.size()-1) {
            int score = calculateOneFrame(frames, i)
            if (i != FIRS_FRAME) {
                frames[i].score = frames[i - 1].score + score
            } else {
                frames[i].score = score
            }
        }
    }

    private calculateOneFrame(List<Frame> frames, int i){
        def additionalScores = 0
        if (isStrike(frames[i])) {
            additionalScores = calculateStrike(frames, i)
        } else if (isSpare(frames[i])) {
            additionalScores = calculateSpare(frames, i)
        }
        return defaultRollOne(frames[i]) + defaultRollTwo(frames[i]) + additionalScores
    }

    private calculateStrike(List<Frame> frames, int i) {
        if (i != LAST_FRAME) {
            if (isStrike(frames[i + 1])) {
                if (i + 2 > LAST_FRAME) {
                    return defaultRollOne(frames[i + 1]) + defaultRollTwo(frames[i + 1])
                }
                return defaultRollOne(frames[i + 1]) + defaultRollOne(frames[i + 2])
            }
            return defaultRollOne(frames[i + 1]) + defaultRollTwo(frames[i + 1])
        }
        def frameRollThree = defaultRollThree(frames[i])
        return  frameRollThree
    }

    private calculateSpare(List<Frame> frames, int i) {
        def nextFrameRollOne = defaultRollOne(frames[i + 1])
        if (i != LAST_FRAME) {
            return nextFrameRollOne
        }
        def frameRollThree = defaultRollThree(frames[i])
        return frameRollThree
    }

    private defaultRollOne(Frame frame){
        frame?.rollOne?:0
    }

    private defaultRollTwo(Frame frame){
        frame?.rollTwo?:0
    }

    private defaultRollThree(Frame frame){
        frame?.rollThree?:0
    }

}
