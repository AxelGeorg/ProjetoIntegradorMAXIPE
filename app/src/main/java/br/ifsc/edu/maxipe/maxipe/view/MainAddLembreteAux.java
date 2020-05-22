package br.ifsc.edu.maxipe.maxipe.view;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.controller.*;

import br.ifsc.edu.maxipe.maxipe.model.*;

public class MainAddLembreteAux extends AppCompatActivity implements View.OnClickListener {

    private Button buttonD;
    private Button buttonH;
    private Button salvar;
    private TextView textViewH;
    private TextView textViewD;
    private EditText tituloVideo;
    private int diaR;
    private int mesR;
    private int anoR;
    private int horaR;
    private int minutosR;
    private boolean edicao = false;
    private LembretesController lembretesController;
    private ResponsavelController responsavelController;
    private AlarmManager alarmManager;

    private TimePickerDialog timePickerDialog;


    private final int VIDEO_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lembrete_aux);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        inicializacaoCampos();
    }


    public void addLembrete(View view) throws ParseException {
        if (edicao) {
            Intent intentAlarm = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), retornaLembrete().getLem_id(), intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(alarmIntent);

            String tituloNovo = tituloVideo.getText().toString();

            String dataCampo = textViewD.getText().toString();
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

            String horaCampo = textViewH.getText().toString();
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
                            tituloNovo, horaInt, minutoInt, diaInt, mesInt, anoInt
                    );

                    Intent intentNovoAlarm = new Intent(getApplicationContext(), AlarmReceiver.class);

                    PendingIntent novoAlarmIntent = PendingIntent.getBroadcast(getApplicationContext(), retornaLembrete().getLem_id(), intentNovoAlarm, 0);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), novoAlarmIntent);

                    Toast.makeText(MainAddLembreteAux.this, getString(R.string.lembrete_modificado), Toast.LENGTH_SHORT).show();
                    lembretesController.updateInfos(lembreteUpdate);
                    finish();
                } else {
                    Snackbar.make(findViewById(R.id.addLembreteAux), R.string.lembrete_existente, Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(findViewById(R.id.addLembreteAux), R.string.erro_atualizacao_lembrete, Snackbar.LENGTH_SHORT).show();
            }

        } else {
            if (validaLembrete(diaR, mesR, anoR, horaR, minutosR)) {
                if (!lembretesController.lembreteExisteInclusao(diaR, mesR, anoR, horaR, minutosR)) {
                    Lembretes lembrete = new Lembretes(0, tituloVideo.getText().toString(), horaR, minutosR, diaR, mesR, anoR);
                    lembretesController.salvarLembrete(lembrete, retornaResp(), retornaAuxiliado() );
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

                    Toast.makeText(MainAddLembreteAux.this, getString(R.string.lembrete_cadastrado), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Snackbar.make(findViewById(R.id.addLembreteAux), R.string.lembrete_existente, Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(findViewById(R.id.addLembreteAux), R.string.erro_cadastro_lembrete, Snackbar.LENGTH_SHORT).show();
            }
        }
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
            textViewD.setError(getString(R.string.data_invalida));
        }

        if (horaCampo < hora && diaCampo == dia && mesCampo == mes && anoCampo == ano) {
            formComErro = true;
            textViewH.setError(getString(R.string.hora_invalida));
        }

        if (horaCampo == hora && minutoCampo < minuto && diaCampo == dia && mesCampo == mes && anoCampo == ano) {
            formComErro = true;
            textViewH.setError(getString(R.string.hora_invalida));
        }

        if (formComErro) {
            return false;
        } else {
            return true;
        }
    }

    private void inicializacaoCampos() {
        this.buttonD = findViewById(R.id.buttonD);
        this.buttonH = findViewById(R.id.buttonH);
        this.textViewH = findViewById(R.id.textViewH);
        this.textViewD = findViewById(R.id.textViewD);
        this.tituloVideo = findViewById(R.id.editTexTTitulo);
        this.salvar = findViewById(R.id.botaoSalvar);
        this.buttonD.setOnClickListener(this);
        this.buttonH.setOnClickListener(this);
        this.lembretesController = new LembretesController(MainAddLembreteAux.this);
        this.responsavelController = new ResponsavelController(MainAddLembreteAux.this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (retornaLembrete() != null) {
                edicao = true;
                salvar.setText("Alterar");
                tituloVideo.setText(retornaLembrete().getLem_tituloText());
                textViewH.setText(retornaLembrete().getLem_hora() + ":" + retornaLembrete().getLem_minuto());
                textViewD.setText(retornaLembrete().getLem_dia() + "/" + retornaLembrete().getLem_mes() + "/" + retornaLembrete().getLem_ano());
            }
        }

    }

    private Lembretes retornaLembrete() {
        Lembretes lembretes = getIntent().getParcelableExtra("lembrete");
        return lembretes;
    }

    private int retornaIdUltimoLembrete() {
        return lembretesController.idUltimoLembrete();
    }

    public void voltarMenuAux(View view) {
        finish();
    }

    //retorna data e hora selecionada
    @Override
    public void onClick(View v) {
        Calendar cal_alarme = Calendar.getInstance();

        if (v == buttonD) {
            final int dia = cal_alarme.get(Calendar.DAY_OF_MONTH);
            final int mes = cal_alarme.get(Calendar.MONTH);
            final int ano = cal_alarme.get(Calendar.YEAR);

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
                    textViewD.setText(sdf.format(data));
                }
            }
                    , dia, mes, ano);
            datePickerDialog.updateDate(ano, mes, dia);
            datePickerDialog.show();
        }
        if (v == buttonH) {
            final int hora = cal_alarme.get(Calendar.HOUR_OF_DAY);
            final int minutos = cal_alarme.get(Calendar.MINUTE);
            timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    horaR = hourOfDay;
                    minutosR = minute;
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, horaR);
                    cal.set(Calendar.MINUTE, minutosR);

                    final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    final Date data = cal.getTime();

                    textViewH.setText(sdf.format(data));


                }
            }, hora, minutos, false);
            timePickerDialog.show();
        }
    }

    private Auxiliados retornaAuxiliado() {
        Auxiliados auxiliados = getIntent().getParcelableExtra("auxiliado");
        return auxiliados;
    }

    private Responsaveis retornaResp() {
        return responsavelController.retornaResp(retornaAuxiliado().getAux_fkRes());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == VIDEO_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                Toast.makeText(MainAddLembreteAux.this, getString(R.string.video_sucesso), Toast.LENGTH_LONG).show();
                //aqui é necessario ainda selecionar o arquivo salvo

            } else {

                Toast.makeText(MainAddLembreteAux.this, getString(R.string.erro_video), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void MudarVideoLibrasTitulo(View view) {
        String uriPath;
        final Dialog dialog = new Dialog(MainAddLembreteAux.this);// add here your class name
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

        uriPath = "android.resource://" + getPackageName() + "/raw/titulo_maxipe";
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Uri teste = Uri.parse(uriPath);
        mVideoView.setVideoURI(teste);
        mVideoView.start();
    }

    public void MudarVideoLibrasHorario(View view) {
        String uriPath;
        final Dialog dialog = new Dialog(MainAddLembreteAux.this);// add here your class name
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

        uriPath = "android.resource://" + getPackageName() + "/raw/horario_maxipe";
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Uri teste = Uri.parse(uriPath);
        mVideoView.setVideoURI(teste);
        mVideoView.start();
    }

    public void MudarVideoLibrasData(View view) {
        String uriPath;
        final Dialog dialog = new Dialog(MainAddLembreteAux.this);// add here your class name
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

        uriPath = "android.resource://" + getPackageName() + "/raw/data_maxipe";
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Uri teste = Uri.parse(uriPath);
        mVideoView.setVideoURI(teste);
        mVideoView.start();
    }

    public void MudarVideoLibrasSalvar(View view) {
        String uriPath;
        final Dialog dialog = new Dialog(MainAddLembreteAux.this);// add here your class name
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

        uriPath = "android.resource://" + getPackageName() + "/raw/salvar_maxipe";
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Uri teste = Uri.parse(uriPath);
        mVideoView.setVideoURI(teste);
        mVideoView.start();
    }

    public void librasVoltar(View view) {
        String uriPath;
        final Dialog dialog = new Dialog(MainAddLembreteAux.this);// add here your class name
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
}
