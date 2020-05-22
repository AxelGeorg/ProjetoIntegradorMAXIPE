package br.ifsc.edu.maxipe.maxipe.controller;

import android.content.Context;

import java.util.List;

import br.ifsc.edu.maxipe.maxipe.model.*;

public class LembretesController {
    Context mContext;
    LembretesDAO lemDAO;

    public LembretesController(Context context) {
        this.mContext = context;
        lemDAO = new LembretesDAO(this.mContext);
    }

    public boolean salvarLembrete(Lembretes lem, Responsaveis resp, Auxiliados aux) {
        return lemDAO.inserirLembrete(lem, resp, aux) > 0;
    }

    public List<Lembretes> retornaListaLembretes(int idResp, int idAux){
        return lemDAO.recuperaListaLembretes(idResp, idAux);
    }

    public void excluiLembrete(int id) {
        lemDAO.excluiLembrete(id);
    }

    public void updateInfos(Lembretes lembretes) {
        lemDAO.updateInfosDAO(lembretes);
    }

    public boolean lembreteExisteEdicao(int dia, int mes, int ano, int hora, int minuto, String titulo) {
        return lemDAO.lembreteExistenteEdicao(dia, mes, ano, hora, minuto, titulo);
    }

    public boolean lembreteExisteInclusao(int dia, int mes, int ano, int hora, int minuto) {
        return lemDAO.lembreteExistenteInclusao(dia, mes, ano, hora, minuto);
    }

    public int idUltimoLembrete() {
        return lemDAO.idUltimoLembrete();
    }

    public int retonaTamanhoLista() {
        return lemDAO.retornaTamanhoLista(); //3j5w
    }

    public Lembretes retornaLembretePeloResp(int idresp, int idAux) {
        return lemDAO.retornaLembretePeloId(idresp, idAux);
    }
}
