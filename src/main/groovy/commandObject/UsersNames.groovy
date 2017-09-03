package commandObject


class UsersNames implements grails.validation.Validateable{
    List<String> names

    //TODO add constraints for all rules
    static constraints = {
        names nullable: false, validator: { val, obj ->
            val.each {
                if(it.isEmpty()|| it.size() > 15){
                    return false
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
        names?.each {sb.append(it.toString()).append(" \n") }
        return sb.toString()
    }
}
