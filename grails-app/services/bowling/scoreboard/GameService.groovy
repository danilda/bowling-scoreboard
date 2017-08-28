package bowling.scoreboard

import commandObject.CommandFrame
import commandObject.CommandGame
import commandObject.CommandUser
import grails.gorm.transactions.Transactional

import java.text.DateFormat
import java.text.SimpleDateFormat

import static bowling.scoreboard.ScoreService.LAST_FRAME

@Transactional
class GameService {
    public static final DATE_FORMAT = "MMM d, yyyy HH:mm:ss SSS"

    ScoreService scoreService

    Game saveGameByCommandGame(CommandGame commandGame) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT)
        Game game = new Game(date: dateFormat.parse(commandGame.date))
        commandGame.users.each {
            game.addToUsers(commandUserToUser(it))
        }
        game
    }

    User commandUserToUser(CommandUser commandUser) {
        User user = new User(name: commandUser.name)
        List<Frame> frames = listOfCommandFrameToListOfFrame(commandUser.frames)
        frames.each { user.addToFrames(it) }
        user.totalScore = frames.get(LAST_FRAME).score
        user
    }

    List<Frame> listOfCommandFrameToListOfFrame(List<CommandFrame> commandFrames) {
        List<Frame> frames = new ArrayList(10)
        for (int i = 0; i < commandFrames.size(); i++) {
            commandFrames.get(i).setNumber(i)
            frames.add(frameInGameToFrame(commandFrames.get(i)))
        }
        scoreService.calculateFrames(frames)
        frames
    }

    Frame frameInGameToFrame(CommandFrame commandFrame) {
        Frame frame = new Frame(commandFrame.properties)
//        frame.rollOne = commandFrame.rollOne
//        frame.rollTwo = commandFrame.rollTwo
//        frame.rollThree = commandFrame.rollThree
        frame
    }

    def domainGameInCommand(Game game) {
        def commandGame = new CommandGame()
        game.getUsers.each { user ->
            user.
        }
    }

    //отдельно обрабатывать список Фреймов длоя того что бы их сортировать
    private domainUserInCommand(User user) {
        def commandUser = new CommandUser()
        commandUser.setFrames new ArrayList<>()
        commandUser.setFrames(listDomainFramesInCommand(user.getFrames))
    }

    private List<CommandFrame> listDomainFramesInCommand (List<Frame> frame){

    }

}
