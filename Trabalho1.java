import java.util.Random;
import java.io.PrintWriter;
import java.io.IOException;

public class Trabalho1 {

    static int VAZIO = -1;
    static int acessos = 0;
    static int colisoes = 0;
    static int duplicadasDescartadas = 0;
    static int testesAprovados = 0;
    static int testesFeitos = 0;


    // calcula a posição da chave na tabela e retorna chave % tamanho
    static int funcaoHash(int chave, int tamanho) {
        return chave % tamanho;
    }

    // calcula a próxima posição quando da colisão e retorna (posicao + 1) % tamanho
    static int rehash(int posicao, int tamanho) {
        return (posicao + 1) % tamanho;
    }

    // cria a tabela do rehashing com todas as posiçõess vazias e retorna o vetor criado
    static int[] criaTabelaRehashing(int tamanho) {
        int[] tabela = new int[tamanho];
        for (int posicao = 0; posicao < tamanho; posicao++) {
            tabela[posicao] = VAZIO;
        }
        return tabela;
    }

    // cria a tabela do encadeamento com um no vazio em cada posição e retorna o vetor criado
    static No[] criaTabelaEncadeada(int tamanho) {
        No[] tabela = new No[tamanho];
        for (int posicao = 0; posicao < tamanho; posicao++) {
            tabela[posicao] = new No(VAZIO);
        }
        return tabela;
    }

    //insere a chave usando o rehashing liinear, nisso ele retorna 1 se inseriu ou 0 se ja exisita e -1 se ja tava cheia
    static int insereRehashing(int[] tabela, int chave)
    {
        int tamanho = tabela.length;
        int posicao = funcaoHash(chave, tamanho);
        int passos = 0;

        while(passos < tamanho){
            acessos++;
            passos++;

            if(tabela[posicao] == VAZIO){
                tabela[posicao]= chave;
                return 1;
            }

            if(passos ==1){colisoes++;}

            if(tabela[posicao] == chave){return 0;}


            posicao = rehash(posicao,tamanho);
        }
        return -1;
    }

    //procura a chave na tabela, retorna a posição se achou e se não -1
    static int buscaRehashing(int[] tabela, int chave){
        int tamanho = tabela.length;
        int posicao = funcaoHash(chave, tamanho);
        int passos = 0;

        while(passos < tamanho){
            acessos++;
            passos++;
            
            if (tabela[posicao]==VAZIO){return -1;}

            if (tabela[posicao]==chave){return posicao;}

            posicao = rehash(posicao,tamanho);

        }
        return -1;
    }

    //insere a chave usando o encademaneto com a lista ordenada, retorna 1 se inseriu certinho ou 0 se ja existia
    static int insereEncadeamento(No [] tabela, int chave){
        int posicao =  funcaoHash(chave, tabela.length);
        acessos++;
        if(tabela [posicao].chave == VAZIO){
            tabela[posicao].chave = chave;
            return 1;
        }

        colisoes++;
        if(tabela[posicao].chave == chave){return 0;}
        No anterior = tabela[posicao];
        No atual = tabela[posicao].proximo;

        boolean continua = true;

        while(atual != null && continua){
            acessos++;
            if(atual.chave < chave){
                anterior = atual;
                atual = atual.proximo;
            }
            else{continua = false;}
        }

        if(atual != null && atual.chave == chave){return 0;}

        No novo = new No(chave);
        novo.proximo = atual;
        anterior.proximo = novo;
        return 1;
    }

    //procura a chave na tabela, se encontrar retorna a posição onde ela esta ou -1 se não achou
    static int buscaEncadeamento(No[] tabela, int chave){
        int posicao = funcaoHash(chave, tabela.length);
        
        acessos++;
        if(tabela[posicao].chave == VAZIO){return -1;}

        if(tabela[posicao].chave == chave){return posicao;}

        No atual = tabela[posicao].proximo;
        boolean continua = true;

        while(  atual != null && continua){
            acessos++;
            if(atual.chave < chave){
                atual = atual.proximo;
            }
            else{continua = false;}

        }
        
        if(atual != null && atual.chave == chave){return posicao;}

        return -1;
        
    }

