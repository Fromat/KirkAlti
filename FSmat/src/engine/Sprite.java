package engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


public class Sprite {

	private int row,column;
    private boolean isPlayer;
	private Bitmap image;
	private int width, height;
	private int currentFrame = 0;
	private int x = 0, y = 0;
	private int srcX, srcY, index = 0, count1 = 0, count2 = 1, speed;

	public Sprite(Bitmap s_image, int row, int col, int speed, boolean isPlayer) {
		this.image = s_image;
		this.row = row;
		this.column = col;
		this.width = image.getWidth() / col;
		this.height = image.getHeight() / row;
		this.speed = speed;
        this.isPlayer=isPlayer;
	}
	public void update() {}
	
	public void draw(Canvas canvas,int x,int y) {
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        if ( isPlayer ) { //!GameScreen.player.isStopped &&

            canvas.drawBitmap(image, src, dst, null);
        }
        else
            if (!isPlayer)
        {

            canvas.drawBitmap(image, src, dst, null);
        }

	}
	
	public void runAnimation() {
		index++;
		if (index > speed) {
			index = 0;
			if ( !isPlayer) // GameScreen.fark>0 ||
			nextFrame();
		}
	}

	private void nextFrame() {
	srcX = count1 * width;
	srcY = count2%row * height;
	count1++;
		
		if (count1 >= column) {
			count1 = 0;
			if (row != 1)
			count2++;
		}
	}
}