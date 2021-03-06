package br.edu.ifspsaocarlos.sdm.mensageirows.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zigui on 22/03/2018.
 */

public class Mensagem {

    private String id;
    @SerializedName("origem_id")
    private String origemId;
    @SerializedName("destino_id")
    private String destinoId;
    private String assunto;
    private String corpo;
    private Contato origem;
    private Contato destino;

    public Mensagem(String id, String origemId, String destinoId, String assunto, String corpo,
                    Contato origem, Contato destino) {
        this.id = id;
        this.origemId = origemId;
        this.destinoId = destinoId;
        this.assunto = assunto;
        this.corpo = corpo;
        this.origem = origem;
        this.destino = destino;
    }

    public Mensagem(String origemId, String destinoId, String assunto, String corpo) {
        this.origemId = origemId;
        this.destinoId = destinoId;
        this.assunto = assunto;
        this.corpo = corpo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigemId() {
        return origemId;
    }

    public void setOrigemId(String origemId) {
        this.origemId = origemId;
    }

    public String getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(String destinoId) {
        this.destinoId = destinoId;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public Contato getOrigem() {
        return origem;
    }

    public void setOrigem(Contato origem) {
        this.origem = origem;
    }

    public Contato getDestino() {
        return destino;
    }

    public void setDestino(Contato destino) {
        this.destino = destino;
    }

    @Override
    public String toString() {
        return "Mensagem{" +
                "id='" + id + '\'' +
                ", origemId='" + origemId + '\'' +
                ", destinoId='" + destinoId + '\'' +
                ", assunto='" + assunto + '\'' +
                ", corpo='" + corpo + '\'' +
                ", origem=" + origem +
                ", destino=" + destino +
                '}';
    }
}
