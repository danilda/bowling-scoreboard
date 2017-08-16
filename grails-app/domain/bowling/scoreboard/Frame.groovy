package bowling.scoreboard

class Frame {
    Game game
    User user
    Integer number
    Integer rollOne
    Integer rollTwo
    Integer rollThree

    static belongsTo = [User]
    static constraints = {
        game nullable: false
        user nullable: false
        rollOne range: 0..10
        rollTwo range: 0..10
        rollThree nullable: true, range: 0..10, validator: { val, obj -> obj.number == 10 || val ==0 }
    }
}
