package bowling.scoreboard

import exception.DateValidationException
import exception.FramesValidationException
import grails.gorm.transactions.Transactional

@Transactional
class ScoreService {
    public static FIRS_FRAME = 0
    public static LAST_FRAME = 9

    static isStrike(Frame frame) throws FramesValidationException {
        if (!frame.validate(['rollOne'])) {
            throw new FramesValidationException()
        }
        frame.rollOne == 10
    }

    static isSpare(Frame frame) throws FramesValidationException {
        if (!frame.validate(['rollOne', 'rollTwo'])) {
            throw new FramesValidationException()
        }
        (frame.rollOne + frame.rollTwo) == 10
    }

    def calculateFrames(List<Frame> frames) throws FramesValidationException {
        preprocessFramesForCalculation frames
        def score
        for (i in FIRS_FRAME..LAST_FRAME) {
            if (isStrike(frames[i])) {
                score = calculateStrike(frames, i)
            } else if (isSpare(frames[i])) {
                score = calculateSpare(frames, i)
            } else {
                score = calculateOther(frames, i)
            }
            if(i != FIRS_FRAME){
                frames[i].score = frames[i - 1].score + score
            } else {
                frames[i].score = score
            }
        }
    }

    def preprocessFramesForCalculation(List<Frame> frames) throws FramesValidationException {
        frames.sort { current, next -> current.number <=> next.number }
        frames.each {
            if (it.validate(["rollOne", "rollTwo", "rollThree", "number"])) {

            } else {
                StringBuilder sb = new StringBuilder()
                it.errors.allErrors.each {
                    sb.append(it).append("\n")
                }
                throw new FramesValidationException(sb.toString())
            }
        }
    }

    private calculateStrike(List<Frame> frames, int i) {
        if (i != LAST_FRAME) {
            if (isStrike(frames[i + 1])) {
                if(i+2 > LAST_FRAME){
                    return frames[i].rollOne + frames[i + 1].rollOne + frames[i + 1].rollTwo
                }
                return frames[i].rollOne + frames[i + 1].rollOne + frames[i + 2].rollOne
            }
            return  frames[i].rollOne + frames[i + 1].rollOne + frames[i + 1].rollTwo
        }
        return frames[i].rollOne + frames[i].rollTwo + frames[i].rollThree
    }

    private calculateSpare(List<Frame> frames, int i) {
        if (i != LAST_FRAME) {
            return frames[i].rollOne + frames[i].rollTwo + frames[i + 1].rollOne
        }
        return frames[i].rollOne + frames[i].rollTwo + frames[i].rollThree
    }

    private calculateOther(List<Frame> frames, int i) {
        return frames[i].rollOne + frames[i].rollTwo
    }
}
