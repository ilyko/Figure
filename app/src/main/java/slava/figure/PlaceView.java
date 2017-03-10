package slava.figure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Slava on 21.02.2017.
 */
public class PlaceView extends View {
    private ArrayList<Rectangle> figures;
    float xTouch,yTouch;
    public PlaceView(Context context) {
        super(context);
    }

    public PlaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFigures(ArrayList<Rectangle> figures) {
        this.figures = new ArrayList<>(figures);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        //Log.d(this.getClass().getSimpleName(), "OnDraw");
        xTouch = motionEvent.getX();
        yTouch = motionEvent.getY();
        if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
            if (figures==null) return true;
            for (int i = figures.size() - 1; i >= 0; i--) {
                Rectangle myRect;

                if(figures.get(i) instanceof Rectangle){
                myRect = (Rectangle) figures.get(i);
                if ((myRect.getX() <= xTouch) && (myRect.getX() + myRect.getWidth()) >= xTouch && (myRect.getY() <= yTouch) && (myRect.getY() + myRect.getHeight()) >= yTouch) {
                    System.out.println("GOTCHA");
                }
            }
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        if (figures==null) return;
        Log.d("draw", "Figures: "+figures + "\n size: " + figures.size());
        for (int i = 0; i < figures.size(); i++) {
            figures.get(i).draw(canvas);
        }
    }
}
