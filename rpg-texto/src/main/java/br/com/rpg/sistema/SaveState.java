package br.com.rpg.sistema;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.rpg.modelo.item.Item;
import br.com.rpg.modelo.item.TipoEfeito;
import br.com.rpg.modelo.personagem.*;

public class SaveState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public int capituloAtual = 1;
    public Integer rotaEscolhida = null;

    public boolean reviveDisponivel = true;

    public PersonagemSnap heroiSnap;
    public PersonagemSnap inimigoSnap;

    public static PersonagemSnap snapFrom(Personagem p) {
        PersonagemSnap s = new PersonagemSnap();

        s.classe = (p instanceof Guerreiro) ? "Guerreiro"
                : (p instanceof Mago) ? "Mago"
                : (p instanceof Arqueiro) ? "Arqueiro"
                : (p instanceof Inimigo) ? "Inimigo"
                : "Personagem";

        s.nome = p.getNome();
        s.pv = p.getPontosVida();
        s.pvMax = p.getPontosVidaMax();
        s.atk = p.getAtaque();
        s.def = p.getDefesa();
        s.nivel = p.getNivel();

        s.itens = new ArrayList<>();
        for (Item i : p.getInventario().listaOrdenada()) {
            ItemSnap is = new ItemSnap();
            is.nome = i.getNome();
            is.efeito = i.getEfeito().name();
            is.quantidade = i.getQuantidade();
            s.itens.add(is);
        }

        if (p instanceof Inimigo in) {
            s.especie = in.getEspecie();
        }

        return s;
    }

    public static Personagem restorePersonagem(PersonagemSnap s) {
        Personagem novo;

        switch (s.classe) {
            case "Guerreiro" -> novo = new Guerreiro(s.nome);
            case "Mago" -> novo = new Mago(s.nome);
            case "Arqueiro" -> novo = new Arqueiro(s.nome);
            case "Inimigo" -> novo = new Inimigo(
                    s.especie != null ? s.especie : "Inimigo",
                    s.nome, s.pvMax, s.atk, s.def, s.nivel);
            default -> novo = new Guerreiro(s.nome);
        }

        while (novo.getPontosVida() > s.pv) {
            novo.receberDano(1);
        }

        for (ItemSnap is : s.itens) {
            novo.getInventario().adicionar(
                    new Item(is.nome, TipoEfeito.valueOf(is.efeito), is.quantidade)
            );
        }

        return novo;
    }

    public static class PersonagemSnap implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        public String classe;
        public String especie;
        public String nome;
        public int pv, pvMax, atk, def, nivel;
        public List<ItemSnap> itens;
    }

    public static class ItemSnap implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        public String nome;
        public String efeito;
        public int quantidade;
    }
}
