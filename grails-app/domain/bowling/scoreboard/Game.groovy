package bowling.scoreboard

import static ScoreService.MAX_NUMBER_OF_USERS

class Game {

    Date date

    static hasMany = [users: User]
    static constraints = {
        date nullable: true
        users nullable: true, validator: {
            val, obj ->
                if(val != null){
                    def numbers = val*.number
                    return numbers.size() == numbers.unique().size()&& val.size() <= MAX_NUMBER_OF_USERS
                }
                true
        }
    }
}
