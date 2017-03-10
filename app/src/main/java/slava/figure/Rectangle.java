package slava.figure;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

/**
 * Created by Slava on 21.02.2017.
 */


class Rectangle extends Figure implements IFigure {
    private float[] points = new float[8];
    private float width;
    private float height;

    float getWidth() {
        return width;
    }

    float getHeight() {
        return height;
    }

    /*private Rectangle(float width, float height){
        this.width=width;
        this.height=height;

        points[0]= getX();
        points[1]= getY();
        points[2]= getX()+width;
        points[3]= getY();
        points[4]= getX();
        points[5]= getY()+height;
        points[6]= getX()+width;
        points[7]= getY()+height;
        init();
    }*/

    @Override
    public void setX(float x) {
        super.setX(x);
        points[0]= getX();
        points[2]= getX()+width;
        points[4]= getX();
        points[6]= getX()+width;
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        points[1]= getY();
        points[3]= getY();
        points[5]= getY()+height;
        points[7]= getY()+height;
    }

    @Override
    public float perimeter() {
        return 2*(height+width);
    }

    @Override
    public float area() {
        return height*width;
    }
    @Override
    public void draw(Canvas canvas) {
        path.reset();
        path.moveTo(points[0], points[1]);
        path.lineTo(points[2], points[3]);
        path.lineTo(points[6], points[7]);
        path.lineTo(points[4], points[5]);
        path.close();
        canvas.drawPath(path, p);
        //canvas.restore();
    }
    static class RectangleBuilder extends Builder{
        private float x=0;
        private float y=0;
        private float width;
        private float height;
        private int color= Color.BLUE;

        RectangleBuilder(int width, int height) {
            this.width = width;
            this.height = height;
        }
        RectangleBuilder x(int x) {
            this.x = x;
            return this;
        }
        RectangleBuilder y(int y) {
            this.y = y;
            return this;
        }
        /*public RectangleBuilder width(int width) {
            this.width = width;
            return this;
        }

        public RectangleBuilder height(int height) {
            this.height = height;
            return this;
        }*/

        RectangleBuilder color(int color) {
            this.color = color;
            return this;
        }
        Rectangle create(){
            return new Rectangle(this);

        }

    }
    private Rectangle(RectangleBuilder rectangleBuilder){
        width = rectangleBuilder.width;
        height = rectangleBuilder.height;
        setX(rectangleBuilder.x);
        setY(rectangleBuilder.y);
        setColor(rectangleBuilder.color);
        init();
    }
}
