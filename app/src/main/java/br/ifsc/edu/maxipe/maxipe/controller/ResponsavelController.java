package br.ifsc.edu.maxipe.maxipe.controller;

import android.content.Context;

import br.ifsc.edu.maxipe.maxipe.model.Responsaveis;
import br.ifsc.edu.maxipe.maxipe.model.ResponsavelDAO;

public class   ResponsavelController {
    ResponsavelDAO resDAO;
    Context mContext;

    public ResponsavelController(Context context) {
        this.mContext = context;
        resDAO = new ResponsavelDAO(this.mContext);

    }

    public boolean salvarResp(Responsaveis resp) {
        if (resDAO.inserir(resp) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean salvarRespFireBase(Responsaveis resp){
        if (resDAO.inserirFireBase(resp) > 0)
            return true;
        else
            return false;
    }

    public boolean verificaSeJaHaEsseIdNoBanco (int id){
        if(resDAO.compararIdFirebase(id) > 0){
            return true;
        }else{
            return false;
        }
    }
    public boolean recuperarResp(Responsaveis resp, String email, String senha) {
        String emailValido = resDAO.recuperarEmail(resp, email);
        String senhaValido = resDAO.recuperarSenha(senha, email);
        if (emailValido != null && senhaValido!=null) {
            if (resp.getRes_email().equals(email) && resp.getRes_senha().equals(senha)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Integer recuperarId(Responsaveis resp, String email) {
        Integer id = resDAO.recuperarId(resp, email);
        if (id != null) {
            return id;
        }
        return null;
    }

    public boolean emailExistente(String email) {
        if (resDAO.emailExistente(email)){
            return true;
        }
        return false;
    }

    public void addCodigo(String codigo, int id){
        resDAO.inserirCodigoResp(codigo, id);
    }

    public Responsaveis retornaResp(int id) {
        return resDAO.retornaResp(id);
    }

    public boolean telefoneExistente(String telefone) {
        if (resDAO.telefoneExistente(telefone)) {
            return true;
        }
        return false;
    }

    public boolean verificaSeHaCodigo(String codigo){
        int verificacao = resDAO.validaCodigo(codigo);
        if (verificacao > 0){
            return true;
        }else{
            return false;
        }
    }

    public String retornarCodigoPorId (int id){
        return resDAO.retornarCodigoPorId(id);
    }

    public Responsaveis retornaRespPorTelefone (String telefone){
        return resDAO.retornaRespPorTelefone(telefone);
    }

}

