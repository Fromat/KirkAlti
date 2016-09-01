package com.example.fsmat;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import engine.Line;
import engine.Screen;
import engine.hex.HexGrid;
import engine.hex.HexPath;
import engine.hex.Node.nodeState;

public class GameScreen extends Screen{
	HexGrid hexMap;

	HexPath selectedPath=null;
	Paint tempPaint = new Paint();

	public GameScreen(Activity act) {
		super(act);
		hexMap = new HexGrid();
		hexMap.setNumberOfTargets();
		
		tempPaint.setTextSize(100);
		tempPaint.setColor(Color.GREEN);
	}

	@Override
	public void draw(Canvas canvas) {
		if(selectedPath!=null)
		{ 
			selectedPath.drawPath(canvas);
			//canvas.drawText(selectedPath.getTotalScore() +"", 550, 1800, tempPaint);
		}

		hexMap.draw(canvas);
		
		if(selectedPath!=null)
		{
			canvas.drawCircle((float)selectedPath.firstPoint.x, (float) selectedPath.firstPoint.y, 10, tempPaint);
			canvas.drawText(selectedPath.getTotalScore() +"", 550, 1800, tempPaint);
		}

	}

	@Override
	public void update(float dt) {

			// added a text line
	}

	@Override
	public void touch_start(float x, float y, MotionEvent event) {
		for (int i = 0; i < hexMap.nodes.size(); i++) {
			if(Line.isInside(x, y, hexMap.nodes.get(i).getBounds())) //hexMap.nodes.get(i).isInPoint(x,y))//
			{

				// control start to draw hexpath
				if (hexMap.nodes.get(i).state == nodeState.GUARD) {
					hexMap.activateNodes();
					selectedPath = new HexPath(hexMap.nodes.get(i));
					selectedPath.isSelected =true;
					return;
				}
				if (hexMap.nodes.get(i).state == nodeState.CASTLE) {
					hexMap.inActivateNodes();
				}


				if (hexMap.nodes.get(i).isEnable()) {
					selectedPath.addPoint(hexMap.nodes.get(i));
				}

			}
		}

	}

	@Override
	public void touch_up(float x, float y, MotionEvent event) {
		
	}

	@Override
	public void touch_move(float x, float y, MotionEvent event) {
		touch_start(x, y, event);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

	}


}
