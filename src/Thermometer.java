import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Thermometer implements ActionListener
{
	private int value; // The value of the temperature
	private int lowThreshold; // The low threshold
	private int highThreshold; // The high threshold
	private int minValue; // The minimum value the temperature can take
	private int maxValue; //  The maximum value the temperature can take
	private Indicator indicator;
	//The following variables are used with the actionListener
	private JFrame window;
	private JMenuItem menuLowThreshold;
	private JMenuItem menuHighThreshold;
	private JLabel displayLowThreshold;
	private JLabel displayHighThreshold;
	private JButton diminueTemperature;
	private JButton diminueTemperatureAuto;
	private JButton increaseTemperature;
	private JButton increaseTemperatureAuto;
	private JLabel displayTemperature;
	// Timers used for the "auto" buttons:
	private Timer timerDiminueTemperature;
	private Timer timerIncreaseTemperature;
	private int flashing;
	private boolean flash;
	
	public Thermometer(int value)
	{
		this.value=value;
		lowThreshold=10;
		highThreshold=90;
		minValue=0;
		maxValue=100;
		indicator = new Indicator(this);
		flashing=0;
		flash=false;
		
		// 500ms timers:
		timerDiminueTemperature = new Timer(500, this); 
		timerIncreaseTemperature = new Timer(500, this); 
		
		IHM();
	}
	
	public void IHM() // Interface 
	{
		// Creation of the window
		window = new JFrame("Temperature");
		
		// Creation of three panels:
		// The north panel is created for the thresholds
		// The south panel is created for the buttons 
		// The principal panel will contain the other panels
		JPanel northPanel = new JPanel(new FlowLayout());
		JPanel southPanel = new JPanel(new FlowLayout());
		JPanel principalPanel = new JPanel (new BorderLayout());
		
		// Creation of the menus
		JMenuBar menus = new JMenuBar();
		JMenu menuThreshold = new JMenu("Thresholds");
		menuLowThreshold = new JMenuItem("Bas");
		menuHighThreshold = new JMenuItem("Haut");
		menuThreshold.add(menuLowThreshold);
		menuThreshold.add(menuHighThreshold);
		menus.add(menuThreshold);
		// We add the menus to the window
		window.setJMenuBar(menus);
		
		window.setContentPane(principalPanel);
		
		// Creation of the objects
		// JLabels:
		displayLowThreshold = new JLabel("Low threshold : " + Integer.toString(this.lowThreshold) + "°C");
		displayHighThreshold = new JLabel("High threshold : " + Integer.toString(this.highThreshold) + "°C");
		displayTemperature = new JLabel(Integer.toString(this.value) + "°C");
		// JButtons:
		diminueTemperature = new JButton("-1");
		increaseTemperature = new JButton("+1");
		diminueTemperatureAuto = new JButton("-auto");
		increaseTemperatureAuto = new JButton("+auto");
		
		//ActionListener:
		menuLowThreshold.addActionListener(this);
		menuHighThreshold.addActionListener(this);
		diminueTemperature.addActionListener(this);
		increaseTemperature.addActionListener(this);
		diminueTemperatureAuto.addActionListener(this);
		increaseTemperatureAuto.addActionListener(this);
		
		// We add the objects:
		// JLabel - North panel
		northPanel.add(displayLowThreshold);
		northPanel.add(displayHighThreshold);
		// We add the north panel to the principal panel:
		principalPanel.add(northPanel, BorderLayout.NORTH);
		// JButton - South panel
		southPanel.add(diminueTemperature);
		southPanel.add(diminueTemperatureAuto);
		southPanel.add(increaseTemperature);
		southPanel.add(increaseTemperatureAuto);
		// The indicator is added as well
		indicator.add(displayTemperature, BorderLayout.SOUTH);
		displayTemperature.setFont(new Font("Dialog", Font.PLAIN, 20));
		// We add these panels to the principal one:
		principalPanel.add(southPanel, BorderLayout.SOUTH);
		principalPanel.add(indicator, BorderLayout.CENTER);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.pack();
		window.setVisible(true);
	}
	
	public int getTemperature()
	{
		return value;
	}
	
	public static void main (String[] args)
	{
		new Thermometer(20);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source==menuLowThreshold)
		{
			String saisie = JOptionPane.showInputDialog(this, "Enter the low threshold.");
			// Is the new threshold correct ?
			while (Integer.parseInt(saisie)<minValue || Integer.parseInt(saisie)>highThreshold)
				saisie = JOptionPane.showInputDialog(this, "The value you entered is not possible. Enter another value.");
			lowThreshold = Integer.parseInt(saisie);
			displayLowThreshold.setText("Low threshold : " + Integer.toString(this.lowThreshold) + "°C");
			window.pack();
		}
		else if (source==menuHighThreshold)
		{
			String saisie = JOptionPane.showInputDialog(this, "Enter the high threshold.");
			// Is the new threshold correct ?
			while (Integer.parseInt(saisie)>maxValue || Integer.parseInt(saisie)<lowThreshold)
				saisie = JOptionPane.showInputDialog(this, "The value you entered is not possible. Enter another value.");
			highThreshold = Integer.parseInt(saisie);
			displayHighThreshold.setText("High threshold : " + Integer.toString(this.highThreshold) + "°C");
			window.pack();
		}
		else if (source==diminueTemperature)
		{
			// Have we reached the threshold ?
			if (value>0)
			{
				value--;
				displayTemperature.setText(Integer.toString(this.value) + "°C");
				indicator.repaint();
			}
			window.pack();
		}
		else if (source==increaseTemperature)
		{
			// Have we reached the threshold ?
			if (value<100)
			{
				value++;
				displayTemperature.setText(Integer.toString(this.value) + "°C");
				indicator.repaint();
			}
			window.pack();
		}
		else if (source==diminueTemperatureAuto)
		{
			// In order to go from the "auto+" button to the "auto-" button directly:
			if (timerIncreaseTemperature.isRunning())
				timerIncreaseTemperature.stop();
			// Timer off ?
			if (!timerDiminueTemperature.isRunning())
				timerDiminueTemperature.start();
			else // Timer off.
				timerDiminueTemperature.stop();
		}
		else if (source==increaseTemperatureAuto)
		{
			// In order to go from the "auto-" button to the "auto+" button directly:
			if (timerDiminueTemperature.isRunning())
				timerDiminueTemperature.stop();
			// Timer off ?
			if (!timerIncreaseTemperature.isRunning())
				timerIncreaseTemperature.start();
			else // Timer off.
				timerIncreaseTemperature.stop();
		}
		
		if (timerDiminueTemperature.isRunning())
		{
			// Have we reached the threshold?
			if (value>0)
			{
				value--;
				displayTemperature.setText(Integer.toString(this.value) + "°C");
				indicator.repaint();
			}
			else if (value==0)
				timerDiminueTemperature.stop();
			window.pack();
		}
		
		if (timerIncreaseTemperature.isRunning())
		{
			// Have we reached the threshold?
			if (value<100)
			{
				value++;
				displayTemperature.setText(Integer.toString(this.value) + "°C");
				indicator.repaint();
			}
			else if (value==100)
				timerIncreaseTemperature.stop();
			window.pack();
		}
		
		if (value<=lowThreshold || value>=highThreshold)
		{
			flash=true;
		}
		
		else
		{
			displayTemperature.setForeground(Color.BLACK);
			flash=false;
		}
		
		if (flash) 
		{
			flashing++;
			if (flashing%2==0)
				displayTemperature.setForeground(Color.RED);
			else if (flashing%2==1)
				displayTemperature.setForeground(Color.BLACK);
		}
		
	}
}
