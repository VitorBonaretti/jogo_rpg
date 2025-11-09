package br.com.rpg.modelo.personagem;

public class Mago extends Personagem{
    public Mago(String nome){
        super(nome, 20, 8, 2, 1);
    }

    @Override
    public void atacar(Personagem alvo){
        int dano = this.ataque - alvo.defesa;
        if(dano < 0) dano = 0;
        alvo.receberDano(dano);
        System.out.println(nome + " ataca com a espada e causa " + dano + " de dano!");
    }
}