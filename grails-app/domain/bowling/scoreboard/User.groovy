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
                if(val != null) {
                    Set<Integer> set = new HashSet()
                    val.each {
                        set.add(it.getNumber())
                    }
                    return set.size() == val.size() && val.size() <= MAX_NUMBER_OF_FRAMES
                }
                true
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
