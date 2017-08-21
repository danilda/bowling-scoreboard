package bowling.scoreboard

import exception.DateValidationException
import exception.FramesValidationException
import grails.gorm.transactions.Transactional

@Transactional
class ScoreService {

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

    def saveGame(Game game) throws DateValidationException {

    }

    def addUsersInGame(Game game, List<User> users) throws DateValidationException {

    }

    def addFramesForUser(User user, List<Frame> frames) throws DateValidationException {

    }



    def calculateFrames(List<Frame> frames) throws FramesValidationException {
        preprocessFramesForCalculation frames

        for (number in 0..9) {
            if (isStrike(frames[number] as Frame)) {
                calculateStrike(frames, number)
            } else if (isSpare(frames[number] as Frame)) {
                calculateSpare(frames, number)
            } else {
                calculateOther(frames, number)
            }
            if(number != 0){
                frames[number].score += frames[number - 1]
            }
        }
    }

    def preprocessFramesForCalculation(List<Frame> frames) throws FramesValidationException {
        frames.sort { current, next -> current.number <=> next.number }
        frames.each {
            if (!it.validate()) {
                throw new FramesValidationException("Exception in preprocessFramesForCalculation method");
            }
        }
    }

    private calculateStrike(List<Frame> frames, int number) {
        if (number != 9) {
            if (isStrike(frames[number + 1] as Frame)) {
                frames[number].score = frames[number].rollOne + frames[number + 1].rollOne + frames[number + 2].rollOne
            }
            frames[number].score = frames[number].rollOne + frames[number + 1].rollOne + frames[number + 1].rollTwo
        }
        frames[number].score = frames[number].rollOne + frames[number].rollTwo + frames[number].rollThree
    }

    private calculateSpare(List<Frame> frames, int number) {
        if (number != 9) {
            frames[number].score = frames[number].rollOne + frames[number].rollTwo + frames[number + 1].rollOne
        }
        frames[number].score = frames[number].rollOne + frames[number].rollTwo + frames[number].rollThree
    }

    private calculateOther(List<Frame> frames, int number) {
        frames[number].score = frames[number].rollOne + frames[number].rollTwo
    }
}
