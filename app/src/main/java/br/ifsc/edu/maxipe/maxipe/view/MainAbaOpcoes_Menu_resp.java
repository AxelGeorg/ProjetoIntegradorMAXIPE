package br.ifsc.edu.maxipe.maxipe.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import java.util.Calendar;

import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.controller.AdapterResponsavel;
import br.ifsc.edu.maxipe.maxipe.controller.AlarmReceiver;
import br.ifsc.edu.maxipe.maxipe.controller.AuxiliadoController;
import br.ifsc.edu.maxipe.maxipe.controller.LembretesController;
import br.ifsc.edu.maxipe.maxipe.model.Lembretes;
import br.ifsc.edu.maxipe.maxipe.model.RecyclerClicavel;
import br.ifsc.edu.maxipe.maxipe.model.Responsaveis;

public class MainAbaOpcoes_Menu_resp extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView listaLembretes;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private AdapterResponsavel adapterResponsavel;
    private LembretesController lembretesController;
    private AuxiliadoController auxiliadoController;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aba_opcoes_menu_resp);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        inicializacaoCampos();

        if (retornaAuxiliadoId() != 0) {
            criaListaLembretes();
            adapterResponsavel.setOnClickListener(new RecyclerClicavel() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(MainAbaOpcoes_Menu_resp.this, MainAddLembreteResp.class);
                    intent.putExtra("lembrete", adapterResponsavel.retornaLembretes(position));
                    startActivity(intent);
                }
            });
        }

        if (retornaTamanhoLista() == 0) {
            for (int i = 0; i <= retornaTamanhoLista(); i++) {
                if (retornaLembrete() != null) {
                    if (i == retornaLembrete().getLem_id()) {
                        Intent intentAlarm = new Intent(getApplicationContext(), AlarmReceiver.class);
                        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), i, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.cancel(alarmIntent);

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MINUTE, retornaLembrete().getLem_minuto());
                        calendar.set(Calendar.DAY_OF_MONTH, retornaLembrete().getLem_dia());
                        calendar.set(Calendar.MONTH, retornaLembrete().getLem_mes());
                        calendar.set(Calendar.YEAR, retornaLembrete().getLem_ano());
                        calendar.set(Calendar.HOUR_OF_DAY, retornaLembrete().getLem_hora());

                        Intent intentNovoAlarm = new Intent(getApplicationContext(), AlarmReceiver.class);

                        PendingIntent novoAlarmIntent = PendingIntent.getBroadcast(getApplicationContext(), i, intentNovoAlarm, 0);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), novoAlarmIntent);
                    }
                }
            }
        }


        // outra opcao para tentar abrir o arquivo xml atraves do botao fab calenderio
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamaTelaAddLembrete();
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onResume() { //956T
        super.onResume();
        inicializacaoCampos();
        if (retornaAuxiliadoId() != 0 ) {
            criaListaLembretes();
            adapterResponsavel.setOnClickListener(new RecyclerClicavel() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(MainAbaOpcoes_Menu_resp.this, MainAddLembreteResp.class);
                    intent.putExtra("lembrete", adapterResponsavel.retornaLembretes(position));
                    startActivity(intent);
                }
            });
        }
        if (adapterResponsavel != null && retornaAuxiliadoId() != 0) {
            adapterResponsavel.setLembretesResp(lembretesController.retornaListaLembretes(retornaResp().getRes_id(), retornaAuxiliadoId()));
        }
    }

    private int retornaTamanhoLista() {
        return lembretesController.retonaTamanhoLista();
    }

    private Lembretes retornaLembrete() {
        return lembretesController.retornaLembretePeloResp(retornaResp().getRes_id(), retornaAuxiliadoId());
    }

    private void inicializacaoCampos() {
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.drawer = findViewById(R.id.drawer_layoutResp);
        this.navigationView = findViewById(R.id.nav_view);
        this.lembretesController = new LembretesController(MainAbaOpcoes_Menu_resp.this);
        this.auxiliadoController = new AuxiliadoController(MainAbaOpcoes_Menu_resp.this);
    }

    private void chamaTelaAddLembrete() {
        infoResponsavelAddLembrete();
    }

    private int retornaAuxiliadoId() {
        int aux = auxiliadoController.retornaAuxPeloFk(retornaResp().getRes_id());
        return aux;
    }

    private void criaListaLembretes() {
        this.listaLembretes = findViewById(R.id.listaLembretesResp);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(MainAbaOpcoes_Menu_resp.this, DividerItemDecoration.VERTICAL);
        adapterResponsavel = new AdapterResponsavel(lembretesController.retornaListaLembretes(retornaResp().getRes_id(), retornaAuxiliadoId()), MainAbaOpcoes_Menu_resp.this);
        listaLembretes.setAdapter(adapterResponsavel);
        listaLembretes.setLayoutManager(new LinearLayoutManager(MainAbaOpcoes_Menu_resp.this));
        listaLembretes.addItemDecoration(itemDecoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.resp_main_aba_opcoes, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //itens aba
    public void PerfilResp(MenuItem item) {
        infoResponsavelPerfil();
    }

    public void AddAux(MenuItem item) {
            Intent intent = getIntent();
            Responsaveis resp = intent.getParcelableExtra("responsavel");
            if (resp != null) {
                Intent intent2 = new Intent(this, MainItemAbaAddAux.class);
                intent2.putExtra("responsavel", resp);
                startActivity(intent2);
            }
        }

    public void sairApp(MenuItem item) {
        Intent VoltarIdentificacao = new Intent(this, MainTelaIdentificacao.class);
        startActivity(VoltarIdentificacao);
    }

    public void FecharApp(MenuItem item) {
        finishAffinity();
    }

    private void infoResponsavelPerfil() {
        Intent intent = getIntent();
        Responsaveis resp = intent.getParcelableExtra("responsavel");
        if (resp != null) {
            Intent intent2 = new Intent(MainAbaOpcoes_Menu_resp.this, MainItemAbaPerfilResp.class);
            intent2.putExtra("responsavel", resp);
            startActivity(intent2);
        }
    }

    private void infoResponsavelAddLembrete() {
        Intent intent = getIntent();
        Responsaveis resp = intent.getParcelableExtra("responsavel");
        if (resp != null) {
            Intent intent2 = new Intent(MainAbaOpcoes_Menu_resp.this, MainAddLembreteResp.class);
            intent2.putExtra("responsavel", resp);
            startActivity(intent2);
        }
    }

    private Responsaveis retornaResp() {
        Responsaveis resp = getIntent().getParcelableExtra("responsavel");
        return resp;
    }

}
