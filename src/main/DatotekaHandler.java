package main;

import Element.Element;
import Composite.IElement;
import Composite.JednostavniElement;
import Composite.SlozeniElement;
import Memento.CareTaker;
import Memento.Originator;
import State.Context;
import State.PrikaziElement;
import State.SakrijElement;
import State.State;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ana-Marija
 */
public class DatotekaHandler {

    SlozeniElement slozElem;
    private String datoteka;
    public int n;
    List<Element> listaElemenata = new ArrayList<>();

    List<Element> listaElemenataIspravnihRoditelja = new ArrayList<>();
    public static List<Element> listaIspravnihElemenata = new ArrayList<>();
    public static List<Element> listaNeispravnihElemenata = new ArrayList<>();
    List<IElement> listaSlozenihElemenata = new ArrayList<>();
    public static IElement slozeniElement = null;
    IElement jednostavniElement = null;
    public static List<Element> konacnaLista;

    Originator originator = new Originator();
    CareTaker careTaker = new CareTaker();

    public DatotekaHandler(String datoteka) {
        this.datoteka = datoteka;
    }

    public void startMenu() {
        System.out.println("\n=================================\n");
        System.out.println("Odaberite sljedeći korak:\n");
        System.out.println("1 - pregled stanja");
        System.out.println("2 - pregled elemenata u presjeku");
        System.out.println("3 - promjena statusa elementa");
        System.out.println("4 - ukupne površine boja");
        System.out.println("5 - učitavanje dodatne datoteke");
        System.out.println("0 - izlaz iz programa\n");
        System.out.println("=================================\n");

        Scanner reader = new Scanner(System.in);
        System.out.println("Vaš odabir: ");
        n = reader.nextInt();

        originator.setState(n);
        careTaker.add(originator.saveStateToMemento());

        //provjera je li broj u rasponu i ispis pogreške
        if (n > 5) {
            originator.getStateFromMemento(careTaker.get(0));
            System.out.println("Neispravno. Vraćamo vas na početni odabir iz menija:\n");
            System.out.println(originator.getState());
            n = originator.getState();
        }

        odabir();
    }

    private void odabir() {
        switch (n) {
            case 1:
                pregledStanja();
                break;
            case 2:
                System.out.println("Pregled elemenata u presjeku nije implementiran");
                break;
            case 3:
                promjenaStatusa();
                break;
            case 4:
                povrsineBoja();
                break;
            case 5:
                dodajDatoteku();
                break;
            case 0:
                System.out.println("Kraj programa.");
                break;

        }
    }

    public void ucitajDatoteku() {
        BufferedReader fajl;

        try {
            fajl = new BufferedReader(new FileReader(this.datoteka));
            System.out.println("Naziv datoteke: " + this.datoteka + "\n");
            validiraj(fajl);

        } catch (FileNotFoundException fnfex) {
            System.out.println("Datoteka nije pronađena");
            System.exit(0);
        } catch (IOException IOex) {
            System.out.println("Pogreška pri čitanju datoteke");
        }

    }

