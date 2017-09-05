package bowling.scoreboard

import commandObject.RollCommand
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification
import static GameService.SORT_BY_NUMBER

@Integration
@Rollback
class IntegrationGameDBServiceSpec extends Specification {
    GameDBService gameDBService
    GameService gameService

    def setup() {
    }

    def cleanup() {
    }

    void "test addNewGame"() {
        when:
        def names = ["test", "test1", "test2", "test3"]
        def users = []
        for (i in 0..<names.size()) {
            users.add new User(number: i, name: names[i])
        }

        Game game = gameDBService.addNewGame users
        then:
        game.users.size() == 4
        game.users.find().name in names
    }

    void "test addRollInGame (not last frame)"() {
        when:
        def users = []
        User user = new User(name: "test", number: 0)
        def rollsOne = [2, 5, 10, 4, 8, 5]
        def rollsTwo = [8, 0, 0, 5, 0, 5]
        for (i in 0..<rollsOne.size()) {
            user.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i]))
        }
        User user2 = new User(name: "test2", number: 1)
        rollsOne = [2, 5, 10, 4, 8, 2]
        rollsTwo = [8, 0, 0, 5, 0, null]
        for (i in 0..<rollsOne.size()) {
            user2.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i]))
        }
        User user3 = new User(name: "test3", number: 2)
        rollsOne = [2, 5, 10, 4, 8]
        rollsTwo = [8, 0, 0, 5, 0]
        for (i in 0..<rollsOne.size()) {
            user3.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i]))
        }
        users.add(user)
        users.add(user2)
        users.add(user3)


        Game game = gameDBService.addNewGame users
        RollCommand roll = gameService.getNextStep game
        roll.value = 5
        game = gameDBService.addRollInGame(roll)
        users = game.users.sort SORT_BY_NUMBER

        then: ""
        users[1].frames.sort(SORT_BY_NUMBER)[5].rollTwo == 5

        when:
        roll = gameService.getNextStep game
        roll.value = 6
        game = gameDBService.addRollInGame(roll)
        users = game.users.sort SORT_BY_NUMBER
        then:
        users[2].frames.sort(SORT_BY_NUMBER)[5].rollOne == 6

        when:
        roll = gameService.getNextStep game
        roll.setValue(4)
        game = gameDBService.addRollInGame(roll)
        users = game.users.sort SORT_BY_NUMBER
        then:
        users[2].frames.sort(SORT_BY_NUMBER)[5].rollTwo == 4

        when:
        roll = gameService.getNextStep game
        roll.setValue(10)
        game = gameDBService.addRollInGame(roll)
        users = game.users.sort SORT_BY_NUMBER
        then:
        users[0].frames.sort(SORT_BY_NUMBER)[6].rollOne == 10
        users[0].frames.sort(SORT_BY_NUMBER)[6].rollTwo == 0
    }

    void "test addRollInGame (last frame)"() {
        when:
        def users = []
        User user = new User(name: "test", number: 0)
        def rollsOne = [2, 5, 10, 4, 8, 5, 0, 10, 8, 10]
        def rollsTwo = [8, 0, 0, 5, 0, 5, 0, 0, 2, null]
        def rollsThree = [null, null, null, null, null, null, null, null, null, null]
        for (i in 0..<rollsOne.size()) {
            user.addToFrames(new Frame(number: i, rollOne: rollsOne[i], rollTwo: rollsTwo[i], rollThree: rollsThree[i]))
        }
        users.add(user)

        Game game = gameDBService.addNewGame users
        RollCommand roll = gameService.getNextStep game
        roll.value = 10
        game = gameDBService.addRollInGame(roll)
        users = game.users.sort SORT_BY_NUMBER

        then: ""
        users[0].frames.sort(SORT_BY_NUMBER)[9].rollTwo == 10

        when:
        roll = gameService.getNextStep game
        roll.value = 8
        game = gameDBService.addRollInGame(roll)
        users = game.users.sort SORT_BY_NUMBER
        then: ""
        users[0].frames.sort(SORT_BY_NUMBER)[9].rollThree == 8

    }
}
