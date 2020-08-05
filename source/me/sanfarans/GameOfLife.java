package me.sanfarans;

public class GameOfLife{

	public static final int HEIGHT = 1200;
	public static final int WIDTH = 1000;

	public static void main(String[] args){
		new GameOfLife().rozpocznij();
	}

	void rozpocznij(){
		new GolGUI().przygotujGUI();
	}
}