    private void validiraj(BufferedReader fajl) throws IOException {
        String line;
        List<Integer> sifre = new ArrayList<>();
        Boolean ishodisni = false;
        List<Integer> listaRoditelja = new ArrayList<>();

        while ((line = fajl.readLine()) != null) {
            //odvajanje polja u pojedinom retku
            String[] values = line.split("\t");
            Boolean errorIspravnostiZapisa = true;
            String error = "";
            String[] koordinateRoditelja = null;
            String presjek = "";

            String[] koordinate = values[3].split(",");

            Element element = new Element(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), koordinate, values[4], errorIspravnostiZapisa, error, presjek, koordinateRoditelja);
            listaElemenata.add(element);

            dodavanjeRoditeljaUListu(listaRoditelja, element);

            provjeraIspravnostiRoditelja(element);

            ispitivanjeDuzinePolja(values, element, errorIspravnostiZapisa);

            negativneKoordinate(koordinate, element);

            if (ishodisni == true && element.getTip() == 0 && element.getSifra() == element.getRoditelj()) {
                element.setErrorIspravnostiZapisa(false);
                element.setGreska("Ponovno ishodišni element");
            }

            if (ishodisni == false && element.getTip() == 0 && element.getSifra() == element.getRoditelj()) {
                ishodisni = true;
                element.setErrorIspravnostiZapisa(ishodisni);
            }

            neispravanTipElementa(element);

            ispitivanjeBrojaKoordinata(element, error);

            ispitivanjePonavljanjaSifre(element, error, sifre);

            postojiLiRoditelj(element, listaRoditelja);

            dohvatiKoordinateRoditelja(listaElemenata, element);

            presjekSRoditeljem(element, koordinate, koordinateRoditelja, presjek);

            dodajNaIspravnuNeispravnuListu(element);

        }

        ispisiListe();

        konacnaLista = listaIspravnihElemenata;

