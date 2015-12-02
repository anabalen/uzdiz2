
import Composite.IElement;
import Composite.JednostavniElement;
import Composite.SlozeniElement;
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

        //provjera je li broj u rasponu i ispis pogreške
        if (n > 5) {
            System.out.println("Neispravno. Unesite broj u rasponu 1-5:\n");
            n = reader.nextInt();
        }

        odabir();
    }

    private void odabir() {
        switch (n) {
            case 1:
                System.out.println("Odabir 1.");
                pregledStanja();
                //startMenu();
                break;
            case 2:
                System.out.println("Odabir 2.");
                startMenu();
                break;
            case 3:
                System.out.println("Odabir 3.");
                startMenu();
                break;
            case 4:
                System.out.println("Odabir 4.");
                startMenu();
                break;
            case 5:
                System.out.println("Odabir 5.");
                dodajDatoteku();
                break;
            case 0:
                System.out.println("Odabir 0. Kraj programa.");
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
        //List<Element> listaElemenata = new ArrayList<Element>();
        List<Integer> sifre = new ArrayList<>();

        while ((line = fajl.readLine()) != null) {
            //odvajanje polja u pojedinom retku
            String[] values = line.split("\t");
            Boolean errorIspravnostiZapisa = true;
            String error = "";
            String testPoruka = "";
            String[] koordinateRoditelja = null;

            String[] koordinate = values[3].split(",");

            Element element = new Element(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), koordinate, values[4], errorIspravnostiZapisa, error, testPoruka, koordinateRoditelja);
            listaElemenata.add(element);

            //ispitivanje ispravnosti dužine polja
            if ((values[0].length() == 1) && (values[1].length() == 5) && (values[2].length() == 5) && (values[4].length() == 7) && (errorIspravnostiZapisa == true)) {

            } else {
                element.setGreska("neispravna dužina polja");
                element.setErrorIspravnostiZapisa(false);
            }
            
            
            //ako je neispravan roditelj - onda je i dijete
            for(int i=0; i<listaNeispravnihElemenata.size(); i++){
                if(element.getRoditelj() == listaNeispravnihElemenata.get(i).getSifra()){
                    element.setGreska("Neispravan roditelj");
                    element.setErrorIspravnostiZapisa(false);
                }
            }            

            //provjera je li broj koordinata 3, 4 ili 2i
            if (element.getKoordinate().length == 3 || element.getKoordinate().length == 4 || (element.getKoordinate().length % 2) == 0) {
                //System.out.println("Ispravan broj koordinata");
            } else {
                error = "Neispravan broj koordinata";
                element.setGreska(error);
                element.setErrorIspravnostiZapisa(false);
            }

            if (Integer.parseInt(values[0]) == 0) {
                slozeniElement = new SlozeniElement(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), koordinate, values[4]);
                listaSlozenihElemenata.add(slozeniElement);
                // System.out.println(slozeniElement);
                dodavanjeDjece(2);

            } else {
                jednostavniElement = new JednostavniElement(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), koordinate, values[4]);
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

            if ((element.getTip() == 1) && (listaRoditelja.contains(element.getRoditelj())== false)) {
                element.setErrorIspravnostiZapisa(false);
            }

            //postoji li roditelj
          

            //provjera je li prvi element ishodišni
            for (int i = 0; i < listaElemenata.size(); i++) {
                if (listaElemenata.get(i).getSifra() == listaElemenata.get(i).getRoditelj() && listaElemenata.get(i).getTip() == 0) {
                    listaElemenata.get(i).setTestPoruka("Ishodišni element");
                } else {
                    listaElemenata.get(i).setTestPoruka("Nije ishodišni element");
                }
            }

            //provjera ima li više ishodišnih elemenata
            for (int i = 1; i < listaElemenata.size(); i++) {
                if (listaElemenata.get(i).getSifra() == listaElemenata.get(i).getRoditelj()) {
                    listaElemenata.get(i).setTestPoruka("\t Opet Ishodišni element");
                    listaElemenata.get(i).setErrorIspravnostiZapisa(false); break;
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

            //provjera je su li koordinate elementa relativne unutar roditeljskog elementa
            int velicina = koordinate.length;
            koordinateRoditelja = element.getKoordinateRoditelja();
/*
            //provjera je li dijete pravokutnik unutar roditeljskog elementa i jesu li tocke elementa ispravno zadane da tvore pravokutnik
            if (velicina == 4 && ((((Integer.parseInt(koordinate[0]) >= Integer.parseInt(koordinateRoditelja[0]))
                    && (Integer.parseInt(koordinate[0]) <= Integer.parseInt(koordinateRoditelja[2]))
                    && (Integer.parseInt(koordinate[2]) >= Integer.parseInt(koordinateRoditelja[0]))
                    && (Integer.parseInt(koordinate[2]) <= Integer.parseInt(koordinateRoditelja[2]))
                    || (Integer.parseInt(koordinate[1]) >= Integer.parseInt(koordinateRoditelja[1]))
                    && (Integer.parseInt(koordinate[1]) <= Integer.parseInt(koordinateRoditelja[3]))
                    && (Integer.parseInt(koordinate[3]) >= Integer.parseInt(koordinateRoditelja[1]))
                    && (Integer.parseInt(koordinate[3]) <= Integer.parseInt(koordinateRoditelja[3]))))
                    && ((Integer.parseInt(koordinate[0]) != Integer.parseInt(koordinate[2]))
                    && (Integer.parseInt(koordinate[1]) != Integer.parseInt(koordinate[3]))))) {
                //System.out.println("pravokutnik");
            } else if (velicina == 3 && (((Integer.parseInt(koordinate[0]) + (Integer.parseInt(koordinate[2]))) >= Integer.parseInt(koordinateRoditelja[0]))
                    && ((Integer.parseInt(koordinate[0]) - (Integer.parseInt(koordinate[2]))) <= Integer.parseInt(koordinateRoditelja[2]))
                    || ((Integer.parseInt(koordinate[1]) + (Integer.parseInt(koordinate[2]))) >= Integer.parseInt(koordinateRoditelja[1]))
                    && ((Integer.parseInt(koordinate[1]) - (Integer.parseInt(koordinate[2]))) <= Integer.parseInt(koordinateRoditelja[3])))) {
                //System.out.println("krug");
            } else {
                for (int i = 0; i > 2; i++) {
                    if (velicina == 2 * i && ((Integer.parseInt(koordinate[i]) >= Integer.parseInt(koordinateRoditelja[0]))
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

                //System.out.println("Nije unutar roditelja");
                error = "Nije unutar roditelja";
                element.setGreska(error);
                element.setErrorIspravnostiZapisa(false);
            }

 
            //provjera siječe li dijete element roditelja
            if (velicina == 4 && ((Integer.parseInt(koordinate[0]) < Integer.parseInt(koordinateRoditelja[0]))
                    && (Integer.parseInt(koordinate[2]) > Integer.parseInt(koordinateRoditelja[0]))
                    && (Integer.parseInt(koordinate[2]) <= Integer.parseInt(koordinateRoditelja[2]))
                    || (Integer.parseInt(koordinate[0]) >= Integer.parseInt(koordinateRoditelja[0]))
                    && (Integer.parseInt(koordinate[0]) < Integer.parseInt(koordinateRoditelja[2]))
                    && (Integer.parseInt(koordinate[2]) > Integer.parseInt(koordinateRoditelja[2])))) {
                System.out.println("siječe x os");
            }

            if (velicina == 4 && ((Integer.parseInt(koordinate[1]) < Integer.parseInt(koordinateRoditelja[1]))
                    && (Integer.parseInt(koordinate[3]) > Integer.parseInt(koordinateRoditelja[1]))
                    && (Integer.parseInt(koordinate[3]) <= Integer.parseInt(koordinateRoditelja[3]))
                    || (Integer.parseInt(koordinate[1]) >= Integer.parseInt(koordinateRoditelja[1]))
                    && (Integer.parseInt(koordinate[1]) < Integer.parseInt(koordinateRoditelja[3]))
                    && (Integer.parseInt(koordinate[3]) > Integer.parseInt(koordinateRoditelja[3])))) {
                System.out.println("siječe y os");
            }

            if (velicina == 4 && ((Integer.parseInt(koordinate[0]) < Integer.parseInt(koordinateRoditelja[0]))
                    && (Integer.parseInt(koordinate[2]) > Integer.parseInt(koordinateRoditelja[0]))
                    && (Integer.parseInt(koordinate[2]) <= Integer.parseInt(koordinateRoditelja[2]))
                    && (Integer.parseInt(koordinate[1]) < Integer.parseInt(koordinateRoditelja[1]))
                    && (Integer.parseInt(koordinate[3]) > Integer.parseInt(koordinateRoditelja[1]))
                    && (Integer.parseInt(koordinate[3]) <= Integer.parseInt(koordinateRoditelja[3]))
                    || (Integer.parseInt(koordinate[0]) >= Integer.parseInt(koordinateRoditelja[0]))
                    && (Integer.parseInt(koordinate[0]) < Integer.parseInt(koordinateRoditelja[2]))
                    && (Integer.parseInt(koordinate[2]) <= Integer.parseInt(koordinateRoditelja[2]))
                    && (Integer.parseInt(koordinate[1]) < Integer.parseInt(koordinateRoditelja[1]))
                    && (Integer.parseInt(koordinate[3]) > Integer.parseInt(koordinateRoditelja[1]))
                    && (Integer.parseInt(koordinate[3]) <= Integer.parseInt(koordinateRoditelja[3]))
                    || (Integer.parseInt(koordinate[0]) < Integer.parseInt(koordinateRoditelja[0]))
                    && (Integer.parseInt(koordinate[2]) > Integer.parseInt(koordinateRoditelja[0]))
                    && (Integer.parseInt(koordinate[2]) <= Integer.parseInt(koordinateRoditelja[2]))
                    && (Integer.parseInt(koordinate[1]) >= Integer.parseInt(koordinateRoditelja[1]))
                    && (Integer.parseInt(koordinate[1]) < Integer.parseInt(koordinateRoditelja[3]))
                    && (Integer.parseInt(koordinate[3]) > Integer.parseInt(koordinateRoditelja[3]))
                    || (Integer.parseInt(koordinate[0]) >= Integer.parseInt(koordinateRoditelja[0]))
                    && (Integer.parseInt(koordinate[0]) < Integer.parseInt(koordinateRoditelja[3]))
                    && (Integer.parseInt(koordinate[2]) > Integer.parseInt(koordinateRoditelja[2]))
                    && (Integer.parseInt(koordinate[1]) >= Integer.parseInt(koordinateRoditelja[1]))
                    && (Integer.parseInt(koordinate[1]) < Integer.parseInt(koordinateRoditelja[3]))
                    && (Integer.parseInt(koordinate[3]) > Integer.parseInt(koordinateRoditelja[3])))) {
                System.out.println("siječe i x i y os");
            }

*/       
            /* 
            
             for(int i = 0; i < listaRoditelja.size(); i++){
             if(listaRoditelja.get(i).getErrorIspravnostiZapisa()==false){
             listaRoditelja.remove(i);
                    
             }
            }
              */
            
            if(element.getErrorIspravnostiZapisa()==false){
                listaNeispravnihElemenata.add(element);
            }
            else{
                listaIspravnihElemenata.add(element);
            }
            
            //System.out.println(element.getSifra() + " " + element.getErrorIspravnostiZapisa() + " " + element.getGreska() + " " + element.getTestPoruka());

        }

         System.out.println( "Lista ispravnih elemenata: " + "\n" +
                                "=========================================================");
        for(int i = 0; i < listaIspravnihElemenata.size(); i++){
            System.out.println(listaIspravnihElemenata.get(i).getSifra() + "\t" +
                                listaIspravnihElemenata.get(i).getRoditelj() + "\t" +
                                Arrays.toString(listaIspravnihElemenata.get(i).getKoordinate()) + "\t" +
                                listaIspravnihElemenata.get(i).getBoja());
        }
        
         System.out.println( "\n Lista neispravnih elemenata: " + "\n" +
                                "=========================================================");
        for(int i = 0; i < listaNeispravnihElemenata.size(); i++){
            System.out.println( listaNeispravnihElemenata.get(i).getGreska() + "\t" +
                                listaNeispravnihElemenata.get(i).getSifra() + "\t" +
                                listaNeispravnihElemenata.get(i).getRoditelj() + "\t" +
                                Arrays.toString(listaNeispravnihElemenata.get(i).getKoordinate()) + "\t" +
                                listaNeispravnihElemenata.get(i).getBoja()
                                );
        }
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

            //System.out.println("Struktura roditelja s djecom: " + slozElem.getElementi());
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
        for (int i = 0; i < listaElemenata.size(); i++) {
            Element elem = listaElemenata.get(i);
            System.out.println(elem.getTip() + " "
                    + elem.getSifra() + " "
                    + elem.getRoditelj() + " "
                    + Arrays.toString(elem.getKoordinate()) + " "
                    + elem.getBoja());
        }

        startMenu();
    }

}
