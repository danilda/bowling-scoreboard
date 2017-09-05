package bowling.scoreboard

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class GameSpec extends Specification implements DomainUnitTest<Game> {

    def setup() {
    }

    def cleanup() {
    }

    void "test validator"() {
        when:
            def users = []
            for(i in 0..5){
                users.add new User(name: i, number: i)
            }
            domain.users = users
        then:"fix me"
            domain.validate(['users'])

        when:
            users = []
            for(i in 0..4){
                users.add new User(name: i, number: i)
            }
            users.add new User(name: "fail", number: 0)
            domain.users = users
        then:"fix me"
            !domain.validate(['users'])

        when:
            users = []
            for(i in 0..10){
                users.add new User(name: i, number: i)
            }
            domain.users = users
        then:"fix me"
            !domain.validate(['users'])
    }
}
