
package ad.cdm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;   
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import ad.cdm.model.Persona;
import ad.cdm.persistencia.DataIOPersistencia;
import ad.cdm.persistencia.IPersistencia;
import ad.cdm.persistencia.RandomAccessPersistencia;

/**
 *
 * @author mfernandez
 */
public class Main {

    private static String OUTPUT_FILE = "";

    static Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        String numeroString = "";

        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("mylogging.properties"));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }

        try {

            System.out.println("Introduzca el número del tipo de persistencia deseada");
            System.out.println("1: DataInput/Output");
            System.out.println("2: RandomAccess");
            // Enter data using BufferReader

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Reading data using readLine
            numeroString = reader.readLine();

            int numero = Integer.valueOf(numeroString);

            Persona persona = new Persona(1, "12345678A", 18, 20000.65f);

            IPersistencia persistencia = crearPersistencia(numero);
            persistencia.escribirPersona(persona, OUTPUT_FILE);

            Persona personaRecuperada = persistencia.leerPersona(OUTPUT_FILE);
            System.out.println("Se ha recuperado: " + personaRecuperada);

        } catch (NumberFormatException ex) {
            logger.log(Level.INFO, "No se ha podido convertir a un número: " + numeroString, ex);
        } catch (IOException ioex) {
            logger.log(Level.SEVERE, "Ha ocurrido una excepción I/O:", ioex);

        } catch (Exception gex) {
            logger.log(Level.SEVERE, "Ha ocurrido una excepción", gex);

        }

    }

    private static IPersistencia crearPersistencia(int type) {
        IPersistencia persistencia = null;
        switch (type) {
            case 1:
                persistencia = new DataIOPersistencia();
                OUTPUT_FILE = "personaDataIO.dat";
                break;

            case 2:
                persistencia = new RandomAccessPersistencia();
                OUTPUT_FILE = "personaRandom.dat";
                break;

            default:
                throw new UnsupportedOperationException("No se reconoce el tipo de persistencia");

        }
        return persistencia;
    }

}
