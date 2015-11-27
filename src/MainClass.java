
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
public class MainClass {

    public static int n;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String datoteka = args[0];
        
        ucitajDatoteku(datoteka);
        startMenu();
    }

    private static void startMenu() {
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
        if (n>5){
        System.out.println("Neispravno. Unesite broj u rasponu 1-5:\n");
        n = reader.nextInt();
        }
        
        odabir();
    }

    private static void odabir() {
        switch(n){
            case 1:
                System.out.println("Odabir 1.");
                startMenu();
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
                startMenu();
                break;    
            case 0:
                System.out.println("Odabir 0. Kraj programa.");
                System.exit(n);
                break;
                   
        }
    }

    private static void ucitajDatoteku(String datoteka) {
        BufferedReader in;
        String line;
        System.out.println("Ispravni zapisi");
        try  {
           in = new BufferedReader(new FileReader(datoteka));
            System.out.println("Naziv datoteke: " + datoteka + "\n");
            //čitanje datoteke
            List<Element> listaElemenata = new ArrayList<Element>();
            List<Integer> koordinateElementa = new ArrayList<>();
            
            while((line = in.readLine()) != null)
            {
                //ispis datoteke
                //System.out.println(line);
                
                //odvajanje polja u pojedinom retku
                String[] values = line.split("\t");
                Boolean errorIspravnostiZapisa = false;
                
                String[] koordinate = values[3].split(",");
             
                //ispitivanje ispravnosti dužine polja i ispis samo podataka koji imaju ispravnu duzinu polja
                if((values[0].length() == 1) && (values[1].length() == 5) && (values[2].length() == 5) && (values[4].length() == 7) && (errorIspravnostiZapisa == false)){
                   
                    for(int i = 0; i<=koordinate.length-1; i++){
                    //System.out.println(koordinate[i]);
                    koordinateElementa.add(Integer.parseInt(koordinate[i]));}
                    System.out.println(koordinateElementa);
                    //System.out.println(values[0] + " " +  values[1] + " " + values[2] + " " + values[3] + " " + values[4]);
               
                } else {
                    errorIspravnostiZapisa = true;
                }
                 
               //ispis neispravnog elementa 
               /* 
                if(errorIspravnostiZapisa == true){
                    System.out.println("Neispravni zapisi");
                    System.out.println(values[0] + " " +  values[1] + " " + values[2] + " " + values[3] + " " + values[4] + " " + errorIspravnostiZapisa);
                }
                       
                */
               Element element = new Element(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), koordinateElementa, values[4]);
               listaElemenata.add(element);
                
            }
           
            //ispis elementa iz liste elementa
            System.out.println(listaElemenata.get(1).getKoordinate());
            
        } catch (FileNotFoundException fnfex) {
            System.out.println("Datoteka nije pronađena");
            System.exit(0);
        } catch (IOException IOex) {
            System.out.println("Pogreška pri čitanju datoteke");
        }
        
       
       
   
    }

    
    
}
