package enums;

/**
 * Created by danil on 04.09.2017.
 */
public enum RollsEnum {
    ROLL_ONE(1), ROLL_TWO(2), ROLL_THREE(3);

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
