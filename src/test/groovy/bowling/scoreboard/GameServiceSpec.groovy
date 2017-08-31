package bowling.scoreboard

import commandObject.Roll
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

    void "test next roll"() {
        when: "test with no ended game"
        Game game = new Game()
        User user = new User(name: "test", number: 0)
        def rollsOne = [2, 5, 10, 4, 8, 5]
        def rollsTwo = [8, 0, 0, 5, 0, 5]
        for(i in 0..rollsOne.size()-1){
            user.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i]))
        }
        User user2 = new User(name: "test2", number: 1)
        rollsOne = [2, 5, 10, 4, 8, 2]
        rollsTwo = [8, 0, 0, 5, 0, null]
        for(i in 0..rollsOne.size()-1){
            user2.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i]))
        }
        User user3 = new User(name: "test3", number: 2)
        rollsOne = [2, 5, 10, 4, 8]
        rollsTwo = [8, 0, 0, 5, 0]
        for(i in 0..rollsOne.size()-1){
            user3.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i]))
        }
        game.addToUsers(user)
        game.addToUsers(user2)
        game.addToUsers(user3)
        Roll roll = service.getNextStep(game)
        then:
        roll.userNumber == (long)1
        roll.rollNumber == 1
        roll.frameNumber == 5

        when: "Test with ended game"
        game = new Game()
        user = new User()
        for(i in 0..8){
            user.addToFrames new Frame(number: i, rollOne: 10, rollTwo: 0)
        }
        user.addToFrames  new Frame(number: 9, rollOne: 10, rollTwo: 10, rollThree: 10)
        game.addToUsers user
        user = new User()
        rollsOne = [10, 10, 10, 7, 8, 0, 10, 7, 9, 10]
        rollsTwo = [0, 0, 0, 2, 2, 9, 0, 3, 0, 10]
        def rollsThree = [null, null, null, null, null, null, null, null, null, 8]
        for(i in 0..9) {
            user.addToFrames new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i], rollThree: rollsThree[i])
        }
        game.addToUsers user
        roll = service.getNextStep(game)
        then:
        roll == null
    }
}
