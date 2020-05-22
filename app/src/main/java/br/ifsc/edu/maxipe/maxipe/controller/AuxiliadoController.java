package br.ifsc.edu.maxipe.maxipe.controller;

import android.content.Context;

import java.util.Random;

import br.ifsc.edu.maxipe.maxipe.model.AuxiliadoDAO;
import br.ifsc.edu.maxipe.maxipe.model.Auxiliados;


public class AuxiliadoController {
    private AuxiliadoDAO auxDAO;
    private Context mContext;

    public AuxiliadoController(Context context) {
        this.mContext = context;
        auxDAO = new AuxiliadoDAO(this.mContext);
    }

    public String sorteio(String a) {
        String letra = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        a = "";
        Random random = new Random();
        int index = -1;
        for (int i = 0; i < 4; i++) {
            index = random.nextInt(letra.length());
            a += letra.substring(index, index + 1);

        }

        return a;
    }

    public boolean addAux(Auxiliados auxiliados) {
        if (auxDAO.inserirAux(auxiliados) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Auxiliados retornaAux(String codigo) {
        return auxDAO.retornaAux(codigo);
    }

    public String retornaNomeAux(int idRes) {
        return auxDAO.retornaNomeAux(idRes);
    }

    public void insereCodigo(String codigo, int id) {
        auxDAO.insereCodigo(codigo, id);
    }

    public int idUltimoAuxCAdastrado() {
        return auxDAO.ultimoAuxCadastrado();
    }

    public boolean emailExistente(String emailEdit) {
        return auxDAO.emailExistente(emailEdit);
    }

    public boolean telefoneExistente(String telefoneEdit) {
        return auxDAO.telefoneExistente(telefoneEdit);
    }

    public boolean verificaSeHaCodigo(String toString) {
        return auxDAO.codigoExistente(toString);
    }

    public Auxiliados retornarAuxPorContato(String contato) {
        return auxDAO.retornaAuxPorContato(contato);
    }

    public int retornaAuxPeloFk(int idRes) {
        return auxDAO.retornaAuxPeloFk(idRes);

    }

    public boolean addAuxFirebase(Auxiliados aux) {
        if (auxDAO.inserirAuxFirebase(aux) > 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isereCodigoNaTabela(String codigo, int idUltimoAUx) {
        if (auxDAO.insereCodigoNaTabela(codigo, idUltimoAUx) > 0) {
            return true;
        } else {
            return false;
        }
    }

        public Auxiliados retornaAux ( int idResp){
            return auxDAO.retornaObjAuxPeloFk(idResp);
        }

        public String retornaCodigo (Integer res_id){
            return auxDAO.retornaCodigo(res_id);

        }
    }

