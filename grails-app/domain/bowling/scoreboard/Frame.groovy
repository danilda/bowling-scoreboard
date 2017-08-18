package bowling.scoreboard

class Frame {
    Integer number
    Integer rollOne
    Integer rollTwo
    Integer rollThree
    Integer score

    static belongsTo = [user:User, game:Game]
    static constraints = {
        game nullable: false
        user nullable: false
        number range: 0..9, nullable: false
        score range: 0..300
        rollOne range: 0..10, nullable: false
        rollTwo range: 0..10, nullable: false, validator: {val, obj -> (val + obj.rollOne) <=10}
        rollThree nullable: true, range: 0..10, validator:
                { val, obj -> obj.number == 9 && (obj.rollOne + obj.rollTwo) == 10 && val != null || obj.number != 9 && val == null }
    }
}
