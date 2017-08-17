package bowling.scoreboard

class Frame {
    Integer number
    Integer rollOne
    Integer rollTwo
    Integer rollThree

    static belongsTo = [user:User, game:Game]
    static constraints = {
        game nullable: false
        user nullable: false

        rollOne range: 0..10
        rollTwo range: 0..10, validator: {val, obj -> (val + obj.rollTwo) <=10}
        rollThree nullable: true, range: 0..10, validator: { val, obj -> obj.number == 10 || val == null }
    }
}
