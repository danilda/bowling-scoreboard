package bowling.scoreboard

import commandObject.RollCommand
import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class GameServiceSpec extends HibernateSpec implements ServiceUnitTest<GameService>{

    def setup() {
    }

    def cleanup() {
    }

    List<Class> getDomainClasses() { [Frame, User, Game] }

    void "test render map (one user & not ended game)"() {
        expect:
        Game game = new Game()
        User user = new User(name: "test", number: 0)
        def rollsOne = [2, 5, 10, 4, 8]
        def rollsTwo = [8, 0, 0, 5, 0]
        def scores = [15, 20, 39, 48, 56]
        for(i in 0..rollsOne.size()){
            user.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i], score: scores[i]))
        }
        game.addToUsers(user)
        Map renderMap = service.getModelForRendering game
        renderMap.users[0].frames[f].rollOne == v1 && renderMap.users[0].frames[f].rollTwo == v2 && renderMap.users[0].frames[f].score == v3
        where:
        f|v1 |v2 |v3
        0|"2"|"/"|15
        1|"5"|"-"|20
        2|"X"|"" |39
        3|"4"|"5"|48
        4|"8"|"-"|56
    }

    void "test render map (several users & ended game)"() {
        expect:
        Game game = new Game()
        User user = new User(name: "test", number: 0)
        def rollsOne = [10, 10, 10, 10, 10, 10, 10, 10, 10, 10]
        def rollsTwo = [0, 0, 0, 0, 0, 0, 0, 0, 0, 10]
        def rollsThree = [null, null, null, null, null, null, null, null, null, 10]
        def scores = [30, 60, 90, 120, 150, 180, 210, 240, 270, 300]
        for(i in 0..rollsOne.size()){
            user.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i], rollThree: rollsThree[i], score: scores[i]))
        }
        game.addToUsers(user)
        def user2 = new User(name: "test2", number: 1)
        def rollsOne2 = [10, 10, 10, 7, 8, 0, 10, 7, 9, 10]
        def rollsTwo2 = [0, 0, 0, 2, 2, 9, 0, 3, 0, 10]
        def rollsThree2 = [null, null, null, null, null, null, null, null, null, 8]
        def scores2 = [30, 57, 76, 85, 95, 104, 124, 143, 152, 180]
        for(i in 0..rollsOne2.size()){
            user2.addToFrames(new Frame(number: i, rollOne: rollsOne2[i], rollTwo: rollsTwo2[i], rollThree: rollsThree2[i], score: scores2[i]))
        }
        game.addToUsers(user2)
        Map renderMap = service.getModelForRendering game
        renderMap.users[u].frames[f].rollOne == v1 && renderMap.users[u].frames[f].rollTwo == v2 && renderMap.users[u].frames[f].score == v3
        where:
        u|f|v1 |v2 |v3
        0|0|"X"|"" |30
        0|1|"X"|"" |60
        0|2|"X"|"" |90
        0|3|"X"|"" |120
        0|4|"X"|"" |150
        0|5|"X"|"" |180
        0|6|"X"|"" |210
        0|7|"X"|"" |240
        0|8|"X"|"" |270
        0|9|"X"|"X" |300
        1|0|"X"|"" |30
        1|1|"X"|"" |57
        1|2|"X"|"" |76
        1|3|"7"|"2"|85
        1|4|"8"|"/" |95
        1|5|"-"|"9" |104
        1|6|"X"|"" |124
        1|7|"7"|"/" |143
        1|8|"9"|"-" |152
        1|9|"X"|"X" |180
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
        RollCommand roll = service.getNextStep(game)
        then:
        roll.user.number == (long)1
        roll.rollNumber == 1
        roll.frame.number == 5
        roll.maxValue == 8

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
