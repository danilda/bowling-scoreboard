package bowling.scoreboard

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ScoreServiceSpec extends Specification implements ServiceUnitTest<ScoreService>{

    def setup() {
    }

    def cleanup() {
    }


    void "test calculateFrames"() {
        when:
            def testFrames = []
        [0..8].each {}
        for(i in 0..8){
                testFrames.add new Frame(number: i, rollOne: 10, rollTwo: 0)
            }
            testFrames.add new Frame(number: 9, rollOne: 10, rollTwo: 0, rollThree: 10)
            service.calculateFrames testFrames
        then:
        testFrames.each {it.score == (it.number + 1)*30}
//            testFrames.get(0).score == 30
//            testFrames.get(1).score == 60
//            testFrames.get(2).score == 90
//            testFrames.get(3).score == 120
//            testFrames.get(4).score == 150
//            testFrames.get(5).score == 180
//            testFrames.get(6).score == 210
//            testFrames.get(7).score == 240
//            testFrames.get(8).score == 270
//            testFrames.get(9).score == 300

        when:
            testFrames = new ArrayList()
            testFrames.add new Frame(number: 0, rollOne: 10, rollTwo: 0, rollThree: null)
            testFrames.add new Frame(number: 1, rollOne: 10, rollTwo: 0, rollThree: null)
            testFrames.add new Frame(number: 2, rollOne: 10, rollTwo: 0, rollThree: null)
            testFrames.add new Frame(number: 3, rollOne: 7, rollTwo: 2, rollThree: null)
            testFrames.add new Frame(number: 4, rollOne: 8, rollTwo: 2, rollThree: null)
            testFrames.add new Frame(number: 5, rollOne: 0, rollTwo: 9, rollThree: null)
            testFrames.add new Frame(number: 6, rollOne: 10, rollTwo: 0, rollThree: null)
            testFrames.add new Frame(number: 7, rollOne: 7, rollTwo: 3, rollThree: null)
            testFrames.add new Frame(number: 8, rollOne: 9, rollTwo: 0, rollThree: null)
            testFrames.add new Frame(number: 9, rollOne: 10, rollTwo: 10, rollThree: 8)
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
