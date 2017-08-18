package bowling.scoreboard

import grails.gorm.transactions.Transactional

@Transactional
class ScoreService {

    def saveGame (Game game){

    }

    def addUserInGame(Game game, User user){

    }

    def addFramesForUser(User user, List frames){

    }

    /*TODO add throwing ValidateException*/
    private calculateFrames(List frames) {

    }

    /*TODO add ValidateException
    * - validate (like Domain and like Frame for sequence);
    * - sort;
    * */
    private preprocessFramesForCalculation(List frames) {

    }

}
