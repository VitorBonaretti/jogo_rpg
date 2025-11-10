package br.com.rpg.sistema;

import java.util.Scanner;

import br.com.rpg.modelo.item.Inventario;
import br.com.rpg.modelo.item.Item;
import br.com.rpg.modelo.item.TipoEfeito;
import br.com.rpg.modelo.personagem.Inimigo;
import br.com.rpg.modelo.personagem.Inimigo.AcaoIA;
import br.com.rpg.modelo.personagem.Personagem;

public final class SistemaDeCombate {
    private SistemaDeCombate(){}

    private static final Scanner SC = new Scanner(System.in);

    public static void batalhar(Personagem jogador, Inimigo inimigo){
        System.out.printf("Combate iniciado: %s vs %s%n", jogador.getNome(), inimigo.getNome());
        Dado dado = new Dado();

        while (jogador.estaVivo() && inimigo.estaVivo()){
            executarTurnoDoJogador(jogador, inimigo);
            if(!inimigo.estaVivo()) break;

            AcaoIA acao = inimigo.decidirAcao(jogador, dado);
            executarAcaoDoInimigo(acao, inimigo, jogador);
        }

        if(jogador.estaVivo()){
            jogador.restaurarVidaTotal();
            System.out.println("Vitória!");
            aplicarLoot(jogador, inimigo);
        }else{
            System.out.println("Derrota...");
        }
    }

    private static void executarTurnoDoJogador(Personagem jogador, Inimigo inimigo){
    System.out.println("\nSeu turno!");
    boolean executou = false;
    while (!executou) {
        int acao = menu(
            "1) Atacar",
            "2) Usar item",
            "3) Status",
            "4) Inventário",
            "5) Fugir"
        );
        switch (acao) {
            case 1 -> {
                jogador.atacar(inimigo);
                executou = true;
            }
            case 2 -> {
                if (usarItemDoInventario(jogador, inimigo)) executou = true;
                else System.out.println("Nenhum item usado.");
            }
            case 3 -> jogador.exibirStatus();
            case 4 -> jogador.getInventario().listar();
            case 5 -> {
                if (inimigo.isChefeFinal()) {
                    System.out.println("Não há para onde fugir diante do Chefe Final!");
                    executou = false;
                } else{
                    int rolagem = Dado.rolar(20);
                    if (rolagem >= 15) {
                        System.out.println("> (d20=" + rolagem + ") Você escapou com sucesso!");
                        inimigo.receberDano(inimigo.getPontosVida());
                        executou = true;
                }   else {
                        System.out.println("> (d20=" + rolagem + ") Tentou fugir, mas o inimigo te alcançou!");
                        executou = true;
                    }
                }
            }
        }
    }
}

    private static void executarAcaoDoInimigo(AcaoIA acao, Inimigo inimigo, Personagem jogador){
        System.out.println("\nTurno do inimigo");

        switch(acao){
            case USAR_ITEM -> {
                String itemCura = inimigo.getInventario().nomeDeUmItemCom(TipoEfeito.CURA);
                if(itemCura != null){
                    inimigo.usarItem(itemCura);
                }else{
                    inimigo.atacar(jogador);
                }
            }
            case ATACAR, PASSAR -> inimigo.atacar(jogador);
        }
    }

    private static void aplicarLoot(Personagem jogador, Inimigo inimigo){
        Inventario drop = inimigo.getInventario().copiaProfunda();
        for(Item i : drop.listaOrdenada()){
            if(i.getQuantidade() > 0 && Dado.rolar(100) <= 50){
                jogador.getInventario().adicionar(new Item(i.getNome(), i.getEfeito(), 1));
                System.out.println("Você saqueou 1x " + i.getNome() + ".");
            }
        }
    }

    private static int menu(String... opcoes){
        while (true){
            for (String op : opcoes) System.out.println(op);
            System.out.print("> ");
            String l = SC.nextLine().trim();
            try {
                int v = Integer.parseInt(l);
                if (v >= 1 && v <= opcoes.length) return v;
            } catch (NumberFormatException ignored) {}
            System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static boolean usarItemDoInventario(Personagem p, Personagem alvoInimigo){
    var itens = p.getInventario().listaOrdenada();
    if (itens.isEmpty()){
        System.out.println("Inventário vazio.");
        return false;
    }
    System.out.println("=== Escolha um item para usar ===");
    System.out.println("0) Voltar");
    for (int i = 0; i < itens.size(); i++){
        Item it = itens.get(i);
        System.out.printf("%d) %s (%dx) [%s]%n", i + 1, it.getNome(), it.getQuantidade(), it.getEfeito());
    }
    System.out.print("> ");
    String entrada = SC.nextLine().trim();
    int idx;
    try { idx = Integer.parseInt(entrada) - 1; }
    catch (NumberFormatException e) { System.out.println("Entrada inválida."); return false; }

    if (idx == -1) return false;
    if (idx < 0 || idx >= itens.size()){
        System.out.println("Opção inválida.");
        return false;
    }

    Item escolhido = itens.get(idx);
    if (escolhido.getEfeito() == TipoEfeito.DANO_DIRETO) {
        p.usarItem(escolhido.getNome(), alvoInimigo);
    } else {
        p.usarItem(escolhido.getNome());
    }
    return true;
}
}