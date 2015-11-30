import data_structures.Queue;
import data_structures.Stack;
public class MazeSolver {
	protected int mazeSize;
	protected Queue<GridCell> qList;
	protected Stack<GridCell> sList;
	protected MazeGrid myMaze;
	//Takes a single argument, the number of rows and columns in the grid. Suggested values are 25 .. 50.
	public MazeSolver(int dimension) {
		mazeSize = dimension;
		myMaze = new MazeGrid(this, dimension);
	}
	//runs the breadth first traversal, and marks each reachable cell in the grid with its distance from the origin.
	public void mark() {
		qList = new Queue<GridCell>();
		//enqueue cell(0,0)
		qList.enqueue(myMaze.getCell(0,0));
		while( !qList.isEmpty() ) {
		    //dequeue a GridCell from the queue
			GridCell currCell = qList.dequeue();
			int currX = currCell.getX();
			int currY = currCell.getY();
			int currDist = currCell.getDistance();
			if (currX == 0 && currY == 0) {
				currDist = 0;
				currCell.setDistance(currDist);
			}
			currDist += 1;
		    //mark each adjacent neighboring cell and enqueue it
			GridCell cellBot = myMaze.getCell(currX, currY+1);
			GridCell cellTop = myMaze.getCell(currX, currY-1);
			GridCell cellRight = myMaze.getCell(currX+1, currY);
			GridCell cellLeft = myMaze.getCell(currX-1, currY);
			markCell(cellBot, currDist);
			markCell(cellTop, currDist);
			markCell(cellRight, currDist);
			markCell(cellLeft, currDist);
		}
	}
	private void markCell(GridCell cell, int dist) {
		if (myMaze.isValidMove(cell)) {
			if (!cell.wasVisited()) {
				cell.setDistance(dist);
				myMaze.markDistance(cell);
				qList.enqueue(cell);
			}
		}
	}	
	//Does part two, indicates in the GUI the shortest path found.
	public boolean move() {
		sList = new Stack<GridCell>();
		GridCell terminalCell = myMaze.getCell(mazeSize-1, mazeSize-1);
		int termX = terminalCell.getX();
		int termY = terminalCell.getY();
		int distance = terminalCell.getDistance();
		if (distance == -1) return false;
		sList.push(terminalCell);
		while (distance != 0) {
			GridCell cellTop = myMaze.getCell(termX, termY-1);
			GridCell cellLeft = myMaze.getCell(termX-1, termY);				
			GridCell cellBot = myMaze.getCell(termX, termY+1);
			GridCell cellRight = myMaze.getCell(termX+1, termY);
			distance -= 1;
			if (myMaze.isValidMove(cellTop) && cellTop.getDistance() == distance) {
				termX = cellTop.getX();
				termY = cellTop.getY();
				sList.push(cellTop);
			}
			else if (myMaze.isValidMove(cellLeft) && cellLeft.getDistance() == distance) {
				termX = cellLeft.getX();
				termY = cellLeft.getY();
				sList.push(cellLeft);
			}
			else if (myMaze.isValidMove(cellBot) && cellBot.getDistance() == distance) {
				termX = cellBot.getX();
				termY = cellBot.getY();
				sList.push(cellBot);
			}
			else if (myMaze.isValidMove(cellRight) && cellRight.getDistance() == distance) {
				termX = cellRight.getX();
				termY = cellRight.getY();
				sList.push(cellRight);
			}
		}
		while (!sList.isEmpty())
			myMaze.markMove(sList.pop());
		return true;
	}
	//Reinitializes the puzzle. Clears the stack and queue (calls makeEmpty() )
	public void reset() {
		qList.makeEmpty();
		
	}
}