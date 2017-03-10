package slava.figure;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by Slava on 23.02.2017.
 */

abstract class Figure implements IFigure {
    private int color;
    private float x,y;
    Paint p = new Paint();
    Path path = new Path();
    @Override
    public void init() {
        p.setColor(getColor());
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);
    }

    @Override
    public float getY() {
        return y;
    }
    @Override
    public float getX() {
        return x;
    }
    @Override
    public void setX(float x) {
        this.x=x;
    }
    @Override
    public void setY(float y) {
        this.y=y;
    }
    @Override
    public void setColor(int color) {
        this.color=color;
        p.setColor(color);
    }
    @Override
    public int getColor() {
        return color;
    }

    abstract static class Builder{}
}