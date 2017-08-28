package bowling.scoreboard

class User {
    String name
    Integer totalScore

    static belongsTo = [game: Game]
    static hasMany = [frames: Frame]
    static constraints = {
        totalScore nullable: true
        frames nullable: true, validator: {
            val, obj ->
                if (val == null) {
                    return true
                }
                Set set = new LinkedHashSet()
                val.each { e -> set.add(e) }
                set.size() == 10 && val.size() == 10
        }
    }
}
