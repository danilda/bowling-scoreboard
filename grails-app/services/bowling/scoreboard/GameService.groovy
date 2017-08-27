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
    public static final DATE_FORMAT = "MMM d, yyyy HH:mm:ss"

    ScoreService scoreService

    Game saveGameFromCommandGame(CommandGame commandGame) {
        Game game = commandGameInGame(commandGame)
        game.save()
        saveAllUsersInGame(game)
        saveAllFramesInGame(game)
        game
    }

    def saveAllFramesInGame(Game game){
        game.users.each { user ->
            user.frames.each { frame ->
                frame.save()
            }
        }
    }

    def saveAllUsersInGame(Game game){
        game.users.each { user ->
                user.save()
        }
    }

    Game commandGameInGame(CommandGame commandGame) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT)
        Game game = new Game(date: dateFormat.parse(commandGame.date))
        game.users = new LinkedHashSet<>()
        commandGame.users.each {
            game.users.add(commandUserToUser(commandGame.users))
        }
        game.winner = findWinner(game.users)
        game.winner.game = game
        injectGameAndUserInFrames(game)
        game
    }

    User findWinner(Set<User> users) {
        User winner = null
        users.each {
            if (winner == null || winner.totalScore < it.totalScore) {
                winner = it
            }
        }
        winner
    }

    def injectGameAndUserInFrames(Game game){
        game.users.each { user ->
            user.frames.each { frame ->
                frame.game = game
                frame.user = user
            }
        }
    }

    User commandUserToUser(CommandUser commandUser) {
        User user = new User(name: commandUser.name)
        List<Frame> frames = listOfCommandFrameToListOfFrame(commandUser.frames)
        user.totalScore = frames.get(LAST_FRAME).score
        user.frames = frames.toSet()
        user
    }

    List<Frame> listOfCommandFrameToListOfFrame(List<CommandFrame> commandFrames) {
        List<Frame> frames = new ArrayList(10)
        for (int i = 0; i < commandFrames.size(); i++) {
            frames.add(frameInGameToFrame(commandFrames.get(i), i))
        }
        scoreService.calculateFrames(frames)
        frames
    }

    Frame frameInGameToFrame(CommandFrame commandFrame, Integer number) {
        Frame frame = new Frame(number: number)
        frame.rollOne = Integer.valueOf(commandFrame.rollOne)
        frame.rollTwo = Integer.valueOf(commandFrame.rollTwo)
        frame.rollThree = commandFrame.rollThree == null ? commandFrame.rollThree : Integer.valueOf(commandFrame.rollThree)
        frame
    }
}
