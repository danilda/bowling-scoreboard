package commandObject

import bowling.scoreboard.Frame
import bowling.scoreboard.User

class RollCommand implements grails.validation.Validateable{
    User user
    Frame frame
    Integer rollNumber
    Integer value
    Integer maxValue
}
