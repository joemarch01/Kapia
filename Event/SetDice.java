package Event;

/**
 * Created by jm360 on 06/03/16.
 */
public class SetDice extends Event {
    private int diceNumber;
    private int value;

    public  SetDice (int diceNumber, int value) {
        this.diceNumber = diceNumber;
        this.value = value;
    }
}
