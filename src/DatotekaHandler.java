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

    private String datoteka;
    public int n;
    List<Element> listaElemenata = new ArrayList<>();
    List<Integer> listaRoditelja = new ArrayList<>();
    List<Element> listaElemenataIspravnihRoditelja = new ArrayList<>();
    List<Element> listaIspravnihElemenata = new ArrayList<>();
    List<Element> listaNeispravnihElemenata = new ArrayList<>();
    List<IElement> listaSlozenihElemenata = new ArrayList<>();
    IElement slozeniElement = null;
    IElement jednostavniElement = null;
    List<Element> konacnaLista;
    
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
        System.out.println("6 - vraćanje stanja");
        System.out.println("0 - izlaz iz programa\n");
        System.out.println("=================================\n");

        Scanner reader = new Scanner(System.in);
        System.out.println("Vaš odabir: ");
        n = reader.nextInt();
        

        //provjera je li broj u rasponu i ispis pogreške
        if (n > 6) {
            System.out.println("Neispravno. Unesite broj u rasponu 1-6:\n");
            n = reader.nextInt();
        }

        odabir();
    }

    private void odabir() {
        switch (n) {
            case 1:
                pregledStanja();
                break;
            case 2:
                startMenu();
                break;
            case 3:
                promjenaStatusa();
                break;
            case 4:
                startMenu();
                break;
            case 5:
                dodajDatoteku();
                break;
            case 6:
                povratStanja();
                break;
            case 0:
                System.out.println("Kraj programa.");
                System.exit(n);
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

            //ispitivanje ispravnosti dužine polja
            if ((values[0].length() == 1) && (values[1].length() == 5) && (values[2].length() == 5) && (values[4].length() == 7) && (errorIspravnostiZapisa == true)) {

            } else {
                element.setGreska("neispravna dužina polja");
                element.setErrorIspravnostiZapisa(false);
            }
            
            provjeraIspravnostiRoditelja(element);
            
            

            //provjera je li broj koordinata 3, 4 ili 2i
            if (element.getKoordinate().length == 3 || element.getKoordinate().length == 4 || (element.getKoordinate().length % 2) == 0) {
                //System.out.println("Ispravan broj koordinata");
            } else {
                error = "Neispravan broj koordinata";
                element.setGreska(error);
                element.setErrorIspravnostiZapisa(false);
            }
            

            if (Integer.parseInt(values[0]) == 0) {
                slozeniElement = new SlozeniElement(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), koordinate, values[4], errorIspravnostiZapisa, error, koordinateRoditelja);
                listaSlozenihElemenata.add(slozeniElement);
                // System.out.println(slozeniElement);
                dodavanjeDjece(2);

            } else {
                jednostavniElement = new JednostavniElement(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), koordinate, values[4], errorIspravnostiZapisa, error, koordinateRoditelja);
                dodavanjeDjece(1);
            }

            //provjera postoji li već element s istom šifrom
            if (sifre.contains(element.getSifra()) == false) {
                sifre.add(element.getSifra());
            } else {
                element.setErrorIspravnostiZapisa(false);
                error = "Već postoji element s istom šifrom";
                element.setGreska(error);
                element.setErrorIspravnostiZapisa(false);
            }

            //dodavanje roditelja na listu
            if (element.getTip() == 0) {
                listaRoditelja.add(element.getSifra());
            }

            //provjera je li jednostavan element sadržan u složenom koji postoji
            if (element.getTip() == 1) {
                if ((listaRoditelja.contains(element.getRoditelj()) == false)) {
                    element.setErrorIspravnostiZapisa(false);
                    element.setGreska("Roditelj ne postoji");
                }
            }

            //dohvaćanje koordinata roditelja za pojedini element u listi
            for (int i = 0; i < listaElemenata.size(); i++) {

                int roditelj = element.getRoditelj();
                int el1 = listaElemenata.get(i).getSifra();
                String[] koord = listaElemenata.get(i).getKoordinate();
                if (el1 == roditelj) {
                    // System.out.println(Arrays.toString(koord));
                    element.setKoordinateRoditelja(koord);
                }
            }

            //provjera ima li više ishodišnih elemenata
            for (int i = 1; i < listaElemenata.size(); i++) {
                if (listaElemenata.get(i).getSifra() == listaElemenata.get(i).getRoditelj()) {
                    //listaElemenata.get(i).setTestPoruka("\t Opet Ishodišni element");
                    //listaElemenata.get(i).setErrorIspravnostiZapisa(false); 
                }
            }

            //provjeri presjeke samo za elemente koji su ispravni
            if (element.getErrorIspravnostiZapisa() == true) {

                int velicina = koordinate.length;
                koordinateRoditelja = element.getKoordinateRoditelja();

                //provjera siječe li dijete element roditelja
                if (velicina == 4 && (((Integer.parseInt(koordinate[0]) + Integer.parseInt(koordinateRoditelja[0])) < Integer.parseInt(koordinateRoditelja[0]))
                        && ((Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[0])) > Integer.parseInt(koordinateRoditelja[0]))
                        && ((Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[0])) <= Integer.parseInt(koordinateRoditelja[2]))
                        || ((Integer.parseInt(koordinate[0]) + Integer.parseInt(koordinateRoditelja[0])) >= Integer.parseInt(koordinateRoditelja[0]))
                        && ((Integer.parseInt(koordinate[0]) + Integer.parseInt(koordinateRoditelja[0])) < Integer.parseInt(koordinateRoditelja[2]))
                        && ((Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[0])) > Integer.parseInt(koordinateRoditelja[2])))) {
                    //System.out.println(element.getSifra() + " siječe x os");
                    presjek = " Siječe x os.";
                    element.setPresjek(presjek);
                }

                if (velicina == 4 && (((Integer.parseInt(koordinate[1]) + Integer.parseInt(koordinateRoditelja[1])) < Integer.parseInt(koordinateRoditelja[1]))
                        && ((Integer.parseInt(koordinate[3]) + Integer.parseInt(koordinateRoditelja[1])) > Integer.parseInt(koordinateRoditelja[1]))
                        && ((Integer.parseInt(koordinate[3]) + Integer.parseInt(koordinateRoditelja[1])) <= Integer.parseInt(koordinateRoditelja[3]))
                        || ((Integer.parseInt(koordinate[1]) + Integer.parseInt(koordinateRoditelja[1])) >= Integer.parseInt(koordinateRoditelja[1]))
                        && ((Integer.parseInt(koordinate[1]) + Integer.parseInt(koordinateRoditelja[1])) < Integer.parseInt(koordinateRoditelja[3]))
                        && ((Integer.parseInt(koordinate[3]) + Integer.parseInt(koordinateRoditelja[1])) > Integer.parseInt(koordinateRoditelja[3])))) {
                    //System.out.println(element.getSifra() + " siječe y os");
                    presjek = presjek + " Siječe y os.";
                    element.setPresjek(presjek);
                }

                if (velicina == 4) {
                    if ((Integer.parseInt(koordinate[0]) != Integer.parseInt(koordinate[2]))
                            && (Integer.parseInt(koordinate[1]) != Integer.parseInt(koordinate[3]))) {

                    } else {
                        element.setErrorIspravnostiZapisa(false);
                        element.setGreska("Nije geometrijski lik");
                    }
                }

                if (velicina == 3 && (((Integer.parseInt(koordinate[0]) - Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[0])) < Integer.parseInt(koordinateRoditelja[0]))
                        && ((Integer.parseInt(koordinate[0]) + Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[0])) > Integer.parseInt(koordinateRoditelja[0]))
                        && ((Integer.parseInt(koordinate[0]) + Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[0])) <= Integer.parseInt(koordinateRoditelja[2]))
                        || ((Integer.parseInt(koordinate[0]) + Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[0])) > Integer.parseInt(koordinateRoditelja[2]))
                        && ((Integer.parseInt(koordinate[0]) - Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[0])) < Integer.parseInt(koordinateRoditelja[2]))
                        && ((Integer.parseInt(koordinate[0]) - Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[0])) <= Integer.parseInt(koordinateRoditelja[0])))) {
                //System.out.println(element.getSifra() + " siječe x os");

                    presjek = " Siječe x os.";
                    element.setPresjek(presjek);
                }

                if (velicina == 3 && (((Integer.parseInt(koordinate[1]) - Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[1])) < Integer.parseInt(koordinateRoditelja[1]))
                        && ((Integer.parseInt(koordinate[1]) + Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[1])) > Integer.parseInt(koordinateRoditelja[1]))
                        && ((Integer.parseInt(koordinate[1]) + Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[1])) <= Integer.parseInt(koordinateRoditelja[3]))
                        || ((Integer.parseInt(koordinate[1]) + Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[1])) > Integer.parseInt(koordinateRoditelja[3]))
                        && ((Integer.parseInt(koordinate[1]) - Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[1])) < Integer.parseInt(koordinateRoditelja[3]))
                        && ((Integer.parseInt(koordinate[1]) - Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinateRoditelja[1])) >= Integer.parseInt(koordinateRoditelja[1])))) {
               // System.out.println(element.getSifra() + " siječe y os");

                    presjek = presjek + " Siječe y os.";
                    element.setPresjek(presjek);
                }

                // provjera siječe li poliedar granice
                for (int i = 0; i > 2; i++) {
                    if (velicina == 2 * i && (((Integer.parseInt(koordinate[0]) + Integer.parseInt(koordinate[0])) < Integer.parseInt(koordinateRoditelja[0]))
                            || ((Integer.parseInt(koordinate[2]) + Integer.parseInt(koordinate[0])) < Integer.parseInt(koordinateRoditelja[0]))
                            || ((Integer.parseInt(koordinate[i + 1]) + Integer.parseInt(koordinate[0])) < Integer.parseInt(koordinateRoditelja[0]))
                            || ((Integer.parseInt(koordinate[4]) + Integer.parseInt(koordinate[0])) < Integer.parseInt(koordinateRoditelja[0]))
                            || (Integer.parseInt(koordinate[i]) <= Integer.parseInt(koordinateRoditelja[2]))
                            || (Integer.parseInt(koordinate[i + 2]) >= Integer.parseInt(koordinateRoditelja[0]))
                            || (Integer.parseInt(koordinate[i + 2]) <= Integer.parseInt(koordinateRoditelja[2]))
                            || (Integer.parseInt(koordinate[i + 1]) >= Integer.parseInt(koordinateRoditelja[1]))
                            || (Integer.parseInt(koordinate[i + 1]) <= Integer.parseInt(koordinateRoditelja[3]))
                            || (Integer.parseInt(koordinate[i + 3]) >= Integer.parseInt(koordinateRoditelja[1]))
                            || (Integer.parseInt(koordinate[i + 3]) <= Integer.parseInt(koordinateRoditelja[3])))) {

                        //System.out.println("poliedar");
                    }
                }

            }
            
          //  dodavanjeIspravnojNeispravnojListi(element);
            if (element.getErrorIspravnostiZapisa() == false) {
                listaNeispravnihElemenata.add(element);
            } else {
                listaIspravnihElemenata.add(element);
            }
            

            //System.out.println(element.getSifra() + " " + element.getErrorIspravnostiZapisa() + " " + element.getGreska() + " " + element.getTestPoruka());
        }

        ispisiListe();
        
        konacnaLista = listaIspravnihElemenata;
        //spremimo stanje listeElemenata za Memento svaki put kada se promijeni lista
        // ************************************************************************************************
        //ne mogu u listu stanja spremiti listu elemenata
         originator.setState(konacnaLista);
         careTaker.add(originator.saveStateToMemento());
        
         for(int i =0; i<originator.getState().size(); i++){
         System.out.println("First saved State: " + originator.getState().get(i).getSifra());}

        
    }

    private void dodavanjeDjece(int n) {
        for (int i = 0; i < listaSlozenihElemenata.size(); i++) {
            SlozeniElement slozElem = (SlozeniElement) listaSlozenihElemenata.get(i);

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

            List<IElement> test = slozElem.getElementi();
             for(int j=0;j<test.size();j++){
             test.get(j).getRoditelj(); // getat što god
             //System.out.println(slozElem.getSifra()+"   "+test.get(j).getSifra() + "    "+ test.get(j).getRoditelj() + "    " + Arrays.toString(test.get(j).getKoordinate()));
             }
             
             
            
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
        for (int i = 0; i < listaIspravnihElemenata.size(); i++) {
            System.out.println(listaIspravnihElemenata.get(i).getSifra() + "\t"
                    + listaIspravnihElemenata.get(i).getRoditelj() + "\t"
                    + Arrays.toString(listaIspravnihElemenata.get(i).getKoordinate()) + "\t"
                    + listaIspravnihElemenata.get(i).getBoja() + "\t"
                    + listaIspravnihElemenata.get(i).getPresjek());
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
            if(trenutniElement.getSifra()==sifraZaPromjenu){
                if(stanje==0){
                    context.setState(sakrij);
                    Element noviElement = context.doAction(trenutniElement);
                    
                     listaIspravnihElemenata.remove(trenutniElement);
                     dodavanjeIspravnojNeispravnojListi(noviElement);
  
                    
                }else{
                    context.setState(prikazi);
                    Element noviElement = context.doAction(trenutniElement);
                   listaNeispravnihElemenata.remove(trenutniElement);
                    dodavanjeIspravnojNeispravnojListi(noviElement);
                }

            }
                provjeraIspravnostiRoditelja(listaElemenata.get(i));
                //System.out.println(listaElemenata.get(i).getSifra() + " " + listaElemenata.get(i).getErrorIspravnostiZapisa());
        }
       
        ispisiListe();
        startMenu();
    }
    
    
    
    private void povratStanja() {
        
        // povrat stanja s Mementom na prvo ucitano iz datoteke
       
        originator.getStateFromMemento(careTaker.get(0));
      /* 
        for(int i =0; i<originator.getState().size(); i++){
        System.out.println("First saved State: " + originator.getState().get(i).getSifra());}
        */
        startMenu();
    }

    private void ispisiListe() {
        

        System.out.println("\n Lista neispravnih elemenata: " + "\n"
                + "=========================================================");
        for (int i = 0; i < listaNeispravnihElemenata.size(); i++) {
                    System.out.println(listaNeispravnihElemenata.get(i).getSifra() + "\t"
                    + listaNeispravnihElemenata.get(i).getRoditelj() + "\t"
                    + Arrays.toString(listaNeispravnihElemenata.get(i).getKoordinate()) + "\t"
                    + listaNeispravnihElemenata.get(i).getBoja() + "\t" +
                     listaNeispravnihElemenata.get(i).getGreska()
            );
        }
    }

    private void dodavanjeIspravnojNeispravnojListi(Element element) {
        if (element.getErrorIspravnostiZapisa() == false) {
                listaNeispravnihElemenata.add(element);
            } else {
                listaIspravnihElemenata.add(element);
            }
        
         
         System.out.println("**********************");
         System.out.println(konacnaLista.size());
         System.out.println("**********************");
    }

    private void provjeraIspravnostiRoditelja(Element element) {
        //ako je neispravan roditelj - onda je i dijete
            for (int i = 0; i < listaNeispravnihElemenata.size(); i++) {
                if (element.getRoditelj() == listaNeispravnihElemenata.get(i).getSifra()) {
                    element.setGreska("Neispravan roditelj");
                    listaIspravnihElemenata.remove(element);
                    listaNeispravnihElemenata.add(element);
                }
            }
    }
}
