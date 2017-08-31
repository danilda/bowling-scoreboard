package bowling.scoreboard

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
                if((a + b) <= 10){
                    return true
                } else if(obj.number == 9 && obj.rollOne == 10 && (val + obj.rollOne) <= 20){
                    return true
                }
                false
        }
        rollThree nullable: true, range: 0..10, validator: {
            val, obj ->
                def a = obj.rollOne?:0
                def b = obj.rollTwo?:0
                if(obj.number == 9 && (a + b) >= 10 && val != null){
                    return true
                } else if((obj.number != 9 || (a + b) < 10) && val == null){
                    return true
                }
                false
        }
    }


    @Override
    String toString() {
        return "Frame{" +
                "id=" + id +
                ", version=" + version +
                ", user=" + user +
                ", number=" + number +
                ", rollOne=" + rollOne +
                ", rollTwo=" + rollTwo +
                ", rollThree=" + rollThree +
                ", score=" + score +
                '}'
    }
}
