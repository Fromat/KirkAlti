package engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class NotificationGame {

	public int count = 0;
	private Bitmap image, tempImage;
	private String explain;
	public boolean changeText = true;
	public enum changeType {openingMiddle, openingTop, openingBottom}
	
	public NotificationGame(Bitmap image, String explain) {
		this.image = image;
		this.tempImage = image;
		if (explain == null)
			this.explain = "";
		else
		this.explain = explain;
	}

	public void setImage(Bitmap image) {
		this.image = image;
		this.tempImage = image;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public void paint(Canvas canvas, Paint paint, float left, float top, int transparency, changeType type, int maxLine, int animationSpeed) {
//		if (GameActivity.sayGosterim>0)
//		{
		
		if (type == null)
			chaneAlpha(changeType.openingBottom);
		else
			chaneAlpha(type);

		count += 10 * animationSpeed;

//			if (count<transparency)
//				paint.setAlpha(count);
//			else
		paint.setAlpha(transparency);
		canvas.drawBitmap(this.image, left, top, paint);
		if (count > 300) {
			paint.setColor(Color.BLACK);
			if (count - 300 - transparency < 256) {
				if(count - 300 - transparency > 0)
					paint.setAlpha(count - 300 - transparency);
				else
					paint.setAlpha(0);
			} else {
				paint.setAlpha(255);
			}

			float length = paint.measureText(this.explain);
			if (length > this.image.getWidth() - paint.getTextSize()) {
				String[] words = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 
						"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 
						"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 
						"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 
						"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 
						"", "", "", "", "", "", "", "", ""};
				String[] lines ={"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", ""};
				String h = explain;
				int countLine = 0;
				int countWord = 0;

				while (h.contains(" ")) {
					words[countWord++] = h.substring(0, h.indexOf(" "));
					h = h.substring(h.indexOf(" ")).trim();
				}
				words[countWord++] = h;

				for (int i = 0; i < countWord; i++) {
					if (paint.measureText(words[i] + " " + words[i+1]) < this.image.getWidth() - paint.getTextSize() && i != countWord - 1) {
						words[i + 1] = words[i] + " " + words[i + 1]; words[i] = "";
					} else {
						if (countLine < maxLine)//paint.measureText(words[i])<this.image.getWidth()-paint.getTextSize() && ( i!=countWord-1 || (i>0 &&!words[i].contains(words[i-1]))) )
							lines[countLine++] = words[i];
						else {
							paint.setTextSize(paint.getTextSize() - 1);
							i = -1;
							for (int j = 0; j < words.length; j++)
								words[j] = "";
							for (int j = 0; j < lines.length; j++)
								lines[j] = "";

							h = explain;
							countLine = 0;
							countWord = 0;
							while (h.contains(" ")) {
								words[countWord++] = h.substring(0, h.indexOf(" "));
								h = h.substring(h.indexOf(" ")).trim();
							}
							words[countWord++] = h;
						}
					}
				}
				if (changeText)
					paint.setTextSize(paint.getTextSize() * .9F);
				changeText = false;
				for (int i = 0; i < countLine; i++) {
					float makeCenter = paint.measureText(lines[i]);
					canvas.drawText(lines[i], left + this.image.getWidth() / 2 - makeCenter / 2, top
                            + this.image.getHeight() / 2 - (paint.getTextSize()
                            * (float)((float)(countLine - 1) / 2 - i) * 1.125F)
                            + paint.getTextSize() / 2 * .875F, paint);
				}
			} else
				canvas.drawText(this.explain, left + this.image.getWidth() / 2 - length / 2, top
                        + this.image.getHeight() / 2 + paint.getTextSize() / 2, paint);
		}
	}

	public void chaneAlpha(changeType type) {
		if (count < 310) {
			int picw, pich, startY = 0, startX = 0, endY, endX;
			int[] pix, tempPix ;
			picw = image.getWidth();
			if (count == 0 || count > 255)
				pich = image.getHeight();
			else
				pich = image.getHeight() * count / 255;
			if (type == changeType.openingBottom || type == changeType.openingMiddle) {
				pix = new int[picw * image.getHeight()];
				tempPix = new int[picw * image.getHeight()];
				image.getPixels(pix, 0, picw, 0, 0, picw, image.getHeight());
				tempImage.getPixels(tempPix, 0, picw, 0, 0, picw, tempImage.getHeight());
				endY = image.getHeight();
				startY = image.getHeight() - pich;
				endX = picw;
			} else {
				pix = new int[picw * pich];
				tempPix = new int[picw * pich];
				image.getPixels(pix, 0, picw, 0, 0, picw, pich);
				tempImage.getPixels(tempPix, 0, picw, 0, 0, picw, pich);
				endY = pich;
				startY = 0;
				endX = picw;
			}
			int Y,R,G,B;

			for (int y = startY; y < endY; y++) {
				for (int x = startX; x < endX; x++) {
					int index = y * picw + x;
					Y = (tempPix[index] >> 24) & 0xff;
					R = (tempPix[index] >> 16) & 0xff;
					G = (tempPix[index] >> 8) & 0xff;
					B = (tempPix[index]) & 0xff;
					if (count == 0) {
						pix[index] = 0x00000000 | (R << 16) | (G << 8) | B;
					} else {
						pix[index] = 0x00000000 | (Y << 24) | (R << 16) | (G << 8) | B;
					}
				}
			}

			Bitmap temp;
			temp = image.copy(image.getConfig(), true);
		  	if (type == changeType.openingMiddle || type == changeType.openingTop)
				temp.setPixels(pix, 0, picw, 0, 0, picw, pich);
			else if (type == changeType.openingBottom)
				temp.setPixels(pix, 0, picw, 0, 0, picw, image.getHeight());
			image = temp;
		}
	}
}
