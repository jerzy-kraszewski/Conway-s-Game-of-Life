package me.sanfarans;

import javax.swing.*;
import java.awt.*;

public class Prostokat extends JPanel{

	Color color;

	public void setColor(Color k){
		color = k;
	}
	@Override
	public void paintComponent(Graphics g){
		g.setColor(color);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
	}
}