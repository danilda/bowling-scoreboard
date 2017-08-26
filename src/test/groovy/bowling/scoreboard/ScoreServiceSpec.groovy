package bowling.scoreboard

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ScoreServiceSpec extends HibernateSpec implements ServiceUnitTest<ScoreService>{

    def setup() {
    }

    def cleanup() {
    }

    List<Class> getDomainClasses() { [Frame] }

    void "calculateFrames"() {
        when:
            def testFrames = []
        [0..8].each {}
        for(i in 0..8){
                testFrames.add new Frame(number: i, rollOne: 10, rollTwo: 0)
            }
            testFrames.add new Frame(number: 9, rollOne: 10, rollTwo: 10, rollThree: 10)
            service.calculateFrames testFrames
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
            testFrames = []
            def rollsOne = [10, 10, 10, 7, 8, 0, 10, 7, 9, 10]
            def rollsTwo = [0, 0, 0, 2, 2, 9, 0, 3, 0, 10]
            def rollsThree = [null, null, null, null, null, null, null, null, null, 8]
            for(i in 0..9) {
                testFrames.add new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i], rollThree: rollsThree[i])
            }
            service.calculateFrames testFrames
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
    }
}
