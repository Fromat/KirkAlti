package engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;


public abstract class Screen {
    public static Activity activity;
    public static int height, width;
    public static HashMap<String, Screen> screens = new HashMap<String, Screen>();
    public static SoundManager soundManager;
    public static SharedPreferences prefs;

    public Screen(Activity act) {
        activity = act;
        screens.put(getClass().getSimpleName(), this);
        if (soundManager == null) {
            soundManager = new SoundManager(activity);
        }
        //Screen Width Height
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) activity.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        height = metrics.heightPixels;
        width  = metrics.widthPixels;
    }


    abstract public void draw(Canvas canvas);
    abstract public void update(float dt);

    abstract public void touch_start(float x, float y, MotionEvent event);
    abstract public void touch_up   (float x, float y, MotionEvent event);
    abstract public void touch_move (float x, float y, MotionEvent event);

    public static Bitmap getImage(int id) {
        Bitmap b = null;
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            InputStream is = activity.getApplicationContext().getResources().openRawResource(id);
            BitmapFactory.decodeStream(is, null, o);
            is.reset();

            int scale = 1;
            int IMAGE_MAX_SIZE = 2000;

            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double)
                        Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }
            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            o2.inPurgeable = true;

            b = BitmapFactory.decodeStream(is, null, o2);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
		/*InputStream is = ctx.getResources().openRawResource(id);
	        Bitmap b = BitmapFactory.decodeStream(is);
	        try{is.close();}catch(Exception e){e.printStackTrace();}
	        return b;*/

        //BitmapFactory.Options options=new BitmapFactory.Options();
        //options.inSampleSize = 8;
        //return BitmapFactory.decodeStream(is, null, options);

        //return BitmapFactory.decodeResource(ctx.getResources(), id);
    }

    public void setScreen(String screen) {

        GameView.setScreen(screens.get(screen));

    }

    public void setScreen(String screenName, Screen screen) {
        screens.remove(screenName);
        screens.put(screenName, screen);
        GameView.setScreen(screens.get(screenName));
    }

    public static int loadSound(int id){
        return soundManager.loadSound(id);
    }

    public static void playSound(int soundId, int i, float soundVolume) {
        if(GameView.sound)
            soundManager.playSound(soundId,1,soundVolume );
    }
    public abstract void onBackPressed();
    public abstract void onPause();
    public abstract void onResume();
}