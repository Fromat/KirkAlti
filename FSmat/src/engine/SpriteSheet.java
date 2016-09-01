package engine;

import android.graphics.Bitmap;

public class SpriteSheet {

	private Bitmap image;
	private int width, height;

	public SpriteSheet(Bitmap image) {
		this.image = image;
	}
	
	public Bitmap grabImage(int col, int row){
		return image;
	}
}
