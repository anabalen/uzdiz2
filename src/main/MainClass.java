package main;


import main.DatotekaHandler;

/**
 *
 * @author Ana-Marija
 */
public class MainClass {

   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String datoteka = args[0];
        DatotekaHandler handler = new DatotekaHandler(datoteka);
        handler.ucitajDatoteku();
        handler.startMenu();
    }
    
}
