package commandObject

/**
 * Created by dach1016 on 25.08.2017.
 */
class CommandGame implements grails.validation.Validateable {
    String date
    List<UsersNames> users

    static constraints = {
        date nullable: true
        users nullable: true
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
        users?.each {sb.append(it.toString()).append("\n")}
        return "CommandGame{ date='$date', users: \n "+ sb.toString() + " }";
    }
}
