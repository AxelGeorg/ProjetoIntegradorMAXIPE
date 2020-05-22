package br.ifsc.edu.maxipe.maxipe.controller;

public class ValidaLembrete {

    public boolean camposComErro(String hora) {
        if (hora==null || hora.trim().isEmpty()) {
            return true;
        }
        return false;
    }


}
