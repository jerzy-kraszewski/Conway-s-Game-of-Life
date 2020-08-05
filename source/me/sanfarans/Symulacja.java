package me.sanfarans;

public class Symulacja{

	boolean[][] grid = new boolean[GolGUI.GRID_SIZE+2][GolGUI.GRID_SIZE+2];

	boolean[][] temp = new boolean[GolGUI.GRID_SIZE+2][GolGUI.GRID_SIZE+2];

	Symulacja(boolean[][] g){
		for (int i = 0; i < 34; ++i){
			for (int j = 0; j < 34; ++j){
				grid[i][j] = false;
			}
		}
		grid = g;
	}

	void nextStep(){
		for (int i = 1; i <= GolGUI.GRID_SIZE; ++i){
			for (int j = 1; j <= GolGUI.GRID_SIZE; ++j){
				int sasiedzi = 0;
				for (int k = i-1; k <= i+1; ++k){
					for (int l = j-1; l <= j+1; ++l){
						if (k == i && l == j){
							continue;
						}
						if (grid[k][l]){
							sasiedzi++;
						}
					}
				}
				if (grid[i][j]){
					if (sasiedzi < 2 || sasiedzi > 3){
						temp[i][j] = false;
					}
					else{
						temp[i][j] = true;
					}
				}
				else{
					if (sasiedzi == 3){
						temp[i][j] = true;
					}
					else{
						temp[i][j] = false;
					}
				}
			}
		}
		for (int i = 1; i <= GolGUI.GRID_SIZE; ++i){
			for (int j = 1; j <= GolGUI.GRID_SIZE; ++j){
				grid[i][j] = temp[i][j];
			}
		}
	}

	public boolean[][] getGridState(){
		return grid;
	}

}