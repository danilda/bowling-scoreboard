package bowling.scoreboard

import static ScoreService.MAX_NUMBER_OF_USERS

class Game {
    Date date

    static hasMany = [users: User]
    static constraints = {
        date nullable: true, validator: {val , obj -> val.getTime() <= new Date().getTime()}
        users nullable: true, validator: {
            val, obj ->
            if(val != null){
                Set<Integer> set = new HashSet()
                val.each {
                    set.add(it.getNumber())
                }
                return set.size() == val.size()&& val.size() <= MAX_NUMBER_OF_USERS
            }
                return true
        }
    }
}
