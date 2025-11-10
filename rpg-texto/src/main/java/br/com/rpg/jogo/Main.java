package br.com.rpg.jogo;

import java.util.Scanner;

import br.com.rpg.modelo.item.*;
import br.com.rpg.modelo.personagem.*;
import br.com.rpg.sistema.Dado;
import br.com.rpg.sistema.SistemaDeCombate;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("        RPG — Ravina da Névoa      ");
        System.out.println("===================================");

        Personagem heroi = escolherClasse();
        darItensIniciais(heroi);

        narrativa("\nVocê desperta na Ravina da Névoa...");
        narrativa("Um sussurro distante fala sobre um alquimista que corrompe a floresta.");
        narrativa("Sua busca começa ao norte, onde uivos rasgam o nevoeiro...");

        esperarEnter("\n[Capítulo 1] Trilha Enevoada — pressione ENTER para continuar");
        narrativa("Andando nessa estrada tortuosa ao virar uma esqueni você encontra...");
        eventoAleatorio(heroi);
        Inimigo lobo = Inimigo.criarLoboDaNevoa(1);
        narrativa("Entre a névoa, um vulto surge: um Lobo da Névoa com olhos cintilantes aparece!");
        SistemaDeCombate.batalhar(heroi, lobo);
        if (!heroi.estaVivo()) encerrar("O frio da névoa consome sua última fagulha... Fim de jogo.");

        narrativa("\nEntre ruínas à esquerda e o bosque à direita, qual caminho seguir?");
        int escolha = menu("1) Ruínas antigas", "2) Bosque alquímico");

        if (escolha == 1) {
            esperarEnter("\n[Capítulo 2] As Ruínas Silenciosas — ENTER");
            narrativa("Você adentra corredores cobertos de musgo. Runas antigas brilham nas paredes.");
            narrativa("Você decide olhar em voltar para ver se entra alguma reliquia perdida por entre os corredores");
            eventoAleatorio(heroi);
            Inimigo golem = Inimigo.criarGolemEnfeiticado(2);
            narrativa("Um estrondo ecoa — um Golem Enfeitiçado desperta das pedras e avança!");
            SistemaDeCombate.batalhar(heroi, golem);
            if (!heroi.estaVivo()) encerrar("As pedras colapsam ao seu redor... Fim de jogo.");
        } else {
            esperarEnter("\n[Capítulo 2B] Bosque Alquímico — ENTER");
            narrativa("As árvores se curvam, os galhos parecem observá-lo. Frascos pendem das copas.");
            narrativa("Ao longe você vê uma das arvores com um buraco no meio, ao olhar dentro dela...");
            eventoAleatorio(heroi);
            Inimigo corvo = Inimigo.criarCorvoAlquimico(2);
            narrativa("Um grasnar metálico corta o ar — um Corvo Alquímico surge das sombras!");
            SistemaDeCombate.batalhar(heroi, corvo);
            if (!heroi.estaVivo()) encerrar("As folhas murmuram sua queda... Fim de jogo.");
        }

        esperarEnter("\n[Capítulo 3] Pântano das Lamentações — ENTER");
        narrativa("Você alcança o pântano. A água turva ferve com uma energia esverdeada.");
        narrativa("Andando pelos lamaçais do pantano você se depara com algo se mexendo na agua...");
        eventoAleatorio(heroi);
        Inimigo bruxa = Inimigo.criarBruxaDoPantano(3);
        narrativa("Das sombras, surge uma velha de olhos vazios: 'Só passarás se sobreviveres, forasteiro!'");
        SistemaDeCombate.batalhar(heroi, bruxa);
        if (!heroi.estaVivo()) encerrar("O pântano guarda mais um segredo no fundo... Fim de jogo.");

        esperarEnter("\n[Capítulo Final] Laboratório Oculto — ENTER");
        narrativa("As ruínas levam a uma câmara subterrânea repleta de tubos, frascos e máquinas.");
        narrativa("No centro, um homem de jaleco manchado de sangue observa calmamente.");
        Inimigo chefe = Inimigo.oAlquimista(4);
        narrativa("'A curiosidade te trouxe até aqui. A coragem te fará ficar.', diz o Alquimista.");
        SistemaDeCombate.batalhar(heroi, chefe);
        if (!heroi.estaVivo()) encerrar("A lâmpada apaga. A experiência chegou ao fim... Fim de jogo.");

        narrativa("\nA luz retorna à floresta. O nevoeiro recua.");
        narrativa("Você libertou a Ravina da Névoa e derrotou o Alquimista!");
        System.out.println("\nPARABÉNS, " + heroi.getNome().toUpperCase() + "! Você venceu o jogo!");
    }

    private static Personagem escolherClasse() {
        System.out.println("\nEscolha sua classe:");
        int opc = menu("1) Guerreiro", "2) Mago", "3) Arqueiro");
        System.out.print("Nome do herói: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) nome = "Herói";

        return switch (opc) {
            case 1 -> new Guerreiro(nome);
            case 2 -> new Mago(nome);
            case 3 -> new Arqueiro(nome);
            default -> new Guerreiro(nome);
        };
    }

    private static void darItensIniciais(Personagem heroi) {
        heroi.getInventario().adicionar(new Item("Poção de Cura",    TipoEfeito.CURA,        3));
        heroi.getInventario().adicionar(new Item("Elixir de Foco",   TipoEfeito.BUFF_ATAQUE, 1));
        heroi.getInventario().adicionar(new Item("Manto Reforçado",  TipoEfeito.BUFF_DEFESA, 1));
        System.out.println("\nVocê recebeu itens iniciais!");
    }

    private static int menu(String... opcoes) {
        while (true) {
            for (String op : opcoes) System.out.println(op);
            System.out.print("> ");
            String entrada = scanner.nextLine().trim();
            try {
                int v = Integer.parseInt(entrada);
                if (v >= 1 && v <= opcoes.length) return v;
            } catch (NumberFormatException ignored) {}
            System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static void narrativa(String texto) {
        System.out.println(texto);
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    private static void esperarEnter(String msg) {
        System.out.println(msg);
        scanner.nextLine();
    }

    private static void eventoAleatorio(Personagem heroi) {
    int rolagem = Dado.rolar(100);
    if (rolagem <= 20) {
        int dano = Dado.rolar(4);
        heroi.receberDano(dano);
        System.out.println("Uma armadilha foi acionada! Você perde " + dano + " de vida.");
    } else if (rolagem <= 40) {
        heroi.getInventario().adicionar(new Item("Pequena Poção", TipoEfeito.CURA, 1));
        System.out.println("Você encontrou uma bolsa caída: +1 Poção de Cura!");
    } else {
        System.out.println("Nada de interessante encontrado durante a exploração.");
    }
}


    private static void encerrar(String msg) {
        System.out.println("\n" + msg);
        System.exit(0);
    }
}