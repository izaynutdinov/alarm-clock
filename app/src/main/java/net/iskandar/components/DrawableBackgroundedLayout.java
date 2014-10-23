package net.iskandar.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by iskandar on 9/15/14.
 */
public class DrawableBackgroundedLayout extends FrameLayout {

    private Drawable background;

    public DrawableBackgroundedLayout(Context context) {
        super(context);
    }

    public DrawableBackgroundedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableBackgroundedLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDrawableBackground(Drawable background) {
        this.background = background;
    }

    @Override
    public void draw(Canvas canvas) {
        if(background != null) {
            background.setBounds(getLeft(), getTop(), getRight(), getBottom());
            background.draw(canvas);
        }
        super.draw(canvas);
    }
}
