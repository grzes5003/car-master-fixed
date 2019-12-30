package Samochod;

public class Komponent {

    String nazwa;
    float waga;
    float cena;

    public Komponent(String nazwa, float waga, float cena) {
        this.nazwa = nazwa;
        this.waga = waga;
        this.cena = cena;
    }

    public String getNazwa(){
        return this.nazwa;
    }

    public float getWaga(){
        return this.waga;
    }

    public float getCena(){
        return this.cena;
    }
}