package bowling.scoreboard

class Game {
    Date date
    User winner

    static hasMany = [users: User]
    static constraints = {
        date validator: {val , obj -> val.getTime() <= new Date().getTime()}
    }
}
