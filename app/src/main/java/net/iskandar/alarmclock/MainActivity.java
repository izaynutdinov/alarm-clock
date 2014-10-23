package net.iskandar.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import net.iskandar.components.PictureAndFillColorDrawable;

import java.util.Date;

public class MainActivity extends Activity {

    private int hours = 7;
    private int minutes = 0;

    private TimePicker timePicker;
    private TextView nextAlarm;

    private Logger log = new Logger("MainActivity");

    public void setTime(int hours, int minutes){
        this.hours = hours;
        this.minutes = minutes;
        updateTimePicker();
    }

    private void setTimePicker(TimePicker timePicker){
        this.timePicker = timePicker;
        updateTimePicker();
    }

    private void updateTimePicker(){
        if(timePicker != null) {
            timePicker.setCurrentHour(hours);
            timePicker.setCurrentMinute(minutes);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.d("onCreate " + getIntent().getComponent());
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        setContentView(R.layout.activity_main);
        getActionBar().hide();
        AlarmModel model = AlarmModel.instance(this);

        PictureAndFillColorDrawable pictureAndFillColorDrawable = new PictureAndFillColorDrawable(
                BitmapFactory.decodeResource(getResources(), R.drawable.big_ben3),
                PictureAndFillColorDrawable.DockingSide.WEST,
                Color.parseColor("#5191c9")
        );

/*        DrawableBackgroundedLayout dbl = (DrawableBackgroundedLayout) findViewById(R.id.container);
        dbl.setDrawableBackground(pictureAndFillColorDrawable);*/

        setTimePicker((TimePicker) findViewById(R.id.timePicker));
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(model.getHours());
        timePicker.setCurrentMinute(model.getMinutes());

        nextAlarm = (TextView) findViewById(R.id.nextAlarm);

        final Button setAlarmButton = (Button) findViewById(R.id.setAlarmButton);
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.onAlarmTimeChanged(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            }
        });

        final Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = PlayActivity.newTestIntent();
                MainActivity.this.startActivity(i);
                MainActivity.this.overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });

/*        final Button newAlarmButton = (Button) findViewById(R.id.newAlarmButton);
        newAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NewAlarmActivity.class);
                MainActivity.this.startActivity(i);
                MainActivity.this.overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });*/



    }

    @Override
    protected void onStart() {
        super.onStart();
        log.d("onStart");
        AlarmModel model = AlarmModel.instance(this);
        updateNextAlarm(model);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log.d("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log.d("onResume");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        log.d("onPostResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log.d("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log.d("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log.d("onDestroy");
    }

    private void updateNextAlarm(AlarmModel model) {
        if(model.getNextAlarm() > 0)
            nextAlarm.setText(Utils.formatToYesterdayOrTodayOrTomorrow(new Date(model.getNextAlarm())));
        else
            nextAlarm.setText("Alarm is not set");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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


    public void onAlarmTimeChanged(int hours, int minutes) {
        AlarmModel model = AlarmModel.instance(this);
        model.setTime(hours, minutes);
        updateNextAlarm(model);
    }


}
