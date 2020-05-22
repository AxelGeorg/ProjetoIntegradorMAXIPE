package br.ifsc.edu.maxipe.maxipe.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.controller.AlarmReceiver;

public class MainDesligaAlarme extends AppCompatActivity {
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desliga_alarme);
    }

    public void desligaAlarme(View view) {
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("desativar", true);
        sendBroadcast(intent);
        finish();
    }
}
