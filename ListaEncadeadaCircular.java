package projeto_vi;

public class ListaEncadeadaCircular {
    private int count;
    private Node head, tail;

    public ListaEncadeadaCircular() {
        head = tail = null;
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        Node aux = new Node();
        return aux == null;
    }

    public int getCount() {
        return count;
    }

    public Node getHead() {
        return head;
    }

    public boolean insertHead(String dado) {
        if (isFull()) {
            return false;
        }
        Node aux = new Node(dado, null);
        if (isEmpty()) {
            head = tail = aux;
        } else {
            aux.setProx(head);
            head = aux;
        }
        count++;
        return true;
    }

    public boolean insertTail(String s1) {
        Node aux;
        if (!isFull()) {
            aux = new Node(s1, null);
            if (isEmpty()) {
                head = tail = aux;
            } else {
                tail.setProx(aux);
                tail = aux;
            }
            count++;
            return true;
        } else
            return false;
    }


    //Método search para realizar a busca recebendo o dado no parâmetro
    public Node search(String s) {
        Node pAnda;
        if (isEmpty()) {
            return null;
        } else {
            pAnda = head;
            while ((pAnda.getProx() != null && !pAnda.getDado().equals(s))) {
                pAnda = pAnda.getProx();
            }
            if (pAnda.getDado().equals(s)) {
                return pAnda;
            }
            return null;
        }
    }

    public void print() {
        Node pAnda;
        System.out.print("L: [");
        if (!isEmpty()) {
            pAnda = head;
            while (pAnda.getProx() != null) {
                System.out.print(pAnda.getDado() + " ");
                pAnda = pAnda.getProx();
            }
            System.out.print(pAnda.getDado() + " ");
        }
        System.out.println("], Quantidade: " + count + ".\n");
    }

    public void clear() {
        Node pAnt, pAnda = head;
        while (pAnda.getProx() != null) {
            pAnt = pAnda;
            pAnda = pAnda.getProx();
            pAnt.setProx(null);
            pAnt = null;
        }
        count = 0;
        tail = head = null;
    }

    public boolean remove(int index) {
        if (index < 1 || index > count) {
            return false;
        }
        Node pAnt = null, pAnda = head;
        int pos = 1;

        while (pos < index) {
            pAnt = pAnda;
            pAnda = pAnda.getProx();
            pos++;
        }

        if (pAnt == null) {
            head = head.getProx();
            if (head == null) {
                tail = null;
            }
        } else {
            pAnt.setProx(pAnda.getProx());
            if (pAnda == tail) {
                tail = pAnt;
            }
        }
        count--;
        return true;
    }

    public void insert(int index, String dado) {
        System.out.println("Insira a posição da nova linha:\n");
        if (index < 0 || index > count) {
            System.out.println("Posição inválida.");
            return;
        }
        Node novo = new Node(dado, null);
        if (index == 0) {
            novo.setProx(head);
            head = novo;
            if (count == 0) {
                tail = novo;
            }
        } else {
            Node anterior = getNode(index - 1);
            novo.setProx(anterior.getProx());
            anterior.setProx(novo);
            if (novo.getProx() == null) {
                tail = novo;
            }
        }
        count++;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= count) {
            return null;
        }
        Node atual = head;
        for (int i = 0; i < index; i++) {
            atual = atual.getProx();
        }
        return atual;
    }

}
