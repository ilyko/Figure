package slava.figure;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private final static String FILE_NAME = "MyJson.json";
    PlaceView placeView;
    ArrayList<Rectangle> figures;
    ArrayList<Rectangle> rectangles;
    //LinkedList<IFigure> linkedFigures;
    //ArrayList<IFigure> arrayFigures;
    //Map<Integer,IFigure> mapFigure;
    Gson gson;
    FileInputStream fis;
    FileOutputStream fos;
    int screenWidth;
    int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        figures = new ArrayList<>();
        rectangles = new ArrayList<>();
        gson = new Gson();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

        //linkedFigures = new LinkedList<>();
        //arrayFigures = new ArrayList<>();

        placeView = (PlaceView) findViewById(R.id.placeView);
        findViewById(R.id.btnGenerate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    generate();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btnRectPacking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    rectPack();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //clickCollection();
            }
        });

    }

    private void rectPack() throws IOException {
        rectangles.clear();
        float temp = 0.0f;

        try {
            rectangles  = gson.fromJson(getStringFromFile(),
                    new TypeToken<ArrayList<Rectangle>>() {}.getType());
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
            e.printStackTrace();
        }
        Log.i("rectangles", ":" + rectangles.size());
        Collections.sort(rectangles, new Comparator<IFigure>() {
            @Override
            public int compare(IFigure o1, IFigure o2) {
                return (int) ((((Rectangle) o2)).getHeight() - ((Rectangle) o1).getHeight());
            }
        });

        for (int i = 0; i < rectangles.size(); i++) {
            rectangles.get(i).setX(temp);
            rectangles.get(i).setY(0);
            temp += rectangles.get(i).getWidth();
        }
        placeView.setFigures(rectangles);
    }

    private void generate() throws JSONException, IOException {
        figures.clear();

        Random r = new Random();
        int countTri = 0, countRect = 0;
        for (int i = 0; i < 2; i++) {
            Rectangle rectangle = new Rectangle
                    .RectangleBuilder(r.nextInt(200),r.nextInt(200))
                    .x(r.nextInt(screenWidth * 3 / 4))
                    .y(r.nextInt(screenHeight * 3 / 4))
                    .color(-r.nextInt(16777216))
                    .create();
            figures.add(rectangle);
        }

       /* Writer osWriter = new OutputStreamWriter(getApplicationContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
        Gson gson = new Gson();
        gson.toJson(figures, osWriter);
        osWriter.close();*/
        Gson gson = new Gson();
        String string = gson.toJson(figures);
        saveToFile(string);

        for (Iterator<Rectangle> myIterator = figures.iterator(); myIterator.hasNext(); ) {
            if (myIterator.next() instanceof Rectangle) countRect++;
            else countTri++;
        }
        System.out.println("Прямоугольники: " + countRect + " Треугольники:" + countTri);
        placeView.setFigures(figures);
    }

    private void saveToFile(String data) {
        try {
            fos = getApplicationContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getStringFromFile() {
        try {
            fis = openFileInput(FILE_NAME);
            StringBuilder fileContent = new StringBuilder("");

            byte[] buffer = new byte[256];
            int n;
            while ((n = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer, 0, n));
            }
            fis.close();
            return fileContent.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

 /*private void clickCollection(){
        figures.clear();
        arrayFigures.clear();
        linkedFigures.clear();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        Random r = new Random();
        int value = 100_000;
        long timeout= System.currentTimeMillis();
        for(int i=0;i<value;i++){
                Rectangle rectangle = new Rectangle(r.nextInt(200),r.nextInt(200));
                rectangle.setColor(-r.nextInt(16777216));
                rectangle.setX(r.nextInt(screenWidth*3/4));
                rectangle.setY(r.nextInt(screenHeight*3/4));
                arrayFigures.add(rectangle);
            }
        timeout = System.currentTimeMillis() - timeout;
        Log.i("TIME Array add", ": "+timeout);
        timeout= System.currentTimeMillis();
        Collections.sort(arrayFigures, new Comparator<IFigure>() {
            @Override
            public int compare(IFigure o1, IFigure o2) {
                return (int)(o1.getX()-o2.getX());
            }
        });
        timeout = System.currentTimeMillis() - timeout;
        Log.i("TIME Array sort", ": "+timeout);
        timeout = System.currentTimeMillis();
        for(int i=0;i<value;i++){
            Rectangle rectangle = new Rectangle(r.nextInt(200),r.nextInt(200));
            rectangle.setColor(-r.nextInt(16777216));
            rectangle.setX(r.nextInt(screenWidth*3/4));
            rectangle.setY(r.nextInt(screenHeight*3/4));
            linkedFigures.add(rectangle);
        }
        timeout = System.currentTimeMillis() - timeout;
        Log.i("TIME Linked add", ": "+timeout);
        timeout = System.currentTimeMillis();
        Collections.sort(linkedFigures, new Comparator<IFigure>() {
            @Override
            public int compare(IFigure o1, IFigure o2) {
                return (int)(o1.getX()-o2.getX());
                //return (int)(((Rectangle)o1)).getHeight()-((Rectangle)o2)).getHeight())
            }
        });
        timeout = System.currentTimeMillis() - timeout;
        Log.i("TIME Linked sort", ": "+timeout);

    }*/