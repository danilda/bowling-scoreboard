package bowling.scoreboard

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class GameServiceSpec extends HibernateSpec implements ServiceUnitTest<GameService>{

    def setup() {
    }

    def cleanup() {
    }

    List<Class> getDomainClasses() { [Frame, User, Game] }

    //TODO переписать тест с использованием матрицы валидации
    void "test render map"() {
        when:
        Game game = new Game()
        User user = new User(name: "test", number: 0)
        def rollsOne = [2, 5, 10, 4, 8]
        def rollsTwo = [8, 0, 0, 5, 0]
        def scores = [15, 20, 39, 48, 56]
        for(i in 0..rollsOne.size()){
            user.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i], score: scores[i]))
        }
        game.addToUsers(user)
        Map renderMap = service.getMapForRenderingFromGame game
        then:
        renderMap.users.get(0).get(0).rollOne == "2" && renderMap.users.get(0).get(0).rollTwo == "/"
        renderMap.users.get(0).get(1).rollOne == "5" && renderMap.users.get(0).get(1).rollTwo == "-"
        renderMap.users.get(0).get(2).rollOne == "X" && renderMap.users.get(0).get(2).rollTwo == ""
        renderMap.users.get(0).get(3).rollOne == "4" && renderMap.users.get(0).get(3).rollTwo == "5"
        renderMap.users.get(0).get(4).rollOne == "8" && renderMap.users.get(0).get(4).rollTwo == "-"
    }
}
