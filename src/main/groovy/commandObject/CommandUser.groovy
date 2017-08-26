package commandObject

/**
 * Created by dach1016 on 25.08.2017.
 */
class CommandUser implements grails.validation.Validateable{
    String name
    List<CommandFrame> frames

    //TODO add constraints for all rules
    static constraints = {
        frames nullable: true
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
        sb.append(name).append(": \n")
        frames?.each {sb.append(it.toString()).append("\n") }
        return sb.toString()
    }
}
