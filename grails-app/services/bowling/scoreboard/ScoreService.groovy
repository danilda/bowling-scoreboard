package bowling.scoreboard

import exception.FramesValidationException
import grails.gorm.transactions.Transactional

@Transactional
class ScoreService {
    public static final FIRS_FRAME = 0
    public static final LAST_FRAME = 9
    public static final FIRST_USER = 0
    public static final MAX_NUMBER_OF_USERS = 6
    public static final MAX_NUMBER_OF_FRAMES = 10
    public static final ALL_BOWLS = 10


    static isStrike(Frame frame) {
        frame?.rollOne == ALL_BOWLS
    }

    static isSpare(Frame frame) {
        if(frame.rollOne != null && frame.rollTwo != null) {
            return  (frame.rollOne + frame.rollTwo) == ALL_BOWLS
        }
        false
    }

    static getSortedValidListOfFramesFromUser(User user) {
        List<Frame> list = user.getFrames().toList().sort { current, next -> current.number <=> next.number }
        list.each {
            if (!it.validate(['number', 'rollOne', 'rollTwo', 'rollThree'])) {
                println it.errors.each {it.toString()}
                throw new FramesValidationException("Exception in " + it.toString())
            }
        }
        list
    }

    def calculateFrames(User user) throws FramesValidationException {
        List<Frame> frames = getSortedValidListOfFramesFromUser(user)
        for (Frame frame: frames) {
            frame.setScore(0)
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
        def rollOne = frames[i].rollOne?:0
        def rollTwo = frames[i].rollTwo?:0
        def additionalScores = 0
        if (isStrike(frames[i])) {
            additionalScores = calculateStrike(frames, i)
        } else if (isSpare(frames[i])) {
            additionalScores = calculateSpare(frames, i)
        }
        return rollOne + rollTwo + additionalScores
    }

    private calculateStrike(List<Frame> frames, int i) {
        def nextFrameRollOne = frames[i + 1]?.rollOne?:0
        def nextFrameRollTwo = frames[i + 1]?.rollTwo?:0
        def throughOneFrameRollOne = frames[i + 2]?.rollOne?:0
        if (i != LAST_FRAME) {
            if (isStrike(frames[i + 1])) {
                if (i + 2 > LAST_FRAME) {
                    return nextFrameRollOne + nextFrameRollTwo
                }
                return nextFrameRollOne + throughOneFrameRollOne
            }
            return nextFrameRollOne + nextFrameRollTwo
        }
        def frameRollThree = frames[i].rollThree?:0
        return  frameRollThree
    }

    private calculateSpare(List<Frame> frames, int i) {
        def nextFrameRollOne = frames[i + 1]?.rollOne?:0
        if (i != LAST_FRAME) {
            return nextFrameRollOne
        }
        def frameRollThree = frames[i].rollThree?:0
        return frameRollThree
    }

}
