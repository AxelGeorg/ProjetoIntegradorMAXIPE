package br.ifsc.edu.maxipe.maxipe.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.model.Lembretes;
import br.ifsc.edu.maxipe.maxipe.model.RecyclerClicavel;

public class AdapterResponsavel extends RecyclerView.Adapter<AdapterResponsavel.ViewHolderResp> {

    private List<Lembretes> mListaLembretes;
    private Context context;
    private RecyclerClicavel recyclerClicavel;
    private LembretesController controller;
    private Lembretes lembretePego;

    public AdapterResponsavel(List<Lembretes> lembretes, Context context) {
        this.mListaLembretes = new ArrayList<>(lembretes);
        this.context = context;
    }

    public void setLembretesResp(List<Lembretes> lembrete) {
        mListaLembretes.clear();
        mListaLembretes.addAll(lembrete);
        Collections.sort(mListaLembretes, new Comparator<Lembretes>() {
            @Override
            public int compare(Lembretes o1, Lembretes o2) {
                return o1.getLem_tituloText().compareToIgnoreCase(o2.getLem_tituloText());
            }
        });
        notifyDataSetChanged();
    }

    public Lembretes retornaLembretes (int position) {
        return mListaLembretes.get(position);
    }

    private void criaDialog() {
        controller = new LembretesController(context);
        AlertDialog dialogAlterar;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.alerta);
        builder.setMessage(R.string.excluir_lembrete);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                controller.excluiLembrete(lembretePego.getLem_id());
                mListaLembretes.remove(lembretePego);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogAlterar = builder.create();
        dialogAlterar.show();
    }

    @NonNull
    @Override
    public AdapterResponsavel.ViewHolderResp onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context contexto = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View lembretesView = inflater.inflate(R.layout.teste, viewGroup, false);
        ViewHolderResp viewHolderResp = new ViewHolderResp(lembretesView);
        return viewHolderResp;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterResponsavel.ViewHolderResp viewHolderResp, int i) {
        final Lembretes lembrete = mListaLembretes.get(i);
        String hora = String.valueOf(lembrete.getLem_hora());
        if (lembrete.getLem_hora() < 10) {
            hora = "0"+lembrete.getLem_hora();
        }
        String minuto = String.valueOf(lembrete.getLem_minuto());
        if (lembrete.getLem_minuto() < 10) {
            minuto = "0"+lembrete.getLem_minuto();
        }
        final String dia = String.valueOf(lembrete.getLem_dia());
        final String mes = String.valueOf(lembrete.getLem_mes());
        final String ano = String.valueOf(lembrete.getLem_ano());
        final String diaSemana = dia + "/" + mes + "/" + ano;

        viewHolderResp.horarioLembrete.setText(hora+":"+minuto);
        viewHolderResp.tituloLembrete.setText(lembrete.getLem_tituloText());
        viewHolderResp.diaSemanaLembrete.setText(diaSemana);

        viewHolderResp.botaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criaDialog();
                lembretePego = lembrete;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListaLembretes.size();
    }

    public void setOnClickListener(RecyclerClicavel recyclerClicavel1) {
        this.recyclerClicavel = recyclerClicavel1;
    }

    public class ViewHolderResp extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView horarioLembrete;
        private TextView tituloLembrete;
        private TextView diaSemanaLembrete;
        private ImageButton botaoExcluir;

        public ViewHolderResp(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(ViewHolderResp.this);
            inicializacaoCamposViewHolder(itemView);
        }

        private void inicializacaoCamposViewHolder(View itemView) {
            horarioLembrete = itemView.findViewById(R.id.horarioLembrete);
            tituloLembrete = itemView.findViewById(R.id.tituloLembrete);
            diaSemanaLembrete = itemView.findViewById(R.id.diaSemanaLembrete);
            botaoExcluir = itemView.findViewById(R.id.botaoLixo);
        }

        @Override
        public void onClick(View v) {
            if (recyclerClicavel != null) {
                recyclerClicavel.onClick(getAdapterPosition());
            }
        }
    }
}
