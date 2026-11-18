public class PilhaDesfazer {
    private static class No {
        Tarefa dado;
        No abaixo;

        No(Tarefa dado) {
            this.dado = dado;
            this.abaixo = null;
        }
    }

    private No topo;
    private int tamanho;

    public boolean isVazia() {
        return topo == null;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void adicionar(Tarefa t) {
        No novo = new No(t);
        novo.abaixo = topo;
        topo = novo;
        tamanho++;
    }

    public Tarefa buscar(int id) {
        No atual = topo;
        while (atual != null) {
            if (atual.dado.getId() == id) {
                return atual.dado;
            }
            atual = atual.abaixo;
        }
        return null;
    }

    public Tarefa remover() {
        if (topo == null) {
            return null;
        }
        Tarefa removida = topo.dado;
        topo = topo.abaixo;
        tamanho--;
        return removida;
    }

    public void listar() {
        if (topo == null) {
            System.out.println("Nenhuma exclusao para desfazer.");
            return;
        }
        No atual = topo;
        while (atual != null) {
            System.out.println(atual.dado);
            atual = atual.abaixo;
        }
    }
}
