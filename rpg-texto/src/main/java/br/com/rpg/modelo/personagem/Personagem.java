package br.com.rpg.modelo.personagem;

import br.com.rpg.modelo.item.*;
import br.com.rpg.sistema.Dado;

public abstract class Personagem{
    protected String nome;
    protected int pontosVida;
    protected int pontosVidaMax;
    protected int ataque;
    protected int defesa;
    protected int nivel;
    protected Inventario inventario;

    public Personagem(String nome, int pontosVida, int ataque, int defesa, int nivel, Inventario inventario){
        this.nome = nome;
        this.pontosVida = pontosVida;
        this.pontosVidaMax = pontosVida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
        this.inventario = inventario;
    }

    public Personagem(String nome, int pontosVida, int ataque, int defesa, int nivel){
        this(nome, pontosVida, ataque, defesa, nivel, new Inventario());
    }

    public Inventario getInventario(){
        return inventario;
    }

    public String getNome(){
        return nome;
    }

    public int getAtaque(){
        return ataque;
    }

    public int getDefesa(){
        return defesa;
    }


    public int getPontosVida(){
        return pontosVida;
    }

    public int getPontosVidaMax(){ 
        return pontosVidaMax;
    }

    public int getNivel(){
        return nivel;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setAtaque(int ataque){
        this.ataque = ataque;
    }

    public void setDefesa(int defesa){
        this.defesa = defesa;
    }

    public void setPontosVida(int pontosVida){
        this.pontosVida = pontosVida;
    }

    public void setPontosVidaMax(int pontosVidaMax){
        this.pontosVidaMax = pontosVida;
    }

    public void setNivel(int nivel){
        this.nivel = nivel;
    }

    public void receberDano(int dano){
        pontosVida -= dano;
        if (pontosVida < 0) pontosVida = 0;
    }

    public boolean estaVivo(){
        return pontosVida > 0;
    }

    public abstract void atacar(Personagem alvo);

    public void restaurarVidaTotal(){
        this.pontosVida = this.pontosVidaMax;
    }

    public void usarItem(String nomeItem){
        usarItem(nomeItem, null);
    }

    public void usarItem(String nomeItem, Personagem alvo){
        Item item = inventario.buscarItem(nomeItem);
        if(item == null){
            System.out.println(nome + " nao possui o item " + nomeItem + ".");
            return;
        }

        switch (item.getEfeito()){
            case CURA -> {
                int totalCura = 10 + (10 * nivel);
                pontosVida += totalCura;
                if (pontosVida > pontosVidaMax) pontosVida = pontosVidaMax;
                System.out.println(nome + " usou " + nomeItem + " e recuperou " + totalCura + " de vida!");
                inventario.remover(nomeItem, 1);
            }
            case BUFF_ATAQUE -> {
                ataque += 2;
                System.out.println(nome + " usou " + nomeItem + " e aumentou seu ataque em 2!");
                inventario.remover(nomeItem, 1);
            }
            case BUFF_DEFESA -> {
                defesa += 2;
                System.out.println(nome + " usou " + nomeItem + " e aumentou sua defesa em 2!");
                inventario.remover(nomeItem, 1);
            }
            case DANO_DIRETO -> {
                if (alvo == null) {
                    System.out.println(nome + " usou " + nomeItem + ", mas precisa de um alvo!");
                    return;
                }
                int rolagem = Dado.rolar(6);
                int dano = (this.ataque + rolagem) - alvo.getDefesa();
                if (dano < 0) dano = 0;
                alvo.receberDano(dano);
                System.out.println(nome + " usou " + nomeItem + " (d6=" + rolagem + ") e causou " + dano + " de dano em " + alvo.getNome() + "!");
                inventario.remover(nomeItem, 1);
            }
        }
    }

    public void exibirStatus(){
        System.out.println(nome + " Vida: " + pontosVida + "/" + pontosVidaMax + " | Ataque: " + ataque + " | Defesa: " + defesa + " | Nivel: " + nivel);
    }
}