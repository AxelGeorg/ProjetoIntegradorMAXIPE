package br.ifsc.edu.maxipe.maxipe.controller;

public class ValidacoesCadastrarAux {

    public boolean nomeComErro(String valor) {
        if (valor==null || valor.trim().isEmpty()) {
            return true;
        }try {
            if (valor.matches("[*0-9]")) {
                return true;
            }
        }catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    public boolean emailComErro(String valor) {
        if (valor==null || valor.trim().isEmpty()) {
            return true;
        }
        return false;
    }
}
