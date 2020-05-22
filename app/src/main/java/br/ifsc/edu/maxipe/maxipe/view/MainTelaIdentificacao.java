package br.ifsc.edu.maxipe.maxipe.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.controller.AuxiliadoController;
import br.ifsc.edu.maxipe.maxipe.controller.ResponsavelController;
import br.ifsc.edu.maxipe.maxipe.model.Auxiliados;
import br.ifsc.edu.maxipe.maxipe.model.Responsaveis;

public class MainTelaIdentificacao extends AppCompatActivity {

    private ResponsavelController responsavelController;
    private DatabaseReference myRef;
    private AuxiliadoController auxiliadoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_identificacao);
        myRef = FirebaseDatabase.getInstance().getReference();
        responsavelController = new ResponsavelController(MainTelaIdentificacao.this);
        auxiliadoController = new AuxiliadoController(MainTelaIdentificacao.this);


        retornarResponsaveisFirebase();
        retornarAuxiliadosFirebase();
    }

    private void retornarAuxiliadosFirebase() {
        myRef.child("Auxiliados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot obj:dataSnapshot.getChildren()) {
                        Auxiliados aux = obj.getValue(Auxiliados.class);
                        if (auxiliadoController.emailExistente(aux.getAux_email()) == false) {
                            auxiliadoController.addAuxFirebase(aux);
                            Toast.makeText(MainTelaIdentificacao.this, "FOI", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void retornarResponsaveisFirebase(){
        myRef.child("Responsaveis").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snpShot : dataSnapshot.getChildren()) {
                        Responsaveis r = snpShot.getValue(Responsaveis.class);
                        if (responsavelController.verificaSeJaHaEsseIdNoBanco(r.getRes_id()) == false) {
                            responsavelController.salvarRespFireBase(r);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void mudarTela1Aux(View view) {
        Intent primeiraTelaAux = new Intent(this, MainLoginAux.class);
        startActivity(primeiraTelaAux);
    }

    public void mudarTelaResp(View view) {
        Intent primeiraTelaResp = new Intent(this, MainPrimeiraTelaResp.class);
        startActivity(primeiraTelaResp);
    }

    public void librasResponsavel(View view){
        String uriPath;
        final Dialog dialog = new Dialog(MainTelaIdentificacao.this);// add here your class name
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

    public void librasAuxiliado(View view) {
        String uriPath;
        final Dialog dialog = new Dialog(MainTelaIdentificacao.this);// add here your class name
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

        uriPath= "android.resource://" + getPackageName() + "/raw/auxiliado_maxipe";
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Uri teste = Uri.parse(uriPath);
        mVideoView.setVideoURI(teste);
        mVideoView.start();
    }
}