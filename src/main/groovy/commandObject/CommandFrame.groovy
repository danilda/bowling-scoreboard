package commandObject

/**
 * Created by dach1016 on 25.08.2017.
 */
class CommandFrame implements grails.validation.Validateable{
    Integer number
    Integer rollOne
    Integer rollTwo
    Integer rollThree

    static constraints = {
        rollOne range: 0..10
        rollTwo range: 0..10, validator: {
            val, obj ->
                if((val + obj.rollOne) <= 10){
                    return true
                } else if(obj.number == 9 && obj.rollOne == 10 && (val + obj.rollOne) <= 20){
                    return true
                }
                false
        }
        rollThree nullable: true, range: 0..10, validator: {
            val, obj ->
                if(obj.number == 9 && (obj.rollOne + obj.rollTwo) >= 10 && val != null){
                    return true
                } else if((obj.number != 9 || (obj.rollOne + obj.rollTwo) < 10) && val == null){
                    return true
                }
                false
        }
    }


    @Override
    public String toString() {
        return "CommandFrame{" +
                " number='"+ number + '\'' +
                ", rollOne='" + rollOne + '\'' +
                ", rollTwo='" + rollTwo + '\'' +
                ", rollThree='" + rollThree + '\'' +
                '}';
    }

}
