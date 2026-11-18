public class FilaTarefas {
    private static class No {
        Tarefa dado;
        No prox;

        No(Tarefa dado) {
            this.dado = dado;
            this.prox = null;
        }
    }

    private No inicio;
    private No fim;
    private int tamanho;

    public boolean isVazia() {
        return inicio == null;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void adicionar(Tarefa t) {
        No novo = new No(t);
        if (fim == null) {
            inicio = novo;
        } else {
            fim.prox = novo;
        }
        fim = novo;
        tamanho++;
    }

    public Tarefa buscar(int id) {
        No atual = inicio;
        while (atual != null) {
            if (atual.dado.getId() == id) {
                return atual.dado;
            }
            atual = atual.prox;
        }
        return null;
    }

    public Tarefa remover() {
        if (inicio == null) {
            return null;
        }
        Tarefa removida = inicio.dado;
        inicio = inicio.prox;
        if (inicio == null) {
            fim = null;
        }
        tamanho--;
        return removida;
    }

    public Tarefa removerPorId(int id) {
        if (inicio == null) {
            return null;
        }
        if (inicio.dado.getId() == id) {
            return remover();
        }
        No anterior = inicio;
        while (anterior.prox != null && anterior.prox.dado.getId() != id) {
            anterior = anterior.prox;
        }
        if (anterior.prox == null) {
            return null;
        }
        No alvo = anterior.prox;
        anterior.prox = alvo.prox;
        if (alvo == fim) {
            fim = anterior;
        }
        tamanho--;
        return alvo.dado;
    }

    public boolean temResponsavel(int idResponsavel) {
        No atual = inicio;
        while (atual != null) {
            if (atual.dado.getIdResponsavel() == idResponsavel) {
                return true;
            }
            atual = atual.prox;
        }
        return false;
    }

    public void listar() {
        if (inicio == null) {
            System.out.println("Backlog vazio.");
            return;
        }
        No atual = inicio;
        int posicao = 1;
        while (atual != null) {
            System.out.println(posicao + "o da fila -> " + atual.dado);
            atual = atual.prox;
            posicao++;
        }
    }
}
