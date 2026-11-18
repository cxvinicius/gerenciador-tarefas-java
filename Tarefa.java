public class Tarefa {
    private int id;
    private String titulo;
    private int idResponsavel;

    public Tarefa(int id, String titulo, int idResponsavel) {
        this.id = id;
        this.titulo = titulo;
        this.idResponsavel = idResponsavel;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public String toString() {
        return "Tarefa #" + id + " | " + titulo + " | Responsavel (ID): " + idResponsavel;
    }
}
