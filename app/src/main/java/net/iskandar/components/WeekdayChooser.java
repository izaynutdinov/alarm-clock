package net.iskandar.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import net.iskandar.alarmclock.R;

/**
 * Created by iskandar on 9/16/14.
 */
public class WeekdayChooser extends FrameLayout {

    private View view;

    public WeekdayChooser(Context context) {
        super(context);
        init();
    }

    public WeekdayChooser(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeekdayChooser(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        view = View.inflate(getContext(), R.layout.day_of_week, this);
    }


}
