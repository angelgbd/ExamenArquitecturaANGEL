package Entidades;

import java.util.ArrayList;
import java.util.List;

public class CatalogoCursos {
    private List<Curso> cursosDisponibles;

    public CatalogoCursos() {
        cursosDisponibles = new ArrayList<>();
        // Datos Mockeados basados en el Figma
        cursosDisponibles.add(new Curso("CS-101", "Introduction to Programming", 150.00));
        cursosDisponibles.add(new Curso("CS-201", "Data Structures", 180.00));
        cursosDisponibles.add(new Curso("CS-105", "Web Development Basics", 200.00));
        cursosDisponibles.add(new Curso("CS-301", "Database Management", 170.00));
        cursosDisponibles.add(new Curso("CS-205", "Algorithms Analysis", 190.00));
    }

    public List<Curso> obtenerCursosDisponibles() {
        return cursosDisponibles;
    }

    public Curso buscarCurso(String idCurso) {
        for (Curso c : cursosDisponibles) {
            if (c.getIdCurso().equals(idCurso)) return c;
        }
        return null;
    }

    public void removerCurso(String idCurso) {
        cursosDisponibles.removeIf(c -> c.getIdCurso().equals(idCurso));
    }
}
