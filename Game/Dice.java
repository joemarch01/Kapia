package Game;


public class Dice {
    private int value;
    private boolean isUsed;

    public Dice () {
        isUsed = false;
        value = 1;
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

    public Dice clone () {
        Dice clone = new Dice();
        clone.setUsed(isUsed);
        clone.setValue(value);
        return clone;
    }
}
