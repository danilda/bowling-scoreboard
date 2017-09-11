package bowling.scoreboard

import static constants.GameConstants.LAST_FRAME
import static constants.GameConstants.ALL_BOWLS

class Frame {
    Integer number
    Integer rollOne
    Integer rollTwo
    Integer rollThree
    Integer score

    static belongsTo = [user: User]
    static constraints = {
        number range: 0..9
        score range: 0..300, nullable: true
        rollOne range: 0..10, nullable: true
        rollTwo range: 0..10, nullable: true, validator: {
            val, obj ->
                def a = val?:0
                def b = obj.rollOne?:0
                if((a + b) <= ALL_BOWLS){
                    return true
                } else if(obj.number == LAST_FRAME && obj.rollOne == ALL_BOWLS && (val + obj.rollOne) <= 20){
                    return true
                }
                false
        }
        rollThree nullable: true, range: 0..10, validator: {
            val, obj ->
                def a = obj.rollOne?:0
                def b = obj.rollTwo?:0
                if(val != null ) {
                    return obj.number == LAST_FRAME && ((a + b) >= ALL_BOWLS || val == 0)
                }
                true
        }
    }

    def isStrike() {
        this?.rollOne == ALL_BOWLS
    }

    def isSpare() {
        if(this.rollOne != null && this.rollTwo != null) {
            return  (this.rollOne + this.rollTwo) == ALL_BOWLS
        }
        false
    }

}
