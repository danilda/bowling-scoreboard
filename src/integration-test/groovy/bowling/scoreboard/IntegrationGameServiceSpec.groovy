package bowling.scoreboard

import commandObject.CommandFrame
import commandObject.CommandGame
import commandObject.CommandUser
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

import java.text.SimpleDateFormat

@Integration
@Rollback
class IntegrationGameServiceSpec extends Specification {
    GameService gameService

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        when:
            def rollsOne = ["10", "10", "10", "7", "8", "0", "10", "7", "9", "10"]
            def rollsTwo = [0, 0, 0, 2, 2, 9, 0, 3, 0, 10]
            def rollsThree = [null, null, null, null, null, null, null, null, null, 8]
            def name = "Danil"
            def time = 9999999
            CommandUser commandUser = new CommandUser(name: name)
            commandUser.frames = new ArrayList<>()
            for(int i in 0..9){
                commandUser.frames.add(new CommandFrame(rollOne: rollsOne[i], rollTwo: rollsTwo[i], rollThree: rollsThree[i]))
            }
            SimpleDateFormat sdf = new SimpleDateFormat(GameService.DATE_FORMAT)
            CommandGame commandGame = new CommandGame(date: sdf.format(new Date(time)))
            commandGame.users = new ArrayList<>(1)
            commandGame.users.add(commandUser)

            Game game = gameService.saveGameFromCommandGame(commandGame)
        then: "проверить равно ли 0 - false"
            game.errors.iterator().size() == 0
            game.date.getTime() == time
            (++game.users.iterator()).name == name
    }
}
