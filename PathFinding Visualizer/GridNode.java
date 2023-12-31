package application;


import javafx.scene.paint.Color;

public class GridNode extends javafx.scene.shape.Rectangle{
private int row;
private int col;
private GridNode parent;
private NodeType nodeType;
private int weight;



	public GridNode(int row, int col, double height, double width,GridNode parent,boolean isStart, boolean isGoal, boolean isWall, boolean isWeighted) {
		this.row = row;
		this.col = col;
		this.parent = parent;
		setWidth(width);
		setHeight(height);
		if (isStart) {
			this.nodeType = NodeType.START;
			setFill(Color.GREEN); // Change the color as needed
		} else if (isGoal) {
			this.nodeType = NodeType.GOAL;
			setFill(Color.RED); // Change the color as needed
		} else if (isWall) {
			this.nodeType = NodeType.WALL;
			setFill(Color.BLACK); // Change the color as needed
		} else if (isWeighted) {
			this.nodeType = NodeType.WEIGHTED;
			this.weight = 2; // Weighted nodes have a weight of 2
			setFill(Color.BLUE); // Change the color as needed
		} else {
			this.nodeType = NodeType.NORMAL;
			this.weight = 1; // Normal nodes have a weight of 1
			setFill(Color.TRANSPARENT); // Change the color as needed
		}
	}
	// Add this method
	public int getWeight() {
		return weight;
	}


	public int getRow() {return row;}

	public GridNode getNodeParent() {return parent;}
	
	public int getCol() {return col;}
	
	
	public NodeType getType() {return nodeType;}

	public void setType(NodeType type) {this.nodeType = type;}

	public void setNodeParent(GridNode Node) {this.parent = Node;}
	
	public boolean isWall() {return nodeType == nodeType.WALL;}

	public boolean isStart() {return nodeType == nodeType.START;}
	
	public boolean isGoal() {return nodeType == nodeType.GOAL;}

	public boolean isWeighted(){ return nodeType == nodeType.WEIGHTED;}

	public boolean isTraversed(){return nodeType == nodeType.TRAVERSED;}
	
	public void resetType() {nodeType = nodeType.NORMAL;}

	public void setPosition(double x, double y) {
		setLayoutX(x);
		setLayoutY(y);
		}
	}


