package br.ifsc.edu.maxipe.maxipe.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.controller.AlarmReceiver;
import br.ifsc.edu.maxipe.maxipe.controller.ResponsavelController;
import br.ifsc.edu.maxipe.maxipe.controller.ValidaLogin;
import br.ifsc.edu.maxipe.maxipe.model.Responsaveis;

public class MainLoginResp extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    ResponsavelController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_resp);

        inicializacaoCampos();

    }

    private void inicializacaoCampos() {
        this.email = findViewById(R.id.editTextEmail);
        this.senha = findViewById(R.id.editTextSenha);

        this.controller = new ResponsavelController(MainLoginResp.this);
    }

    public void voltarTela1Resp(View view){
        Intent voltarTela1Resp = new Intent(this, MainPrimeiraTelaResp.class);
        startActivity(voltarTela1Resp);
        finish();
    }

    public void EntrarResp(View view) {
        String emailStr = email.getText().toString();
        String senhaStr = senha.getText().toString();
        if (emailStr.trim().isEmpty() ||senhaStr.trim().isEmpty()) {
            Snackbar.make(findViewById(R.id.layoutConstraint), getString(R.string.usuario_invalido), Snackbar.LENGTH_LONG).show();
        } else {
            Responsaveis r = new Responsaveis(emailStr, senhaStr);
            boolean verificacao = controller.recuperarResp(r, emailStr, senhaStr);
            if (verificacao) {
                Integer numero = controller.recuperarId(r, emailStr);
                Intent intent = new Intent(MainLoginResp.this, MainAbaOpcoes_Menu_resp.class);
                intent.putExtra("responsavel", controller.retornaResp(numero));
                startActivity(intent);
                AlarmReceiver.setResp(true);
                finish();
            } else {
                Snackbar.make(findViewById(R.id.layoutConstraint), getString(R.string.usuario_invalido), Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
