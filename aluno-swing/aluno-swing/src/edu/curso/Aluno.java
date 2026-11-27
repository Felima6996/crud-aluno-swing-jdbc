package edu.curso;

import java.time.LocalDate;

public class Aluno {

    private String ra;
    private String nome;
    private int idade;
    private LocalDate nascimento;

    public Aluno(String ra, String nome, int idade, LocalDate nascimento) {
        this.ra = ra;
        this.nome = nome;
        this.idade = idade;
        this.nascimento = nascimento;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }
}
