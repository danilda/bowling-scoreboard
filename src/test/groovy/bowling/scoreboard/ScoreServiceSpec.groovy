package bowling.scoreboard

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class ScoreServiceSpec extends HibernateSpec implements ServiceUnitTest<ScoreService>{

    def setup() {
    }

    def cleanup() {
    }

    List<Class> getDomainClasses() { [Frame, User] }

    void "calculateFrames"() {
        when:
            User user = new User()
            for(i in 0..8){
                user.addToFrames new Frame(number: i, rollOne: 10, rollTwo: 0)
            }
            user.addToFrames  new Frame(number: 9, rollOne: 10, rollTwo: 10, rollThree: 10)
            service.calculateFrames user
            def testFrames = service.getSortedValidListOfFrames user
        then:
            testFrames.get(0).score == 30
            testFrames.get(1).score == 60
            testFrames.get(2).score == 90
            testFrames.get(3).score == 120
            testFrames.get(4).score == 150
            testFrames.get(5).score == 180
            testFrames.get(6).score == 210
            testFrames.get(7).score == 240
            testFrames.get(8).score == 270
            testFrames.get(9).score == 300
        when:
            user = new User()
            def rollsOne = [10, 10, 10, 7, 8, 0, 10, 7, 9, 10]
            def rollsTwo = [0, 0, 0, 2, 2, 9, 0, 3, 0, 10]
            def rollsThree = [null, null, null, null, null, null, null, null, null, 8]
            for(i in 0..9) {
                user.addToFrames new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i], rollThree: rollsThree[i])
            }
            service.calculateFrames user
            testFrames = service.getSortedValidListOfFrames user
        then:
            testFrames.get(0).score == 30
            testFrames.get(1).score == 57
            testFrames.get(2).score == 76
            testFrames.get(3).score == 85
            testFrames.get(4).score == 95
            testFrames.get(5).score == 104
            testFrames.get(6).score == 124
            testFrames.get(7).score == 143
            testFrames.get(8).score == 152
            testFrames.get(9).score == 180
        when:
            user = new User()
            rollsOne = [10, 8, 10, 7]
            rollsTwo = [0, 0, 0, 2]
            rollsThree = [null, null, null, null]
            for(i in 0..3) {
                user.addToFrames new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i], rollThree: rollsThree[i])
            }
            service.calculateFrames user
            testFrames = service.getSortedValidListOfFrames user
        then:
            testFrames.get(0).score == 18
            testFrames.get(1).score == 26
            testFrames.get(2).score == 45
            testFrames.get(3).score == 54

    }
}
