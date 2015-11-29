
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

        System.out.println("Ispravni zapisi");
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

    List<Element> listaElemenata = new ArrayList<Element>();
    
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

            String[] koordinate = values[3].split(",");

            //ispitivanje ispravnosti dužine polja i ispis samo podataka koji imaju ispravnu duzinu polja
            if ((values[0].length() == 1) && (values[1].length() == 5) && (values[2].length() == 5) && (values[4].length() == 7) && (errorIspravnostiZapisa == true)) {

                for (int i = 0; i <= koordinate.length - 1; i++) {
                    Integer.parseInt(koordinate[i]);
                }

                //System.out.println(Arrays.toString(koordinate));
            } else {
                errorIspravnostiZapisa = false;
            }

            //ispis neispravnog elementa 
               /* 
             if(errorIspravnostiZapisa == true){
             System.out.println("Neispravni zapisi");
             System.out.println(values[0] + " " +  values[1] + " " + values[2] + " " + values[3] + " " + values[4] + " " + errorIspravnostiZapisa);
             }
                       
             */

            if(Integer.parseInt(values[0]) == 0){
                IElement slozeniElement = new SlozeniElement(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), koordinate, values[4]);
            }
            else{
                IElement jednostavniElement = new JednostavniElement(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), koordinate, values[4]); 
            }
            
            
           // slozeniElement.add(jednostavniElement);
            
            
            Element element = new Element(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), koordinate, values[4], errorIspravnostiZapisa, error, testPoruka);
            listaElemenata.add(element);

            //provjera postojji li već element s istom šifrom
            if (sifre.contains(element.getSifra()) == false) {
                sifre.add(element.getSifra());
            } else {
                element.setErrorIspravnostiZapisa(false);
                error = "Već postoji element s istom šifrom";
                element.setGreska(error);
            }

            //provjera je li jednostavan element sadržan u složenom
            if (element.getTip() == 1) {
                for (int i = 0; i < listaElemenata.size() - 1; i++) {
                    if (listaElemenata.get(i).getTip() == 0 && listaElemenata.get(i).getSifra() == element.getRoditelj()) {
                        element.setGreska("Ispravan roditelj");
                    }
                }
            }

            //provjera je li prvi element ishodišni
           
            for(int i = 0; i < listaElemenata.size(); i++){
                if (listaElemenata.get(i).getSifra() == listaElemenata.get(i).getRoditelj() && listaElemenata.get(i).getTip() == 0) {
                    listaElemenata.get(i).setTestPoruka("Ishodišni element");
                }
                else {
                    listaElemenata.get(i).setTestPoruka("Nije ishodišni element");
                }
            }
                
            //provjera ima li više ishodišnih elemenata
            for (int i = 1; i < listaElemenata.size(); i++){
                if (listaElemenata.get(i).getSifra() == listaElemenata.get(i).getRoditelj()) {
                    listaElemenata.get(i).setTestPoruka("\t Opet Ishodišni element");
                    listaElemenata.get(i).setErrorIspravnostiZapisa(false);
                }
            }
            
            //ako je roditelj neispravan, i djeca su neispravna - napraviti provjeru
            // svaki element koji je false, a drugi elementi sadrze njega kao roditelja, i njima se mijenja stanje u false
           

            System.out.println(element.getSifra() + " " + element.getErrorIspravnostiZapisa() + " " + element.getGreska() + " " + element.getTestPoruka());
        }
    }

    private void dodajDatoteku() {
        System.out.println("Unesite naziv nove datoteke: ");
        
        //dohvaćanje imena nove datoteke
        Scanner reader = new Scanner(System.in);
        String novaDatoteka = reader.nextLine();
        
        this.datoteka = novaDatoteka;
        ucitajDatoteku();
        
        System.out.println(this.listaElemenata);
        
        startMenu();
        
    }

    private void pregledStanja() {
        for(int i=0; i < listaElemenata.size(); i++){
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
