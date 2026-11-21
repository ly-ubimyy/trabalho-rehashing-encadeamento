public class Trabalho1 {

    static int VAZIO = -1;
    static int acessos = 0;
    static int colisoes = 0;
    static int duplicadasDescartadas = 0;

    // calcula a posicao da chave na tabela. retorna chave % tamanho
    static int funcaoHash(int chave, int tamanho) {
        return chave % tamanho;
    }

    // calcula a proxima posicao quando da colisao. retorna (posicao + 1) % tamanho
    static int rehash(int posicao, int tamanho) {
        return (posicao + 1) % tamanho;
    }

    // cria a tabela do rehashing com todas as posicoes vazias. retorna o vetor criado
    static int[] criaTabelaRehashing(int tamanho) {
        int[] tabela = new int[tamanho];
        for (int posicao = 0; posicao < tamanho; posicao++) {
            tabela[posicao] = VAZIO;
        }
        return tabela;
    }

    // cria a tabela do encadeamento com um no vazio em cada posicao. retorna o vetor criado
    static No[] criaTabelaEncadeada(int tamanho) {
        No[] tabela = new No[tamanho];
        for (int posicao = 0; posicao < tamanho; posicao++) {
            tabela[posicao] = new No(VAZIO);
        }
        return tabela;
    }

    //insere a chave usansdo o rehashing liinear, nisso ele retorna 1 se inseriu ou 0 se ja exisita e -1 se ja tava cheia
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

    //procura a chave na tabela, retorna a oposicao se achou e se não -1
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
}

class No {
    int chave;
    No proximo;

    No(int chave) {
        this.chave = chave;
        this.proximo = null;
    }
}