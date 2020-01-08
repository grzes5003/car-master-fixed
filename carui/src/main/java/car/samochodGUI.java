package car;

import Pozycja.Pozycja;
import Samochod.Samochod;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Vector;

public class samochodGUI {
    private JPanel mainJPanel;
    private JPanel listJPanel;
    private JPanel mapJPanel;
    private JPanel carJPanel;
    private JPanel propertiesJPanel;
    private JPanel globalJPanel;
    private JButton addButton;
    private JButton deleteButton;
    private JList list1;
    private JButton resetPosButton;
    private JButton szybciejButton;
    private JButton mniejSzybkoButton;
    private JTextField nazwaField;
    private JTextField rejestracyjnyField;
    private JTextField predkoscField;
    private JTextField pozycjaField;
    private JTextField poz1Field;
    private JTextField poz2Field;
    private JButton jedzButton;
    private JLabel carRepresentation;

    private JFrame frame;
    private Vector<Samochod> sam_tab;
    private int list1Index;

    boolean b_nazwaField; // if nazwaField is focused
    boolean b_rejestracyjnyField;

    public samochodGUI() {
        list1Index = -1;
        timer.start();
        sam_tab = new Vector<Samochod>();
        b_nazwaField = false;

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                addSamochod();
            }
        });

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                deleteSamochod();
            }
        });

        list1.addListSelectionListener(new ListSelectionListener() {
            //@Override
            public void valueChanged(ListSelectionEvent e) {
                list1Index = e.getFirstIndex();
            }
        });

        jedzButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // get poz1Field poz2Field, samochod.jedzDo(no tam jedz)
                if(sam_tab.get(list1Index).getStanWlaczenia()){
                    sam_tab.get(list1Index).wylacz();
                }
                else if(!poz1Field.getText().equals("") && !poz2Field.getText().equals("")){
                    //if there is anything
                    try {
                        sam_tab.get(list1Index).jedzDo(new Pozycja(Integer.parseInt(poz1Field.getText()), Integer.parseInt(poz2Field.getText())) );
                    }
                    catch (NumberFormatException ex){
                        ex.printStackTrace();
                    }
                }
            }
        });

        // if focused stop refreshing
        nazwaField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                b_nazwaField = true;
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                sam_tab.get(list1Index).setModel(nazwaField.getText());
                b_nazwaField = false;
            }
        });

        // if focused stop refreshing
        rejestracyjnyField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                b_rejestracyjnyField = true;
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                sam_tab.get(list1Index).setNrRejest(rejestracyjnyField.getText());
                b_rejestracyjnyField = false;
            }
        });
        mniejSzybkoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                sam_tab.get(list1Index).zmniejszBieg();
            }
        });
        szybciejButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                sam_tab.get(list1Index).zwiekszBieg();
            }
        });
        mapJPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Pozycja cel = new Pozycja(e.getX()*10, e.getY()*10);
                if (list1Index >= 0){
                    sam_tab.get(list1Index).jedzDo(cel);

                }
            }
        });
        resetPosButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (list1Index >= 0){
                    sam_tab.get(list1Index).setPozycja(new Pozycja(0,0));
                }
            }
        });
    }

    private Timer timer = new Timer(100, new ActionListener() {
        //@Override
        public void actionPerformed(ActionEvent e) {
            refreshValues();
        }
    });

    public void refreshValues(){
        if(list1Index < 0){
            rejestracyjnyField.setText("");
            nazwaField.setText("");
            predkoscField.setText("");
            pozycjaField.setText("");
            jedzButton.setText("");

            carRepresentation.setVisible(false);
        }
        else{
            if(!b_rejestracyjnyField){ rejestracyjnyField.setText(sam_tab.get(list1Index).getNrRejest());}
            if(!b_nazwaField){ nazwaField.setText(sam_tab.get(list1Index).getModel());}
            predkoscField.setText(Float.toString(sam_tab.get(list1Index).getAktPredkosc()));
            pozycjaField.setText(sam_tab.get(list1Index).getAktPozycja().toString());

            if(sam_tab.get(list1Index).getStanWlaczenia()){ jedzButton.setText("Zatrzymaj");}
            else{ jedzButton.setText("Jedź do"); }

            carRepresentation.setVisible(true);
            carRepresentation.setLocation((int) sam_tab.get(list1Index).getAktPozycja().x, (int) sam_tab.get(list1Index).getAktPozycja().y);
            System.out.println(sam_tab.get(list1Index).getAktPozycja().x);
        }
        System.out.println(list1Index);
        list1.setListData(sam_tab);

    }

    /// events methods
    private void addSamochod(){
        sam_tab.add(new Samochod(200));
    }

    private void deleteSamochod(){
        if(sam_tab.size() > 0 ){
            sam_tab.remove(list1Index);
        }
        list1Index = -1;
    }

    public static void main(String[] args) {
        int i = 1;
        JFrame frame = new JFrame("samochodGUI");
        frame.setContentPane(new samochodGUI().mainJPanel);
        frame.setSize(700,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack(); // not sure if necessary
        frame.setVisible(true);
    }
}