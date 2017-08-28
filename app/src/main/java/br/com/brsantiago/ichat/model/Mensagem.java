package br.com.brsantiago.ichat.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bruno.oliveira on 27/01/2017.
 */
public class Mensagem {

    private int id;
    @SerializedName("text")
    private String texto;

    public Mensagem(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
