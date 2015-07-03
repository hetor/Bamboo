package ly.bamboo.bad_code;

/**
 * Created by hetao on 15-6-29.
 * 任何调用者都能修改数组的内容
 */
public class UnsafeStates {
    private String[] states = new String[] {
            "AK", "AL"
    };

    public String[] getStates() {
        return states;
    }
}
