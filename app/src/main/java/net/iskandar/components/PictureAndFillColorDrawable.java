package net.iskandar.components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by iskandar on 9/15/14.
 */
public class PictureAndFillColorDrawable extends Drawable {

    private Rect srcRect;

    public enum DockingSide { NORTH, WEST, EAST, SOUTH }

    private Bitmap bitmap;
    private DockingSide bitmapDockingSide;
    private Rect bmpTargetBounds;
    private Integer fillColor = null;
    private Paint paint;

    private Rect[] emptyRects = null;

    public PictureAndFillColorDrawable(Bitmap bitmap, DockingSide bitmapDockingSide) {
        this.bitmap = bitmap;
        this.bitmapDockingSide = bitmapDockingSide;
        paint = new Paint();
        paint.setAntiAlias(true);
    }


    public PictureAndFillColorDrawable(Bitmap bitmap, DockingSide bitmapDockingSide, int fillColor) {
        this.bitmap = bitmap;
        this.bitmapDockingSide = bitmapDockingSide;
        this.fillColor = fillColor;
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if(fillColor != null) {
            paint.setColor(fillColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(bounds, paint);
        }

        bmpTargetBounds = calcTargetBounds(bitmap.getWidth(), bitmap.getHeight());
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawBitmap(bitmap, null, bmpTargetBounds, paint);

        if (fillColor == null) {
            Rect srcRect = calcSrcRect();
            Rect[] dstRects = calcDstRects();
            for (Rect r : dstRects) {
                canvas.drawBitmap(bitmap, srcRect, r, paint);
            }
        }

//        bmpTargetBounds.set(bounds.left, bounds.bottom + );
//        canvas.drawBitmap();
    }

    private Rect[] calcDstRects() {
        if(emptyRects == null) {
            int emptySize = 0;
            if (bitmapDockingSide == DockingSide.NORTH || bitmapDockingSide == DockingSide.SOUTH) {
                emptySize = getBounds().height() - bmpTargetBounds.height();
            } else {
                emptySize = getBounds().width() - bmpTargetBounds.width();
            }
            int rectCount = emptySize / 5;
            if((emptySize % 5) > 0)
                rectCount++;
            emptyRects = new Rect[emptySize / 5];
            int emptyRemaining = emptySize;
            for(int i = 0; i < rectCount; i++){
                emptyRects[i] = new Rect();
                int left = 0;
                int top = 0;
                int right = 0;
                int bottom = 0;
                int weight = Math.min(5, emptyRemaining);
                switch (bitmapDockingSide) {
                    case NORTH:
                        left = 0;
                        top = ((i > 0) ? (emptyRects[i-1].bottom - 5 + weight) : bmpTargetBounds.bottom);
                        right = getBounds().width();
                        bottom = top + (weight * 2) - 5;
                        break;
                    case SOUTH:

                        break;
                    case WEST:

                        break;
                    case EAST:

                        break;
                }
            }
        }
        return emptyRects;
    }

    private Rect calcSrcRect(){
        if(srcRect == null) {
            srcRect = new Rect();
            switch (bitmapDockingSide) {
                case NORTH:
                    srcRect.set(0, 0, bitmap.getWidth(), 5);
                    break;
                case SOUTH:
                    srcRect.set(0, bitmap.getHeight() - 5, bitmap.getWidth(), bitmap.getHeight());
                    break;
                case WEST:
                    srcRect.set(bitmap.getWidth() - 5, 0, bitmap.getWidth(), bitmap.getHeight());
                    break;
                case EAST:
                    srcRect.set(0, 0, 5, bitmap.getHeight());
                    break;
            }
        }
        return srcRect;
    }

    private Rect calcTargetBounds(int bmpWidth, int bmpHeight){
        Rect bounds = getBounds();
        int bndWidth = bounds.right - bounds.left;
        int bndHeight = bounds.bottom - bounds.top;

        double factor = 0.0d;

        if(bitmapDockingSide == DockingSide.NORTH || bitmapDockingSide == DockingSide.SOUTH) {
            factor = ((double) bndWidth) / ((double) bmpWidth);
        } else {
            factor = ((double) bndHeight) / ((double) bmpHeight);
        }

        bmpWidth = (int) Math.round(((double) bmpWidth) * factor);
        bmpHeight = (int) Math.round(((double) bmpHeight) * factor);

        int top, left, right, bottom;
        top = 0;
        left = 0;
        right = 0;
        bottom = 0;

        switch(bitmapDockingSide){
            case SOUTH:
                left = bounds.left;
                top = bounds.bottom - bmpHeight;
                right = bounds.right;
                bottom = bounds.bottom;
                break;
            case NORTH:
                left = bounds.left;
                top = bounds.top;
                right = bounds.right;
                bottom = bounds.top + bmpHeight;
                break;
            case WEST:
                left = bounds.left;
                top = bounds.top;
                bottom = bounds.bottom;
                right = bounds.left + bmpWidth;
                break;
            case EAST:
                left = bounds.right - bmpWidth;
                top = bounds.top;
                bottom = bounds.bottom;
                right = bounds.right;
                break;
        }

        Rect targetBounds = new Rect();
        targetBounds.set(left, top, right, bottom);

        return targetBounds;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}

