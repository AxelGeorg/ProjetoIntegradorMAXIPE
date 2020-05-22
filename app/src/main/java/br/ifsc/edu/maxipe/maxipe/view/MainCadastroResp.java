package br.ifsc.edu.maxipe.maxipe.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.controller.*;
import br.ifsc.edu.maxipe.maxipe.controller.*;
import br.ifsc.edu.maxipe.maxipe.model.*;

public class MainCadastroResp extends AppCompatActivity  {

    private boolean isConnected;
    private DatabaseReference myRef;
    private EditText res_nome;
    private EditText res_email;
    private EditText res_telefone;
    private EditText res_senha;
    private EditText res_confirmarSenha;

    private ResponsavelController controllerRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_resp);

        inicializacaoCampos();

        res_telefone.addTextChangedListener(MaskEditUtil.mask(res_telefone, MaskEditUtil.FORMAT_FONE));
    }

    private void inicializacaoCampos() {
        this.res_nome = findViewById(R.id.nomeCompleto_EditText);
        this.res_email = findViewById(R.id.email_EditText);
        this.res_telefone = findViewById(R.id.telefone_EditText);
        this.res_senha = findViewById(R.id.senha_EditText);
        this.res_confirmarSenha = findViewById(R.id.confirmarSenha_EditText);

        this.controllerRes = new ResponsavelController(MainCadastroResp.this);
        verificaConcexao();
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    private void verificaConcexao() {
        ConnectivityManager cm = (ConnectivityManager) MainCadastroResp.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public void voltarTela(View view) {
        Intent primeiraTelaResp = new Intent(this, MainPrimeiraTelaResp.class);
        startActivity(primeiraTelaResp);
        finish();
    }

    public void CadastrarResp(View view) {
         if(isConnected) {
            Responsaveis r = new Responsaveis(
                    1,
                    res_nome.getText().toString(),
                    res_email.getText().toString(),
                    res_telefone.getText().toString(),
                    res_senha.getText().toString()
            );
            if (validaResponsavel()) {
                if (controllerRes.salvarResp(r)) {
                    Responsaveis novo = controllerRes.retornaRespPorTelefone(res_telefone.getText().toString());

                    myRef.child("Responsaveis").child(novo.getRes_id().toString()).setValue(novo);
                } else {
                    Snackbar.make(findViewById(R.id.layoutC), getString(R.string.erro_cadastro), Snackbar.LENGTH_LONG).show();
                }

            } else {
                Snackbar.make(findViewById(R.id.layoutC), getString(R.string.erro_cadastro), Snackbar.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MainCadastroResp.this, "NÃO HÁ CONEXÃO, RETORNANDO PARA O INÍCIO.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainTelaIdentificacao.class);
            startActivity(intent);
        }
    }

    private boolean validaResponsavel() {
        boolean formComErro = false;
        MainValidacoes validacoes = new MainValidacoes();
        String nomeEdit = res_nome.getText().toString();
        String emailEdit = res_email.getText().toString();
        String telefoneEdit = res_telefone.getText().toString();
        String senhaEdit = res_senha.getText().toString();
        String senhaConfirmarEdit = res_confirmarSenha.getText().toString();

        if (validacoes.nomeErrado(nomeEdit)) {
            formComErro = true;
            res_nome.setError("Nome Inválido");
        }
        if (validacoes.emailErrado(emailEdit)){
            formComErro = true;
            res_email.setError("Email Inválido");
        }
        if (controllerRes.emailExistente(emailEdit)){
            formComErro = true;
            res_email.setError(getString(R.string.email_existente));
        }
        if(validacoes.telefoneErrado(telefoneEdit)){
            formComErro =  true;
            res_telefone.setError("Telefone Inválido");
        }
        if (controllerRes.telefoneExistente(telefoneEdit)) {
            formComErro = true;
            res_telefone.setError(getString(R.string.telefone_existente));
        }
        if(validacoes.senhaErrada(senhaEdit)){
            formComErro = true;
            res_senha.setError("Senha Inválida");
        }
        if(validacoes.senhaErrada(senhaConfirmarEdit)){
            formComErro = true;
            res_confirmarSenha.setError("Senha Inválida");
        }
        if(validacoes.senhasDiferentes(senhaEdit,senhaConfirmarEdit)){
            formComErro = true;
            res_senha.setError("Senha Inválida");
            res_confirmarSenha.setError("Senha Inválida");

        }
        if (formComErro) {
            Snackbar.make(findViewById(R.id.layoutC), getString(R.string.erro_cadastro), Snackbar.LENGTH_LONG).show();
            return false;
        } else {
            Toast.makeText(MainCadastroResp.this, getString(R.string.cadastro_sucesso), Toast.LENGTH_LONG).show();
            Intent primeiraTelaResp = new Intent(this, MainLoginResp.class);
            startActivity(primeiraTelaResp);
            finish();
            return true;
        }
    }

    public abstract static class MaskEditUtil {

        public static final String FORMAT_FONE = "(##)#####-#####";
        public static final String FORMAT_NUMBER = "##:##";

        public static TextWatcher mask(final EditText ediTxt, final String mask) {
            return new TextWatcher() {
                boolean isUpdating;
                String old = "";

                @Override
                public void afterTextChanged(final Editable s) {}

                @Override
                public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {}

                @Override
                public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                    final String str = MaskEditUtil.unmask(s.toString());
                    String mascara = "";
                    if (isUpdating) {
                        old = str;
                        isUpdating = false;
                        return;
                    }
                    int i = 0;
                    for (final char m : mask.toCharArray()) {
                        if (m != '#' && str.length() > old.length()) {
                            mascara += m;
                            continue;
                        }
                        try {
                            mascara += str.charAt(i);
                        } catch (final Exception e) {
                            break;
                        }
                        i++;
                    }
                    isUpdating = true;
                    ediTxt.setText(mascara);
                    ediTxt.setSelection(mascara.length());
                }
            };
        }

        public static String unmask(final String s) {
            return s.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "")
                    .replaceAll("[(]", "").replaceAll("[ ]","").replaceAll("[:]", "").
                            replaceAll("[)]", "");
        }
    }

}
