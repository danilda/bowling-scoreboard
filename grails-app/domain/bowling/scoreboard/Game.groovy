package bowling.scoreboard

class Game {
    Date date
    User winner

    static hasMany = [frames: Frame, users: User]
    static constraints = {
        frames nullable: true
        date validator: {val , obj -> val.getTime() <= new Date().getTime()}
    }
}
