package bowling.scoreboard

class Game {
    Date date

    static hasMany = [users: User]
    static constraints = {
        date nullable: true
        users nullable: true
        date validator: {val , obj -> val.getTime() <= new Date().getTime()}
    }
}
