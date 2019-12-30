package Samochod;

public class Silnik extends Komponent{

    int maxobroty;
    int obroty;
    private final int res = 1;

    public Silnik(String nazwa, float waga, float cena, int maxobroty, int obroty) {
        super(nazwa, waga, cena);
        this.maxobroty = maxobroty;
        this.obroty = obroty;
    }

    public void uruchom(){}

    public void zatrzymaj(){}

    public void zwiekszObroty(){
        if(maxobroty <= obroty+res){
            // TODO return error
            return;
        }
        this.obroty += res;
    }

    public void zmniejszObroty(){}

    public float getObroty(){
        return this.obroty;
    }
}