package bowling.scoreboard

class Frame {
    Integer number
    Integer rollOne
    Integer rollTwo
    Integer rollThree
    Integer score

    static belongsTo = [user: User]
    static constraints = {
        user nullable: false
        number range: 0..9, nullable: false
        score range: 0..300
        rollOne range: 0..10, nullable: false
        rollTwo range: 0..10, nullable: false, validator: {
            val, obj ->
                if((val + obj.rollOne) <= 10){
                    return true
                } else if(obj.number == 9 && obj.rollOne == 10 && (val + obj.rollOne) <= 20){
                    return true
                }
                false
        }
        rollThree nullable: true, range: 0..10, validator: {
            val, obj ->
                if(obj.number == 9 && (obj.rollOne + obj.rollTwo) >= 10 && val != null){
                    return true
                } else if((obj.number != 9 || (obj.rollOne + obj.rollTwo) < 10) && val == null){
                    return true
                }
                false
        }
    }
}
