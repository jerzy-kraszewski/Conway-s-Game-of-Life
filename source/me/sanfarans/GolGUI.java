package me.sanfarans;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GolGUI{

	JFrame ramkaGlowna;
	JPanel panelGlowny;

	JPanel panelMenu;
	JPanel panelHelp;
	JPanel panelAbout;
	JPanel panelGry;
	JPanel panelAnimacji;

	Symulacja symulacja;
	boolean stopSymulacja = false;

	int speed = 128;
	JButton guzikSpeedUp;
	JButton guzikSpeedDown;

	static final int GRID_SIZE = 64;

	JCheckBox[][] krateczki = new JCheckBox[GRID_SIZE+2][GRID_SIZE+2];
	Prostokat[][] prostokaty = new Prostokat[GRID_SIZE+2][GRID_SIZE+2];
	boolean[][] gridState;

	void przygotujGUI(){
		ramkaGlowna = new JFrame("Game of life");
		ramkaGlowna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelGlowny = new JPanel(new BorderLayout());
		ramkaGlowna.getContentPane().add(panelGlowny);

		setupPanels();

		ustawPanel(panelMenu);

		ramkaGlowna.setSize(GameOfLife.WIDTH,GameOfLife.HEIGHT);
		ramkaGlowna.setVisible(true);
	}

	void ustawPanel(JPanel panel){
		panelGlowny.removeAll();
		panelGlowny.add(panel);
		panelGlowny.repaint();
		panelGlowny.revalidate();
	}

	void setupPanels(){
		panelMenuSetup();
		panelGrySetup();
		panelAnimacjiSetup();
		panelHelpSetup();
		panelAboutSetup();
	}

	void rysujProstokaty(){
		gridState = symulacja.getGridState();
		for (int i = 1; i <= GRID_SIZE; ++i){
			for (int j = 1; j <= GRID_SIZE; ++j){
				if (gridState[i][j]){
					prostokaty[i][j].setColor(Color.black);
				}
				else{
					prostokaty[i][j].setColor(Color.white);
				}
			}
			panelGlowny.repaint();
		}
	}

	class Animacja implements Runnable{

		public void run(){
			while (! stopSymulacja){
				rysujProstokaty();
				try{
					Thread.sleep(speed);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				symulacja.nextStep();
			}
		}
	}

	// setup funkcje
	void panelMenuSetup(){
		panelMenu = new JPanel(new BorderLayout());
		panelMenu.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
		panelMenu.setBackground(Color.pink);

		JPanel panelGuzikowMenuGlowne = new JPanel();
		panelGuzikowMenuGlowne.setBackground(Color.pink);

		JLabel napisTytulowy = new JLabel("Conway's Game of Life", SwingConstants.CENTER);
		napisTytulowy.setFont(new Font("Serif", Font.PLAIN, 69));

		JButton guzikPlay = new JButton("Play");
		guzikPlay.addActionListener(new PlayListener());

		JButton guzikHelp = new JButton("Help");
		guzikHelp.addActionListener(new HelpListener());

		JButton guzikAbout = new JButton("About");
		guzikAbout.addActionListener(new AboutListener());

		panelGuzikowMenuGlowne.add(guzikPlay);
		panelGuzikowMenuGlowne.add(guzikHelp);
		panelGuzikowMenuGlowne.add(guzikAbout);

		panelMenu.add(napisTytulowy);
		panelMenu.add(panelGuzikowMenuGlowne, BorderLayout.SOUTH);

	}

	void panelHelpSetup(){
		panelHelp = new JPanel(new BorderLayout());
		panelHelp.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
		panelHelp.setBackground(Color.pink);

		String tekst = "<html>In Conway's Game of Life there is no goal<br>Just setup the starting position and observe the evolution<br><br>The rules are simple:<br> Any live cell with fewer than two live neighbours dies, as if by underpopulation.<br> Any live cell with two or three live neighbours lives on to the next generation.<br> Any live cell with more than three live neighbours dies, as if by overpopulation.<br> Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.";
		JLabel napisHelp = new JLabel(tekst, SwingConstants.CENTER);
		napisHelp.setFont(new Font("Serif",Font.PLAIN, 20));

		JPanel panelZGuzikiemBack = new JPanel();
		panelZGuzikiemBack.setBackground(Color.pink);
		JButton guzikBack = new JButton("Back");
		guzikBack.addActionListener(new BackListener());
		panelZGuzikiemBack.add(guzikBack);

		panelHelp.add(napisHelp);
		panelHelp.add(panelZGuzikiemBack, BorderLayout.SOUTH);
	}

	void panelAboutSetup(){
		panelAbout = new JPanel(new BorderLayout());
		panelAbout.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
		panelAbout.setBackground(Color.pink);

		String tekst = "<html>Made by Jerzy Kraszewski<br><center>using Java Swing";
		JLabel napisAbout = new JLabel(tekst, SwingConstants.CENTER);
		napisAbout.setFont(new Font("Serif", Font.PLAIN, 30));

		JPanel panelZGuzikiemBack = new JPanel();
		panelZGuzikiemBack.setBackground(Color.pink);
		JButton guzikBack = new JButton("Back");
		guzikBack.addActionListener(new BackListener());
		panelZGuzikiemBack.add(guzikBack);

		panelAbout.add(napisAbout);
		panelAbout.add(panelZGuzikiemBack, BorderLayout.SOUTH);
	}

	void panelGrySetup(){
		panelGry = new JPanel(new BorderLayout());
		panelGry.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
		panelGry.setBackground(Color.pink);
		GridLayout siatka = new GridLayout(GRID_SIZE,GRID_SIZE);
		JPanel panelPlanszy = new JPanel(siatka);
		panelPlanszy.setBackground(Color.pink);
		for (int i = 1; i <= GRID_SIZE; ++i){
			for (int j = 1; j <= GRID_SIZE; ++j){
				JCheckBox komorka = new JCheckBox();
				komorka.setBackground(Color.pink);
				panelPlanszy.add(komorka);
				krateczki[i][j] = komorka;
			}
		}
		panelGry.add(panelPlanszy);

		JPanel panelMenuGry = new JPanel();

		JButton guzikGo = new JButton("Go!");
		guzikGo.addActionListener(new GoListener());

		JButton guzikClear = new JButton("Clear");
		guzikClear.addActionListener(new ClearListener());

		JButton guzikBack = new JButton("Back");
		guzikBack.addActionListener(new BackListener());

		panelMenuGry.add(guzikGo);
		panelMenuGry.add(guzikClear);
		panelMenuGry.add(guzikBack);
		panelMenuGry.setBackground(Color.pink);
		panelGry.add(panelMenuGry, BorderLayout.SOUTH);
	}

	void panelAnimacjiSetup(){
		panelAnimacji = new JPanel(new BorderLayout());
		panelAnimacji.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
		panelAnimacji.setBackground(Color.pink);
		GridLayout siatka = new GridLayout(GRID_SIZE,GRID_SIZE);
		JPanel panelPlanszy = new JPanel(siatka);

		for (int i = 1; i <= GRID_SIZE; ++i){
			for (int j = 1; j <= GRID_SIZE; ++j){
				Prostokat p = new Prostokat();
				panelPlanszy.add(p);
				prostokaty[i][j] = p;
			}
		}
		panelAnimacji.add(panelPlanszy);

		JPanel panelMenuAnimacji = new JPanel();
		panelMenuAnimacji.setBackground(Color.pink);

		JButton guzikAnimacjaBack = new JButton("Back");
		guzikAnimacjaBack.addActionListener(new AnimacjaBackListener());
		panelMenuAnimacji.add(guzikAnimacjaBack);

		guzikSpeedUp = new JButton("Speed +");
		guzikSpeedUp.addActionListener(new SpeedUpListener());
		panelMenuAnimacji.add(guzikSpeedUp);

		guzikSpeedDown = new JButton("Speed -");
		guzikSpeedDown.addActionListener(new SpeedDownListener());
		panelMenuAnimacji.add(guzikSpeedDown);


		panelAnimacji.add(panelMenuAnimacji, BorderLayout.SOUTH);
	}
	// koniec setup funkcji

	// listenery
	class PlayListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae){
			ustawPanel(panelGry);
		}
	} 
	class HelpListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae){
			ustawPanel(panelHelp);
		}
	}
	class AboutListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae){
			ustawPanel(panelAbout);
		}
	}

	class GoListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae){
			ustawPanel(panelAnimacji);
			
			gridState = new boolean[GRID_SIZE+2][GRID_SIZE+2];
			for (int i = 1; i <= GRID_SIZE; ++i){
				for (int j = 1; j <= GRID_SIZE; ++j){
					gridState[i][j] = krateczki[i][j].isSelected();
				}
			}
			symulacja = new Symulacja(gridState);
			stopSymulacja = false;
			Runnable animacja = new Animacja();
			Thread watekAnimacyjny = new Thread(animacja);
			watekAnimacyjny.start();

		}
	}
	class ClearListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae){
			//System.out.println("kliknieto clear");
			for (int i = 1; i <= GRID_SIZE; ++i){
				for (int j = 1; j <= GRID_SIZE; ++j){
					krateczki[i][j].setSelected(false);
				}
			}
		}
	}
	class BackListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae){
			ustawPanel(panelMenu);
		}
	}

	class AnimacjaBackListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae){
			stopSymulacja = true;
			ustawPanel(panelGry);
		}
	}

	class SpeedUpListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae){
			speed = speed / 2;
			if (speed == 8){
				guzikSpeedUp.setEnabled(false);
			}
			guzikSpeedDown.setEnabled(true);
		}
	}
	class SpeedDownListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae){
			speed = speed * 2;
			if (speed == 2048){
				guzikSpeedDown.setEnabled(false);
			}
			guzikSpeedUp.setEnabled(true);
		}
	}
	// koniec listenerow

}