    //sorteia a chaves de 7 digitos sem repetir e retorna o vetor de chaves
    static int[] geraChaves(Random sorteio, int quantidade){
        int[] chaves = new int[quantidade];
        int gerados = 0;

        duplicadasDescartadas = 0;

        while(gerados < quantidade){
            int candidata = 1000000 + sorteio.nextInt(9000000);
            boolean repetida = false;
            for(int indice = 0; indice < gerados; indice++){
                if(chaves [indice] == candidata){repetida = true;}
            }       
             
            if(repetida){duplicadasDescartadas++;}
            else{
                chaves[gerados] = candidata;
                gerados++;
            }
        }
        return chaves;
    }

    //soteia chaves que não estão no vetor de chaves e retorna o vator de ausentes
    static int[] geraAusentes(Random sorteio, int[] chaves, int quantidade){
        int[] ausentes = new int[quantidade];
        int gerados = 0;

        while(gerados < quantidade){
            int candidata = 1000000 + sorteio.nextInt(9000000);
            boolean existe = false;

            for (int indice = 0; indice < chaves.length; indice++){
                if(chaves[indice] == candidata){existe = true;}


            }
            if(!existe){
                ausentes[gerados] = candidata;
                gerados++;
            }
        }
        return ausentes;
    }

    //conta quantas posições estão vazias na tabela rehashing e retorna a quantidade
    static int contaVaziasRehashing(int[] tabela){
        int vazias = 0;
        for(int posicao = 0; posicao < tabela.length; posicao++){
            if(tabela[posicao] == VAZIO){vazias++;}
        }
        return vazias;

    }

    //mesma coisa que a função anterior mas para tabela encadeamento
    static int contaVaziasEncadeamento(No[] tabela){
        int vazias = 0;
        for(int posicao = 0; posicao < tabela.length; posicao++){
            if(tabela[posicao].chave == VAZIO){vazias++;}
        }
        return vazias;
    }

    //procura a posiçaõ com mais chaves na tabela rehashing e retorna a quantidade 
    static int maiorAglomeradoRehashing(int[] tabela) {
        int maior = 0;
        int sequencia = 0;
        for (int posicao = 0; posicao < tabela.length; posicao++) {
            if (tabela[posicao] != VAZIO) {
                sequencia++;
                if (sequencia > maior) {
                    maior = sequencia;
                }
            } else {
                sequencia = 0;
            }
        }
        return maior;
    }

    //mesma coisa que a função anterior mas para tabela encadeamento
    static int maiorAglomeradoEncadeamento(No[] tabela) {
        int maior = 0;

        for (int posicao = 0; posicao < tabela.length; posicao++) {
            int quantidade = 0;

            if (tabela[posicao].chave != VAZIO) {quantidade++;}
            
            No atual = tabela[posicao].proximo;
            while (atual != null) {
                quantidade++;
                atual = atual.proximo;
            }

            if (quantidade > maior) {maior = quantidade;}
        }
        return maior;
    }

    //aqui conta todos os nós das listas encadeadas da tabela e retorna o total
    static int contaNosExtras(No [] tabela){
        int total = 0;

        for(int posicao = 0; posicao< tabela.length; posicao++){
            No atual = tabela[posicao].proximo;
            while(atual != null){
                total++;
                atual = atual.proximo;
            }
        }
        return total;
    }

    //compara dois vetores e retorna true se forem iguais
    static boolean vetoresIguais(int[]primeiro, int[]segundo)
    {
        if(primeiro.length != segundo.length){return false;}

        boolean iguais = true;
        for(int indice = 0; indice < primeiro.length; indice++){
            if(primeiro[indice] != segundo[indice]){iguais = false;}
        }
        return iguais;
    }

    //confere se a lista que começa em inicio tem as mesmas chaves esperadas e retorna true se tiver]
    static boolean listaConfere(No inicio, int[] esperado){
        No atual = inicio;

        int indice = 0;
        boolean certo = true;

        while(atual != null){
            if (indice >= esperado.length){certo = false;}
            else if(atual.chave != esperado[indice]){certo = false;}
            
            indice++;
            atual = atual.proximo;
        }

        if (indice != esperado.length) {certo = false;}
        return certo;
    }

    //imprime o resultado de um teste e soma com o contador mas não retorna nada pq é void
    static void mostraTeste(String codigo, boolean passou){
        testesFeitos++;
        if(passou){
            testesAprovados++;
            System.out.println("Teste" + codigo + ": Aprovado");}
        else{System.out.println("Teste" + codigo + ": Reprovado");}
    }

