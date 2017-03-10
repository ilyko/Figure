package slava.figure;

import android.graphics.Canvas;

/**
 * Created by Slava on 21.02.2017.
 */

public interface IFigure {
    void init();
    float getX();
    void setX(float x);
    float getY();
    void setY(float y);
    float perimeter();
    float area();
    int getColor();
    void setColor(int color);
    void draw(Canvas canvas);

}
