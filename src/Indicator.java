import javax.swing.*;
import java.awt.*;

public class Indicator extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private Thermometer thermometer;
	
	public Indicator(Thermometer t)
	{
		thermometer=t; // It gives us the actual temperature and the thresholds
		setPreferredSize(new Dimension(800, 400));
		this.setLayout(new BorderLayout()); // We create the grid
		repaint();
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		// We create the rectangle
		g.drawLine(370,350,370,50);
		g.drawLine(430,350,430,50);
		g.drawLine(370,50,430,50);
		g.drawLine(370,350,430,350);
		
		// We create the rectangle which indicates the temperature
		if (thermometer.getTemperature()<=100 && thermometer.getTemperature()>=0)
			g.fillRect(370,350-thermometer.getTemperature()*3,60,thermometer.getTemperature()*3); 
		
		g.drawString(String.valueOf(100) + "°C", 330, 60);
		g.drawString(String.valueOf(0) + "°C", 330, 350);
		
	}
	
	
}
