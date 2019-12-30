package Samochod;

import Pozycja.Pozycja;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SamochodTest {

    @org.junit.jupiter.api.Test
    void testJedzDo() {
        List<Samochod> sam_tab = new ArrayList<Samochod>();
        Random r = new Random();
        int x, y = 0;
        for(int i = 0; i<7; i++){
            x = r.nextInt(10000);
            y = r.nextInt(10000);
            sam_tab.add(new Samochod(200));
            sam_tab.get(i).jedzDo(new Pozycja(x,y));
            System.out.println("jedzDo("+x+","+y+")");
        }
        boolean allGood = false;
        for (int i = 0; i<10000; i++){
            allGood = true;
            for(Samochod s : sam_tab){
                if(s.getState() != Thread.State.WAITING){ allGood = false;}
            }
        }
        assertTrue(allGood);
    }

}