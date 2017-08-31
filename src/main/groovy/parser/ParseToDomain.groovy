package parser

import bowling.scoreboard.Frame
import bowling.scoreboard.Game
import bowling.scoreboard.ScoreService
import bowling.scoreboard.User
import commandObject.CommandFrame
import commandObject.CommandGame
import commandObject.CommandUser
import static ScoreService.LAST_FRAME

import java.text.DateFormat
import java.text.SimpleDateFormat

class ParseToDomain {

    ScoreService scoreService
/*
    Game commandGameInGame(CommandGame commandGame) {
        DateFormat dateFormat = new SimpleDateFormat()
        Game game = new Game(date: dateFormat.parse(commandGame.date))
        game.users = new LinkedHashSet<>()
        commandGame.users.each {
            game.users.add(commandUserToUser(commandGame.users))
        }
        game.winner = findWinner(game.users)
        game
    }

    User findWinner(Set<User> users){
        User winner = null
        users.each {
            if(winner == null || winner.totalScore < it.totalScore){
                winner = it
            }
        }
        winner
    }

    User commandUserToUser(CommandUser commandUser) {
        User user = new Game(name: commandUser.name)
        List<Frame> frames = listOfCommandFrameToListOfFrame(commandUser.frames);
        user.totalScore = frames.get(LAST_FRAME).score
        user.frames = frames.toSet()
        user
    }

    List<Frame> listOfCommandFrameToListOfFrame(List<CommandFrame> commandFrames){
        List<Frame> frames = new ArrayList(10)
        for (int i = 0; i < commandFrames.size(); i++) {
            frames.add(frameInGameToFrame(commandFrames.get(i), i))
        }
        scoreService.calculateFrames(frames)
    }

    Frame frameInGameToFrame(CommandFrame commandFrame, Integer number) {
        Frame frame = new Frame(number: number)
        frame.rollOne = Integer.valueOf(commandFrame.rollOne)
        frame.rollTwo = Integer.valueOf(commandFrame.rollTwo)
        frame.rollThree = Integer.valueOf(commandFrame.rollThree)
        frame
    }
*/
}
