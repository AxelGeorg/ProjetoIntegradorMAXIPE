package br.ifsc.edu.maxipe.maxipe.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.VideoView;

import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.controller.ResponsavelController;
import br.ifsc.edu.maxipe.maxipe.model.Auxiliados;
import br.ifsc.edu.maxipe.maxipe.model.Responsaveis;

public class MainItemAbaPerfilAux extends AppCompatActivity {
    private TextView nome;
    private TextView contato;
    private TextView email;
    private TextView responsavel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aux_item_perfil);

        inicializacaoCampos();
        retornaAux();

    }

    private void inicializacaoCampos() {
        this.nome = findViewById(R.id.textViewNome);
        this.contato = findViewById(R.id.textViewTelefone);
        this.email = findViewById(R.id.textViewEmail);
        this.responsavel = findViewById(R.id.textViewAuxiliadosResp);
    }

    private void retornaAux() {
        ResponsavelController controller = new ResponsavelController(MainItemAbaPerfilAux.this);
        Auxiliados auxiliados = getIntent().getParcelableExtra("auxiliado");
        nome.setText(auxiliados.getAux_nome());
        contato.setText(auxiliados.getAux_contato());
        email.setText(auxiliados.getAux_email());
        Responsaveis resp = controller.retornaResp(auxiliados.getAux_fkRes());
        responsavel.setText(resp.getRes_nome());
    }

    public void ItemPerfilaux_voltaMenuAux (View view){
        finish();
    }

    public void librasVoltar (View view){
        String uriPath;
        final Dialog dialog = new Dialog(MainItemAbaPerfilAux.this);// add here your class name
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_video);//add your own xml with defied with and height of videoview
        final VideoView mVideoView = dialog.findViewById(R.id.videoViewResp);
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.dimAmount=0;
        dialog.getWindow().setAttributes(lp);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        uriPath= "android.resource://" + getPackageName() + "/raw/voltar_maxipe";
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Uri teste = Uri.parse(uriPath);
        mVideoView.setVideoURI(teste);
        mVideoView.start();
    }

    public void MudarVideoLibrasNome (View view){
        String uriPath;
        final Dialog dialog = new Dialog(MainItemAbaPerfilAux.this);// add here your class name
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_video);//add your own xml with defied with and height of videoview
        final VideoView mVideoView = dialog.findViewById(R.id.videoViewResp);
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.dimAmount=0;
        dialog.getWindow().setAttributes(lp);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        uriPath= "android.resource://" + getPackageName() + "/raw/nome_maxipe";
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Uri teste = Uri.parse(uriPath);
        mVideoView.setVideoURI(teste);
        mVideoView.start();
    }

    public void MudarVideoLibrasTel (View view){
        String uriPath;
        final Dialog dialog = new Dialog(MainItemAbaPerfilAux.this);// add here your class name
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_video);//add your own xml with defied with and height of videoview
        final VideoView mVideoView = dialog.findViewById(R.id.videoViewResp);
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.dimAmount=0;
        dialog.getWindow().setAttributes(lp);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        uriPath= "android.resource://" + getPackageName() + "/raw/telefone_maxipe";
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Uri teste = Uri.parse(uriPath);
        mVideoView.setVideoURI(teste);
        mVideoView.start();
    }

    public void MudarVideoLibrasEmail (View view){
        String uriPath;
        final Dialog dialog = new Dialog(MainItemAbaPerfilAux.this);// add here your class name
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_video);//add your own xml with defied with and height of videoview
        final VideoView mVideoView = dialog.findViewById(R.id.videoViewResp);
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.dimAmount=0;
        dialog.getWindow().setAttributes(lp);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        uriPath= "android.resource://" + getPackageName() + "/raw/email_maxipe";
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Uri teste = Uri.parse(uriPath);
        mVideoView.setVideoURI(teste);
        mVideoView.start();
    }

    public void MudarVideoLibraResp (View view){
        String uriPath;
        final Dialog dialog = new Dialog(MainItemAbaPerfilAux.this);// add here your class name
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_video);//add your own xml with defied with and height of videoview
        final VideoView mVideoView = dialog.findViewById(R.id.videoViewResp);
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.dimAmount=0;
        dialog.getWindow().setAttributes(lp);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        uriPath= "android.resource://" + getPackageName() + "/raw/responsavel_maxipe";
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Uri teste = Uri.parse(uriPath);
        mVideoView.setVideoURI(teste);
        mVideoView.start();
    }

}
