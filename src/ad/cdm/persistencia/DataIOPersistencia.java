
package ad.cdm.persistencia;


import ad.cdm.model.Persona;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author mfernandez
 */
public class DataIOPersistencia implements IPersistencia {

    static Logger logger = Logger.getLogger(DataIOPersistencia.class.getName());

    public DataIOPersistencia() {
    
         try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("mylogging.properties"));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void escribirPersona(Persona persona, String ruta) {

        if (persona != null) {

            try (FileOutputStream fos = new FileOutputStream(ruta);
                    DataOutputStream dos = new DataOutputStream(fos);) {

                dos.writeLong(persona.getId());
                // Aquí se escribe el DNI de 2 formas (bastaría con una)
                // writeChars escribe texto como una secuencia de 2 bytes por carácter
                dos.writeChars(persona.getDni());
                // writeUFT escribe texto en una codificación UTF-8 ligeramente modificada,
                // precedida de 2 bytes que indican la longitud en bytes escritos
                dos.writeUTF(persona.getDni());
                dos.writeInt(persona.getEdad());
                dos.writeFloat(persona.getSalario());

            } catch (FileNotFoundException ex) {                
                logger.log(Level.SEVERE, "Ha ocurrido una excepción escribiendo persona relacionada con el fichero " + ruta, ex);
               
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Ha ocurrido una excepción escribiendo persona " + persona +
                 "relacionada  con I/O ", ex);
         
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Ha ocurrido una excepción escribiendo persona en " + ruta, ex);
              
            }
        }
    }

    @Override
    public Persona leerPersona(String ruta) {

        long id = 0;
        char caracter;
        StringBuilder sb = new StringBuilder();
        String dniUTF;
        int edad = 0;
        float salario = 0;
        Persona persona = null;

        try (
                FileInputStream fis = new FileInputStream(ruta);
                DataInputStream dis = new DataInputStream(fis);) {

            id = dis.readLong();
            // Para leer los datos escritos con writeChars, necesitamos saber la longitud
            // escrita
            for (int i = 0; i < Persona.DNI_LENGTH; i++) {
                caracter = dis.readChar();
                sb.append(caracter);
            }
            // Para leer los datos escritos con writeUTF, no es necesario conocer la
            // longitud
            dniUTF = dis.readUTF();
            edad = dis.readInt();
            salario = dis.readFloat();

            //Aquí habría que usar la variable que deseemos: dniUTF o sb
          //  persona = new Persona(id, dniUTF, edad, salario);
            persona = new Persona(id, sb.toString(), edad, salario);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Ha ocurrido una excepción leyendo persona relacionada con I/O en la ruta: " + ruta, ex);

        }
        catch (Exception ex) {
            logger.log(Level.SEVERE, "Ha ocurrido una excepción leyendo persona de ruta " + ruta , ex);
         
        }
        return persona;
    }

}
