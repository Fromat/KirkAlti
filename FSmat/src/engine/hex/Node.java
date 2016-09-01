package engine.hex;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.Rect;

public class Node {

	final static Layout layout = new Layout(Layout.flat, new Point(100, 90), new Point(600, 960));

	public static enum nodeState { ACTIVE_NODE,PASSIVE_NODE,GUARD,CASTLE,TARGET};

	private Hex hexNode;
	private Paint paintLine,paintArea;
	private Path path;
	private int number=0;
	public nodeState state = nodeState.PASSIVE_NODE;
	private boolean hasHexPath;
	public int hexPathId=-1;

	public Node(Hex hex) {
		super();
		hexNode = hex;
		setPaints();
		//setNumber();
		this.path = new Path();
	}

	public void setInitializeNumber() {
		if (state == nodeState.PASSIVE_NODE) {
			for (int i = 0; i < 6; i++) {
				if (hexNode.q == Hex.directions.get(i).q && hexNode.r == Hex.directions.get(i).r ) {
					this.number =3; //canvas.drawText("3", (float)getCenter().x, (float) getCenter().y, paintLine);
				}
				if ( (hexNode.q == Hex.directions.get(i).q*2 && hexNode.r == Hex.directions.get(i).r*2 )
						|| (hexNode.q == Hex.diagonals.get(i).q && hexNode.r == Hex.diagonals.get(i).r )) {
					this.number =2; //canvas.drawText("2", (float)getCenter().x, (float) getCenter().y, paintLine);
				}
			}
			if ( hexNode.q+ hexNode.r ==-3 | hexNode.q+ hexNode.r == 3 | hexNode.q ==3 | hexNode.q ==-3 | hexNode.r ==3 | hexNode.r ==-3) 
				this.number =1;//canvas.drawText("1", (float)getCenter().x, (float) getCenter().y, paintLine);
		}



	}

	public void draw(Canvas canvas){
		canvas.drawPath(path,paintArea);
		canvas.drawPath(path,paintLine);
		//	canvas.drawCircle((float) getCenter().x, (float)getCenter().y, (float)layout.size.x*3/4, paintLine);
	        canvas.drawRect((float)getCenter().x  - 60, (float)getCenter().y  - 60, (float)getCenter().x  + 60, (float)getCenter().y  + 60, paintLine);
		if (number != 0) {
			canvas.drawText(this.number+"", (float)getCenter().x -paintLine.measureText(""+this.number)/2, (float) getCenter().y + paintLine.getTextSize()/4, paintLine);
		}

	}

	public Point pixel_to_hex(double x, double y){
	    float q = (float) (x * 2/3 / 90);
	    float r = (float) ((-x / 3 + Math.sqrt(3)/3 * y) / 90);
	    return new Point(q, r);
	}
	public int getColor()
	{
		return paintArea.getColor();
	}
	public void setHexNode(Hex hexNode) {
		this.hexNode = hexNode;
	}

	public Hex getHexNode() {
		return hexNode;
	}

	public void setPaints() {

		paintLine = new Paint();
		paintLine.setColor(Color.BLACK);
		paintLine.setStrokeWidth(5);
		paintLine.setStyle(Style.STROKE);
		paintLine.setTextSize(60);

		paintArea = new Paint();
		paintArea.setColor(Color.TRANSPARENT);
		paintArea.setStrokeWidth(5);
		paintArea.setStyle(Style.FILL);


	}

	public Point getCenter() {
		Point point = Layout.hexToPixel(layout, hexNode);
		return point;
	}

	public Path getPath() {
		return path;
	}

	public ArrayList<Point> getCorners() {
		return Layout.polygonCorners(layout, hexNode);
	}

	public Paint getPaintArea() {
		return paintArea;
	}

	public Paint getPaintLine() {
		return paintLine;
	}

	public boolean isInPoint(double x,double y){

		Point p = new Point(x, y);
		if (pixel_to_hex(x, y).x == hexNode.q && pixel_to_hex(x, y).y == hexNode.r) 
			return true;
		else
			return false;
	}

	public void setColor(int color) {
		paintArea.setColor(color);
	}

	public Rect getBounds(){
		return new Rect((int)(getCenter().x - 60), (int) (getCenter().y - 60), (int)(getCenter().x + 60), (int)(getCenter().y + 60));
	}

	public void activateNode(){
		if (!hasHexPath && state == nodeState.PASSIVE_NODE ) {
			state = nodeState.ACTIVE_NODE;
		}
	}
	public boolean isEnable() {
		if (state != nodeState.ACTIVE_NODE) {
			return false;
		}
		else
			return true;
	}

	public void inActivateNode() {
		if (!hasHexPath && state == nodeState.ACTIVE_NODE ) {
			state = nodeState.PASSIVE_NODE;
		}
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}





}