        kreirajComposite();
    }

    private void dodavanjeDjece(int n) {
        for (int i = 0; i < listaSlozenihElemenata.size(); i++) {
            slozElem = (SlozeniElement) listaSlozenihElemenata.get(i);

            switch (n) {
                case 1: //dodavanje jednostavnog elementa roditelju
                    if (slozElem != null && jednostavniElement != null && slozElem.getSifra() == jednostavniElement.getRoditelj()) {
                        slozElem.add(jednostavniElement);

                        //System.out.println("dodavanje jednostavnog");
                    }
                    break;
                case 2: //dodavanje složenog elementa roditelju
                    if (slozElem != null && slozElem.getSifra() == slozeniElement.getRoditelj()) {
                        slozElem.add(slozeniElement);
                        //System.out.println("dodavanje slozenog");
                    }
                    break;
            }
            /*
             List<IElement> test = slozElem.getElementi();
             for (int j = 0; j < test.size(); j++) {
             test.get(j).getRoditelj(); // getat što god
             System.out.println(slozElem.getSifra() + "   " + test.get(j).getSifra() + "    " + test.get(j).getRoditelj() + "    " + Arrays.toString(test.get(j).getKoordinate()));
             }
             */
        }

    }

    private void dodajDatoteku() {
        System.out.println("Unesite naziv nove datoteke: ");

        //dohvaćanje imena nove datoteke
        Scanner reader = new Scanner(System.in);
        String novaDatoteka = reader.nextLine();

        this.datoteka = novaDatoteka;
        ucitajDatoteku();

        //System.out.println(this.listaElemenata);
        startMenu();

    }

    private void pregledStanja() {

        System.out.println("Lista ispravnih elemenata: " + "\n"
                + "=========================================================");
        for (int i = 0; i < konacnaLista.size(); i++) {
            System.out.println(konacnaLista.get(i).getSifra() + "\t"
                    + konacnaLista.get(i).getRoditelj() + "\t"
                    + Arrays.toString(konacnaLista.get(i).getKoordinate()) + "\t"
                    + konacnaLista.get(i).getBoja() + "\t"
                    + konacnaLista.get(i).getPresjek());
        }

        startMenu();

    }

    private void promjenaStatusa() {

        Context context = new Context();

        State prikazi = new PrikaziElement();
        State sakrij = new SakrijElement();

        System.out.println("Unesite šifru elementa kojem želite promijeniti status: ");
        Scanner reader = new Scanner(System.in);
        int sifraZaPromjenu = reader.nextInt();

        System.out.println("Unesite 0 ako želite da je stanje sakriveno, 1 za aktivno: ");
        int stanje = reader.nextInt();

        for (int i = 0; i < listaElemenata.size(); i++) {

            Element trenutniElement = listaElemenata.get(i);
            if (trenutniElement.getSifra() == sifraZaPromjenu) {
                if (stanje == 0) {
                    context.setState(sakrij);
                    Element noviElement = context.doAction(trenutniElement);

                    listaIspravnihElemenata.remove(trenutniElement);
                    dodajNaIspravnuNeispravnuListu(noviElement);

                } else {
                    context.setState(prikazi);
                    Element noviElement = context.doAction(trenutniElement);
                    listaNeispravnihElemenata.remove(trenutniElement);
                    dodajNaIspravnuNeispravnuListu(noviElement);
                }
            }
            provjeraIspravnostiRoditelja(listaElemenata.get(i));
            
        }

        ispisiListe();
        startMenu();
    }

    private void ispisiListe() {
        System.out.println("\n Lista neispravnih elemenata: " + "\n"
                + "=========================================================");
        for (int i = 0; i < listaNeispravnihElemenata.size(); i++) {
            System.out.println(listaNeispravnihElemenata.get(i).getSifra() + "\t"
                    + listaNeispravnihElemenata.get(i).getRoditelj() + "\t"
                    + Arrays.toString(listaNeispravnihElemenata.get(i).getKoordinate()) + "\t"
                    + listaNeispravnihElemenata.get(i).getBoja() + "\t"
                    + listaNeispravnihElemenata.get(i).getGreska()
            );
        }
    }

    private void provjeraIspravnostiRoditelja(Element element) {
        //ako je neispravan roditelj - onda je i dijete
        for (int i = 0; i < listaNeispravnihElemenata.size(); i++) {
            if (element.getRoditelj() == listaNeispravnihElemenata.get(i).getSifra() && !(listaNeispravnihElemenata.get(i).getSifra() == listaNeispravnihElemenata.get(i).getRoditelj()) && !listaNeispravnihElemenata.contains(element)) {
                element.setGreska("Neispravan roditelj");
                element.setErrorIspravnostiZapisa(false);
                dodajNaIspravnuNeispravnuListu(element);
                listaIspravnihElemenata.remove(element);
            }
        }
    }

    private void ispitivanjeDuzinePolja(String[] values, Element element, Boolean errorIspravnostiZapisa) {
        //ispitivanje ispravnosti dužine polja
        if ((values[0].length() == 1) && (values[1].length() == 5) && (values[2].length() == 5) && (values[4].length() == 7) && (errorIspravnostiZapisa == true)) {
        } else {
            element.setGreska("neispravna dužina polja");
            element.setErrorIspravnostiZapisa(false);
        }
    }

    private void ispitivanjeBrojaKoordinata(Element element, String error) {
        //provjera je li broj koordinata 3, 4 ili 2i
        if (element.getKoordinate().length == 3 || element.getKoordinate().length == 4 || ((element.getKoordinate().length % 2) == 0) && element.getKoordinate().length <= 14) {
            //System.out.println("Ispravan broj koordinata");
        } else {
            error = "Neispravan broj koordinata";
            element.setGreska(error);
            element.setErrorIspravnostiZapisa(false);
        }
    }

    private void ispitivanjePonavljanjaSifre(Element element, String error, List<Integer> sifre) {
        //provjera postoji li već element s istom šifrom
        if (sifre.contains(element.getSifra()) == false) {
            sifre.add(element.getSifra());
        } else {
            element.setErrorIspravnostiZapisa(false);
            error = "Već postoji element s istom šifrom";
            element.setGreska(error);
            element.setErrorIspravnostiZapisa(false);
        }
    }

    private void dodavanjeRoditeljaUListu(List<Integer> listaRoditelja, Element element) {
        //dodavanje roditelja na listu
        if (element.getTip() == 0) {
            listaRoditelja.add(element.getSifra());
        }
    }

    private void postojiLiRoditelj(Element element, List<Integer> listaRoditelja) {
        //provjera je li jednostavan element sadržan u složenom koji postoji
        if (element.getTip() == 1) {
            if ((listaRoditelja.contains(element.getRoditelj()) == false)) {
                element.setErrorIspravnostiZapisa(false);
                element.setGreska("Roditelj ne postoji");
            }
        }

    }

    private void dohvatiKoordinateRoditelja(List<Element> listaElemenata, Element element) {
        //dohvaćanje koordinata roditelja za pojedini element u listi
        for (int i = 0; i < listaElemenata.size(); i++) {

            int roditelj = element.getRoditelj();
            int el1 = listaElemenata.get(i).getSifra();
            String[] koord = listaElemenata.get(i).getKoordinate();
            if (el1 == roditelj) {
                element.setKoordinateRoditelja(koord);
            }
        }
    }

    private void presjekSRoditeljem(Element element, String[] koordinate, String[] koordinateRoditelja, String presjek) {
        //provjeri presjeke samo za elemente koji su ispravni
        if (element.getErrorIspravnostiZapisa() == true) {

            int velicina = koordinate.length;
            koordinateRoditelja = element.getKoordinateRoditelja();

            //provjera siječe li dijete element roditelja
            if (velicina == 4 && (((Integer.parseInt(koordinate[0]) + Integer.parseInt(koordinateRoditelja[0])) >= Integer.parseInt(koordinateRoditelja[0]))
                    && ((Integer.parseInt(koordinate[0]) + Integer.parseInt(koordinateRoditelja[0])) < Integer.parseInt(koordinateRoditelja[2]))
                    && ((Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[0])) > Integer.parseInt(koordinateRoditelja[2])))) {
                presjek = " Siječe x os.";
                element.setPresjek(presjek);
            }

            if (velicina == 4 && (((Integer.parseInt(koordinate[1]) + Integer.parseInt(koordinateRoditelja[1])) >= Integer.parseInt(koordinateRoditelja[1]))
                    && ((Integer.parseInt(koordinate[1]) + Integer.parseInt(koordinateRoditelja[1])) < Integer.parseInt(koordinateRoditelja[3]))
                    && ((Integer.parseInt(koordinate[3]) + Integer.parseInt(koordinateRoditelja[1])) > Integer.parseInt(koordinateRoditelja[3])))) {
                presjek = presjek + " Siječe y os.";
                element.setPresjek(presjek);
            }

            if (velicina == 3 && (((Integer.parseInt(koordinate[0]) - Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[0])) < Integer.parseInt(koordinateRoditelja[0]))
                    && ((Integer.parseInt(koordinate[0]) + Integer.parseInt(koordinateRoditelja[0])) >= Integer.parseInt(koordinateRoditelja[0]))
                    || ((Integer.parseInt(koordinate[0]) + Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[0])) > Integer.parseInt(koordinateRoditelja[2]))
                    && ((Integer.parseInt(koordinate[0]) + Integer.parseInt(koordinateRoditelja[0])) <= Integer.parseInt(koordinateRoditelja[2])))) {
                presjek = " Siječe x os.";
                element.setPresjek(presjek);
            }

            if (velicina == 3 && (((Integer.parseInt(koordinate[1]) - Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[1])) < Integer.parseInt(koordinateRoditelja[1]))
                    && ((Integer.parseInt(koordinate[1]) + Integer.parseInt(koordinateRoditelja[1])) >= Integer.parseInt(koordinateRoditelja[1]))
                    || ((Integer.parseInt(koordinate[1]) + Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[1])) > Integer.parseInt(koordinateRoditelja[3]))
                    && ((Integer.parseInt(koordinate[1]) + Integer.parseInt(koordinateRoditelja[1])) <= Integer.parseInt(koordinateRoditelja[3])))) {
                presjek = presjek + " Siječe y os.";
                element.setPresjek(presjek);
            }

            // provjera siječe li poliedar granice
            for (int i = 0; i < (velicina / 2); i++) {
                if (velicina == 3 && (((Integer.parseInt(koordinate[2 * i]) + Integer.parseInt(koordinate[0])) > Integer.parseInt(koordinateRoditelja[2])))) {

                    presjek = " Siječe x os.";
                    element.setPresjek(presjek);
                }
            }

            for (int i = 0; i < (velicina / 2); i++) {
                if (velicina == 3 && (((Integer.parseInt(koordinate[(2 * i) + 1]) + Integer.parseInt(koordinate[1])) > Integer.parseInt(koordinateRoditelja[3])))) {

                    presjek = presjek + " Siječe y os.";
                    element.setPresjek(presjek);
                }
            }

        }
    }

    private void dodajNaIspravnuNeispravnuListu(Element element) {
        if (element.getErrorIspravnostiZapisa() == false) {
            listaNeispravnihElemenata.add(element);
        } else {
            listaIspravnihElemenata.add(element);
        }
    }

    private void kreirajComposite() {
        for (int i = 0; i < konacnaLista.size(); i++) {
            if (konacnaLista.get(i).getTip() == 0) {
                slozeniElement = new SlozeniElement(konacnaLista.get(i).getTip(), konacnaLista.get(i).getSifra(), konacnaLista.get(i).getRoditelj(), konacnaLista.get(i).getKoordinate(), konacnaLista.get(i).getBoja(), konacnaLista.get(i).getErrorIspravnostiZapisa(), konacnaLista.get(i).getGreska(), konacnaLista.get(i).getPresjek(), konacnaLista.get(i).getKoordinateRoditelja());
                listaSlozenihElemenata.add(slozeniElement);
                dodavanjeDjece(2);

            } else {
                jednostavniElement = new JednostavniElement(konacnaLista.get(i).getTip(), konacnaLista.get(i).getSifra(), konacnaLista.get(i).getRoditelj(), konacnaLista.get(i).getKoordinate(), konacnaLista.get(i).getBoja(), konacnaLista.get(i).getErrorIspravnostiZapisa(), konacnaLista.get(i).getGreska(), konacnaLista.get(i).getPresjek(), konacnaLista.get(i).getKoordinateRoditelja());
                dodavanjeDjece(1);
            }

        }
    }

    private void negativneKoordinate(String[] koordinate, Element element) {
        for (int i = 0; i < koordinate.length; i++) {
            if (Integer.parseInt(koordinate[i]) < 0 && Integer.parseInt(koordinate[i]) > 10000) {
                element.setErrorIspravnostiZapisa(false);
                element.setGreska("Neispravne koordinate");
            }
        }
    }

    private void neispravanTipElementa(Element element) {
        if (element.getTip() > 1) {
            element.setErrorIspravnostiZapisa(false);
            element.setGreska("Neispravan tip");
        }
    }

    private void povrsineBoja() {
        String boja = "";
        String[] koordinate;

        for (int i = 0; i < konacnaLista.size(); i++) {

            boja = konacnaLista.get(i).getBoja();
            int povrsina = 0;

            if (konacnaLista.get(i).getBoja().equals(boja) && konacnaLista.get(i).getKoordinate().length == 3 && konacnaLista.get(i).getPresjek().equals("")) {
                koordinate = konacnaLista.get(i).getKoordinate();
                //povrsina kruga
                povrsina = povrsina + (int) (Integer.parseInt(koordinate[2]) * Integer.parseInt(koordinate[2]) * 3.14);
                System.out.println(boja + " povrsina = " + povrsina);

            }

            if (konacnaLista.get(i).getBoja().equals(boja) && konacnaLista.get(i).getKoordinate().length == 4 && konacnaLista.get(i).getPresjek().equals("")) {
                koordinate = konacnaLista.get(i).getKoordinate();
                //povrsina pravokutnika
                povrsina = povrsina + (int) ((Integer.parseInt(koordinate[2]) - Integer.parseInt(koordinate[0])) * (Integer.parseInt(koordinate[3]) - Integer.parseInt(koordinate[1])));

                System.out.println(boja + " povrsina = " + povrsina);
            }

        }
        startMenu();
    }

}