    //roda os 29 testes solicitados no documento e imprime o resultado de cada um
    static void executaTestes(){
        int retorno;

        //testes de gerar chaves
        Random sorteio = new Random(1990);
        int[] chavesG1 = geraChaves(sorteio, 5);
        int[] esperadoG1 = {4429279, 3976163, 9187615, 1167825, 9651672};
        mostraTeste("G1", vetoresIguais(chavesG1, esperadoG1) && duplicadasDescartadas == 0);

        Random sorteioG2 = new Random(1990);
        int[] chavesG2 = geraChaves(sorteioG2, 9000);
        mostraTeste("G2", duplicadasDescartadas == 6 && chavesG2[8999] == 1368787);

        Random sorteioG3 = new Random(1990);
        int[] chavesG3 = geraChaves(sorteioG3, 900);
        mostraTeste("G3", duplicadasDescartadas == 0 && chavesG3[899] == 7248893);

        int[] ausentesG4 = geraAusentes(sorteio, chavesG1, 3);
        int[] esperadoG4 = {8282916, 7251645, 6739522};
        mostraTeste("G4", vetoresIguais(ausentesG4, esperadoG4));

        // testes do rehashing
        int[] sequencia = {15, 25, 35, 5, 12, 22, 9, 19, 29};
        int[] tabela = criaTabelaRehashing(10);
        acessos = 0;
        colisoes = 0;
        boolean todasInseriram = true;
        for (int indice = 0; indice < sequencia.length; indice++) {
            retorno = insereRehashing(tabela, sequencia[indice]);
            if (retorno != 1) {
                todasInseriram = false;
            }
        }
        mostraTeste("R1", todasInseriram);

        int[] esperadoR2 = {19, 29, 12, 22, VAZIO, 15, 25, 35, 5, 9};
        mostraTeste("R2", vetoresIguais(tabela, esperadoR2));
        mostraTeste("R3", colisoes == 6);
        mostraTeste("R4", acessos == 19);

        acessos = 0;
        retorno = buscaRehashing(tabela, 29);
        mostraTeste("R5", retorno == 1 && acessos == 3);

        acessos = 0;
        retorno = buscaRehashing(tabela, 99);
        mostraTeste("R6", retorno == -1 && acessos == 6);

        acessos = 0;
        retorno = buscaRehashing(tabela, 18);
        mostraTeste("R7", retorno == -1 && acessos == 7);

        acessos = 0;
        retorno = insereRehashing(tabela, 25);
        mostraTeste("R8", retorno == 0 && acessos == 2 && tabela[6] == 25 && contaVaziasRehashing(tabela) == 1);

        int[] tabelaPequena = criaTabelaRehashing(3);
        insereRehashing(tabelaPequena, 1);
        insereRehashing(tabelaPequena, 4);
        insereRehashing(tabelaPequena, 7);
        int[] esperadoR9 = {7, 1, 4};
        mostraTeste("R9", vetoresIguais(tabelaPequena, esperadoR9));

        acessos = 0;
        retorno = insereRehashing(tabelaPequena, 10);
        mostraTeste("R10", retorno == -1 && acessos == 3);

        acessos = 0;
        retorno = buscaRehashing(tabelaPequena, 10);
        mostraTeste("R11", retorno == -1 && acessos == 3);

        mostraTeste("R12", maiorAglomeradoRehashing(tabela) == 5);

        // testes do encadeamento
        No[] tabelaEncadeada = criaTabelaEncadeada(10);
        acessos = 0;
        colisoes = 0;
        todasInseriram = true;
        for (int indice = 0; indice < sequencia.length; indice++) {
            retorno = insereEncadeamento(tabelaEncadeada, sequencia[indice]);
            if (retorno != 1) {
                todasInseriram = false;
            }
        }
        mostraTeste("E1", todasInseriram);

        int[] listaCinco = {5, 25, 35};
        mostraTeste("E2", tabelaEncadeada[5].chave == 15 && listaConfere(tabelaEncadeada[5].proximo, listaCinco));

        int[] listaDois = {22};
        int[] listaNove = {19, 29};
        mostraTeste("E3", tabelaEncadeada[2].chave == 12 && listaConfere(tabelaEncadeada[2].proximo, listaDois)
                && tabelaEncadeada[9].chave == 9 && listaConfere(tabelaEncadeada[9].proximo, listaNove));

        int[] posicoesVazias = {0, 1, 3, 4, 6, 7, 8};
        boolean vaziasCertas = contaVaziasEncadeamento(tabelaEncadeada) == 7;
        for (int indice = 0; indice < posicoesVazias.length; indice++) {
            if (tabelaEncadeada[posicoesVazias[indice]].chave != VAZIO) {
                vaziasCertas = false;
            }
        }
        mostraTeste("E4", vaziasCertas);
        mostraTeste("E5", colisoes == 6);
        mostraTeste("E6", acessos == 12);

        acessos = 0;
        retorno = buscaEncadeamento(tabelaEncadeada, 29);
        mostraTeste("E7", retorno == 9 && acessos == 3);

        acessos = 0;
        retorno = buscaEncadeamento(tabelaEncadeada, 99);
        mostraTeste("E8", retorno == -1 && acessos == 3);

        acessos = 0;
        retorno = buscaEncadeamento(tabelaEncadeada, 18);
        mostraTeste("E9", retorno == -1 && acessos == 1);

        acessos = 0;
        retorno = buscaEncadeamento(tabelaEncadeada, 25);
        mostraTeste("E10", retorno == 5 && acessos == 3);

        acessos = 0;
        retorno = insereEncadeamento(tabelaEncadeada, 25);
        mostraTeste("E11", retorno == 0 && acessos == 3 && contaNosExtras(tabelaEncadeada) == 6);

        mostraTeste("E12", contaNosExtras(tabelaEncadeada) == 6);
        mostraTeste("E13", maiorAglomeradoEncadeamento(tabelaEncadeada) == 4);

        System.out.println("TESTES: " + testesAprovados + " de " + testesFeitos + " aprovados");
    }

