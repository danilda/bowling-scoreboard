package bowling.scoreboard

class User {
    String name
    Integer totalScore

    static belongsTo = [game: Game]
    static hasMany = [frames: Frame]
    static constraints = {
        game nullable: false
        name nullable: false
//        frames nullable: true, validator = {val, obj -> val?.size() == 10 }
    }

}
