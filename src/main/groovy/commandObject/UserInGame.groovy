package commandObject

/**
 * Created by dach1016 on 25.08.2017.
 */
class UserInGame implements grails.validation.Validateable{
    List<FrameInGame> frames

    static constraints = {
        frames nullable: true
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
        frames?.each {sb.append(it.toString()).append("\n") }
        return sb.toString()
    }
}
