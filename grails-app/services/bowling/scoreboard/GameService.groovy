package bowling.scoreboard


import grails.gorm.transactions.Transactional
import commandObject.Roll

@Transactional
class GameService {
//    public static final DATE_FORMAT = "MMM d, yyyy HH:mm:ss SSS"
    Closure sortByNumber = { current, next -> current.number <=> next.number }
    ScoreService scoreService

    def getMapForRenderingFromGame(Game game) {
        def map = [game: game.getId()]
        map << [users: getUserListForRendering(game)]
        map
    }

    private getUserListForRendering(Game game) {
        List<User> users = game.getUsers().toList().sort sortByNumber
        List<List> resultUserList = new LinkedList<>()
        users.each { user ->
            resultUserList.add(getFrameListFromUser(user))
        }
        resultUserList
    }

    private getFrameListFromUser(User user) {
        List<Frame> frames = user.frames.toList().sort sortByNumber
        List<Map> resultFrameList = new LinkedList<>()
        frames.each { frame ->
            resultFrameList.add(getRollMapFromFrame(frame))
        }
        resultFrameList
    }

    private getRollMapFromFrame(Frame frame) {
        def rolls = null
        if (frame != null && frame.rollOne != null) {
            rolls = [score: frame.score]
            if (ScoreService.isStrike(frame)) {
                rolls << [rollOne: "X", rollTwo: ""]
            } else {
                def rollOne = frame.rollOne == 0 ? "-" : frame.rollOne.toString()
                def rollTwo = frame.rollTwo == 0 ? "-" : frame.rollTwo.toString()
                if (ScoreService.isSpare(frame)) {
                    rollTwo = "/"
                }
                rolls << [rollOne: rollOne, rollTwo: rollTwo]
            }
        }
        rolls
    }


    def getNextStep(Game game) {
        if (isGameEnded(game)) {
            println "isGameEnded"
            return null
        }
        Roll roll = getNextRoll(game)
        roll.setGameId(game?.getId())
        roll
    }

    private getNextRoll(Game game) {
        List users = game.getUsers().toList().sort sortByNumber
        for (i in 0..users.size() - 2) {
            if (users[i].getFrames().size() > users[i + 1].getFrames().size()) {
                return getRollFromFramesWithDifferenceSize(users, i)
            }
        }
        user lastUser = users[users.size()-1]
        if(isFrameEnded(getLastUserFrame(lastUser))){
            return new Roll(userNumber: 0, frameNumber: getLastUserFrame(users[0]).getNumber(), rollNumber: 0)
        } else {
            Roll roll = new Roll(userNumber: users.size()-1)
            roll.setFrameNumber(getLastUserFrame(lastUser).getNumber())
            roll.setRollNumber(getRollNumberForNotEndedFrame(getLastUserFrame(lastUser)))
            return roll
        }
    }

    private getRollFromFramesWithDifferenceSize(List<User> users, int i){
        Frame lastFrame = getLastUserFrame(users[i])
        if (isFrameEnded(lastFrame)) {
            return new Roll(userNumber: users[i + 1].getNumber(), frameNumber: lastFrame.number, rollNumber: 0)
        }
        def roll = new Roll(userNumber: users[i].getNumber(), frameNumber: lastFrame.number)
        roll.setRollNumber(getRollNumberForNotEndedFrame(lastFrame))
        return roll
    }

    private getRollNumberForNotEndedFrame(Frame frame){
        if (frame.rollOne == null) {
            return 0
        } else if (frame.rollTwo == null) {
            return 1
        } else {
            return 2
        }
    }

    def isGameEnded(Game game) {
        def result = true
        game.getUsers().each { user ->
            println user.getFrames().size()
            if (user.getFrames().size() != 10) {
                println "isGameEnded - return false"
                result = false
            }
            user.getFrames().each { frame ->
                if (frame.number == 9 && frame.rollOne == null) {
                    println "isGameEnded - return false"
                    result = false
                }
            }
        }
        result
    }

    def isFrameEnded(Frame frame) {
        if (frame.rollOne == null || frame.rollTwo == null || (frame.number == 9 && frame.rollThree == null)) {
            return false
        }
        true
    }

    def getLastUserFrame (User user){
        return user.getFrames().sort(sortByNumber).get(user.getFrames().size() - 1)
    }

    /*
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
            resultedFrame.add([rollOne  : frame.rollOne, rollTwo: frame.rollTwo,
                               rollThree: frame.rollThree, totalScore: user.totalScore])
        }
        resultedFrame
    }
    */
}
