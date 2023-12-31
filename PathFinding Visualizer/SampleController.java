package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import application.NodeType;
import application.GridNode;

import java.util.*;

public class SampleController {
	private NodeType currentType = NodeType.NORMAL;
	boolean isStartNodePlaced = false;
	boolean isGoalNodePlaced = false;
	private int startNodeRow = -1;
	private int startNodeCol = -1;
	private int goalNodeRow = -1;
	private int goalNodeCol = -1;
	private Stage primaryStage;
    private CustomGridPane customGridPane;
    private boolean[][] isWallArray = new boolean[10][10]; // Declare your isWallArray in the SampleController
    private boolean[][] isWeightedArray = new boolean[10][10];
    private GridNode[][] gridNodes;
    @FXML
    private MenuButton algoType;
	@FXML
	private MenuItem Astar;
	@FXML
	private MenuItem BFS;
	@FXML
	private MenuItem DFS;
	@FXML
	private MenuItem Djikstras;
	@FXML
    private TextField dimensions;
	@FXML
    private GridPane gridPane;
    @FXML
    private Button clear;
    @FXML
    private MenuItem goal;
    @FXML
    private MenuButton nodeType;
    @FXML
    private MenuItem normal;
    @FXML
    private Button path;
    @FXML
    private MenuItem start;
    @FXML
    private MenuItem wall;
    @FXML
    private MenuItem weighted;
    @FXML
    void clearGraph(ActionEvent event) {
    	gridPane.getChildren().forEach(node ->{
    		Pane square = (Pane) node;
    		setNormalNode(gridPane.getRowIndex(square), gridPane.getColumnIndex(square));
    	});
    	isStartNodePlaced = false;
    	isGoalNodePlaced = false;
    	startNodeRow = -1;
    	startNodeCol = -1;
    	goalNodeRow = -1;
    	goalNodeCol = -1;
    	start.setDisable(false);
    	goal.setDisable(false);
    }
    @FXML
    void createBlank(ActionEvent event) {
    	nodeType.setText(normal.getText());
    	currentType = NodeType.NORMAL;
    }
    @FXML
    void createWeighted(ActionEvent event){
        nodeType.setText(weighted.getText());
        currentType = NodeType.WEIGHTED;
    }
    @FXML
    void createGraph(ActionEvent event) {
        String gridSizeText = dimensions.getText();
        int gridSize = 0;

        if (!gridSizeText.isEmpty()) {
            gridSize = Integer.parseInt(gridSizeText);
        }
        // Set row and column constraints
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();
        for (int i = 0; i < gridSize; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / gridSize);
            gridPane.getRowConstraints().add(rowConstraints);

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / gridSize);
            gridPane.getColumnConstraints().add(columnConstraints);
        }
        gridPane.getChildren().clear();

        initialize();



        start.setDisable(false);
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Pane square = new Pane();
                square.setStyle("-fx-background-color: white; -fx-border-color: black");
                square.setPrefSize(0, 0); // Adjust the size as needed
                gridPane.add(square, col, row);
                GridPane.setRowIndex(square, row);
                GridPane.setColumnIndex(square, col);
                square.setOnMouseClicked(e -> {
                    switch (currentType) {
                        case START:
                        	settingStart(square);
                            break;
                        case WALL:
                            settingWall(square);
                            break;
                        case GOAL:
                            settingGoal(square);
                            break;
                        case NORMAL:
                            settingNormal(square);
                            break;
                        case WEIGHTED:
                            settingWeighted(square);
                    }
                });
            }
        }
        start.setDisable(false);
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    void createPath(ActionEvent event) {
        String selectedAlgo = algoType.getText();
        initializeGridNodes();
        GridNode start = getNodeAt(startNodeRow,startNodeCol);
        GridNode goal = getNodeAt(goalNodeRow,goalNodeCol);
        switch(selectedAlgo){
            case "BFS":
                bfs();
                break;
            case "DFS":
                dfs();
                break;
            case "Djikstras":
                djikstras(start,goal);
                break;
            case "A*":
                Astar();
                break;
            default:
                System.out.println("No selection has been made");
                break;
       }
    }
    @FXML
    void createWall(ActionEvent event) {
    	nodeType.setText(wall.getText());
    	currentType = NodeType.WALL;
    }
    @FXML
    void goalNode(ActionEvent event) {
    	nodeType.setText(goal.getText());
    	currentType = NodeType.GOAL;
    }
    @FXML
    void startNode(ActionEvent event) {
    	nodeType.setText(start.getText());
    	currentType = NodeType.START;
    	}

    @FXML
    void findAStar(ActionEvent event) {
    	algoType.setText(Astar.getText());
    }
    @FXML
    void findBFS(ActionEvent event) {
    	algoType.setText(BFS.getText());

    }
    @FXML
    void findDFS(ActionEvent event) {
    	algoType.setText(DFS.getText());
    }
    @FXML
    void findDijkstra(ActionEvent event) {
    	algoType.setText(Djikstras.getText());
    }



    private void setGoalNode(int row, int col) {
    	int gridSize = gridPane.getRowConstraints().size();
        Pane square = (Pane) gridPane.getChildren().get(row * gridSize + col);
        square.setStyle("-fx-background-color: red; -fx-border-color: black");
        isGoalNodePlaced = true;
        updateGoalOption();
        goalNodeRow = row;
        goalNodeCol = col;
    }

    private void setWeightedNode(int row, int col) {
        int gridSize = gridPane.getRowConstraints().size();
        Pane square = (Pane) gridPane.getChildren().get(row * gridSize + col);
        square.setStyle("-fx-background-color: blue; -fx-border-color: black");
        isWeightedArray[row][col] = true;
    }

    private void setNormalNode(int row, int col) {
    	int gridSize = gridPane.getRowConstraints().size();
   	 	Pane square = (Pane) gridPane.getChildren().get(row * gridSize + col);
        square.setStyle("-fx-background-color: white; -fx-border-color: black");
    }
    private void setWallNode(int row, int col) {
    	 int gridSize = gridPane.getRowConstraints().size();
    	 Pane square = (Pane) gridPane.getChildren().get(row * gridSize + col);
         square.setStyle("-fx-background-color: black; -fx-border-color: white");
         isWallArray[row][col] = true;

    }
    private void setStartNode(int row, int col) {
        int gridSize = gridPane.getRowConstraints().size();
        Pane square = (Pane) gridPane.getChildren().get(row * gridSize + col);
        square.setStyle("-fx-background-color: green; -fx-border-color: black");
        isStartNodePlaced = true;
        updateStartOption();
        startNodeRow = row;
        startNodeCol = col;
    }

    private void settingStart(Pane square) {
    	int selectedRow = GridPane.getRowIndex(square);
        int selectedCol = GridPane.getColumnIndex(square);
    	if (square.getStyle() == "-fx-background-color: green; -fx-border-color: black") {
            // The current square is a start node, do nothing
            return;
        }
    	  if (isStartNodePlaced) {
    	        setNormalNode(startNodeRow, startNodeCol); // Revert the previous start node to normal
    	    }
    	    setStartNode(selectedRow, selectedCol); // Set the new start node
    	}
    private void settingWall(Pane square) {
   	 int selectedRow = GridPane.getRowIndex(square);
     int selectedCol = GridPane.getColumnIndex(square);
        if (selectedRow == startNodeRow && selectedCol == startNodeCol) {
            isStartNodePlaced = false; // Reset the flag
            start.setDisable(false); // Enable the start node option
        }
        if (selectedRow == goalNodeRow && selectedCol == goalNodeCol) {
            isGoalNodePlaced = false;
            goal.setDisable(false);
        }
     setWallNode(selectedRow, selectedCol);

    }
    private void settingWeighted(Pane square){
        int selectedRow = GridPane.getRowIndex(square);
        int selectedCol = GridPane.getColumnIndex(square);
        if (selectedRow == startNodeRow && selectedCol == startNodeCol) {
            isStartNodePlaced = false; // Reset the flag
            start.setDisable(false); // Enable the start node option
        }
        if (selectedRow == goalNodeRow && selectedCol == goalNodeCol) {
            isGoalNodePlaced = false;
            goal.setDisable(false);
        }
        setWeightedNode(selectedRow,selectedCol);


    }
    private void settingGoal(Pane square) {
    	int selectedRow = GridPane.getRowIndex(square);
        int selectedCol = GridPane.getColumnIndex(square);
    	if (square.getStyle() == "-fx-background-color: red; -fx-border-color: black") {
            // The current square is a start node, do nothing
            return;
        }
    	  if (isGoalNodePlaced) {
    	        setNormalNode(goalNodeRow, goalNodeCol); // Revert the previous start node to normal
    	    }
    	    setGoalNode(selectedRow, selectedCol); // Set the new start node
    	}

    private void settingNormal(Pane square) {
    	int selectedRow = GridPane.getRowIndex(square);
        int selectedCol = GridPane.getColumnIndex(square);
        if (selectedRow == startNodeRow && selectedCol == startNodeCol) {
            isStartNodePlaced = false; // Reset the flag
            start.setDisable(false); // Enable the start node option
        }
        if (selectedRow == goalNodeRow && selectedCol == goalNodeCol) {
        	isGoalNodePlaced = false;
        	goal.setDisable(false);
        }
        setNormalNode(selectedRow, selectedCol);
    }
    private List<GridNode> getNeighbours(GridNode node){
        int gridSize = gridPane.getRowConstraints().size();
        List<GridNode> neighbours = new ArrayList<>();

        if(node.getRow() > 0){
            GridNode neighbour = getNodeAt(node.getRow()-1,node.getCol());
            if (!neighbour.isWall()) {
                neighbours.add(neighbour);
            }
        }
        if(node.getRow()<gridSize-1){
            GridNode neighbour = getNodeAt(node.getRow()+1,node.getCol());
            if (!neighbour.isWall()) {
                neighbours.add(neighbour);
            }
        }
        if(node.getCol() > 0){
            GridNode neighbour = getNodeAt(node.getRow(),node.getCol()-1);
            if (!neighbour.isWall()) {
                neighbours.add(neighbour);
            }
        }
        if(node.getCol()<gridSize-1){
            GridNode neighbour = getNodeAt(node.getRow(),node.getCol()+1);
            if (!neighbour.isWall()) {
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }
    private void highlightPath(GridNode node){
        while(node!=null ){
            if(!node.isStart() && !node.isGoal()&&!node.isWall()){
                setPathNode(node.getRow(),node.getCol());
            }
            node = node.getNodeParent();
        }
    }
    private void setPathNode(int row, int col){
        GridNode node = getNodeAt(row,col);
        if(node != null && !node.isStart() && !node.isGoal() && !node.isWall()){
            System.out.println("Setting path node at (" + row + ", " + col + ")");
            node.setType(NodeType.TRAVERSED);
            node.setFill(Color.YELLOW);
            int gridSize = gridPane.getRowConstraints().size();
            Pane square = (Pane) gridPane.getChildren().get(row * gridSize + col);
            square.setStyle("-fx-background-color: yellow; -fx-border-color: black");
        }
    }


    private void initializeGridNodes() {
        int gridSize = gridPane.getRowConstraints().size();
        gridNodes = new GridNode[gridSize][gridSize];

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                GridNode node = new GridNode(row, col, 30.0, 30.0, null, false, false,false,false);
                gridPane.add(node, col, row); // add the node to the gridPane at the correct position
                gridNodes[row][col] = node;
                System.out.println("Initializing node at (" + row + ", " + col + "): " + node);
            }
        }
    }

    private GridNode getNodeAt(int row, int col) {
        if (row >= 0 && row < gridNodes.length && col >= 0 && col < gridNodes[0].length) {
            GridNode node = gridNodes[row][col];
            System.out.println("Getting node at (" + row + ", " + col + "): " + node);
            return node;
        } else {
            System.out.println("Invalid indices: (" + row + ", " + col + ")");
            return null;
        }
    }
    public void bfs() {
        int gridSize = gridPane.getRowConstraints().size();
        boolean[][] visited = new boolean[gridSize][gridSize];
        Queue<GridNode> queue = new LinkedList<>();
        GridNode startNode = getNodeAt(startNodeRow, startNodeCol);
        queue.add(startNode);
        visited[startNodeRow][startNodeCol] = true;


        while (!queue.isEmpty()) {
            GridNode node = queue.poll();
            if (node == null) {
                continue;
            }
            if (isWallArray[node.getRow()][node.getCol()]) { // Skip processing walls
                continue;
            }

            if (node.getRow() == goalNodeRow && node.getCol() == goalNodeCol) {
                highlightPath(node);
                break;
            }

            List<GridNode> neighbours = getNeighbours(node);
            for (GridNode neighbour : neighbours) {
                int row = neighbour.getRow();
                int col = neighbour.getCol();

                if (row >= 0 && row < gridSize && col >= 0 && col < gridSize &&
                        !visited[row][col] && !neighbour.isWall()) {

                    queue.add(neighbour);
                    visited[row][col] = true;

                    if (!node.isWall() && !neighbour.isWall()) {
                        neighbour.setNodeParent(node);
                    }
                }
            }
        }
    }

    public void dfs(){
        int gridSize = gridPane.getRowConstraints().size();
        boolean [][] visited = new boolean[gridSize][gridSize];
        Stack<GridNode> stack = new Stack<>();
        GridNode startNode = getNodeAt(startNodeRow, startNodeCol);
        stack.push(startNode);
        visited[startNodeRow][startNodeCol] = true;
        while (!stack.isEmpty()) {
            GridNode node = stack.pop();

            if (isWallArray[node.getRow()][node.getCol()]) {
                continue;
            }
            if (node.getRow() == goalNodeRow && node.getCol() == goalNodeCol) {
                highlightPath(node);
                break;
            }
            List<GridNode> neighbours = getNeighbours(node);
            for (GridNode neighbour : neighbours) {
                int row = neighbour.getRow();
                int col = neighbour.getCol();

                if (row >= 0 && row < gridSize && col >= 0 && col < gridSize &&
                        !visited[row][col] && !neighbour.isWall()) {

                    stack.push(neighbour);
                    visited[row][col] = true;
                    neighbour.setNodeParent(node);
                }

            }
        }
        }

    public void djikstras(GridNode start, GridNode goal){
        PriorityQueue<GridNode> queue = new PriorityQueue<>(Comparator.comparingInt(GridNode::getWeight));
        Map<GridNode,Integer> distances = new HashMap<>();
        Map<GridNode,GridNode> previousNodes = new HashMap<>();

        queue.add(start);
        distances.put(start,0);

        while(!queue.isEmpty()){
            GridNode current = queue.poll();

            if(current.equals(goal)){
                reconstructPath(previousNodes,start,goal);
                return;
            }
            for(GridNode neighbour:getNeighbours(current)){
                if(isWallArray[neighbour.getRow()][neighbour.getCol()]){
                    continue;
                }
                int distance = distances.get(current) + neighbour.getWeight();
                if(!distances.containsKey(neighbour)||distance<distances.get(neighbour)){
                    distances.put(neighbour,distance);
                    previousNodes.put(neighbour,current);
                    queue.add(neighbour);
                }
            }
        }

    }

    public void reconstructPath(Map<GridNode,GridNode> previousNodes,GridNode start, GridNode goal){
        List<GridNode> path = new ArrayList<>();
        for(GridNode node = goal; node!=null; node=previousNodes.get(node)){
            path.add(node);
        }
        Collections.reverse(path);

        for(GridNode node: path){
            if(!node.equals(start) && !node.equals(goal)){
                setPathNode(node.getRow(),node.getCol());
            }
        }
    }

    public void Astar(){

    }

    public void initialize() {
        String dimensionsText = dimensions.getText();
        if (!dimensionsText.isEmpty()) {
            int gridSize = Integer.parseInt(dimensions.getText());
            customGridPane = new CustomGridPane(gridSize, startNodeRow, startNodeCol, goalNodeRow, goalNodeCol);

        }
    }

    public Pane getGridPane() {return gridPane;}
	public TextField getGridSizeTextField() {return dimensions;}
	private void updateStartOption() {start.setDisable(isStartNodePlaced);}
	private void updateGoalOption() {goal.setDisable(isGoalNodePlaced);}
}
