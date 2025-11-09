package br.com.rpg.modelo.personagem;

import br.com.rpg.modelo.item.*;

public abstract class Personagem{
    protected String nome;
    protected int pontosVida;
    protected int ataque;
    protected int defesa;
    protected int nivel;
    protected Inventario inventario;

    public Personagem(String nome, int pontosVida, int ataque, int defesa, int nivel, Inventario inventario){
        this.nome = nome;
        this.pontosVida = pontosVida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
        this.inventario = inventario;
    }

    public Personagem(String nome, int pontosVida, int ataque, int defesa, int nivel){
        this(nome, pontosVida, ataque, defesa, nivel, new Inventario());
    }

    public void receberDano(int dano){
        pontosVida -= dano;
        if (pontosVida < 0) pontosVida = 0;
    }

    public boolean estaVivo(){
        return pontosVida > 0;
    }

    public abstract void atacar(Personagem alvo);

    public void usarItem(String nomeItem){
        Item item = inventario.buscarItem(nomeItem);
        int totalCura;

        if(item == null){
            System.out.println(nome + " nao possui o item " + nomeItem + ".");
            return;
        }

        switch (item.getEfeito()){
            
            case CURA -> {
                totalCura = 10 + (10 * nivel);
                pontosVida += totalCura;
                System.out.println(nome + " usou " + nomeItem + " e recuperou " + totalCura + " de vida!");
            }
            case BUFF_ATAQUE -> {
                ataque += 2;
                System.out.println(nome + " usou " + nomeItem + " e aumentou seu ataque em 2!");
            }
            case BUFF_DEFESA -> {
                defesa += 2;
                System.out.println(nome + " usou " + nomeItem + " e aumentou sua defesa em 2!");
            }
            case DANO_DIRETO -> {
                System.out.println(nome + " usou " + nomeItem + ", mas precisa de um alvo!");
                return;
            }
        }

        inventario.remover(nomeItem, 1);
    }

    public void exibirStatus(){
        System.out.println(nome + " Vida: " + pontosVida + " | Ataque: " + ataque + " | Defesa: " + defesa + " | Nivel: " + nivel);
    }

}