package Samochod;

public class SkrzyniaBiegow extends Komponent {

    private int aktualnyBieg;
    public int iloscBiegow;
    protected float aktualnePrzelozenie;
    protected Sprzeglo sprzeglo;

    public SkrzyniaBiegow(String nazwa, float waga, float cena, int aktualnyBieg, int iloscBiegow, float aktualnePrzelozenie, Sprzeglo sprzeglo) {
        super(nazwa, waga, cena);
        this.aktualnyBieg = aktualnyBieg;
        this.iloscBiegow = iloscBiegow;
        this.aktualnePrzelozenie = aktualnePrzelozenie;
        this.sprzeglo = sprzeglo;
    }

    public int zwiekszBieg(){
        if(aktualnyBieg == iloscBiegow){
            // cout exception
            return 1;
        }
        getAktPrzelozenie();
        return this.aktualnyBieg++;
    }

    public int zmniejszBieg(){
        if(aktualnyBieg == 1){
            // cout exception
            return 1;
        }
        getAktPrzelozenie();
        return this.aktualnyBieg--;
    }

    public int getAktBieg(){
        return this.aktualnyBieg;
    }

    public float getAktPrzelozenie(){
        float returned = 1.1f;
        for(int i = 1; i <= aktualnyBieg; i++){
            returned += 0.2f;
        }
        this.aktualnePrzelozenie = returned;
        return returned;
    }

    @Override
    public float getWaga() {
        return super.getWaga() + sprzeglo.getWaga();
    }
}