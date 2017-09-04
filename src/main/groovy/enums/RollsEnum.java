package enums;

/**
 * Created by danil on 04.09.2017.
 */
public enum RollsEnum {
    ROLL_ONE(0), ROLL_TWO(1), ROLL_THREE(2);

    RollsEnum(int id) {
        this.id = id;
    }

    private Integer id;

    public int getId() {
        return id;
    }

    public static String getClassName() {
        return RollsEnum.class.getName();
    }
}
