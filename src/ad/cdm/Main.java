/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ad.cdm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ad.cdm.model.Persona;
import ad.cdm.persistencia.DataIOPersistencia;
import ad.cdm.persistencia.IPersistencia;
import ad.cdm.persistencia.RandomAccessPersistencia;

/**
 *
 * @author mfernandez
 */
public class Main {

    private static String OUTPUT_FILE = "personaRandom.dat";

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {

        try {

            System.out.println("Introduzca el número del tipo de persistencia deseada");
            System.out.println("1: DataInput/Output");
            System.out.println("2: RandomAccess");
            // Enter data using BufferReader

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Reading data using readLine
            String numeroString = reader.readLine();

            int numero = Integer.valueOf(numeroString);

            Persona persona = new Persona(1, "12345678A", 18, 20000.65f);

            IPersistencia persistencia = crearPersistencia(numero);
            persistencia.escribirPersona(persona, OUTPUT_FILE);

            Persona personaRecuperada = persistencia.leerPersona(OUTPUT_FILE);
            System.out.println("Se ha recuperado: " + personaRecuperada);
        } catch (Exception ex) {

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
                throw new UnsupportedOperationException("No se reconoce el tipo de persisencia");

        }
        return persistencia;
    }

}
