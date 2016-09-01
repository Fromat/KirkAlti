package engine;

import java.util.Random;

import engine.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Cell {

	public Bitmap image;
	public int id;
	public Point location;
	public boolean isSwipeable;
	public int hedefId;
	public int size;
	public boolean correct=false;
	public boolean enable =false;
	public enum PuzzleType{rotated0,rotated90,rotated180,rotated270};
	public PuzzleType type;
	public Matrix matrix;
	public int angle;
	public Random rnd = new Random();

	

	public Cell(int id,Bitmap image,PuzzleType type ) {
		
		this.id = id;
		this.image = image;
		this.type = type;
		//this.location = new Point(1200 + ((id&2)*150),0 +  id*100 );
		isSwipeable = false;
		hedefId = -1;
		matrix = new Matrix();
		setImage();
	}
	
	private void setImage() {
		matrix.setRotate(getAngle(type));
		this.image = Bitmap.createBitmap(image, 0, 0, 306, 305, matrix, true);
	}

	public void setSize(int size) {
		this.size = size;
	}
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(5);
//		if(!correct)
//		canvas.drawCircle(this.location.x +(size/2) , this.location.y +(size/2), (size/4), paint);

		if(enable)
			canvas.drawBitmap(image, location.x, location.y, null);
	}
	
	public void setLocation(Point location) {
		this.location = location;
	}
	
	public Point getLocation() {
		return location;
	}

	public void setSwipeable(boolean isSwipeable) {
		this.isSwipeable = isSwipeable;
	}

	public boolean getSwipeable() {
		return this.isSwipeable;
	}

	public int getHedefId() {
		return hedefId;
	}

	public void setHedefId(int hedefId) {
		this.hedefId = hedefId;
	}

    public void rotate() {
		switch (this.type) {
		case rotated0: {
			this.type = PuzzleType.rotated90;
			break;
		}
		case rotated90: {
			this.type = PuzzleType.rotated180;
			break;
		}
		case rotated180: {
			this.type = PuzzleType.rotated270;
			break;
		}
		case rotated270: {
			this.type = PuzzleType.rotated0;
			break;
		}
		default:
			break;
		}

		matrix.setRotate(90);
		this.image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
	}

	public int getAngle(PuzzleType type) {
		switch (type) {
		case rotated0: {
			angle = 0;
			break;
		}
		case rotated90: {
			angle = 90;
			break;
		}
		case rotated180: {
			angle = 180;
			break;
		}
		case rotated270: {
			angle = 270;
			break;
		}
		default:
			break;
		}
		return angle;
	}
}
