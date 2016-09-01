package com.example.fsmat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import engine.GameView;

public class MainActivity extends Activity {

	public static int screenWidth, screenHeight;
	public RelativeLayout mainLayout;
	public GameScreen gameScreen;
	GameView gameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        
//        String filename = "save_file";
//        String string = "Hello world!";
//        FileOutputStream outputStream;
//
//        try {
//          outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//          outputStream.write(string.getBytes());
//          outputStream.close();
//        } catch (Exception e) {
//          e.printStackTrace();
//        }
//        
//        try {
//        	 AssetManager am = getApplicationContext().getAssets();
//			 InputStream is = am.open("save_file.txt");
//			 
//			 
//			 
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        mainLayout = new RelativeLayout(this);
        
        gameScreen = new GameScreen(this);
        gameView = new GameView(getApplicationContext(), gameScreen);
        
        mainLayout.addView(gameView);
        
        
		setContentView(mainLayout);
	}
}
