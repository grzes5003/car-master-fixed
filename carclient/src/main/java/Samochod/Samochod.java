/// CURRENT KNOWN BUGS
// 1. if calling jedzDo(x) and x is position of the car, exception occures

package Samochod;

import static java.lang.Math.pow;
import Pozycja.Pozycja;
//import com.sun.org.apache.xpath.internal.operations.Bool;
import config.ConfigLoader;

import java.util.Properties;

public class Samochod extends Thread{

    private int res; // time threat sleeps

    private Pozycja pozycja;
    private boolean stanWlaczenia;
    private String nrRejest;
    private String model;
    private int predkoscMax;
    private Silnik silnik;
    private SkrzyniaBiegow skrzyniaBiegow;

    Pozycja cel;

    // constructiors

    public Samochod(int res, Pozycja pozycja, boolean stanWlaczenia, String nrRejest, String model, int predkoscMax, Silnik silnik, SkrzyniaBiegow skrzyniaBiegow, Pozycja cel) {
        this.res = res;
        this.pozycja = pozycja;
        this.stanWlaczenia = stanWlaczenia;
        this.nrRejest = nrRejest;
        this.model = model;
        this.predkoscMax = predkoscMax;
        this.silnik = silnik;
        this.skrzyniaBiegow = skrzyniaBiegow;
        this.cel = cel;

        // this.start();
    }

    public Samochod(int res){

        ConfigLoader cfg = new ConfigLoader();

        try {
            Properties prop = cfg.getPropValues();

            this.res = Integer.parseInt(prop.getProperty("res"));
            this.pozycja = new Pozycja(0,0);
            this.stanWlaczenia = Boolean.parseBoolean(prop.getProperty("car.stanWlaczenia"));
            this.nrRejest = prop.getProperty("car.nrRejest");
            this.model = prop.getProperty("car.model");
            this.predkoscMax = Integer.parseInt(prop.getProperty("car.predkoscMax"));
            this.silnik = new Silnik("tak",100,1,100,0);
            this.skrzyniaBiegow = new SkrzyniaBiegow("tak2",101,2,1,6,1,new Sprzeglo("tak3",102,3));
            this.cel = new Pozycja(0,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // minor methods

    public void wlacz(){
        this.stanWlaczenia = true;
    }

    public void wylacz(){
        this.stanWlaczenia = false;
        // version with no wait()
//        try{
//            this.wait();
//            //this.notifyAll();
//        }
//        catch (InterruptedException e){
//            e.printStackTrace();
//        }
    }

    public float getWaga(){
        return skrzyniaBiegow.getWaga() + silnik.getWaga();
    }

    public float getAktPredkosc(){
        return silnik.obroty * skrzyniaBiegow.getAktPrzelozenie() * 2; // TODO obw kola implement
    }

    public Pozycja getAktPozycja(){
        return this.pozycja;
    }

    private double getDistance(){
        return Math.sqrt( pow(getAktPozycja().x - cel.x,2) +  pow(getAktPozycja().y - cel.y,2) );
    }

    public String getNrRejest(){
        return nrRejest;
    }

    public boolean getStanWlaczenia(){
        return stanWlaczenia;
    }

    public String getModel(){
        return model;
    }

    public void setModel(String mod){
        model = mod;
    }

    public void setNrRejest(String nr){
        nrRejest = nr;
    }

    // major methods

    @Override
    public synchronized void start() {
        this.wlacz();
        super.start();
    }

    public void jedzDo(Pozycja cel){
        /**
         * initializes movement, starting all processes
         */

        // get pos
        // if cel != pos
        // change pos
        this.cel = cel;
        this.start();
    }

    @Override
    public void run() {
        /**
         * core of class Samochod.Samochod; controls meta movement
         */
        // version with no wait()

        try {
            while (true) {
                // refresh rate
                Thread.sleep(res);

                    /* // handle of status
                    if(!this.stanWlaczenia){
                        break;
                    }*/
                if (this.stanWlaczenia) {
                    // skrzyniabiegow and obroty handle
                    this.przenies(res);

                    if (this.getAktPredkosc() < this.predkoscMax) {
                        if (this.skrzyniaBiegow.iloscBiegow > this.skrzyniaBiegow.getAktBieg()) {
                            this.skrzyniaBiegow.zwiekszBieg();
                        }

                        if (this.silnik.getObroty() < this.silnik.maxobroty) {
                            this.silnik.zwiekszObroty();
                        }
                    }
                    // debug section
                    System.out.println(this.pozycja);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void przenies(int time){
        /**
         * controls movement; distance etc
         */
        if(getDistance() <= time*this.getAktPredkosc()){
            this.pozycja = cel;
            System.out.println("Dojeachane");
            this.wylacz();
            // TODO stop thread
        }
        // what if driving not from left to upper right?
        double alpha = Math.atan( (cel.y - pozycja.y)/(cel.x - pozycja.x) ); // possible mistake

        // change position
        this.pozycja.x += time*this.getAktPredkosc()*Math.sin(alpha);
        this.pozycja.y += time*this.getAktPredkosc()*Math.cos(alpha);
    }

    public Thread.State getState(){
        if(this.stanWlaczenia) {
            return State.WAITING;
        }
        return State.RUNNABLE;
    }

    @Override
    public String toString() {
        return model + ", ID: " + nrRejest + ", stan: " + stanWlaczenia;
    }

    public void zmniejszBieg() {
        this.skrzyniaBiegow.zmniejszBieg();
    }

    public void zwiekszBieg() {
        this.skrzyniaBiegow.zwiekszBieg();
    }

    public void setPozycja(Pozycja pozycja) {
        this.pozycja = pozycja;
    }
}