package engine;

import android.R.bool;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Animation {

	public int speed;

	private int frames, index = 0, count = 0;

	private Bitmap[] images;
    private Bitmap currentImage;

	long startTime;
	boolean animating = false;

	public Animation (int speed, Bitmap...bitmaps ) {
		this.speed = speed;
		images = new Bitmap[bitmaps.length];
		
		for (int i = 0; i < bitmaps.length; i++) {
			images[i] = bitmaps[i];
		}
		frames = bitmaps.length;
	}
	
	public void runAnimation() {
		index++;
		if (index > speed) {
			index = 0;
			nextFrame();
		}
	}

	private void nextFrame() {
		for (int i = 0; i < frames; i++) {
			if (count == i) {
				currentImage = images[i];
			}
		}
		count++;
		
		if (count > frames) {
			count = 0;
		}
	}
	
	public void draw(Canvas canvas, float x , float y) {
		canvas.drawBitmap(currentImage, x, y, null);
	}
}