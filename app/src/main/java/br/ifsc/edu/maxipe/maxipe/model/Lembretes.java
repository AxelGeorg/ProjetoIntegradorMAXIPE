package br.ifsc.edu.maxipe.maxipe.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Lembretes implements Parcelable {

    private int lem_id;
    private String lem_tituloText;
    private int lem_hora;
    private int lem_minuto;
    private int lem_dia;
    private int lem_mes;
    private int lem_ano;
    private int lem_fkRes;
    private int lem_fkAux;

    public Lembretes(int lem_id, String lem_tituloTxt, int lem_hora, int lem_minuto, int lem_dia, int lem_mes, int lem_ano) {
        this.lem_id = lem_id;
        this.lem_tituloText = lem_tituloTxt;
        this.lem_hora = lem_hora;
        this.lem_minuto = lem_minuto;
        this.lem_dia = lem_dia;
        this.lem_mes = lem_mes;
        this.lem_ano = lem_ano;
    }

    public int getLem_id() {
        return lem_id;
    }

    public String getLem_tituloText() {
        return lem_tituloText;
    }

    public int getLem_hora() {
        return lem_hora;
    }

    public int getLem_minuto() {
        return lem_minuto;
    }

    public int getLem_dia() {
        return lem_dia;
    }

    public int getLem_mes() {
        return lem_mes;
    }

    public int getLem_ano() {
        return lem_ano;
    }

    public int getLem_fkRes() {
        return lem_fkRes;
    }

    public int getLem_fkAux() {
        return lem_fkAux;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.lem_id);
        dest.writeString(this.lem_tituloText);
        dest.writeInt(this.lem_hora);
        dest.writeInt(this.lem_minuto);
        dest.writeInt(this.lem_dia);
        dest.writeInt(this.lem_mes);
        dest.writeInt(this.lem_ano);
    }

    protected Lembretes(Parcel in) {
        this.lem_id = in.readInt();
        this.lem_tituloText = in.readString();
        this.lem_hora = in.readInt();
        this.lem_minuto = in.readInt();
        this.lem_dia = in.readInt();
        this.lem_mes = in.readInt();
        this.lem_ano = in.readInt();
    }

    public static final Parcelable.Creator<Lembretes> CREATOR = new Parcelable.Creator<Lembretes>() {
        @Override
        public Lembretes createFromParcel(Parcel source) {
            return new Lembretes(source);
        }

        @Override
        public Lembretes[] newArray(int size) {
            return new Lembretes[size];
        }
    };
}
