package net.iskandar.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.WindowManager;

import net.iskandar.alarmclock.components.ExcerciseView;

public class PlayActivity extends Activity {

    private MediaPlayer player;
    private ExcerciseView[] exercises = new ExcerciseView[3];
    private boolean[] solved = new boolean[]{false, false, false};

    private PowerManager powerManager;
    private boolean screenOn = false;
    private PowerManager.WakeLock wakeLock;

    private boolean didSolved = false;
    private boolean finishing = false;

    private static final String PLAY_ALARM_ACTION="net.iskandar.alarmclock.PLAY_ALARM";
    private static final String TEST_ALARM_ACTION="net.iskandar.alarmclock.TEST_ALARM";

    public static Intent newPlayIntent(){
        Intent i = new Intent();
        i.setAction(PLAY_ALARM_ACTION);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return i;
    }

    public static Intent newTestIntent(){
        Intent i = new Intent();
        i.setAction(TEST_ALARM_ACTION);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return i;
    }


    private void acquireLock(){
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
                "MyWakelockTag");
        wakeLock.acquire();
    }

    private void releaseLock(){
        if(wakeLock != null){
            wakeLock.release();
            wakeLock = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("PlayActivity", "onCreate");
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        getActionBar().hide();
        setContentView(R.layout.activity_play);

        exercises[0] = (ExcerciseView) findViewById(R.id.exercise1);
        exercises[1] = (ExcerciseView) findViewById(R.id.exercise2);
        exercises[2] = (ExcerciseView) findViewById(R.id.exercise3);
        for (ExcerciseView ev : exercises) {
            ev.setListener(listener);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("PlayActivity", "onStart threadId=" + Thread.currentThread().getName());
        if(powerManager.isScreenOn()) {
            screenOn = true;
            didSolved = false;
            finishing = false;
            acquireLock();
            player = MediaPlayer.create(this, R.raw.i_feel_good);
            player.setLooping(false);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setVolume(1f, 1f);
            player.setLooping(false);
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.d("PlayActivity", "onCompletion threadId=" + Thread.currentThread().getName());
                    if(!finishing)
                        exit();
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("PlayActivity", "onPause threadId=" + Thread.currentThread().getName());
        if(screenOn) {
            if (didSolved)
                AlarmModel.instance(this).setAlarm();
            else
                AlarmModel.instance(this).setRepeat();

            if (player != null) {
                if (player.isPlaying()) {
                    player.setOnCompletionListener(null);
                    player.stop();
                }
                player.release();
                player = null;
            }
            releaseLock();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("PlayActivity", "onStop threadId=" + Thread.currentThread().getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    

    private ExcerciseView.Listener listener = new ExcerciseView.Listener() {

        @Override
        public void solved(ExcerciseView v) {
            for(int i = 0; i < exercises.length; i++){
                if(v == exercises[i]){
                    solved[i] = true;
                }
            }
            didSolved = solved[0] && solved[1] && solved[2];
            if(didSolved){
                exit();
            }
        }

        @Override
        public void error(ExcerciseView v, String errorMessage) {

        }
    };

    private void exit(){
        if(getIntent().getAction() == TEST_ALARM_ACTION) {
            finish();
        } else if (didSolved) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            finish();
        }
    }

}
