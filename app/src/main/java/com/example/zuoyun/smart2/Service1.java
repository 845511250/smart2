package com.example.zuoyun.smart2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zuoyun.smart2.tools.ScreentShotUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class Service1 extends Service
{

    long time1;
    public static View mFloatLayout;
    int rawx,rawy,x,y;
    public static int statusBarHeight=0;
    public static int viewwidth=0;
    public static int viewheight=0;
    private int windowWidth = 0;
    private int windowHeight = 0;
    private static WindowManager.LayoutParams wmParams = null;
    private static WindowManager mWindowManager = null;
    private LayoutInflater inflater = null;
    private TextView mFloatView = null;

    String sdcardPath = Environment.getExternalStorageDirectory().getPath();

    @Override
    public void onCreate()
    {
        super.onCreate();
        createFloatView();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private void createFloatView()
    {
        inflater = LayoutInflater.from(getApplication());
        mFloatLayout = inflater.inflate(R.layout.float_window_small, null);
        mFloatView=(TextView) mFloatLayout.findViewById(R.id.tvv);
        viewheight = mFloatView.getLayoutParams().height;
        viewwidth = mFloatView.getLayoutParams().width;

        wmParams = new WindowManager.LayoutParams();
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        windowWidth = mWindowManager.getDefaultDisplay().getWidth();
        windowHeight = mWindowManager.getDefaultDisplay().getHeight();

        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = windowWidth/2-(viewwidth/2);
        wmParams.y = getStatusBarHeight();
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mWindowManager.addView(mFloatLayout, wmParams);


        mFloatLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int)motionEvent.getX();
                        y = (int)motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        rawx = (int) motionEvent.getRawX();
                        rawy = (int) motionEvent.getRawY();
                        wmParams.x = rawx-x;
                        wmParams.y = rawy-y-getStatusBarHeight();
                        mWindowManager.updateViewLayout(mFloatLayout,wmParams);
                        break;
                }
                return false;
            }
        });
        mFloatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("d", "click");
                time1 = new Date().getTime();

                try{
                    File file=new File(sdcardPath+"/a01/");
                    if(!file.exists())
                        file.mkdirs();
                }catch (Exception e){

                }

                String name = new Date().getTime() + ".png";
                sreenShot01(sdcardPath+"/a01/"+name);
                Log.e("dd", "captured！");

                Bitmap bitmapshot=BitmapFactory.decodeFile(sdcardPath+"/a01/"+name);
                Bitmap bitmapSquare = Bitmap.createBitmap(bitmapshot, 0, (windowHeight-windowWidth)/2,windowWidth, windowWidth);
                remix(bitmapSquare);
            }
        });
    }

    public int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    public void remix(Bitmap bitmap){
        Bitmap bitmapShoe = BitmapFactory.decodeFile(sdcardPath + "/shirt.png");
        int shoeWidth = bitmapShoe.getWidth();
        int shoeHeight = bitmapShoe.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(shoeWidth * 0.5f / bitmap.getWidth(), shoeHeight * 0.5f / bitmap.getHeight());
        int movex=(int)shoeWidth*1/4;
        int movey=(int)shoeHeight*1/4;
        matrix.postTranslate(movex, movey);

        Bitmap bitmapRemix = Bitmap.createBitmap(shoeWidth, shoeHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapRemix);
        canvas.drawBitmap(bitmap,matrix,null);
        Log.e("dd","cost:"+(new Date().getTime()-time1));
        canvas.drawBitmap(bitmapShoe, 0, 0, null);

        try{
            File file=new File(sdcardPath+"/a02/");
            if(!file.exists())
                file.mkdirs();
            String name = new Date().getTime() + ".png";
            FileOutputStream out = new FileOutputStream(sdcardPath+"/a02/"+name);
            bitmapRemix.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();

            Intent media = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(new File(sdcardPath+"/a02/"+name));
            media.setData(contentUri);
            this.sendBroadcast(media);
        }catch (Exception e){

        }
        finally {
            Log.e("dd","cost:"+(new Date().getTime()-time1));
            MainActivity.instance.toast("合成完毕");
        }
    }

    public void sreenShot01(String path) {
        String cmd = "screencap -p " + path;
        try {
            Process process = Runtime.getRuntime().exec("su");//不同的设备权限不一样
            PrintWriter pw = new PrintWriter(process.getOutputStream());
            pw.println(cmd);
            pw.flush();
            pw.println("exit");
            pw.flush();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            pw.close();
            process.destroy();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}