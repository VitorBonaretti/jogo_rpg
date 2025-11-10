package br.com.rpg.modelo.personagem;

import br.com.rpg.modelo.item.*;
import br.com.rpg.sistema.*;

public class Inimigo extends Personagem{
    private final String especie;
    private IAComportamento ia;

    public enum IAComportamento{
        AGRESSIVO,
        CAUTELOSO
    }

    public Inimigo(String especie, String nome, int pontosVida, int ataque, int defesa, int nivel){
        super(nome, pontosVida, ataque, defesa, nivel);
        this.especie = especie;
        this.ia = IAComportamento.AGRESSIVO;
    }

    public String getEspecie(){
        return especie;
    }

    public IAComportamento getIa(){
        return ia;
    }

    public void setIa(IAComportamento ia){
        this.ia = ia;
    }

    @Override
    public void atacar(Personagem alvo){
        int rolagem = Dado.rolar(6);
        int dano = (this.ataque + rolagem) - alvo.getDefesa();

        if (dano < 0) dano = 0;

        alvo.receberDano(dano);
        System.out.println(nome + " ataca " + alvo.getNome() + " e causa " + dano + " de dano!");
    }

    public static Inimigo criarLoboDaNevoa(int nivel){
        Inimigo lobo = new Inimigo("Lobo da Nevoa", "Lobo", 14 + 2*nivel,5 + nivel, 2 + nivel/2, nivel);
        lobo.getInventario().adicionar(new Item("Fatiar", TipoEfeito.DANO_DIRETO, 1));
        return lobo;
    }

    public static Inimigo criarCorvoAlquimico(int nivel){
        Inimigo corvo = new Inimigo("Corvo Alquimico", "Corvo", 12 + 2*nivel, 6 + nivel, 1 + nivel/2, nivel);
        corvo.getInventario().adicionar(new Item("Tonico de aço", TipoEfeito.BUFF_ATAQUE, 1));
        return corvo;
    }

    public static Inimigo criarBruxaDoPantano(int nivel){
        Inimigo bruxa = new Inimigo("Bruxa do Pantano", "Bruxa", 16 + 2*nivel, 7 + nivel, 1 + nivel/2 + nivel, nivel);
        bruxa.getInventario().adicionar(new Item("Agua da Vida", TipoEfeito.CURA, 1));
        return bruxa;
    }

    public static Inimigo criarGolemEnfeiticado(int nivel){
        Inimigo golem = new Inimigo("Golem Enfeiticado", "Golem", 22 + 2*nivel, 4 + nivel, 4 + nivel/2 + nivel, nivel);
        golem.getInventario().adicionar(new Item("Carapaça de Pedra", TipoEfeito.BUFF_DEFESA, 1));
        return golem;
    }

    public static Inimigo oAlquimista(int nivel){
        Inimigo alquimista = new Inimigo("Chefe final", "Alquimista", 35, 10, 7, nivel);
        return alquimista;
    }

    public enum AcaoIA{
        ATACAR,
        USAR_ITEM,
        PASSAR
    }

    public AcaoIA decidirAcao(Personagem jogador, Dado dado){
        if(this.pontosVida <=(int)(0.3 * this.pontosVida) && inventario.temItemDe(TipoEfeito.CURA)){
            if(Dado.rolar(100) <= 35) return AcaoIA.USAR_ITEM;
        }

        if(this.pontosVida <=(int)(0.3 * this.pontosVida) && inventario.temItemDe(TipoEfeito.BUFF_ATAQUE)){
            if(Dado.rolar(100) <= 25) return AcaoIA.USAR_ITEM;
        }

        if(this.pontosVida <=(int)(0.3 * this.pontosVida) && inventario.temItemDe(TipoEfeito.BUFF_DEFESA)){
            if(Dado.rolar(100) <= 40) return AcaoIA.USAR_ITEM;
        }

        if(this.pontosVida <=(int)(0.3 * this.pontosVida) && inventario.temItemDe(TipoEfeito.DANO_DIRETO)){
            if(Dado.rolar(100) <= 20) return AcaoIA.USAR_ITEM;
        }

        return AcaoIA.ATACAR;
    }

}