package commandObject

import bowling.scoreboard.Game

class RollCommand implements grails.validation.Validateable{
    Long game
    Integer userNumber
    Integer frameNumber
    Integer rollNumber
    Integer value
    Integer maxValue
}
