package bowling.scoreboard

import static ScoreService.MAX_NUMBER_OF_FRAMES

class User {
    String name
    Integer number

    static belongsTo = [game: Game]
    static hasMany = [frames: Frame]
    static constraints = {
        number range: 0..5
        frames nullable: true, validator: {
            val, obj ->
                Set<Integer> set = new HashSet()
                val.each {
                    set.add(it.getNumber())
                }
                set.size() == val.size()&& val.size() <= MAX_NUMBER_OF_FRAMES
        }
    }
}
