package Contratos;

import java.util.List;
import Entidades.Curso;

/**
 * Contrato de Lectura. Protege al modelo de modificaciones desde la Vista.
 */
public interface IModeloInscripcion {
    List<Curso> getCursosDisponibles();
    List<Curso> getCursosInscritos();
    Double getTotal();
    boolean isFinalizada();
    void suscribir(IVista v);
}
