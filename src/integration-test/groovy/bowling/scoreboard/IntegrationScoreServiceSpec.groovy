package bowling.scoreboard

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class IntegrationScoreServiceSpec extends Specification {

    @Autowired
    ScoreService service

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        when:
            def game = new Game(date: new Date())
            def alex = new User(name: "Alex")
            def frames = new ArrayList(10)

            frames.add new Frame(number: 0, rollOne: 5, rollTwo: 4)
            frames.add new Frame(number: 1, rollOne: 5, rollTwo: 4)
            frames.add new Frame(number: 5, rollOne: 5, rollTwo: 4)
            frames.add new Frame(number: 4, rollOne: 5, rollTwo: 4)
            frames.add new Frame(number: 3, rollOne: 5, rollTwo: 4)
            frames.add new Frame(number: 2, rollOne: 5, rollTwo: 4)
            frames.add new Frame(number: 8, rollOne: 5, rollTwo: 4)
            frames.add new Frame(number: 7, rollOne: 5, rollTwo: 4)
            frames.add new Frame(number: 6, rollOne: 5, rollTwo: 4)
            frames.add new Frame(number: 9, rollOne: 5, rollTwo: 4)

            service.addFramesForUser(alex, frames)
            service.addUsersInGame(game, new ArrayList().add(alex) as List)
            service.saveGame(game)
            def gameId = game.id
            def gameFromDB = Game.findById(gameId)
        then:"fix me"
            gameFromDB.users.size() == 1
            User.findByName("Alex").id == gameFromDB.users.find().id
            gameFromDB.users.find().frames.size() == 10
    }
}
