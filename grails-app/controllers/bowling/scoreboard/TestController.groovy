package bowling.scoreboard

class TestController {

    def index() {
        def game = new Game(date: new Date()).save()
        def danil = new User(game: game, name: "danil").save()

        def dima = new User(game: game, name: "dima").save()
        def masha = new User(game: game, name: "masha").save()
//        println danil
//        println danil.game
        def frame1 = new Frame(user:danil, game:danil.game, number: 1, rollOne: 3, rollTwo: 5).save(failOnError: true)
        def frame2 = new Frame(user:danil, game:danil.game, number: 2, rollOne: 3, rollTwo: 2).save()
        def frame3 = new Frame(user:danil, game:danil.game, number: 3, rollOne: 3, rollTwo: 3).save()
        def frame4 = new Frame(user:danil, game:danil.game, number: 4, rollOne: 3, rollTwo: 4).save()
        def frame5 = new Frame(user:danil, game:danil.game, number: 5, rollOne: 3, rollTwo: 6).save()
        def frame6 = new Frame(user:danil, game:danil.game, number: 6, rollOne: 3, rollTwo: 7).save()

        println danil
        println frame1
        danil.addToFrames(frame1)
        danil.addToFrames(frame2)
        danil.addToFrames(frame3)
        danil.addToFrames(frame4)
        danil.addToFrames(frame5)
        danil.addToFrames(frame6)
        danil.
        danil.save()

        render "test"
    }
    def sout() {
        def danil = User.findByName("danil")
        danil.frames?.collect{e -> println e.toSting()}
        render "$danil.name - $danil.game.date.toSting"
    }
}
