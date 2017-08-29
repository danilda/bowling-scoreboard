package bowling.scoreboard

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class UserSpec extends Specification implements DomainUnitTest<User> {

    def setup() {
    }

    def cleanup() {
    }

    void "test validator"() {
        when:
            Set testFrames = new LinkedHashSet()
            for(i in 0..9){
                testFrames.add(new Frame(number: i))
            }
            domain.frames = testFrames

        then:"fix me"
            domain.validate(['frames'])

        when:
            testFrames = new LinkedHashSet()
            for(i in 0..11){
                testFrames.add(new Frame(number: i))
            }
            domain.frames = testFrames
        then:"fix me"
            !domain.validate(['frames'])

        when:
            testFrames = new LinkedHashSet()
            for(i in 0..8){
                testFrames.add(new Frame(number: i))
            }
            domain.frames = testFrames
        then:"fix me"
            domain.validate(['frames'])

        when:
            testFrames = new LinkedHashSet()
            for(i in 0..9){
                testFrames.add(new Frame(number: i))
            }
            testFrames.add(new Frame(number: 2, rollOne: 2))
            testFrames.add(new Frame(number: 3, rollOne: 2))
            domain.frames = testFrames
        then:"fix me"
            !domain.validate(['frames'])
    }
}
