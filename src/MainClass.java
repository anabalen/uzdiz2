
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
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
