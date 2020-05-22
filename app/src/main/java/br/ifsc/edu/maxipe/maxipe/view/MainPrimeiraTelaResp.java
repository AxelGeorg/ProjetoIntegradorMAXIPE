package br.ifsc.edu.maxipe.maxipe.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.ifsc.edu.maxipe.maxipe.R;

public class MainPrimeiraTelaResp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primeira_tela_resp);
    }

    public void voltarTela(View view){
        Intent voltarTelaAnterior = new Intent(this, MainTelaIdentificacao.class);
        startActivity(voltarTelaAnterior);
        finish();
    }

    public void telaCadastrar(View view){
        Intent telaCadastrar = new Intent(this, MainCadastroResp.class);
        startActivity(telaCadastrar);
    }

    public void telaLoginResp (View view){
        Intent telaLoginResp = new Intent(this, MainLoginResp.class);
        startActivity(telaLoginResp);
    }
}
