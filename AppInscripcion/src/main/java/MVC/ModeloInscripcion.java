package MVC;

import java.util.ArrayList;
import java.util.List;
import Entidades.CatalogoCursos;
import Entidades.Curso;
import Contratos.IModeloInscripcion;
import Contratos.IVista;

public class ModeloInscripcion implements IModeloInscripcion {
    private CatalogoCursos catalogo;
    private List<Curso> cursosInscritos;
    private Double total;
    private boolean finalizada;
    private List<IVista> observadores;

    public ModeloInscripcion(CatalogoCursos catalogo) {
        this.catalogo = catalogo;
        this.cursosInscritos = new ArrayList<>();
        this.total = 0.0;
        this.finalizada = false;
        this.observadores = new ArrayList<>();
    }

    // Logica de Negocio
    
    public void agregarCurso(String idCurso) {
        if (finalizada) return;
        
        Curso curso = catalogo.buscarCurso(idCurso);
        if (curso != null) {
            catalogo.removerCurso(idCurso); 
            cursosInscritos.add(curso);     
            recalcularTotal();
            notificarVistas();              
        }
    }

    public void finalizar() {
        if (!cursosInscritos.isEmpty()) {
            this.finalizada = true;
            notificarVistas();
        }
    }

    private void recalcularTotal() {
        total = 0.0;
        for (Curso c : cursosInscritos) {
            total += c.getCosto();
        }
    }

    // Implementacion Publisher
    
    private void notificarVistas() {
        for (IVista v : observadores) {
            v.update(this);
        }
    }

    @Override
    public void suscribir(IVista v) {
        observadores.add(v);
    }

    // Lectura

    @Override
    public List<Curso> getCursosDisponibles() { 
        return catalogo.obtenerCursosDisponibles(); 
    }
    
    @Override
    public List<Curso> getCursosInscritos() {
        return cursosInscritos; 
    }
    
    @Override
    public Double getTotal() {
        return total; 
    }
    
    @Override
    public boolean isFinalizada() {
        return finalizada; 
    }
}