    // mede o rehashing em um cenario e grava a linha no console e no arquivo
    static void cenarioRehashing(PrintWriter arquivo, int tamanho, int carga, int quantidade, int descartadas, int[] chaves, int[] ausentes) {
        Runtime.getRuntime().gc();
        long memoriaAntes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        int[] tabela = criaTabelaRehashing(tamanho);
        acessos = 0;
        colisoes = 0;
        long inicio = System.nanoTime();
        for (int indice = 0; indice < quantidade; indice++) {
            insereRehashing(tabela, chaves[indice]);
        }
        long fim = System.nanoTime();
        long memoriaDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        int colisoesInsercao = colisoes;
        int acessosInsercao = acessos;
        long tempoInsercao = (fim - inicio) / quantidade;
        long memoria = memoriaDepois - memoriaAntes;

        acessos = 0;
        int maximo = 0;
        inicio = System.nanoTime();
        for (int indice = 0; indice < quantidade; indice++) {
            int antes = acessos;
            buscaRehashing(tabela, chaves[indice]);
            int gastos = acessos - antes;
            if (gastos > maximo) {
                maximo = gastos;
            }
        }
        fim = System.nanoTime();
        int acessosSucesso = acessos;
        long tempoBusca = (fim - inicio) / quantidade;

        acessos = 0;
        for (int indice = 0; indice < quantidade; indice++) {
            buscaRehashing(tabela, ausentes[indice]);
        }
        int acessosFalha = acessos;

        int vazias = contaVaziasRehashing(tabela);
        int aglomerado = maiorAglomeradoRehashing(tabela);

        String linha = String.format("%s;%d;%d;%d;%d;%d;%d;%.2f;%.2f;%d;%.2f;%d;%d;%d;%d;%d;%d",
                "rehashing", tamanho, carga, quantidade, descartadas, colisoesInsercao, acessosInsercao,
                (float) acessosInsercao / quantidade, (float) acessosSucesso / quantidade, maximo,
                (float) acessosFalha / quantidade, vazias, aglomerado, 0, tempoInsercao, tempoBusca, memoria);
        System.out.println(linha);
        arquivo.println(linha);

        if (vazias + quantidade != tamanho) {
            System.out.println("ERRO: Regra de negócio violada");
        }
    }

