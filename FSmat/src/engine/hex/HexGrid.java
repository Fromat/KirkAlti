package engine.hex;

import java.util.ArrayList;

import engine.hex.Node.nodeState;
import android.graphics.Canvas;
import android.graphics.Color;

public class HexGrid {

	public ArrayList<Node> nodes;
	public ArrayList<HexPath> hexPaths;
	
	public HexGrid(){

		this.nodes = new ArrayList<Node>();
		this.hexPaths = new ArrayList<HexPath>();
		initializeNodes(3);  
		setNumberOfNodes();
	}

	int count=0;
	Hex tempHex;
	private void initializeNodes(int size) {

		for (int q = -size; q <= size; q++) {
			int r1 = Math.max(-size, -q-size);
			int r2 = Math.min(size, -q+size);

			for (int r = r1; r <= r2; r++) {



				tempHex = new Hex(q,r,-q-r);

				Node tempNode = new Node(tempHex);

//				if (Math.abs(q)==4 || Math.abs(r)==4 || Math.abs(-q-r)==4) {
//					tempNode.state=hexState.PASSIVE_PATH;
//				}
				nodes.add(tempNode);

				setPrivateNodes(q,r,count);

				ArrayList<Point> corners = tempNode.getCorners();

				for (int k = 0; k <= 6; k++) {
					if (k==0) {
						tempNode.getPath().moveTo((int)corners.get(k).x, (int)corners.get(k).y);
					}
					else if(k==6){
						tempNode.getPath().lineTo((int)corners.get(0).x, (int)corners.get(0).y);
					}
					else
					{
						tempNode.getPath().lineTo((int)corners.get(k).x, (int)corners.get(k).y);
					}

				}
				count++;
			}

		}

	}

	private void setPrivateNodes(int q, int r, int index) {
		if ((q==-1 && r==3)) 
		{
			nodes.get(index).getPaintArea().setColor(Color.rgb(198, 200, 201)); // gri alan
			nodes.get(index).state = nodeState.GUARD;
			nodes.get(index).hexPathId = 0;
		}
		if ((q==0 && r==2)) 
		{
			nodes.get(index).getPaintArea().setColor(Color.rgb(198, 200, 201)); // gri alan
			nodes.get(index).state = nodeState.GUARD;
			nodes.get(index).hexPathId = 1;
		}
		if ((q==1 && r==2)) 
		{
			nodes.get(index).getPaintArea().setColor(Color.rgb(198, 200, 201)); // gri alan
			nodes.get(index).state = nodeState.GUARD;
			nodes.get(index).hexPathId = 2;
		}
		
		if( (q==0 && r==-2) || (q==-1 && r==-2) || (q==1 && r==-3))
		{
			nodes.get(index).getPaintArea().setColor(Color.rgb(198, 200, 201)); // gri alan
			nodes.get(index).state = nodeState.GUARD;
		}
		
		if((q==0 && r==-3)){
			nodes.get(index).getPaintArea().setColor(Color.rgb(76,182,196)); // mavi alan
			nodes.get(index).state = nodeState.CASTLE;
		}
		if((q==0 && r==3)){
			nodes.get(index).getPaintArea().setColor(Color.rgb(254,153,0)); // turuncu alan
			nodes.get(index).state = nodeState.CASTLE;
		}
		if((q==0 && r==0) || (q==-2 && r==1) || (q==2 && r==-1)){
			nodes.get(index).getPaintArea().setColor(Color.rgb(141,197,62)); // yeþil alan
			nodes.get(index).state =nodeState.TARGET;
		}
	}

	public void activateNodes(){
		for (int i = 0; i < nodes.size(); i++) {
			nodes.get(i).activateNode();
		}
	}
	
	public void inActivateNodes(){
		for (int i = 0; i < nodes.size(); i++) {
			nodes.get(i).inActivateNode();
		}
	}

	public void draw(Canvas canvas){
		for (Node item : nodes) {
				item.draw(canvas);
		}
	}
	
	public void setNumberOfNodes(){
		for (int i = 0; i < nodes.size(); i++) {
			nodes.get(i).setInitializeNumber();
		}
	}
	
	public void setNumberOfTargets(){
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).state == nodeState.TARGET) {
				nodes.get(i).setNumber(15);
			}
			
		}
	}



}
