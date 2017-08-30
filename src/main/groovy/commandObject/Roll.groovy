package commandObject

/**
 * Created by danil on 29.08.2017.
 */
class Roll {
    Long gameId
    Long userNumber
    Integer frameNumber
    Integer rollNumber
    Integer value


    @Override
    public String toString() {
        return "Roll{" +
                "gameId=" + gameId +
                ", userNumber=" + userNumber +
                ", frameNumber=" + frameNumber +
                ", rollNumber=" + rollNumber +
                ", value=" + value +
                '}';
    }
}
