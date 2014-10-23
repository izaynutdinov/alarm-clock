package net.iskandar.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by iskandar on 9/17/14.
 */
public class FormView extends FrameLayout {

    private class Item {
        private int layoutId;
        private String[] fields;
        private int[] components;
        private View view;
    }

    private ArrayList<Item> items = new ArrayList<Item>();

    public FormView(Context context) {
        super(context);
    }

    public FormView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FormView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



}
