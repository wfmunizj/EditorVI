package projeto_vi;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Editor {
    // Declaração das variáveis
    private ListaEncadeadaCircular lines, copia;
    private Scanner s;
    private int linhaInicioMarcada, linhaFinalMarcada;
    int linha, linhaInicio, linhaFinal, posicaoLinha;
    String substituir, dado, conteudoNovaLinha, nomeArq;

    public static void main(String[] args) {
        menuCabecalho();
        Editor editor = new Editor();
        editor.run();
    }

    public Editor() {
        this.lines = new ListaEncadeadaCircular();
        this.s = new Scanner(System.in);
    }

    // Implementação das funcionalidades

    public void abrirArquivo(String nomeArq) {

        File arquivo = new File(nomeArq);

        System.out.println("Arquivo aberto com sucesso");

        // Abre o arquivo e lê o conteúdo linha por linha
        try {
            Scanner s = new Scanner(arquivo);
            while (s.hasNextLine()) {
                String linha = s.nextLine();
                // Cada linha é inserida em um nó da lista
                lines.insertTail(linha);
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo.");
            e.printStackTrace();
        }
        run();
    }

    // Função geral no código para verificar se o arquivo foi aberto
    // caso contrário solicita a abertura do mesmo
    public void verificaAbertura() {
        if (this.lines.isEmpty()) {
            System.out.println("\nVocê precisa abrir um arquivo antes de realizar as operações.\n");
            run();
        }
    }

    // Utiliza a classe PrintWriter para salvar o arquivo percorrendo cada nó da
    // lista

    public void salvaArquivo(String nomeArq) {
        verificaAbertura();
        try {
            PrintWriter writer = new PrintWriter(nomeArq); 
            Node atual = lines.getHead();
            while (atual != null) {
                writer.println(atual.getDado()); //Escreve o dado do nó no arquivo
                atual = atual.getProx();
            }
            writer.close();
            System.out.println("Arquivo salvo com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao salvar o arquivo.");
            e.printStackTrace(); //Retorna o erro 
        }
    }

    public void sair() {
        System.out.println("Você tem certeza que deseja sair? (Sim.1) ou (Não.Pressione qualquer número)");
        int resposta = s.nextInt();
        if (resposta == 1) {
            System.out.println("Editor fechado");
            s.close();
            System.exit(0);
        } else {
            run();
        }
    }

    public void marcarTexto(int linhaInicio, int linhaFinal) {
        verificaAbertura();

        // Verifica se o intervalo é válido
        if (linhaInicio > 0 && linhaInicio <= linhaFinal && linhaFinal <= lines.getCount()) {
            // Armazena as linhas de início e fim nas variáveis de instância
            this.linhaInicioMarcada = linhaInicio;
            this.linhaFinalMarcada = linhaFinal;

            Node atual = lines.getHead();
            int numLinha = 1;
            while (atual != null) {
                if (numLinha >= linhaInicio && numLinha <= linhaFinal) {
                    System.out.println(atual.getDado()); //Exibe o dado que está no nó atual 
                }
                atual = atual.getProx(); //Anda o ponteiro para o próximo nó
                numLinha++; //Soma o número da linha
            }

            System.out.println("Texto marcado do " + linhaInicio + " ao " + linhaFinal + "\n");
            run();
        } else {
            System.out.println("Intervalo inválido.");
            run();
        }
    }

    public void copiaTexto() {
        verificaAbertura();
        if (linhaInicioMarcada == 0) {
            System.out.println("\nNão há texto para copiar, favor selecionar um trecho de código para cópia ':v':\n");
            run();
        }
        copia = new ListaEncadeadaCircular();
        Node atual = lines.getHead();
        int numLinha = 1;
        while (atual != null) {
            if (numLinha >= linhaInicioMarcada && numLinha <= linhaFinalMarcada) {
                copia.insertTail(atual.getDado());
            }
            atual = atual.getProx();
            numLinha++;
        }
        System.out.println("Texto copiado com sucesso!");
        run();
    }

    public void recortaTexto() {
        verificaAbertura();
        if (copia == null) {
            System.out.println("Não há texto para cortar. Marcando as linhas para recorte");
            copiaTexto();
        }
        for (int i = linhaInicioMarcada; i <= linhaFinalMarcada; i++) {
            lines.remove(i);
        }
        System.out.println("Texto cortado com sucesso!");
        run();
    }

    public void colaTexto(int linhaInicio) {
        verificaAbertura();
        if (copia == null) {
            System.out.println("Não há texto para colar. Favor recortar um trecho do código ':y'");
            run();
        }

        Node atual = copia.getHead();
        int numLinha = linhaInicio;
        while (atual != null) {
            lines.insert(numLinha, atual.getDado());
            atual = atual.getProx();
            numLinha++;
        }
        System.out.println("Texto colado com sucesso!");
        run();
    }

    public void mostraConteudo() {
        verificaAbertura();
        Node atual = lines.getHead();
        int numLinha = 1;
        while (atual != null) {
            System.out.println(numLinha + ": " + atual.getDado());
            atual = atual.getProx();
            numLinha++;
            if (numLinha % 10 == 0) {
                System.out.println("Pressione Enter para exibir mais");
                s.nextLine();
            }
        }
        run();
    }

    public void exibirIntervaloConteudo(int linhaInicio, int linhaFinal) {
        verificaAbertura();
        if (linhaInicio < 1 || linhaFinal > lines.getCount()) {
            System.out.println("Intervalo inválido.");
            run();
        }
        Node atual = lines.getHead();
        int numLinha = 1;
        while (atual != null) {
            if (numLinha >= linhaInicio && numLinha <= linhaFinal) {
                System.out.println(numLinha + ": " + atual.getDado());
                if (numLinha % 10 == 0) {
                    System.out.println("Pressione Enter para exibir mais");
                    s.nextLine();
                }
            }
            atual = atual.getProx();
            numLinha++;
        }
        run();
    }

    public void removeLinha(int linha) {
        verificaAbertura();
        if (linha < 1 || linha > lines.getCount()) {
            System.out.println("Linha inválida.");
            run();
        } else {
            lines.remove(linha);
            System.out.println("Linha removida com sucesso!");
            run();
        }
    }

    public void removeDaLinhaAteFim(int linha) {
        verificaAbertura();
        while (linha < 0 || linha > lines.getCount()) {
            System.out.println("Linha inválida. Insira uma nova linha");
            linha = s.nextInt();
        }
        for (int i = lines.getCount(); i >= linha; i--) {
            lines.remove(i);
        }
        System.out.println("Texto removido com sucesso!");
        run();
    }

    public void removeDaLinhaAteComeco(int linha) {
        verificaAbertura();
        while (linha < 0 || linha > lines.getCount()) {
            System.out.println("Linha inválida. Insira uma nova linha");
            linha = s.nextInt();
        }
        for (int i = 1; i <= linha; i++) {
            lines.remove(1);
        }
        System.out.println("Texto removido com sucesso!");
        run();
    }

    public void buscaElemento(String dado) {
        verificaAbertura();
        Node atual = lines.getHead();
        int numLinha = 1;
        while (atual != null) {
            if (atual.getDado().contains(dado)) {
                System.out.println(numLinha + ": " + atual.getDado());
            }
            atual = atual.getProx();
            numLinha++;
        }
        run();
    }

    public void trocaElemento(String dado, String substituir) {
        verificaAbertura();

        Node atual = lines.getHead();
        while (atual != null) {
            if (atual.getDado().contains(dado)) {
                atual.setDado(atual.getDado().replace(dado, substituir));
            }
            atual = atual.getProx();
        }
        System.out.println("Elemento substituído com sucesso!");
        run();
    }

    public void insereNovasLinhas(int posicaoLinha) {
        verificaAbertura();

        if (posicaoLinha < 1 || posicaoLinha > lines.getCount()) {
            System.out.println("Posição inválida.");
            run();
        }
        System.out.println("Insira as novas linhas. Digite ':a' em uma linha vazia para terminar.");
        String linha;
        while (!(linha = s.nextLine()).equals(":a")) {
            lines.insert(posicaoLinha++, linha);
        }
        System.out.println("Linhas inseridas com sucesso!");
        run();
    }

    public void insereLinha(int posicaoLinha, String conteudoNovaLinha) {
        verificaAbertura();
        if (posicaoLinha < 1 || posicaoLinha > lines.getCount()) {
            System.out.println("Posição inválida.");
            run();
        }
        lines.insert(posicaoLinha, conteudoNovaLinha);
        System.out.println("Linha inserida com sucesso!");
        run();
    }

    public void help() {
        String[] opcao = {
                ":e  nomeArq.ext | Abre o arquivo de nome 'nomeArq.ext', lê o seu conteúdo e armazena cada linha em um nó da lista encadeada.",
                ":w  nomeArq.ext | Salva o conteúdo da lista encadeada em um arquivo de nome 'nomeArq.ext'.",
                ":q! Sai do editor sem salvar as modificações realizadas.",
                ":v  LinIni LinFimMarca | um texto da lista (para cópia ou corte) da LinIni até LinFim.",
                ":y Copia o texto marcado para uma lista de Cópia.",
                ":c Corta o texto marcado.",
                ":p LinIniPaste | Cola o texto marcado a partir da linha inicial (LinIniPaste).",
                ":s Exibe na tela o conteúdo do programa fonte completo de 10 em 10 linhas.",
                ":s LinIni LinFim | Exibe na tela o conteúdo do programa fonte que consta na lista da linha inicial 'LimIni' até a linha final 'LinFim' de 10 em 10 linhas.",
                ":x Lin | Apaga a linha de posição 'Lin' da lista.",
                ":xG Lin | Apaga a partir da linha 'Lin' até o final da lista.",
                ":XG Lin | Apaga da linha 'Lin' até o início da lista.",
                ":/ elemento | Percorre a lista, localiza a(s) linha(s) na(s) qual(is) o 'elemento' encontra-se e exibi-las.",
                ":/elem elemTroca | Percorre a lista, localiza o 'elem' e realiza a troca por 'elemTroca' em todas as linhas do código fonte.",
                ":a posLin | Permite a edição de uma ou mais novas linhas e insere na lista depois da posição posLin.",
                ":i posLin [conteudo da nova linha] | Permite a inserção da linha '[conteudo da nova linha]' e insere na lista antes da posição posLin.",
                ":help: Apresenta na tela todas as operações permitidas no editor de 5 em 5 linhas."
        };

        for (int i = 0; i < opcao.length; i++) {
            System.out.println(opcao[i]);
            if ((i + 1) % 5 == 0) {
                System.out.println("Pressione Enter para exibir mais");
                s.nextLine();
            }
        }
        run();
    }

    public static void menuCabecalho() {
        System.out.println("||------------------------||");
        System.out.println("||  BEM VINDO AO ELDITOR   ||");
        System.out.println("||------------------------||");
        System.out.println("Desenvolvido por Wellington Muniz");
    }

    public void run() {
        Scanner s = new Scanner(System.in);
        String entrada = s.nextLine();
        String[] partes = entrada.split(" ");

        switch (partes[0]) {
            case ":e":
                if (partes.length > 1) {
                    this.abrirArquivo(partes[1]);
                } else {
                    System.out.println("Por favor, forneça o nome do arquivo.");
                    run();
                }
                break;
            case ":w":
                if (partes.length > 1) {
                    this.salvaArquivo(partes[1]);
                } else {
                    System.out.println("Por favor, forneça o nome do arquivo.");
                    run();
                }
                break;
            case ":q!":
                this.sair();
                break;
            case ":v":
                if (partes.length > 2) {
                    int linhaInicio = Integer.parseInt(partes[1]); //Força a conversão do primeiro elemento para um inteiro
                    int linhaFinal = Integer.parseInt(partes[2]); //Força a conversão do segundo elemento para um inteiro
                    this.marcarTexto(linhaInicio, linhaFinal);
                } else {
                    System.out.println("Por favor, forneça as linhas de início e fim.");
                    run();
                }
                break;
            case ":y":
                this.copiaTexto();
                break;
            case ":c":
                this.recortaTexto();
                break;
            case ":p":
                if (partes.length > 1) {
                    int linhaInicio = Integer.parseInt(partes[1]); //Força a conversão do segundo elemento para um inteiro
                    this.colaTexto(linhaInicio);
                } else {
                    System.out.println("Por favor, forneça a linha inicial.");
                    run();
                }
                break;

            case ":s":
                if (partes.length == 1) {
                    this.mostraConteudo();
                } else if (partes.length == 3) {
                    int linhaInicio = Integer.parseInt(partes[1]);
                    int linhaFinal = Integer.parseInt(partes[2]);
                    this.exibirIntervaloConteudo(linhaInicio, linhaFinal);
                } else {
                    System.out.println(
                            "Comando inválido. Use ':s' para exibir todo o conteúdo ou ':s LinIni LinFim' para exibir um intervalo de conteúdo.");
                            run();
                }
                break;

            case ":x":
                if (partes.length > 1) {
                    int linha = Integer.parseInt(partes[1]);
                    this.removeLinha(linha);
                } else {
                    System.out.println("Por favor, forneça a linha a ser removida.");
                    run();
                }
                break;
            case ":xG":
                if (partes.length > 1) {
                    int linha = Integer.parseInt(partes[1]);
                    this.removeDaLinhaAteFim(linha);
                } else {
                    System.out.println("Por favor, forneça a linha inicial.");
                    run();
                }
                break;
            case ":XG":
                if (partes.length > 1) {
                    int linha = Integer.parseInt(partes[1]);
                    this.removeDaLinhaAteComeco(linha);
                } else {
                    System.out.println("Por favor, forneça a linha inicial.");
                    run();
                }
                break;
            case ":/":
                if (partes.length > 1) {
                    String dado = partes[1];
                    this.buscaElemento(dado);
                } else {
                    System.out.println("Por favor, forneça o elemento a ser procurado.");
                    run();
                }
                break;
            case ":/elem":
                if (partes.length > 2) {
                    String dado = partes[1];
                    String substituir = partes[2];
                    this.trocaElemento(dado, substituir);
                } else {
                    System.out.println("Por favor, forneça o elemento a ser substituído e o novo elemento.");
                    run();
                }
                break;
            case ":a":
                if (partes.length > 1) {
                    int posicaoLinha = Integer.parseInt(partes[1]);
                    this.insereNovasLinhas(posicaoLinha);
                } else {
                    System.out.println("Por favor, forneça a posição da nova linha.");
                    run();
                }
                break;
            case ":i":
                if (partes.length > 2) {
                    int posicaoLinha = Integer.parseInt(partes[1]);
                    String conteudoNovaLinha = partes[2];
                    this.insereLinha(posicaoLinha, conteudoNovaLinha);
                } else {
                    System.out.println("Por favor, forneça a posição da nova linha e o conteúdo da nova linha.");
                    run();
                }
                break;

            case ":help":
                this.help();
                break;
            default:
                System.out.println("Opção inválida. Reiniciando o programa.");
                this.run();
        }

    }

}

/*
Refências

https://pt.stackoverflow.com/questions/1823/como-ler-um-arquivo-de-texto-em-java

https://docs.oracle.com/javase/8/docs/api/java/io/PrintWriter.html

https://www.w3schools.com/java/java_files.asp

https://stackoverflow.com/questions/37912669/java-linkedlist-node-manipulation
*/