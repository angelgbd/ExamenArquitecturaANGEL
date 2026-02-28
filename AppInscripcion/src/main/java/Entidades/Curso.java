package Entidades;

public class Curso {
    private String idCurso;
    private String nombre;
    private Double costo;

    public Curso(String idCurso, String nombre, Double costo) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.costo = costo;
    }

    public String getIdCurso() {
        return idCurso; 
    
    }
    public String getNombre() {
            return nombre; 
    }
    public Double getCosto() {
        return costo; 
    }

    
    @Override
    public String toString() {
        return String.format("%s - %s ($%.2f)", idCurso, nombre, costo);
    }
}
