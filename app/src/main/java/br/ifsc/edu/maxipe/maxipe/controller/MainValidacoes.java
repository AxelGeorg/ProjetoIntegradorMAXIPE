package br.ifsc.edu.maxipe.maxipe.controller;

public class MainValidacoes {

    public boolean nomeErrado (String s) {
        if (s == null || s.trim().isEmpty()){
            return true;
        }
      try {
           if (s.matches("[*0-9]")) {
               return true;
           }
       } catch (NumberFormatException e) {
           return true;
        }
        return false;
    }

    public boolean emailErrado(String s){
        if (s== null || s.trim().isEmpty()){
            return true;
        }
       return false;
    }

    public boolean telefoneErrado(String s){
        if (s== null || s.trim().isEmpty() || s.length()<=7 || s.length()>14){
            return true;
        }
        return false;
    }

    public boolean senhaErrada(String s){
        if (s== null || s.trim().isEmpty()){
            return true;
        }
        return false;
    }

    public boolean senhasDiferentes(String s,String t){
        if (!s.equals(t)){
            return true;
        }
        return false;
    }


}
