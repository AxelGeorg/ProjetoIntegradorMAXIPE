package br.ifsc.edu.maxipe.maxipe.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Auxiliados implements Parcelable {
    private int aux_id;
    private int aux_fkRes;
    private String aux_codigo;
    private String aux_nome;
    private String aux_email;
    private String aux_contato;

    public Auxiliados() {
    }


    public Auxiliados(int aux_id, int aux_fkRes, String aux_codigo, String aux_nome, String aux_email, String aux_contato) {
        this.aux_id = aux_id;
        this.aux_fkRes = aux_fkRes;
        this.aux_codigo = aux_codigo;
        this.aux_nome = aux_nome;
        this.aux_email = aux_email;
        this.aux_contato = aux_contato;
    }

    public Auxiliados(int aux_id, String aux_nome, String aux_email, String aux_contato, int aux_fkResId) {
        this.aux_id = aux_id;
        this.aux_nome = aux_nome;
        this.aux_email = aux_email;
        this.aux_contato = aux_contato;
        this.aux_fkRes = aux_fkResId;
    }

    public String getAux_email() {
        return aux_email;
    }

    public int getAux_id() {
        return aux_id;
    }

    public String getAux_codigo() {
        return aux_codigo;
    }

    public String getAux_nome() {
        return aux_nome;
    }

    public int getAux_fkRes() {
        return aux_fkRes;
    }

    public String getAux_contato() {
        return aux_contato;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.aux_id);
        dest.writeInt(this.aux_fkRes);
        dest.writeString(this.aux_codigo);
        dest.writeString(this.aux_nome);
        dest.writeString(this.aux_email);
        dest.writeString(this.aux_contato);
    }

    protected Auxiliados(Parcel in) {
        this.aux_id = in.readInt();
        this.aux_fkRes = in.readInt();
        this.aux_codigo = in.readString();
        this.aux_nome = in.readString();
        this.aux_email = in.readString();
        this.aux_contato = in.readString();
    }

    public static final Creator<Auxiliados> CREATOR = new Creator<Auxiliados>() {
        @Override
        public Auxiliados createFromParcel(Parcel source) {
            return new Auxiliados(source);
        }

        @Override
        public Auxiliados[] newArray(int size) {
            return new Auxiliados[size];
        }
    };
}
