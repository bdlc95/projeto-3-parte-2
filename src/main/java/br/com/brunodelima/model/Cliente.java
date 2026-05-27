package br.com.brunodelima.model;

public class Cliente {

    private String nome;
    private String cpf;
    private String email;
    private String telefone; // campo novo

    public Cliente(String nome, String cpf, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }

    public String getNome()     { return nome; }
    public String getCpf()      { return cpf; }
    public String getEmail()    { return email; }
    public String getTelefone() { return telefone; }

    @Override
    public String toString() {
        return "Cliente{nome='" + nome + "', cpf='" + cpf +
                "', email='" + email + "', telefone='" + telefone + "'}";
    }
}