    // mede o encadeamento em um cenario e grava a linha no console e no arquivo
    static void cenarioEncadeamento(PrintWriter arquivo, int tamanho, int carga, int quantidade, int descartadas, int[] chaves, int[] ausentes) {
        Runtime.getRuntime().gc();
        long memoriaAntes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        No[] tabela = criaTabelaEncadeada(tamanho);
        acessos = 0;
        colisoes = 0;
        long inicio = System.nanoTime();
        for (int indice = 0; indice < quantidade; indice++) {
            insereEncadeamento(tabela, chaves[indice]);
        }
        long fim = System.nanoTime();
        long memoriaDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        int colisoesInsercao = colisoes;
        int acessosInsercao = acessos;
        long tempoInsercao = (fim - inicio) / quantidade;
        long memoria = memoriaDepois - memoriaAntes;

        acessos = 0;
        int maximo = 0;
        inicio = System.nanoTime();
        for (int indice = 0; indice < quantidade; indice++) {
            int antes = acessos;
            buscaEncadeamento(tabela, chaves[indice]);
            int gastos = acessos - antes;
            if (gastos > maximo) {
                maximo = gastos;
            }
        }
        fim = System.nanoTime();
        int acessosSucesso = acessos;
        long tempoBusca = (fim - inicio) / quantidade;

        acessos = 0;
        for (int indice = 0; indice < quantidade; indice++) {
            buscaEncadeamento(tabela, ausentes[indice]);
        }
        int acessosFalha = acessos;

        int vazias = contaVaziasEncadeamento(tabela);
        int aglomerado = maiorAglomeradoEncadeamento(tabela);
        int nosExtras = contaNosExtras(tabela);

        String linha = String.format("%s;%d;%d;%d;%d;%d;%d;%.2f;%.2f;%d;%.2f;%d;%d;%d;%d;%d;%d",
                "encadeamento", tamanho, carga, quantidade, descartadas, colisoesInsercao, acessosInsercao,
                (float) acessosInsercao / quantidade, (float) acessosSucesso / quantidade, maximo,
                (float) acessosFalha / quantidade, vazias, aglomerado, nosExtras, tempoInsercao, tempoBusca, memoria);
        System.out.println(linha);
        arquivo.println(linha);

        if ((tamanho - vazias) + nosExtras != quantidade) {
            System.out.println("ERRO: invariante violada");
        }
    }

    // gera as chaves de um cenario e mede as duas tecnicas
    static void executaCenario(PrintWriter arquivo, int tamanho, int carga) {
        int quantidade = tamanho * carga / 100;
        Random sorteio = new Random(1990);
        int[] chaves = geraChaves(sorteio, quantidade);
        int descartadas = duplicadasDescartadas;
        int[] ausentes = geraAusentes(sorteio, chaves, quantidade);

        cenarioRehashing(arquivo, tamanho, carga, quantidade, descartadas, chaves, ausentes);
        cenarioEncadeamento(arquivo, tamanho, carga, quantidade, descartadas, chaves, ausentes);
    }

    // roda os testes, cria o csv/excel e executa os 9 cenarios
    public static void main(String[] args) throws IOException {
        executaTestes();

        PrintWriter arquivo = new PrintWriter("resultados_trabalho1.csv");
        String cabecalho = "tecnica;tamanho;cargaPercentual;chaves;duplicadasDescartadas;colisoes;acessosInsercaoTotal;acessosInsercaoMedia;acessosBuscaSucessoMedia;acessosBuscaSucessoMaximo;acessosBuscaFalhaMedia;posicoesVazias;maiorAglomerado;nosExtras;tempoInsercaoNsPorOp;tempoBuscaSucessoNsPorOp;memoriaMedidaBytes";
        System.out.println(cabecalho);
        arquivo.println(cabecalho);

        int[] tamanhos = {100, 1000, 10000};
        int[] cargas = {50, 70, 90};
        for (int indiceTamanho = 0; indiceTamanho < tamanhos.length; indiceTamanho++) {
            for (int indiceCarga = 0; indiceCarga < cargas.length; indiceCarga++) {
                executaCenario(arquivo, tamanhos[indiceTamanho], cargas[indiceCarga]);
            }
        }

        arquivo.close();
    }
}

class No {
    int chave;
    No proximo;

    No(int chave) {
        this.chave = chave;
        this.proximo = null;
    }
}