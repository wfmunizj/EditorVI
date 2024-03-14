package projeto_vi;

public class Node {
    private String dado;
    private Node prox;

    public Node(String dado, Node prox) {
        this.dado = dado;
        this.prox = prox;
    }

    Node() {
        this(null, null);
    }

    public String getDado() {
        return dado;
    }

    public void setDado(String dado) {
        this.dado = dado;
    }

    public Node getProx() {
        return prox;
    }

    public void setProx(Node prox) {
        this.prox = prox;
    }
}
