package br.ifsc.edu.maxipe.maxipe.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.controller.AuxiliadoController;
import br.ifsc.edu.maxipe.maxipe.controller.MainValidacoes;
import br.ifsc.edu.maxipe.maxipe.controller.ResponsavelController;
import br.ifsc.edu.maxipe.maxipe.controller.ValidaLogin;
import br.ifsc.edu.maxipe.maxipe.model.Auxiliados;
import br.ifsc.edu.maxipe.maxipe.model.Responsaveis;

public class MainItemAbaAddAux extends AppCompatActivity {


    private boolean isConnected;
    private DatabaseReference myRef;
    private TextView codigoText;
    private EditText aux_nome;
    private EditText aux_email;
    private EditText aux_contato;
    private final Context context = this;
    private AlertDialog dialog;
    private AuxiliadoController controllerAux;
    private ResponsavelController controllerResp;
    private String a=null;
    private String codigo;
    private Auxiliados auxiliados;
    private Auxiliados novoAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resp_item_aba_add_aux);

        inicializacaoCampos();
        verificaConcexao();

        aux_contato.addTextChangedListener(MainCadastroResp.MaskEditUtil.mask(aux_contato, MainCadastroResp.MaskEditUtil.FORMAT_FONE));

    }

    private void verificaConcexao() {
        ConnectivityManager cm = (ConnectivityManager) MainItemAbaAddAux.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private void inicializacaoCampos() {
        this.aux_nome = findViewById(R.id.nomeCompletoAux_EditText);
        this.aux_email = findViewById(R.id.emailAux_EditText);
        this.aux_contato = findViewById(R.id.telefoneAux_EditText);
        this.codigoText = findViewById(R.id.textViewCodigo);
        this.controllerAux = new AuxiliadoController(this);
        this.controllerResp = new ResponsavelController(this);
        this.codigoText.setText(retornaResp().getRes_codigoGerado());
        this.myRef = FirebaseDatabase.getInstance().getReference();


    }

    private Responsaveis retornaResp() {
        Responsaveis resp = getIntent().getParcelableExtra("responsavel");
        return resp;
    }

    public void cadastroAux(View view) {
        if (isConnected) {
            if (controllerResp.retornarCodigoPorId(retornaResp().getRes_id()) == null) {
                if (validaResponsavel()) {
                    auxiliados = new Auxiliados(1,
                            aux_nome.getText().toString(),
                            aux_email.getText().toString(),
                            aux_contato.getText().toString(),
                            retornaResp().getRes_id());
                    boolean a = controllerAux.addAux(auxiliados);
                    if (a) {
                        novoAux = controllerAux.retornarAuxPorContato(aux_contato.getText().toString());
                        myRef.child("Auxiliados").child(String.valueOf(novoAux.getAux_id())).setValue(novoAux);
                        dialog();

                    } else {
                        Snackbar.make(findViewById(R.id.layoutConstraint), getString(R.string.erro_cadastro_aux), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(findViewById(R.id.layoutC), getString(R.string.erro_cadastro), Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(findViewById(R.id.layoutConstraint), R.string.nao_cadastro_aux, Snackbar.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(MainItemAbaAddAux.this, "NÃO HÁ CONEXÃO, RETORNANDO PARA O INÍCIO.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainTelaIdentificacao.class);
            startActivity(intent);
        }
    }

    private boolean validaResponsavel() {
        boolean formComErro = false;
        MainValidacoes validacoes = new MainValidacoes();
        String nomeEdit = aux_nome.getText().toString();
        String emailEdit = aux_email.getText().toString();
        String telefoneEdit = aux_contato.getText().toString();

        if (validacoes.nomeErrado(nomeEdit)) {
            formComErro = true;
            aux_nome.setError("Nome Inválido");
        }
        if (validacoes.emailErrado(emailEdit)){
            formComErro = true;
            aux_email.setError("Email Inválido");
        }

        if (controllerAux.emailExistente(emailEdit)){
            formComErro = true;
            aux_email.setError(getString(R.string.email_existente));
        }

        if(validacoes.telefoneErrado(telefoneEdit)){
            formComErro =  true;
            aux_contato.setError("Telefone Inválido");
        }

        if (controllerAux.telefoneExistente(telefoneEdit)) {
            formComErro = true;
            aux_contato.setError(getString(R.string.telefone_existente));
        }

        if (formComErro) {
            Toast.makeText(MainItemAbaAddAux.this, "Erro as cadastrar", Toast.LENGTH_LONG).show();
            return false;
        } else {

            return true;
        }
    }

    public void ItemAddAux_voltaMenuResp(View view) {
//        Intent voltarProMenuResp = new Intent(this, MainAbaOpcoes_Menu_resp.class);
//        startActivity(voltarProMenuResp);
        finish();
    }

    private int idUltimoAUx() {
        return controllerAux.idUltimoAuxCAdastrado();
    }

    private String dialog() {
        a = null;
        codigo = controllerAux.sorteio(a);
        final View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog_custom, null);
        final TextView conteudo = customLayout.findViewById(R.id.conteudo);

        conteudo.setText(codigo);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Código necessário para login do auxiliado");
        builder.setView(customLayout);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                controllerAux.insereCodigo(codigo, idUltimoAUx());
                controllerAux.isereCodigoNaTabela(codigo, idUltimoAUx());
                controllerResp.addCodigo(codigo, novoAux.getAux_fkRes());
                codigoText.setText(codigo);
                myRef.child("Auxiliados").child(String.valueOf(novoAux.getAux_id())).setValue(controllerAux.retornarAuxPorContato(novoAux.getAux_contato()));
            }
        });
        dialog = builder.create();
        dialog.show();
        return codigo;
    }

}
