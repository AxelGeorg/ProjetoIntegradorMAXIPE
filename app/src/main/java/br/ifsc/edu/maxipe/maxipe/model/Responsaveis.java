package br.ifsc.edu.maxipe.maxipe.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Responsaveis implements Parcelable {
    private Integer res_id;
    private String res_nome;
    private String res_email;
    private String res_telefone;
    private String res_senha;
    private String res_confirmarsenha;
    private String res_codigoGerado;

    public Responsaveis(Integer res_id, String res_nome, String res_email, String res_telefone, String res_senha, String res_codigoGerado) {
        this.res_id = res_id;
        this.res_nome = res_nome;
        this.res_email = res_email;
        this.res_telefone = res_telefone;
        this.res_senha = res_senha;
        this.res_codigoGerado = res_codigoGerado;
    }

    public Responsaveis(String res_nome, String res_email, String res_telefone, String res_senha) {
        this.res_nome = res_nome;
        this.res_email = res_email;
        this.res_telefone = res_telefone;
        this.res_senha = res_senha;
    }

    public Responsaveis(Integer id, String res_nome, String res_email, String res_telefone, String res_senha) {
        this.res_id = id;
        this.res_nome = res_nome;
        this.res_email = res_email;
        this.res_telefone = res_telefone;
        this.res_senha = res_senha;
    }

    public Responsaveis(String res_email, String res_senha) {
        this.res_email = res_email;
        this.res_senha = res_senha;
    }

    public Responsaveis() {

    }

    public Integer getRes_id() {
        return res_id;
    }

    public String getRes_nome() {

        return res_nome;
    }

    public String getRes_email() {

        return res_email;
    }

    public String getRes_telefone() {

        return res_telefone;
    }

    public String getRes_senha() {

        return res_senha;
    }

    public String getRes_confirmarsenha() {

        return res_confirmarsenha;
    }

    public String getRes_codigoGerado() {
        return res_codigoGerado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.res_id);
        dest.writeString(this.res_nome);
        dest.writeString(this.res_email);
        dest.writeString(this.res_telefone);
        dest.writeString(this.res_senha);
        dest.writeString(this.res_confirmarsenha);
        dest.writeString(this.res_codigoGerado);
    }

    protected Responsaveis(Parcel in) {
        this.res_id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.res_nome = in.readString();
        this.res_email = in.readString();
        this.res_telefone = in.readString();
        this.res_senha = in.readString();
        this.res_confirmarsenha = in.readString();
        this.res_codigoGerado = in.readString();
    }

    public static final Parcelable.Creator<Responsaveis> CREATOR = new Parcelable.Creator<Responsaveis>() {
        @Override
        public Responsaveis createFromParcel(Parcel source) {
            return new Responsaveis(source);
        }

        @Override
        public Responsaveis[] newArray(int size) {
            return new Responsaveis[size];
        }
    };
}
