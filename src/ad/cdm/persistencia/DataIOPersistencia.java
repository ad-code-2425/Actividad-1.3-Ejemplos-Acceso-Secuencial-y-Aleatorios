/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ad.cdm.persistencia;

import ad.cdm.model.Persona;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author mfernandez
 */
public class DataIOPersistencia implements IPersistencia {

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
                ex.printStackTrace();
                System.out.println("Ha ocurrido una excepción: " + ex.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Ha ocurrido una excepción: " + ex.getMessage());
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
            for (int i = 0; i < 9; i++) {
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
            ex.printStackTrace();
            System.out.println("Ha ocurrido una excepción: " + ex.getMessage());
        }
        return persona;
    }

}
