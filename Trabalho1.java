import java.util.Random;

public class Trabalho1 {

    static int VAZIO = -1;
    static int acessos = 0;
    static int colisoes = 0;
    static int duplicadasDescartadas = 0;

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

                if(repetida){duplicadasDescartadas++;}
                else{
                    chaves[gerados] = candidata;
                    gerados++;
                }
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

class No {
    int chave;
    No proximo;

    No(int chave) {
        this.chave = chave;
        this.proximo = null;
    }
}