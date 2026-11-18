# Trabalho 1 - Rehashing x Encadeamento

Estudo comparativo de duas técnicas de tratamento de colisão em tabela hash, **rehashing linear** e **encadeamento ordenado**, medindo o desempenho de cada uma em 9 cenários.

**Disciplina:** Resolução de Problemas Estruturados em Computação - PUCPR  
**Autora:** Nicolly Stefany Pedroso Cordeiro - matrícula 40113407

## Técnicas

A função hash calcula a posição da chave com `chave % tamanho`. Quando duas chaves caem na mesma posição, acontece uma colisão.

**Rehashing linear:** a tabela é um `int[]`. Se a posição estiver ocupada, a chave tenta a próxima com `(posicao + 1) % tamanho` até achar uma livre.

**Encadeamento ordenado:** a tabela é um `No[]`. A primeira chave fica no nó da própria posição, e as que colidem depois entram em uma lista ligada em ordem crescente.

Exemplo com as chaves `15, 25, 35, 5, 12, 22, 9, 19, 29` numa tabela de 10 posições:

```
Rehashing                         Encadeamento
[0] 19                            [2] 12 -> 22
[1] 29                            [5] 15 -> 5 -> 25 -> 35
[2] 12                            [9] 9 -> 19 -> 29
[3] 22                            (demais posições vazias)
[4] -
[5] 15
[6] 25
[7] 35
[8] 5
[9] 9
```

## Cenários

São 3 tamanhos de tabela (100, 1000 e 10000) com 3 cargas (50%, 70% e 90%), totalizando 9 cenários. As chaves são sorteadas com `Random(1990)`, então os resultados são sempre os mesmos.

Em cada cenário são medidos colisões, acessos na inserção e nas buscas (com sucesso e com falha), posições vazias, maior aglomerado, nós extras, tempo e memória.

## Como executar

Precisa do JDK 17 ou superior.

```
javac Trabalho1.java
java Trabalho1
```

O programa não pede nenhuma entrada. Ele roda os 29 testes de validação, mostra os resultados dos 9 cenários e grava o arquivo `resultados_trabalho1.csv` na mesma pasta.

## Arquivos

| Arquivo | Conteúdo |
|---|---|
| `Trabalho1.java` | Código-fonte com as classes `Trabalho1` e `No` |
| `resultados_trabalho1.csv` | Resultados dos 9 cenários gerados pelo programa |
| `relatorio_trabalho1.pdf` | Relatório com tabela, gráficos e análise |

## Andamento

- [ ] [#1 Estrutura base](https://github.com/ly-ubimyy/trabalho-rehashing-encadeamento/issues/1)
- [ ] [#2 Rehashing linear](https://github.com/ly-ubimyy/trabalho-rehashing-encadeamento/issues/2)
- [ ] [#3 Encadeamento ordenado](https://github.com/ly-ubimyy/trabalho-rehashing-encadeamento/issues/3)
- [ ] [#4 Geração de chaves](https://github.com/ly-ubimyy/trabalho-rehashing-encadeamento/issues/4)
- [ ] [#5 Métricas da estrutura](https://github.com/ly-ubimyy/trabalho-rehashing-encadeamento/issues/5)
- [ ] [#6 Testes de validação](https://github.com/ly-ubimyy/trabalho-rehashing-encadeamento/issues/6)
- [ ] [#7 Cenários e CSV](https://github.com/ly-ubimyy/trabalho-rehashing-encadeamento/issues/7)
- [ ] [#8 Relatório](https://github.com/ly-ubimyy/trabalho-rehashing-encadeamento/issues/8)
