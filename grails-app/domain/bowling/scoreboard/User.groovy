package bowling.scoreboard

import static ScoreService.MAX_NUMBER_OF_FRAMES

class User {
    String name

    /**
     * Sequence number of the player in the game.
     */
    Integer number

    static belongsTo = [game: Game]
    static hasMany = [frames: Frame]
    static constraints = {
        number range: 0..5
        frames nullable: true, validator: {
            val, obj ->
                if(val != null) {
                    def numbers = val*.number
                    return numbers.size() == numbers.unique().size() && val.size() <= MAX_NUMBER_OF_FRAMES
                }
                true
        }
    }
}
