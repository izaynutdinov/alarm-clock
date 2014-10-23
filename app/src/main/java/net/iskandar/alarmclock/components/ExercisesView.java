package net.iskandar.alarmclock.components;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by iskandar on 10/19/14.
 */
public class ExercisesView extends ListView {

    private ExcerciseView[] exercises;

    public ExercisesView(Context context) {
        super(context);
        init();
    }

    public ExercisesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExercisesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        exercises = new ExcerciseView[3];
        for(int i = 0; i < exercises.length; i++)
        {
            exercises[i] = new ExcerciseView(getContext());
        }
        setAdapter(new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return true;
            }

            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return exercises[position];
            }

            @Override
            public int getItemViewType(int position) {
                return 1;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });
    }

}
