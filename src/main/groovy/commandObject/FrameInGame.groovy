package commandObject

/**
 * Created by dach1016 on 25.08.2017.
 */
class FrameInGame implements grails.validation.Validateable{
    String rollOne
    String rollTwo
    String rollThree

    static constraints = {
        rollOne nullable: true
        rollTwo nullable: true
        rollThree nullable: true
    }


    @Override
    public String toString() {
        return "FrameInGame{" +
                " rollOne='" + rollOne + '\'' +
                ", rollTwo='" + rollTwo + '\'' +
                ", rollThree='" + rollThree + '\'' +
                '}';
    }
}
