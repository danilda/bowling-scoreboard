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
            frames.add(new Frame(commandFrames.get(i).properties))
        }
        scoreService.calculateFrames(frames)
        frames
    }

    Map getRenderMapByGame(Game game) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT)
        def map = ["id": game.getId(), "date": sdf.format(game.date)]
        map.put(users: getListOfUsers(game))
        map
    }

    List getListOfUsers(Game game) {
        List<List> users = new ArrayList<>()
        game.getUsers().each { user ->
            users.add(fillFrame(user))
        }
        users
    }

    private List fillFrame(User user) {
        List<Frame> frames = user.frames.toList()
        List<Map> resultedFrame = new LinkedList()
        frames.sort { current, next -> current.number <=> next.number }
        frames.each { frame ->
            resultedFrame.add([rollOne: frame.rollOne, rollTwo: frame.rollTwo,
                               rollThree: frame.rollThree, totalScore: user.totalScore])
        }
        resultedFrame
    }

}
