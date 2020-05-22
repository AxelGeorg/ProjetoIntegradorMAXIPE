package br.ifsc.edu.maxipe.maxipe.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.controller.AuxiliadoController;
import br.ifsc.edu.maxipe.maxipe.controller.ResponsavelController;
import br.ifsc.edu.maxipe.maxipe.model.Responsaveis;


public class MainItemAbaPerfilResp extends AppCompatActivity {

    
    private TextView nome;
    private TextView telefone;
    private TextView email;
    private TextView auxiliado;
    private AuxiliadoController auxController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resp_item_perfil);

        inicializacaoCampos();

        nome.setText(retornaResp().getRes_nome());
        telefone.setText(retornaResp().getRes_telefone());
        email.setText(retornaResp().getRes_email());
        if (retornaNomeAux() != null) {
            auxiliado.setText(retornaNomeAux() + " -> " + retornaCodigoAux());
        } else {
            auxiliado.setText("Auxiliado");
        }
    }

    private Responsaveis retornaResp() {
        Responsaveis resp = getIntent().getParcelableExtra("responsavel");
        return resp;
    }

    private String retornaNomeAux() {
        String nome = auxController.retornaNomeAux(retornaResp().getRes_id());
        return nome;
    }

    private String retornaCodigoAux() {
        String codigo = auxController.retornaCodigo(retornaResp().getRes_id());
        return codigo;
    }

    private void inicializacaoCampos() {
        this.nome = findViewById(R.id.textViewNome);
        this.telefone = findViewById(R.id.textViewTelefone);
        this.email = findViewById(R.id.textViewEmail);
        this.auxiliado = findViewById(R.id.textViewAux);
        this.auxController = new AuxiliadoController(MainItemAbaPerfilResp.this);
    }

    public void ItemPerfilResp_voltaMenuResp (View view){
        finish();
    }
}
