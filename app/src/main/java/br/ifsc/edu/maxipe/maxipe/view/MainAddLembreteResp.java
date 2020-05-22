package br.ifsc.edu.maxipe.maxipe.view;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.controller.AlarmReceiver;
import br.ifsc.edu.maxipe.maxipe.controller.AuxiliadoController;
import br.ifsc.edu.maxipe.maxipe.controller.LembretesController;
import br.ifsc.edu.maxipe.maxipe.controller.ResponsavelController;
import br.ifsc.edu.maxipe.maxipe.controller.ValidaLembrete;
import br.ifsc.edu.maxipe.maxipe.model.Auxiliados;
import br.ifsc.edu.maxipe.maxipe.model.Lembretes;
import br.ifsc.edu.maxipe.maxipe.model.Responsaveis;

public class MainAddLembreteResp extends AppCompatActivity implements View.OnClickListener {

    private Button buttonD;
    private Button buttonH;
    private Button botaoSalvar;
    private EditText editTextD;
    private EditText editTextH;
    private EditText tituloVideo;
    private int diaR;
    private int mesR;
    private int anoR;
    private int horaR;
    private int minutosR;
    private boolean edicao;
    private AlarmManager alarmManager;

    private final int VIDEO_REQUEST_CODE = 100;

    private ResponsavelController responsavelController;
    private LembretesController lembretesController;
    private AuxiliadoController auxiliadoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lembrete_resp);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        inicializacaoCampos();
    }


    private void inicializacaoCampos() {
        this.buttonD = findViewById(R.id.buttonD);
        this.buttonH = findViewById(R.id.buttonH2);
        this.editTextD = findViewById(R.id.editTextD2);
        this.editTextH = findViewById(R.id.editTextH2);
        this.tituloVideo = findViewById(R.id.editText11);
        this.buttonD.setOnClickListener(this);
        this.buttonH.setOnClickListener(this);
        this.lembretesController = new LembretesController(MainAddLembreteResp.this);
        this.responsavelController = new ResponsavelController(MainAddLembreteResp.this);
        this.auxiliadoController = new AuxiliadoController(MainAddLembreteResp.this);
        this.botaoSalvar = findViewById(R.id.buttonSalvarLembrete);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (retornaLembrete() != null) {
                edicao = true;
                botaoSalvar.setText("Alterar");
                tituloVideo.setText(retornaLembrete().getLem_tituloText());
                editTextH.setText(retornaLembrete().getLem_hora() + ":" + retornaLembrete().getLem_minuto());
                editTextD.setText(retornaLembrete().getLem_dia() + "/" + retornaLembrete().getLem_mes() + "/" + retornaLembrete().getLem_ano());
            }
        }
    }

    public void VoltarME(View view) {
        finish();
    }

    private Responsaveis retornaResp() {
        Responsaveis resp = getIntent().getParcelableExtra("responsavel");
        return resp;
    }

    private Auxiliados retornaAux() {
        Auxiliados aux = auxiliadoController.retornaAux(retornaResp().getRes_id());
        return aux;
    }

    public void addLembrete(View view) throws ParseException {
        if (edicao) {
            Intent intentAlarm = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), retornaLembrete().getLem_id(), intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(alarmIntent);
            String tituloNovo = tituloVideo.getText().toString();

            String dataCampo = editTextD.getText().toString();
            SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date data = sdfData.parse(dataCampo);
            DateFormat dateFormatDia = new SimpleDateFormat("dd");
            String dia = dateFormatDia.format(data);
            int diaInt = Integer.parseInt(dia);
            DateFormat dateFormatMes = new SimpleDateFormat("MM");
            String mes = dateFormatMes.format(data);
            int mesInt = Integer.parseInt(mes);
            DateFormat dateFormatAno = new SimpleDateFormat("yyyy");
            String ano = dateFormatAno.format(data);
            int anoInt = Integer.parseInt(ano);

            String horaCampo = editTextH.getText().toString();
            SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date horario = sdfHora.parse(horaCampo);
            DateFormat dateFormatHora = new SimpleDateFormat("HH");
            String hora = dateFormatHora.format(horario);
            int horaInt = Integer.parseInt(hora);
            DateFormat dateFormatMinuto = new SimpleDateFormat("mm");
            String minuto = dateFormatMinuto.format(horario);
            int minutoInt = Integer.parseInt(minuto);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MINUTE, minutoInt);
            calendar.set(Calendar.DAY_OF_MONTH, diaInt);
            calendar.set(Calendar.MONTH, mesInt);
            calendar.set(Calendar.YEAR, anoInt);
            calendar.set(Calendar.HOUR_OF_DAY, horaInt);

            if (validaLembrete(diaInt, mesInt, anoInt, horaInt, minutoInt)) {
                if (!lembretesController.lembreteExisteEdicao(diaInt, mesInt, anoInt, horaInt, minutoInt, tituloNovo)) {

                    Lembretes lembreteUpdate = new Lembretes(
                            retornaLembrete().getLem_id(),
                            tituloNovo,
                            horaInt, minutoInt, diaInt, mesInt, anoInt
                    );
                    Intent intentNovoAlarm = new Intent(getApplicationContext(), AlarmReceiver.class);

                    PendingIntent novoAlarmIntent = PendingIntent.getBroadcast(getApplicationContext(), retornaLembrete().getLem_id(), intentNovoAlarm, 0);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), novoAlarmIntent);
                    lembretesController.updateInfos(lembreteUpdate);
                    Toast.makeText(MainAddLembreteResp.this, getString(R.string.lembrete_modificado), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Snackbar.make(findViewById(R.id.addLembreteAux), R.string.lembrete_existente, Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(findViewById(R.id.addLembreteAux), R.string.erro_cadastro_lembrete, Snackbar.LENGTH_SHORT).show();
            }
        } else {
            if (validaLembrete(diaR, mesR, anoR, horaR, minutosR)) {
                if (!lembretesController.lembreteExisteInclusao(diaR, mesR, anoR, horaR, minutosR)) {
                    Lembretes lembrete = new Lembretes(0, tituloVideo.getText().toString(), horaR, minutosR, diaR, mesR, anoR);
                    if (retornaAux() == null) {
                        Snackbar.make(findViewById(R.id.addLembreteAux), R.string.precisa_cadastrar_aux, Snackbar.LENGTH_SHORT).show();
                    } else {
                        lembretesController.salvarLembrete(lembrete, retornaResp(), retornaAux());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, diaR);
                        calendar.set(Calendar.YEAR, anoR);
                        calendar.set(Calendar.MONTH, mesR);
                        calendar.set(Calendar.HOUR_OF_DAY, horaR);
                        calendar.set(Calendar.MINUTE, minutosR);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        Intent intentAlarm = new Intent(getApplicationContext(), AlarmReceiver.class);
                        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), retornaIdUltimoLembrete(), intentAlarm, 0);

                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
                        Toast.makeText(MainAddLembreteResp.this, getString(R.string.lembrete_cadastrado), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Snackbar.make(findViewById(R.id.addLembreteAux), R.string.lembrete_existente, Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(findViewById(R.id.addLembreteAux), R.string.erro_cadastro_lembrete, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private int retornaIdUltimoLembrete() {
        return lembretesController.idUltimoLembrete();
    }

    private boolean validaLembrete(int diaCampo, int mesCampo, int anoCampo, int horaCampo, int minutoCampo) {
        boolean formComErro = false;
        ValidaLembrete validaLembrete = new ValidaLembrete();
        Calendar calendar = Calendar.getInstance();
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        final int mes = calendar.get(Calendar.MONTH);
        final int ano = calendar.get(Calendar.YEAR);
        final int hora = calendar.get(Calendar.HOUR_OF_DAY);
        final int minuto = calendar.get(Calendar.MINUTE);
        final String tituloStr = tituloVideo.getText().toString();
        final String horaStr = String.valueOf(horaR);
        final String minutoStr = String.valueOf(minutosR);
        final String diaStr = String.valueOf(diaR);
        final String mesStr = String.valueOf(mesR);
        final String anoStr = String.valueOf(anoR);

        if (validaLembrete.camposComErro(tituloStr)) {
            formComErro = true;
            tituloVideo.setError(getString(R.string.titulo_preenchido));
        }

        if (validaLembrete.camposComErro(horaStr)) {
            formComErro = true;
        }

        if (validaLembrete.camposComErro(minutoStr)) {
            formComErro = true;
        }

        if (validaLembrete.camposComErro(diaStr)) {
            formComErro = true;
        }

        if (validaLembrete.camposComErro(mesStr)) {
            formComErro = true;
        }

        if (validaLembrete.camposComErro(anoStr)) {
            formComErro = true;
        }

        if (diaCampo < dia || mesCampo < mes || anoCampo < ano) {
            formComErro = true;
            editTextD.setError(getString(R.string.data_invalida));
        }

        if (horaCampo < hora && diaCampo == dia && mesCampo == mes && anoCampo == ano) {
            formComErro = true;
            editTextH.setError(getString(R.string.hora_invalida));
        }

        if (horaCampo == hora && minutoCampo < minuto && diaCampo == dia && mesCampo == mes && anoCampo == ano) {
            formComErro = true;
            editTextH.setError(getString(R.string.hora_invalida));
        }

        if (formComErro) {
            return false;
        } else {
            return true;
        }
    }

    private Lembretes retornaLembrete() {
        Lembretes lembretes = getIntent().getParcelableExtra("lembrete");
        return lembretes;
    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();

        if (v == buttonD) {

            final int dia = c.get(Calendar.DAY_OF_MONTH);
            final int mes = c.get(Calendar.MONTH);
            final int ano = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    final Calendar cal = Calendar.getInstance();
                    diaR = dayOfMonth;
                    mesR = monthOfYear;
                    anoR = year;
                    cal.set(Calendar.DAY_OF_MONTH, diaR);
                    cal.set(Calendar.MONTH, mesR);
                    cal.set(Calendar.YEAR, anoR);
                    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    final Date data = cal.getTime();
                    editTextD.setText(sdf.format(data));
                }
            }
                    , dia, mes, ano);
            datePickerDialog.updateDate(ano, mes, dia);
            datePickerDialog.show();
        }
        if (v == buttonH) {

            final int hora = c.get(Calendar.HOUR_OF_DAY);
            final int minutos = c.get(Calendar.MINUTE);

            final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    horaR = hourOfDay;
                    minutosR = minute;
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, horaR);
                    cal.set(Calendar.MINUTE, minutosR);

                    final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    final Date data = cal.getTime();

                    editTextH.setText(sdf.format(data));


                }
            }, hora, minutos, false);
            timePickerDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == VIDEO_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                Toast.makeText(getApplicationContext(), getString(R.string.video_sucesso), Toast.LENGTH_LONG).show();
                //aqui é necessario ainda selecionar o arquivo salvo

            } else {

                Toast.makeText(getApplicationContext(), getString(R.string.erro_video), Toast.LENGTH_LONG).show();
            }

        }

    }

}
