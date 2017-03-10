package slava.figure;

import android.graphics.Canvas;

/**
 * Created by Slava on 21.02.2017.
 */

public class Triangle extends Figure implements IFigure {
    private float[] points = new float[6];
    public Triangle(float x0, float y0, float x1, float y1,float x2, float y2) {
        init();
        points[0]=x0;
        points[1]=y0;
        points[2]=x1;
        points[3]=y1;
        points[4]=x2;
        points[5]=y2;
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        points[0]+= getX();
        points[2]+= getX();
        points[4]+= getX();
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        points[1]+= getY();
        points[3]+= getY();
        points[5]+= getY();
    }

    @Override
    public float perimeter() {
        return 0;
    }

    @Override
    public float area() {
        return 0;
    }

    @Override
    public void draw(Canvas canvas) {
        path.moveTo(points[0], points[1]);
        path.lineTo(points[2], points[3]);
        path.lineTo(points[4], points[5]);
        path.close();
        canvas.drawPath(path, p);
    }
}
