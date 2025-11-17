package br.com.rpg.jogo;

import java.util.Scanner;

import br.com.rpg.modelo.item.Item;
import br.com.rpg.modelo.item.TipoEfeito;
import br.com.rpg.modelo.personagem.Arqueiro;
import br.com.rpg.modelo.personagem.Guerreiro;
import br.com.rpg.modelo.personagem.Inimigo;
import br.com.rpg.modelo.personagem.Mago;
import br.com.rpg.modelo.personagem.Personagem;
import br.com.rpg.sistema.Dado;
import br.com.rpg.sistema.SaveService;
import br.com.rpg.sistema.SaveState;
import br.com.rpg.sistema.SistemaDeCombate;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("        RPG — Ravina da Névoa      ");
        System.out.println("===================================");

        SaveState state = SaveService.load();
        Personagem heroi;

        // ====== CARREGA OU INICIA NOVO JOGO ======
        if (state != null && state.heroiSnap != null) {
            System.out.println("Foi encontrado um jogo salvo.");
            System.out.println("1) Continuar do último ponto");
            System.out.println("2) Iniciar novo jogo");
            System.out.print("> ");
            String op = scanner.nextLine().trim();
            if ("1".equals(op)) {
                heroi = SaveState.restorePersonagem(state.heroiSnap);
                System.out.println("Jogo carregado. Bem-vindo de volta, " + heroi.getNome() + "!");
            } else {
                SaveService.delete();
                heroi = escolherClasse();
                darItensIniciais(heroi);
                state = new SaveState();
                state.heroiSnap = SaveState.snapFrom(heroi);
                SaveService.save(state);
            }
        } else {
            heroi = escolherClasse();
            darItensIniciais(heroi);
            state = new SaveState();
            state.heroiSnap = SaveState.snapFrom(heroi);
            SaveService.save(state);
        }

        // ====== INTRODUÇÃO ======
        narrativa("\nVocê desperta na Ravina da Névoa...");
        narrativa("Um sussurro distante fala sobre um alquimista que corrompe a floresta.");
        narrativa("Sua busca começa ao norte, onde uivos rasgam o nevoeiro...");

        // ================== CAPÍTULO 1 ==================
        esperarEnter("\n[Capítulo 1] Trilha Enevoada — pressione ENTER para continuar");
        narrativa("Andando nessa estrada tortuosa, ao virar uma esquina você encontra...");
        eventoAleatorio(heroi);

        state.capituloAtual = 1;
        state.rotaEscolhida = null;
        state.heroiSnap = SaveState.snapFrom(heroi);
        SaveService.save(state);

        Inimigo lobo = Inimigo.criarLoboDaNevoa(1);
        narrativa("Entre a névoa, um vulto surge: um Lobo da Névoa com olhos cintilantes aparece!");
        state.inimigoSnap = SaveState.snapFrom(lobo);
        SaveService.save(state);

        SistemaDeCombate.batalhar(heroi, lobo);
        if (!heroi.estaVivo()) encerrar("O frio da névoa consome sua última fagulha... Fim de jogo.");

        // ================== ESCOLHA DE ROTA ==================
        narrativa("\nEntre ruínas à esquerda e o bosque à direita, qual caminho seguir?");
        int escolha = menu("1) Ruínas antigas", "2) Bosque alquímico");
        state.rotaEscolhida = escolha;
        state.heroiSnap = SaveState.snapFrom(heroi);
        SaveService.save(state);

        if (escolha == 1) {
            // ======= CAPÍTULO 2A — RUÍNAS =======
            esperarEnter("\n[Capítulo 2A] As Ruínas Silenciosas — ENTER");
            narrativa("Você adentra corredores cobertos de musgo. Runas antigas brilham nas paredes.");
            narrativa("Você decide olhar em volta para ver se encontra alguma relíquia perdida entre os corredores.");
            eventoAleatorio(heroi);

            state.capituloAtual = 2;
            state.heroiSnap = SaveState.snapFrom(heroi);
            SaveService.save(state);

            Inimigo golem = Inimigo.criarGolemEnfeiticado(2);
            narrativa("Um estrondo ecoa — um Golem Enfeitiçado desperta das pedras e avança!");
            state.inimigoSnap = SaveState.snapFrom(golem);
            SaveService.save(state);

            SistemaDeCombate.batalhar(heroi, golem);
            if (!heroi.estaVivo()) encerrar("As pedras colapsam ao seu redor... Fim de jogo.");
        } else {
            // ======= CAPÍTULO 2B — BOSQUE =======
            esperarEnter("\n[Capítulo 2B] Bosque Alquímico — ENTER");
            narrativa("As árvores se curvam, os galhos parecem observá-lo. Frascos pendem das copas.");
            narrativa("Ao longe você vê uma das árvores com um buraco no meio. Ao olhar dentro dela...");
            eventoAleatorio(heroi);

            state.capituloAtual = 2;
            state.heroiSnap = SaveState.snapFrom(heroi);
            SaveService.save(state);

            Inimigo corvo = Inimigo.criarCorvoAlquimico(2);
            narrativa("Um grasnar metálico corta o ar — um Corvo Alquímico surge das sombras!");
            state.inimigoSnap = SaveState.snapFrom(corvo);
            SaveService.save(state);

            SistemaDeCombate.batalhar(heroi, corvo);
            if (!heroi.estaVivo()) encerrar("As folhas murmuram sua queda... Fim de jogo.");
        }

        // ================== CAPÍTULO 3 ==================
        esperarEnter("\n[Capítulo 3] Pântano das Lamentações — ENTER");
        narrativa("Você alcança o pântano. A água turva ferve com uma energia esverdeada.");
        narrativa("Andando pelos lamaçais do pântano, você se depara com algo se mexendo na água...");
        eventoAleatorio(heroi);

        state.capituloAtual = 3;
        state.heroiSnap = SaveState.snapFrom(heroi);
        SaveService.save(state);

        Inimigo bruxa = Inimigo.criarBruxaDoPantano(3);
        narrativa("Das sombras, surge uma velha de olhos vazios: 'Só passarás se sobreviveres, forasteiro!'");
        state.inimigoSnap = SaveState.snapFrom(bruxa);
        SaveService.save(state);

        SistemaDeCombate.batalhar(heroi, bruxa);
        if (!heroi.estaVivo()) encerrar("O pântano guarda mais um segredo no fundo... Fim de jogo.");

        // ================== CAPÍTULO FINAL ==================
        esperarEnter("\n[Capítulo Final] Laboratório Oculto — ENTER");
        narrativa("As ruínas levam a uma câmara subterrânea repleta de tubos, frascos e máquinas.");
        narrativa("No centro, um homem de jaleco manchado de sangue observa calmamente.");

        state.capituloAtual = 4;
        state.heroiSnap = SaveState.snapFrom(heroi);
        SaveService.save(state);

        Inimigo chefe = Inimigo.oAlquimista(4);
        narrativa("'A curiosidade te trouxe até aqui. A coragem te fará ficar.', diz o Alquimista.");
        state.inimigoSnap = SaveState.snapFrom(chefe);
        SaveService.save(state);

        SistemaDeCombate.batalhar(heroi, chefe);
        if (!heroi.estaVivo()) encerrar("A lâmpada apaga. A experiência chegou ao fim... Fim de jogo.");

        // ================== FIM ==================
        narrativa("\nA luz retorna à floresta. O nevoeiro recua.");
        narrativa("Você libertou a Ravina da Névoa e derrotou o Alquimista!");
        System.out.println("\nPARABÉNS, " + heroi.getNome().toUpperCase() + "! Você venceu o jogo!");
    }

    // ================== AUXILIARES ==================

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
        heroi.getInventario().adicionar(new Item("Poção de Cura - +20 de vida",    TipoEfeito.CURA,        3));
        heroi.getInventario().adicionar(new Item("Elixir de Foco - +2 de ataque",  TipoEfeito.BUFF_ATAQUE, 1));
        heroi.getInventario().adicionar(new Item("Manto Reforçado - +2 de defesa", TipoEfeito.BUFF_DEFESA, 1));
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
            System.out.println("⚠️  Uma armadilha foi acionada! Você perde " + dano + " de vida.");
        } else if (rolagem <= 40) {
            heroi.getInventario().adicionar(new Item("Poção de Cura - +20 de vida", TipoEfeito.CURA, 1));
            System.out.println("🪙 Você encontrou uma bolsa caída: +1 Poção de Cura!");
        } else {
            System.out.println("Nada de interessante encontrado durante a exploração.");
        }
    }

    private static void encerrar(String msg) {
        System.out.println("\n" + msg);
        System.exit(0);
    }
}
