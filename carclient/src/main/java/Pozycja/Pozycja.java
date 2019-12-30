package Pozycja;

public class Pozycja {
    public float x, y;

    float getOdleglosc(Pozycja p1, Pozycja p2){
        return 0.f;
    }

    public Pozycja(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Pozycja() {
        this.x = 0;
        this.y = 0;
    }

    @Override
    public String toString() {
        return "Pozycja{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}