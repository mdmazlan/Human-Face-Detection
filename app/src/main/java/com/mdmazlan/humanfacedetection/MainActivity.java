package com.mdmazlan.humanfacedetection;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Button Identify................................

        Button btn_img_v = findViewById(R.id.btn_image_id);
        final Button btn_video_v = findViewById(R.id.btn_video_id);


        //Button 2...........................

        btn_img_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Under Developing", Toast.LENGTH_SHORT).show();
            }
        });

        //Button 1...........................

        btn_video_v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "Under Developing", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        //Image loading..................................

        ImageView img_v = findViewById(R.id.imgview_id);
        BitmapFactory.Options optn = new BitmapFactory.Options();
        optn.inMutable = true;
        Bitmap btmp_v = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.test1, optn);


        //Paint stroke drawing.............................

        Paint paint_v = new Paint();
        paint_v.setStrokeWidth(5);
        paint_v.setColor(Color.GREEN);
        paint_v.setStyle(Paint.Style.STROKE);

        Bitmap btmp = Bitmap.createBitmap(btmp_v.getWidth(), btmp_v.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvus_v = new Canvas(btmp);
        canvus_v.drawBitmap(btmp_v, 0, 0, null);


        //Face Detection..................................

        FaceDetector faceDetect_v = new
                FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false).build();

        if (!faceDetect_v.isOperational()) {
            new AlertDialog.Builder(getApplicationContext()).setMessage("The face is not detected !").show();
            return;
        }


        //Detect human Faces..................................

        Frame frame_v = new Frame.Builder().setBitmap(btmp_v).build();
        SparseArray<Face> faces = faceDetect_v.detect(frame_v);


        //Draw a rectangle on the face........................

        for (int i = 0; i < faces.size(); i++) {
            Face thisFace = faces.valueAt(i);
            float x1 = thisFace.getPosition().x;
            float y1 = thisFace.getPosition().y;
            float x2 = x1 + thisFace.getWidth();
            float y2 = y1 + thisFace.getHeight();
            canvus_v.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, paint_v);
        }
        img_v.setImageDrawable(new BitmapDrawable(getResources(), btmp));

    }
}

