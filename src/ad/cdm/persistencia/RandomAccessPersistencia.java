
package ad.cdm.persistencia;

import ad.cdm.model.Persona;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author mfernandez
 */
public class RandomAccessPersistencia implements IPersistencia {
    static Logger logger = Logger.getLogger(RandomAccessPersistencia.class.getName());

    public RandomAccessPersistencia() {
        super();
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("mylogging.properties"));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void escribirPersona(Persona persona, String ruta) {

        try (

                // Abrimos en modo lectura/escritura
                RandomAccessFile raf = new RandomAccessFile(ruta, "rw");) {
            raf.writeLong(persona.getId());
            StringBuilder sb = new StringBuilder(persona.getDni());
            // Establecemos la longitud del StringBuilder en número de caracteres para que
            // se rellene de caracteres null si la cadena fuese más pequeña que el espacio
            // reservado para este campo
            sb.setLength(Persona.DNI_LENGTH);
            // Aquí hay que usar writeChars porque writeUTF puede traducir cada carácter a
            // 1, 2 o 3 bytes y
            // en RandomAccessFile se necesitan registros de longitud fija.
            raf.writeChars(sb.toString());

            raf.writeInt(persona.getEdad());
            raf.writeFloat(persona.getSalario());

        } catch (FileNotFoundException ex) {
            logger.log(Level.SEVERE, "Ha ocurrido una excepción escribiendo persona relacionada con el fichero " + ruta,
                    ex);

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Ha ocurrido una excepción escribiendo persona " + persona +
                    "relacionada  con I/O ", ex);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ha ocurrido una excepción escribiendo persona en " + ruta, ex);

        }
    }

    @Override
    public Persona leerPersona(String ruta) {

        long id = 0;
        String dni = "";
        int edad = 0;
        float salario = 0;
        StringBuilder sb = new StringBuilder();
        Persona persona = null;
        try (
                RandomAccessFile raf = new RandomAccessFile(ruta, "r");) {

            id = raf.readLong();
            for (int i = 0; i <= Persona.DNI_LENGTH-1; i++) {
                sb.append(raf.readChar());
            }

            dni = sb.toString();

            edad = raf.readInt();
            salario = raf.readFloat();

            persona = new Persona(id, dni, edad, salario);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ha ocurrido una excepción leyendo persona de ruta " + ruta, ex);

        }
        return persona;

    }

}
