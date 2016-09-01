package engine;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    Timer timer;

    public static GameView instance;
    public static Timer mUpdateTimer;

    public Screen screen;

    public  int width, height;

    public static boolean sound = true;
    public static boolean vibration = true;

    public static final Object lock = new Object();

    static Vibrator vibrator;
    final long period = 40;

    public float scaleX = 1f;
    public float scaleY = 1f;

    public GameView(Context context, Screen newScreen) {
        super(context);
        instance = this;
        screen = newScreen;
        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        setFocusable(true);
    }

    static void setScreen(Screen screen) {
        if (instance != null) {
            instance.screen = screen;
        } else {
            System.out.println("problem: instance not created " + screen.getClass().getSimpleName());
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.scale(scaleX, scaleY);
        synchronized (lock) {
            screen.draw(canvas);
        }
    }

    public static void vibrate(long ms) {
        if(vibration)
            vibrator.vibrate(ms);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.width = getWidth();
        this.height = getHeight();
        scaleX = r / 1200f;
        scaleY = b / 1920f;
        try {
            if (mUpdateTimer == null) {
                startUpdateTimer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void startUpdateTimer() {
        stopUpdateTimer();
        timer = new Timer();
        timer.schedule(new UpdateTask(), 0, period);
    }

    void stopUpdateTimer() {
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private class UpdateTask extends TimerTask {
        public void run() {
            synchronized (lock) {
                screen.update(period);
            }
            postInvalidate();
        }
    }

    public void pause() {
        try {
            synchronized (lock) {
                stopUpdateTimer();
            }
            screen.onPause();
        } catch (Exception e) {
            Log.i("pause hata: ", e.getMessage());
        }
    }

    public void resume() {
        try {
            startUpdateTimer();
            screen.onResume();
        } catch (Exception e) {
            Log.i("resume hata: ", e.getMessage());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (lock) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    press(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    drag(event);
                    break;
                case MotionEvent.ACTION_UP:
                    release(event);
                    break;
            }
            return true;
        }
    }

    private void press(MotionEvent event) {

        synchronized (lock) {
            float x = event.getX() / scaleX;
            float y = event.getY() / scaleY;

            screen.touch_start(x, y, event);
        }
    }

    private void release(MotionEvent event) { //bunu karsıya at
        synchronized (lock) {
            float x = event.getX() / scaleX;
            float y = event.getY() / scaleY;

            screen.touch_up(x, y, event);
        }
    }

    private void drag(MotionEvent event) { //bunu karsıya at
        synchronized (lock) {
            float x = event.getX() / scaleX;
            float y = event.getY() / scaleY;

            screen.touch_move(x, y, event);
        }
    }
    public void onBackPressed() {
        screen.onBackPressed();
    }
}