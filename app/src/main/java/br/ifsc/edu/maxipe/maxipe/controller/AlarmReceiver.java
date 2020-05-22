package br.ifsc.edu.maxipe.maxipe.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmReceiver extends BroadcastReceiver {

    private static Ringtone r;
    public static boolean resp;

    @Override
    public void onReceive(final Context context, Intent intent) {
        //Set alarme
        final Uri alarmeUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final Ringtone ringtone;
        if (r != null) {
            ringtone = r;
        } else {
            r = RingtoneManager.getRingtone(context.getApplicationContext(), alarmeUri);
            ringtone = r;
        }

        //Set Vibrante
        final Vibrator vibrator;
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        final long[] pattern = {0, 3000, 1000};

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ringtone.stop();
                vibrator.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                    try {
                        for (String cameraId : cameraManager.getCameraIdList()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                cameraManager.setTorchMode(cameraId, false);
                            }
                        }
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                timer.cancel();
            }
        }, 60000, 1000);

        boolean desativar = intent.getBooleanExtra("desativar", false);

        if (!resp) {
            if (!desativar) {
                NotificationHelper notificationHelper = new NotificationHelper(context);
                NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
                notificationHelper.getManager().notify(1, nb.build());

                ringtone.play();
                vibrator.vibrate(pattern, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    final CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                    try {
                        for (String cameraId : cameraManager.getCameraIdList()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                                if (cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                                    cameraManager.setTorchMode(cameraId, true);
                                }
                            }
                        }
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                ringtone.stop();
                vibrator.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    final CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                    try {
                        for (String cameraId : cameraManager.getCameraIdList()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                                if (cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                                    cameraManager.setTorchMode(cameraId, false);
                                }
                            }
                        }
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                r = null;
            }
        } else {
            NotificationHelper notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
            notificationHelper.getManager().notify(1, nb.build());
        }
    }

    public static boolean isResp() {
        return resp;
    }

    public static void setResp(boolean resp) {
        AlarmReceiver.resp = resp;
    }
}
