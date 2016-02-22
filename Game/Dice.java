package Game;


public class Dice {
    private int value;
    private boolean isUsed;

    public Dice () {
        isUsed = false;
    }

    public void roll () {
        value = (int)(Math.random() * 100) % 6 + 1;
        isUsed = false;
    }

    public void setUsed (boolean isUsed) {
        this.isUsed = isUsed;
    }

    public boolean used () {
        return isUsed;
    }

    public int getValue () {
        return value;
    }

    public void setValue (int value) {
        this.value = value;
    }

}
