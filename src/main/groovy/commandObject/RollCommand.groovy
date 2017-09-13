package commandObject

import bowling.scoreboard.Frame
import bowling.scoreboard.User
import enums.RollsEnum
import static constants.GameConstants.ANY_BOWLS

class RollCommand implements grails.validation.Validateable{
    User user
    Frame frame
    Integer rollNumber
    Integer value
    Integer maxValue

    static constraints = {
        rollNumber range: RollsEnum.ROLL_ONE.id..RollsEnum.ROLL_THREE.id
        value nullable: true, validator: { val, obj, errors ->
            if (val != null && !(val in ANY_BOWLS..obj.maxValue)){
                errors.rejectValue("value", "invalidBowlsValue")
                return false
            }
        }
    }
}
