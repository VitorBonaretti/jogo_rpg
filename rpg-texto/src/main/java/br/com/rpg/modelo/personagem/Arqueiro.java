package br.com.rpg.modelo.personagem;

public class Arqueiro extends Personagem{
    public Arqueiro(String nome){
        super(nome, 28, 6, 5, 1);
    }

    @Override
    public void atacar(Personagem alvo){
        int dano = this.ataque - alvo.defesa;
        if(dano < 0) dano = 0;
        alvo.receberDano(dano);
        System.out.println(nome + " ataca com a espada e causa " + dano + " de dano!");
    }
}