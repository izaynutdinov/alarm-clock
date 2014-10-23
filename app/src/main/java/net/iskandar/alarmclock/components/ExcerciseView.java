package net.iskandar.alarmclock.components;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import net.iskandar.alarmclock.R;
import net.iskandar.alarmclock.tools.Excercise;

/**
 * Created by iskandar on 10/18/14.
 */
public class ExcerciseView extends FrameLayout {

    private TextView textView;
    private EditText editText;
    private TextView resultTextView;
    private TextView messageView;
    private Excercise excercise = new Excercise();
    private Listener listener;
//    private ImageView iconView;

    private Thread delayChangeThread;
    private Runnable delaySetError = new Runnable(){

        @Override
        public void run() {
            Log.d("ExcerciseView", "delaySetError.run threadId=" + Thread.currentThread().getName());
            onChanged(true);
        }
    };

    public ExcerciseView(Context context) {
        super(context);
        init();
    }

    public ExcerciseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExcerciseView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void init(){
        View.inflate(getContext(), R.layout.exsercise_view, this);
        setPadding(0, 0, 0, 0);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(excercise.format(false));
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        messageView = (TextView) findViewById(R.id.message);
        editText = (EditText) findViewById(R.id.editText);
/*        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onChanged();
                return false;
            }
        });
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(v instanceof EditText && v == editText){
                    if(!hasFocus){
                        onChanged();
                    }
                }
            }
        });*/

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onChanged(false);
            }
        });
        setMinimumHeight(90);
    }

    private void onChanged(boolean setError){
        String txt = editText.getText().toString();
        try {
            if(txt == null || txt.isEmpty())
                throw new NumberFormatException("\"" + txt + "\" is not the correct number!");
            int value = Integer.parseInt(txt);
            if (value == excercise.getResult()) {
                editText.setVisibility(INVISIBLE);
                resultTextView.setText(Integer.toString(excercise.getResult()));
                resultTextView.setVisibility(VISIBLE);
                messageView.setText("Solved");
                messageView.setTextColor(Color.parseColor("#0000bc"));
                if(listener != null)
                    listener.solved(this);
            } else {
                throw new NumberFormatException("" + value + " is not correct answer!");
            }
        } catch(NumberFormatException nfe) {
            if(setError) {
                if (listener != null)
                    listener.error(this, nfe.getMessage());
                messageView.setText("Error");
                messageView.setTextColor(Color.parseColor("#bc0000"));
            } else {
                getHandler().removeCallbacks(delaySetError);
                Log.d("ExcerciseView", "afterTextChanged threadId=" + Thread.currentThread().getName());
                getHandler().postDelayed(delaySetError, 1000);
            }
            return;
        }
    }

    public interface Listener {
        void solved(ExcerciseView v);
        void error(ExcerciseView v, String errorMessage);
    }

}
