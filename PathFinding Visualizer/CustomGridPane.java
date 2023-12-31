package application;

import javafx.scene.layout.Pane;

public class CustomGridPane extends javafx.scene.layout.GridPane {

public int gridSize;
double nodeSize = 30.0;
private Pane gridPane;
private boolean[][] isWallArray;
private boolean[][] isWeightedArray;
private int startNodeRow, startNodeCol, goalNodeRow, goalNodeCol;
	public CustomGridPane(int gridSize, int startRow, int startCol, int goalRow, int goalCol) {
		this.gridSize = gridSize;
		this.isWallArray = new boolean[gridSize][gridSize];
		this.isWeightedArray = new boolean[gridSize][gridSize];
		this.startNodeRow = startRow;
		this.startNodeCol = startCol;
		this.goalNodeRow = goalRow;
		this.goalNodeCol = goalCol;

		gridPane = generateGridPane();
	}
		private Pane generateGridPane() {
			Pane pane = new Pane();
			double nodeSize = 30.0;
			for (int i = 0; i < gridSize; i++) {	
				for(int j = 0; j < gridSize; j++) {
					boolean isStart = (i == startNodeRow && j == startNodeCol);
					boolean isGoal = (i == goalNodeRow && j == goalNodeCol);
					boolean isWall = isWallArray[i][j];
					boolean isWeighted = isWeightedArray[i][j];
					GridNode gridnode = new GridNode(i, j, nodeSize, nodeSize, null, isStart,isGoal,isWall,isWeighted);
					double x = j*nodeSize;
					double y = i*nodeSize;
					
					gridnode.setPosition(x,y);
					pane.getChildren().add(gridnode);
				}
		
			
		
		}
			return pane;
	}
	public int GridSize() {return gridSize;}
	
	
	public Pane getGridPane(){return gridPane;}

	public boolean[][] getIsWallArray() {
		return isWallArray;
	}
	public void setNodeAsWall(int row, int col) {
		isWallArray[row][col] = true;
	}

	public void setNodeAsNonWall(int row, int col) {
		isWallArray[row][col] = false;
	}
}

