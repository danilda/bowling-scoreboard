package bowling.scoreboard

import grails.gorm.transactions.Transactional
import static GameService.SORT_BY_NUMBER
import static constants.GameConstants.DEFAULT_SCORE_VALUE
import static constants.GameConstants.FIRS_FRAME
import static constants.GameConstants.LAST_FRAME

@Transactional
class ScoreService {

    def calculateFrames(User user) {
        List<Frame> frames = user.getFrames().sort SORT_BY_NUMBER
        for (Frame frame: frames) {
            frame.score = DEFAULT_SCORE_VALUE
        }
        for (int i in FIRS_FRAME..frames.size()-1) {
            int score = calculateOneFrame(frames, i)
            if (i != FIRS_FRAME) {
                frames[i].score = frames[i - 1]?.score + score
            } else {
                frames[i].score = score
            }
        }
    }

    private calculateOneFrame(List<Frame> frames, int i){
        def additionalScores = 0
        if (frames[i].isStrike()) {
            additionalScores = calculateStrike(frames, i)
        } else if (frames[i].isSpare()) {
            additionalScores = calculateSpare(frames, i)
        }
        return defaultRollOne(frames[i]) + defaultRollTwo(frames[i]) + additionalScores
    }

    private calculateStrike(List<Frame> frames, int i) {
        if (i != LAST_FRAME) {
            if (frames[i + 1]?.isStrike()) {
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
