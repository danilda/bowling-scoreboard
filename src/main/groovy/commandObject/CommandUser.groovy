package commandObject


class CommandUser implements grails.validation.Validateable{
    List<String> names

    //TODO add constraints for all rules
    static constraints = {
        names nullable: true
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
        names?.each {sb.append(it.toString()).append(" \n") }
        return sb.toString()
    }
}
