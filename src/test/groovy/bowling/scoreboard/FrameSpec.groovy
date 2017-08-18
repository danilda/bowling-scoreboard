package bowling.scoreboard

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class FrameSpec extends Specification implements DomainUnitTest<Frame> {

    def setup() {
    }

    def cleanup() {
    }

    void "Test of constraint of number"() {
        when: "Test range 1"
            domain.number = 1

        then:
            domain.validate(['number'])

        when: "Test range 2"
            domain.number = 5

        then:
            domain.validate(['number'])

        when: "Test range 3"
            domain.number = 11

        then:
            !domain.validate(['number'])

        when: "Test null"
            domain.number = null
        then:
            !domain.validate(['number'])
    }

    void "Test of constraint of rollOne"() {
        when: "Test range 1"
            domain.rollOne = 1

        then:
            domain.validate(['rollOne'])

        when: "Test range 2"
            domain.rollOne = 5

        then:
            domain.validate(['rollOne'])

        when: "Test range 3"
            domain.rollOne = 10

        then:
            domain.validate(['rollOne'])

        when: "Test range 4"
            domain.rollOne = 11

        then:
            !domain.validate(['rollOne'])

        when: "Test null"
            domain.rollOne = null

        then:
            !domain.validate(['rollOne'])
    }

    void "Test of constraint of rollTwo"() {
        domain.rollOne = 0

        when: "Test range 1"
            domain.rollTwo = 1

        then:
            domain.validate(['rollTwo'])

        when: "Test range 2"
            domain.rollTwo = 5

        then:
            domain.validate(['rollTwo'])

        when: "Test range 3"
            domain.rollTwo = 10

        then:
            domain.validate(['rollTwo'])

        when: "Test range 4"
            domain.rollTwo = 11

        then:
            !domain.validate(['rollTwo'])

        when: "Test null"
            domain.rollTwo = null

        then:
            !domain.validate(['rollTwo'])

        when: "Test validator 1"
            domain.rollOne = 5
            domain.rollTwo = 7

        then:
            !domain.validate(['rollTwo'])

        when: "Test validator 2"
            domain.rollOne = 5
            domain.rollTwo = 5

        then:
            domain.validate(['rollTwo'])

        when: "Test validator 3"
            domain.rollOne = 5
            domain.rollTwo = 2

        then:
            domain.validate(['rollTwo'])
    }

    void "Test of constraint of rollThree"() {
        when: "null"
            domain.rollThree = null

        then:
            domain.validate(['number'])
        when: "validator"
            domain.number = 1
            domain.rollThree = 1

        then:
            domain.validate(['number'])
    }
}
