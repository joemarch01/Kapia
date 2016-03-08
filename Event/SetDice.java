package Event;


public class SetDice extends Event {
    private int diceNumber;
    private int value;

    public  SetDice (int diceNumber, int value) {
        this.diceNumber = diceNumber;
        this.value = value;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public int getValue() {
        return value;
    }

    public String toString () {
        return value + "-";
    }
}
