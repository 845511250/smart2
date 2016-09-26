package com.example.zuoyun.smart2;

import android.app.AlertDialog;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Random;

public class Service1 extends Service {
    public static Service1 instance;

    AlertDialog mydialog;
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
    public EditText et_code=null;

    String sdcardPath = Environment.getExternalStorageDirectory().getPath();

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance=this;
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
        et_code = (EditText) mFloatLayout.findViewById(R.id.et_code);


        viewheight = mFloatView.getLayoutParams().height;
        viewwidth = mFloatView.getLayoutParams().width+et_code.getLayoutParams().width;

        wmParams = new WindowManager.LayoutParams();
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        windowWidth = mWindowManager.getDefaultDisplay().getWidth();
        windowHeight = mWindowManager.getDefaultDisplay().getHeight();

        et_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//                mWindowManager.updateViewLayout(mFloatLayout,wmParams);
                Intent intent=new Intent(getApplicationContext(), SelectActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = windowWidth/2-(viewwidth/2);
//        wmParams.y = getStatusBarHeight();
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mWindowManager.addView(mFloatLayout, wmParams);


//        mFloatLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        x = (int)motionEvent.getX();
//                        y = (int)motionEvent.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        rawx = (int) motionEvent.getRawX();
//                        rawy = (int) motionEvent.getRawY();
//                        wmParams.x = rawx-x;
//                        wmParams.y = rawy-y-getStatusBarHeight();
//                        mWindowManager.updateViewLayout(mFloatLayout,wmParams);
//                        break;
//                }
//                return false;
//            }
//        });
        mFloatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("d", "click");
                cehckImageCode();
                time1 = new Date().getTime();

                try{
                    File file2=new File(sdcardPath+"/a02/");
                    if(!file2.exists())
                        file2.mkdirs();
                }catch (Exception e){

                }

                try{
                    Bitmap bitmapshot=BitmapFactory.decodeFile(sdcardPath+"/insimg.png");
                    Bitmap bitmapname=BitmapFactory.decodeFile(sdcardPath+"/nameimg.png");
                    remix(bitmapshot,bitmapname);
                }catch (NullPointerException e){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"请运行ins推广程序",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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

    public void remix(Bitmap bitmapshot,Bitmap bitmapname){
        int random=new Random().nextInt(14)+1;
//开始
        int etcode=0;
        try{
            etcode = Integer.parseInt(et_code.getText().toString().trim());
        }catch (Exception e){

        }
        if(etcode!=0){
            random = etcode;
        }
//
        int id=0;
        switch (random) {
            case 1:
                id=R.mipmap.template1;
                break;
            case 2:
                id = R.mipmap.template2;
                break;
            case 3:
                id = R.mipmap.template3;
                break;
            case 4:
                id = R.mipmap.template4;
                break;
            case 5:
                id = R.mipmap.template5;
                break;
            case 6:
                id = R.mipmap.template6;
                break;
            case 7:
                id = R.mipmap.template7;
                break;
            case 8:
                id = R.mipmap.template8;
                break;
            case 9:
                id = R.mipmap.template9;
                break;
            case 10:
                id = R.mipmap.template10;
                break;
            case 11:
                id = R.mipmap.template11;
                break;
            case 12:
                id = R.mipmap.template12;
                break;
            case 13:
                id = R.mipmap.template13;
                break;
            case 14:
                id = R.mipmap.template14;
                break;
            default:
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"不存在！",Toast.LENGTH_SHORT).show();
                    }
                });
                id = R.mipmap.template6;
                break;
        }


        Bitmap bitmapTemplate=BitmapFactory.decodeResource(getApplicationContext().getResources(),id);
        int templateWidth = bitmapTemplate.getWidth();
        int templateHeight = bitmapTemplate.getHeight();

        Matrix matrix = getMatrix(random,templateWidth,templateHeight,bitmapshot);

        Bitmap bitmapRemix = Bitmap.createBitmap(templateWidth, templateHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapRemix);
        canvas.drawBitmap(bitmapshot,matrix,null);
        canvas.drawBitmap(bitmapTemplate, 0, 0, null);

        Matrix matrix_name = new Matrix();
        matrix_name.postScale(50 * 0.9f / 67 , 50 * 0.9f / 67);
        matrix_name.postTranslate(380, 645);
        canvas.drawBitmap(bitmapname,matrix_name,null);

        try{
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

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"合成完毕",Toast.LENGTH_SHORT).show();
                }
            });
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

    public Matrix getMatrix(int i,int width,int height,Bitmap bitmap){
        Matrix matrix=new Matrix();
        matrix.reset();
        int movex,movey;
        switch (i){
            case 1:
                matrix.postScale(width * 0.6f / bitmap.getWidth(), width * 0.6f / bitmap.getHeight());
                movex=(int)(width*0.2);
                movey=(int)(190);
                matrix.postTranslate(movex, movey);
                break;
            case 2:
                matrix.postScale(width * 0.725f / bitmap.getWidth(), width * 0.725f / bitmap.getHeight());
                movex=(int)(60);
                movey=(int)(160);
                matrix.postTranslate(movex, movey);
                break;
            case 3:
                matrix.postScale(width * 0.5f / bitmap.getWidth(), width * 0.5f / bitmap.getHeight());
                movex=(int)(203);
                movey=(int)(222);
                matrix.postTranslate(movex, movey);
                break;
            case 4:
                matrix.postScale(width * 0.39f / bitmap.getWidth(), width * 0.39f / bitmap.getHeight());
                movex=(int)(256);
                movey=(int)(281);
                matrix.postTranslate(movex, movey);
                break;
            case 5:
                matrix.postScale(width * 0.58f / bitmap.getWidth(), width * 0.58f / bitmap.getHeight());
                movex=(int)(170);
                movey=(int)(181);
                matrix.postTranslate(movex, movey);
                break;
            case 6:
                matrix.postScale(width * 0.625f / bitmap.getWidth(), width * 0.625f / bitmap.getHeight());
                movex=(int)(150);
                movey=(int)(222);
                matrix.postTranslate(movex, movey);
                break;
            case 7:
                matrix.postScale(width * 0.45f / bitmap.getWidth(), width * 0.45f / bitmap.getHeight());
                movex=(int)(220);
                movey=(int)(265);
                matrix.postTranslate(movex, movey);
                break;

            case 8:
                matrix.postScale(width * 0.587f / bitmap.getWidth(), width * 0.587f / bitmap.getHeight());
                movex=(int)(163);
                movey=(int)(181);
                matrix.postTranslate(movex, movey);
                break;
            case 9:
                matrix.postScale(width * 0.6f / bitmap.getWidth(), width * 0.6f / bitmap.getHeight());
                movex=(int)(158);
                movey=(int)(182);
                matrix.postTranslate(movex, movey);
                break;
            case 10:
                matrix.postScale(width * 0.6f / bitmap.getWidth(), width * 0.6f / bitmap.getHeight());
                movex=(int)(161);
                movey=(int)(181);
                matrix.postTranslate(movex, movey);
                break;
            case 11:
                matrix.postScale(width * 0.5f / bitmap.getWidth(), width * 0.5f / bitmap.getHeight());
                movex=(int)(126);
                movey=(int)(224);
                matrix.postTranslate(movex, movey);
                break;
            case 12:
                matrix.postScale(width * 0.575f / bitmap.getWidth(), width * 0.575f / bitmap.getHeight());
                movex=(int)(170);
                movey=(int)(190);
                matrix.postTranslate(movex, movey);
                break;
            case 13:
                matrix.postScale(width * 0.71f / bitmap.getWidth(), width * 0.71f / bitmap.getHeight());
                movex=(int)(121);
                movey=(int)(150);
                matrix.postTranslate(movex, movey);
                break;
            case 14:
                matrix.postScale(width * 0.8f / bitmap.getWidth(), width * 0.8f / bitmap.getHeight());
                movex=(int)(78);
                movey=(int)(120);
                matrix.postTranslate(movex, movey);
                break;

        }

        return matrix;
    }

    public void cehckImageCode(){
        File img_name=new File(sdcardPath + "/test.png");
        if(!img_name.exists()){
            ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            cm.setText("ok");
        }
        else{
            String imagecode = getImgCode();
            ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            cm.setText(imagecode);
            Log.e("code", imagecode);
        }
    }
    public String getImgCode(){
        String imgcode="";
        try{
            Bitmap bmp=BitmapFactory.decodeFile(sdcardPath + "/test.png");
            for(int i=0;i<10;i++){
                int sumRGB=0;
                for(int k=i*6;k<(i+1)*6;k+=3){
                    for(int j=0;j<60;j++){
                        int[] rgb=getRGB(bmp,k,j);
                        sumRGB+=rgb[0]+rgb[1]+rgb[2]+30;
                    }
                }
                String aveStr=((int)sumRGB/360)+"";
                Log.e("rgb", aveStr + "");
                String lastchar=aveStr.substring(aveStr.length()-2, aveStr.length()-1);
                imgcode+=lastchar;
            }

        }catch(Exception e){

        }
        return imgcode;
    }

    public static int[] getRGB(Bitmap bitmap, int x, int y){
        int[] rgb = new int [3];
        int pixel = bitmap.getPixel(x, y);
        rgb[0] = (pixel & 0xff0000) >> 16;
        rgb[1] = (pixel & 0xff00) >> 8;
        rgb[2] = (pixel & 0xff);

        return  rgb;
    }


}