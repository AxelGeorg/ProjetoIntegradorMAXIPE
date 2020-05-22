package br.ifsc.edu.maxipe.maxipe.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.model.Lembretes;
import br.ifsc.edu.maxipe.maxipe.model.RecyclerClicavel;

public class AdapterAuxiliado extends RecyclerView.Adapter<AdapterAuxiliado.ViewHolderAux> {

    private List<Lembretes> mlembretes;
    private RecyclerClicavel recyclerClicavel;
    private Context context;
    private LembretesController controller;
    private Lembretes lembretePego;

    public AdapterAuxiliado(List<Lembretes> lembretes, Context context) {
        this.mlembretes = new ArrayList<>(lembretes);
        this.context = context;
    }

    public void setLembretes(List<Lembretes> lembretes) {
        mlembretes.clear();
        mlembretes.addAll(lembretes);
        Collections.sort(mlembretes, new Comparator<Lembretes>() {
            @Override
            public int compare(Lembretes o1, Lembretes o2) {
                return o1.getLem_tituloText().compareToIgnoreCase(o2.getLem_tituloText());
            }
        });
        notifyDataSetChanged();
    }

    public Lembretes retornaLembretes(int position) {
        return mlembretes.get(position);
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
                mlembretes.remove(lembretePego);
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
    public AdapterAuxiliado.ViewHolderAux onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context contexto = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View lembretesView = inflater.inflate(R.layout.teste, viewGroup, false);
        ViewHolderAux vHolderAux = new ViewHolderAux(this, lembretesView);
        return vHolderAux;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAuxiliado.ViewHolderAux viewHolderAux, final int i) {
        final Lembretes lembrete = mlembretes.get(i);
        String hora = String.valueOf(lembrete.getLem_hora());
        if (lembrete.getLem_hora() < 10) {
            hora = "0"+lembrete.getLem_hora();
        }
        String minuto = String.valueOf(lembrete.getLem_minuto());
        if (lembrete.getLem_minuto() < 10) {
            minuto = "0"+lembrete.getLem_minuto();
        }
        String horario = hora + ":" + minuto;
        String dia = String.valueOf(lembrete.getLem_dia());
        String mes = String.valueOf(lembrete.getLem_mes() + 1);
        String ano = String.valueOf(lembrete.getLem_ano());
        String diaSemana = dia + "/" + mes + "/" + ano;

        viewHolderAux.horarioLembrete.setText(horario);
        viewHolderAux.tituloLembrete.setText(lembrete.getLem_tituloText());
        viewHolderAux.diaSemanaLembrete.setText(diaSemana);

        viewHolderAux.botaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criaDialog();
                lembretePego = lembrete;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mlembretes.size();
    }

    public void setOnClickListener(RecyclerClicavel recyclerClicavel1) {
        this.recyclerClicavel = recyclerClicavel1;
    }

    public class ViewHolderAux extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView horarioLembrete;
        private TextView tituloLembrete;
        private TextView diaSemanaLembrete;
        private ImageButton botaoExcluir;

        public ViewHolderAux(final AdapterAuxiliado adapterAuxiliado, View itemView) {
            super(itemView);
            itemView.setOnClickListener(ViewHolderAux.this);
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
