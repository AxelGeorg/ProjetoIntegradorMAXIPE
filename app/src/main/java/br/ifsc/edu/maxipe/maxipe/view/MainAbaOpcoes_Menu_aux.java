package br.ifsc.edu.maxipe.maxipe.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import br.ifsc.edu.maxipe.maxipe.controller.AdapterAuxiliado;
import br.ifsc.edu.maxipe.maxipe.controller.LembretesController;
import br.ifsc.edu.maxipe.maxipe.controller.ValidaLogin;
import br.ifsc.edu.maxipe.maxipe.model.Auxiliados;
import br.ifsc.edu.maxipe.maxipe.model.Lembretes;
import br.ifsc.edu.maxipe.maxipe.model.RecyclerClicavel;

public class MainAbaOpcoes_Menu_aux extends AppCompatActivity {

    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private AppBarConfiguration mAppBarConfiguration;
    private LembretesController lembretesController;
    private AdapterAuxiliado adapterAux;
    private RecyclerView listaDeLembretes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aba_opcoes_menu_aux);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializaCampos();
        criaListaDeLembretes();

        adapterAux.setOnClickListener(new RecyclerClicavel() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainAbaOpcoes_Menu_aux.this, MainAddLembreteAux.class);
                intent.putExtra("lembrete", adapterAux.retornaLembretes(position));
                startActivity(intent);
            }
        });

        // outra opcao para tentar abrir o arquivo xml atraves do botao fab calenderio
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamaTelaAddLembrete();
            }
        });


        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController); JSWK
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapterAux!=null) {
            adapterAux.setLembretes(lembretesController.retornaListaLembretes(retornaAux().getAux_fkRes(), retornaAux().getAux_id()));
        }
    }

    private void chamaTelaAddLembrete() {
        Auxiliados auxiliados = getIntent().getParcelableExtra("auxiliado");
        if (auxiliados != null) {
            Intent aux_abrir_fab = new Intent(MainAbaOpcoes_Menu_aux.this, MainAddLembreteAux.class);
            aux_abrir_fab.putExtra("auxiliado", auxiliados);
            startActivity(aux_abrir_fab);
        }
    }

    private void inicializaCampos() {
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.drawer = findViewById(R.id.drawer_layoutAux);
        this.lembretesController = new LembretesController(MainAbaOpcoes_Menu_aux.this);
    }

    private void criaListaDeLembretes() {
        listaDeLembretes = findViewById(R.id.listaLembretes);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(MainAbaOpcoes_Menu_aux.this, DividerItemDecoration.VERTICAL);
        adapterAux = new AdapterAuxiliado(lembretesController.retornaListaLembretes(retornaAux().getAux_fkRes(), retornaAux().getAux_id()), MainAbaOpcoes_Menu_aux.this);
        listaDeLembretes.setAdapter(adapterAux);
        listaDeLembretes.setLayoutManager(new LinearLayoutManager(this));
        listaDeLembretes.addItemDecoration(itemDecoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.aux_main_aba_opcoes, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //itens da aba
    public void PerfilAux(MenuItem item) {
        Auxiliados auxiliados = getIntent().getParcelableExtra("auxiliado");
        if (auxiliados!=null) {
            Intent perfil = new Intent(this, MainItemAbaPerfilAux.class);
            perfil.putExtra("auxiliado", auxiliados);
            startActivity(perfil);
        }
    }

    public void sairApp (MenuItem item){
        Intent VoltarIdentificacao = new Intent(this,MainTelaIdentificacao.class);
        startActivity(VoltarIdentificacao);
        finish();
    }

    public void FecharApp(MenuItem item){
        finishAffinity();
    }

    private Auxiliados retornaAux() {
        Auxiliados auxiliados = getIntent().getParcelableExtra("auxiliado");
        return auxiliados;
    }
}
