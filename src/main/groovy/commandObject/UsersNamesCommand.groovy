package commandObject


class UsersNamesCommand implements grails.validation.Validateable{
    List<String> names

    static constraints = {
        names nullable: false ,validator: { val, obj, errors ->
            if(val.size() > 6){
                errors.rejectValue("names", "maxListSize")
                return false
            } else if(val.size() < 1) {
                errors.rejectValue("names", "minListSize")
                return false
            }
            val.each {
                if(it.isEmpty()|| it.size() > 15){
                    errors.rejectValue("names", "correctName")
                    return false
                }
            }
        }
    }

}
