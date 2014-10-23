package net.iskandar.alarmclock;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import net.iskandar.components.DrawableBackgroundedLayout;
import net.iskandar.components.PictureAndFillColorDrawable;

/**
 * Created by iskandar on 9/17/14.
 */
public class EditAlarmForm extends FrameLayout {

    public EditAlarmForm(Context context) {
        super(context);
        init();
    }

    public EditAlarmForm(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditAlarmForm(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private static String[] strs = null;
    static {
        strs = new String[10];
        for(int i = 0; i < 10; i++)
            strs[i] = "Strs Item-" + (i+1);

    }

    private void init() {
        View.inflate(getContext(), R.layout.edit_alarm, this);
        DrawableBackgroundedLayout dbl = (DrawableBackgroundedLayout) findViewById(R.id.timePanel);
        PictureAndFillColorDrawable pictureAndFillColorDrawable = new PictureAndFillColorDrawable(
                BitmapFactory.decodeResource(getResources(), R.drawable.big_ben3),
                PictureAndFillColorDrawable.DockingSide.WEST,
                Color.parseColor("#80b4d9")
        );

        dbl.setDrawableBackground(pictureAndFillColorDrawable);
    }


}
