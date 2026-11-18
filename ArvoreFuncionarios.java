public class ArvoreFuncionarios {
    private static class No {
        Funcionario dado;
        No esq;
        No dir;

        No(Funcionario dado) {
            this.dado = dado;
            this.esq = null;
            this.dir = null;
        }
    }

    private No raiz;

    public boolean isVazia() {
        return raiz == null;
    }

    public boolean inserir(Funcionario f) {
        if (buscar(f.getId()) != null) {
            return false;
        }
        raiz = inserirRec(raiz, f);
        return true;
    }

    private No inserirRec(No no, Funcionario f) {
        if (no == null) {
            return new No(f);
        }
        if (f.getId() < no.dado.getId()) {
            no.esq = inserirRec(no.esq, f);
        } else {
            no.dir = inserirRec(no.dir, f);
        }
        return no;
    }

    public Funcionario buscar(int id) {
        No atual = raiz;
        while (atual != null) {
            if (id == atual.dado.getId()) {
                return atual.dado;
            }
            if (id < atual.dado.getId()) {
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
        }
        return null;
    }

    public boolean remover(int id) {
        if (buscar(id) == null) {
            return false;
        }
        raiz = removerRec(raiz, id);
        return true;
    }

    private No removerRec(No no, int id) {
        if (no == null) {
            return null;
        }
        if (id < no.dado.getId()) {
            no.esq = removerRec(no.esq, id);
        } else if (id > no.dado.getId()) {
            no.dir = removerRec(no.dir, id);
        } else {
            if (no.esq == null) {
                return no.dir;
            }
            if (no.dir == null) {
                return no.esq;
            }
            No sucessor = menor(no.dir);
            no.dado = sucessor.dado;
            no.dir = removerRec(no.dir, sucessor.dado.getId());
        }
        return no;
    }

    private No menor(No no) {
        while (no.esq != null) {
            no = no.esq;
        }
        return no;
    }

    public void percursos() {
        System.out.println("--- Em-ordem (IDs crescentes) ---");
        emOrdem();
        System.out.println("--- Pre-ordem ---");
        preOrdem();
        System.out.println("--- Pos-ordem ---");
        posOrdem();
    }

    public void emOrdem() {
        emOrdemRec(raiz);
    }

    private void emOrdemRec(No no) {
        if (no != null) {
            emOrdemRec(no.esq);
            System.out.println(no.dado);
            emOrdemRec(no.dir);
        }
    }

    public void preOrdem() {
        preOrdemRec(raiz);
    }

    private void preOrdemRec(No no) {
        if (no != null) {
            System.out.println(no.dado);
            preOrdemRec(no.esq);
            preOrdemRec(no.dir);
        }
    }

    public void posOrdem() {
        posOrdemRec(raiz);
    }

    private void posOrdemRec(No no) {
        if (no != null) {
            posOrdemRec(no.esq);
            posOrdemRec(no.dir);
            System.out.println(no.dado);
        }
    }
}
