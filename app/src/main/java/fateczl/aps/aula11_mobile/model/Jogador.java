package fateczl.aps.aula11_mobile.model;

import androidx.annotation.NonNull;

public class Jogador {
    private int id;
    private String nome;
    private String dataNasc;
    private float altura;
    private float peso;
    private Time time;

    public Jogador() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return "id: " + id +
                "\nNome: " + nome +
                "\nData Nacimento: " + dataNasc +
                "\nAltura: " + altura +
                "\nPeso: " + peso +
                "\nTime: " + time;
    }
}
