package br.ifsc.edu.maxipe.maxipe.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.controller.AlarmReceiver;
import br.ifsc.edu.maxipe.maxipe.controller.AuxiliadoController;
import br.ifsc.edu.maxipe.maxipe.controller.ResponsavelController;
import br.ifsc.edu.maxipe.maxipe.controller.ValidaLogin;
import br.ifsc.edu.maxipe.maxipe.model.Auxiliados;

public class MainLoginAux extends AppCompatActivity {
    private EditText codigo;
    private AuxiliadoController auxiliadoController;
    private ResponsavelController responsavelController;
    private Context context;
    private AlertDialog dialog;
    private EditText nomeAuxiliado;
    private EditText emailAuxiliado;
    private EditText contatoAuxiliado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_aux);

        inicialização();
    }

    public void inicialização() {
        this.codigo = findViewById(R.id.editCodigoAux);
        this.auxiliadoController = new AuxiliadoController(this);
        this.responsavelController = new ResponsavelController(this);
        this.context = this;
    } //mq4m

    public void voltarTela(View view) {
        Intent voltarTelaAnterior = new Intent(this, MainTelaIdentificacao.class);
        startActivity(voltarTelaAnterior);
        finish();
    }

    public void irMenuAux(View view) {
        if (auxiliadoController.verificaSeHaCodigo(codigo.getText().toString())) {
            String codigoStr = codigo.getText().toString();
            Intent irMenuAux = new Intent(this, MainAbaOpcoes_Menu_aux.class);
            irMenuAux.putExtra("auxiliado", auxiliadoController.retornaAux(codigoStr));
            startActivity(irMenuAux);
            AlarmReceiver.setResp(false);
            finish();
        }
        if (!responsavelController.verificaSeHaCodigo(codigo.getText().toString())) {
            Snackbar.make(findViewById(R.id.layoutLogin), getString(R.string.codigo_nao_cadastrado), Snackbar.LENGTH_SHORT).show();
        }
    }

    public void librasVoltar(View view) {
        String uriPath;
        final Dialog dialog = new Dialog(MainLoginAux.this);// add here your class name
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_video);//add your own xml with defied with and height of videoview
        final VideoView mVideoView = dialog.findViewById(R.id.videoViewResp);
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.dimAmount = 0;
        dialog.getWindow().setAttributes(lp);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        uriPath = "android.resource://" + getPackageName() + "/raw/voltar_maxipe";
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Uri teste = Uri.parse(uriPath);
        mVideoView.setVideoURI(teste);
        mVideoView.start();
    }

    public void librasInserirCodigo(View view) {
        String uriPath;
        final Dialog dialog = new Dialog(MainLoginAux.this);// add here your class name
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_video);//add your own xml with defied with and height of videoview
        final VideoView mVideoView = dialog.findViewById(R.id.videoViewResp);
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.dimAmount = 0;
        dialog.getWindow().setAttributes(lp);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        uriPath = "android.resource://" + getPackageName() + "/raw/inserir_maxipe";
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Uri teste = Uri.parse(uriPath);
        mVideoView.setVideoURI(teste);
        mVideoView.start();
    }

    public void EntrarCodigo(View view) {
        String uriPath;
        final Dialog dialog = new Dialog(MainLoginAux.this);// add here your class name
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_video);//add your own xml with defied with and height of videoview
        final VideoView mVideoView = dialog.findViewById(R.id.videoViewResp);
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.dimAmount = 0;
        lp.screenBrightness = 5;
        dialog.getWindow().setAttributes(lp);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        uriPath = "android.resource://" + getPackageName() + "/raw/entrar_maxipe";
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Uri teste = Uri.parse(uriPath);
        mVideoView.setVideoURI(teste);
        mVideoView.start();
    }

}
