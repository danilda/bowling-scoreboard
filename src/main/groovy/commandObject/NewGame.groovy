package commandObject

/**
 * Created by dach1016 on 25.08.2017.
 */
class NewGame implements grails.validation.Validateable {
    int usersCount
    String date
    List<UserInGame> users

    static constraints = {
        date nullable: true
        users nullable: true, validator: {val , obj -> val.size <= 6}
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
        users?.each {sb.append(it.toString()).append("\n")}
        return "NewGame{usersCount= $usersCount, date='$date', users: \n "+ sb.toString() + " }";
    }
}
