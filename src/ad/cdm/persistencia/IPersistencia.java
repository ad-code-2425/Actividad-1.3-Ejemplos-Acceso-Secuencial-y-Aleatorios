
package ad.cdm.persistencia;

import ad.cdm.model.Persona;

/**
 *
 * @author mfernandez
 */
public interface IPersistencia {
    
    void escribirPersona(Persona persona, String ruta);
    Persona leerPersona(String ruta);

    
    
}
