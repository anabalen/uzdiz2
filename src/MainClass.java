
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


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
        try  {
            in = new BufferedReader(new FileReader(datoteka));
            System.out.println("Naziv datoteke: " + datoteka + "\n");
            //čitanje datoteke
            while((line = in.readLine()) != null)
            {
                //ispis datoteke
                //System.out.println(line);
                
                //odvajanje polja u pojedinom retku
                String[] values = line.split("\t");
                
                //ispitivanje ispravnosti dužine polja
                if((values[0].length() == 1) && 
                   (values[1].length() == 5) && 
                   (values[2].length() == 5) &&
                   (values[4].length() == 7)){
                    System.out.println("Ispravan zapis");
                } else {
                    System.out.println("Neispravan zapis");
                }
                
                System.out.println(values[0] + " " +  values[1] + " " + values[2] + " " + values[3] + " " + values[4]);
            }
        } catch (FileNotFoundException fnfex) {
            System.out.println("Datoteka nije pronađena");
            System.exit(0);
        } catch (IOException IOex) {
            System.out.println("Pogreška pri čitanju datoteke");
        }
        
       
       
   
    }

    
    
}
