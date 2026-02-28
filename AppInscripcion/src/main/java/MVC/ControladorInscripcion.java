package MVC;


public class ControladorInscripcion {
    private ModeloInscripcion modelo;

    public ControladorInscripcion(ModeloInscripcion modelo) {
        this.modelo = modelo;
    }

    public void onAgregarCurso(String idCurso) {
        modelo.agregarCurso(idCurso);
    }

    public void onFinalizarInscripcion() {
        modelo.finalizar();
    }
}
