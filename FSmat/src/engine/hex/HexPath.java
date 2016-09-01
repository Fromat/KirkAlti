package engine.hex;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class HexPath {

	private Path mPath;
	public Point firstPoint;
	public ArrayList<Node> nodeList = new ArrayList<Node>(); 
	public ArrayList<Point> pointList = new ArrayList<Point>();

	public ArrayList<Point> removeList = new ArrayList<Point>();

	public int color;

	public boolean pathFinished;

	public boolean isSelected;

	public Paint mPaint;
	
	public int totalScore=0;

	public HexPath(){
		mPath = new Path();
		this.pathFinished=false;
		this.isSelected =false;
		this.color = Color.rgb(254,153,0);
		this.mPaint = new Paint();
		mPaint.setColor(color);
		setPaint();
	}
	public HexPath(Node node)
	{
		this();
		firstPoint = new Point(node.getCenter().x, node.getCenter().y);
		addPoint(node);
	}
	
	private void setPaint() {
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(25);
	}

	public void addPoint(Node node)
	{
	   if(controlPoint(node))
		{
		    nodeList.add(node);
			pointList.add(new Point((int)node.getCenter().x, (int)node.getCenter().y));
			setPath();
	 	}
	   
	   setScore();
	}

	private void setScore() {
		totalScore = 0;
		for (int i = 0; i < nodeList.size(); i++) {
			totalScore +=nodeList.get(i).getNumber();
		}
	}
	public int getTotalScore(){
		return totalScore;
	}
	private boolean isInPath(Node node) {
		return nodeList.contains(node);
	}

	private boolean controlPoint(Node node) {
		if (isEmpty()) {
			return true;
		}
//		if (isInPath(node)) {
//			setPointTo(node);
//			return false;
//		}
		// control neigbour node
		for (int i = 0; i < 6; i++) {
			if (node.getHexNode().q == Hex.neighbor(nodeList.get(nodeList.size()-1).getHexNode(), i).q && 
					node.getHexNode().r == Hex.neighbor(nodeList.get(nodeList.size()-1).getHexNode(), i).r) {
				return true;
			}
		}
		return false;
		
	}



	public void setPath()    
	{
		mPath.reset();  
		for(int i=0; i < pointList.size(); i++)
		{
			if( i == 0) {
				mPath.moveTo((int)pointList.get(i).x, (int) pointList.get(i).y);	
			}
			else
			{ 
				mPath.lineTo((int)pointList.get(i).x, (int)pointList.get(i).y);
			}   
		}
	}

	public void drawPath(Canvas canvas)
	{
		for (int i = 0; i < pointList.size(); i++) {
			canvas.drawCircle((float)pointList.get(i).x, (float) pointList.get(i).y - 5, 20, mPaint);
		}
		canvas.drawPath(mPath, mPaint);
	}

	public void setPointTo(Node node)
	{
		// Log.i("pointList.size", String.valueOf(pointList.size()));
		int index = pointList.indexOf(new Point(node.getCenter().x,node.getCenter().y));
		if( pointList.size() != 0 )
		{
			for(int i=pointList.size()-1; i >= index + 1 ; i--)
			{
				removeList.add(new Point(pointList.get(i).x,pointList.get(i).y));
				pointList.remove(i); 
				nodeList.remove(i);
			}
		}
		setPath();
		
	}

	public boolean isEmpty()
	{
		return pointList.isEmpty();
	}

}
