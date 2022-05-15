import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mindfusion.charting.components.GridColumn;
import com.mindfusion.charting.components.GridPanel;
import com.mindfusion.charting.components.GridRow;
import com.mindfusion.charting.components.gauges.Alignment;
import com.mindfusion.charting.components.gauges.Length;
import com.mindfusion.charting.components.gauges.LengthType;
import com.mindfusion.charting.components.gauges.Range;
import com.mindfusion.charting.swing.Dashboard;
import com.mindfusion.drawing.Brushes;


public class GaugeDemo extends JFrame
{
	private static SpeedometerGauge speedometer;
	private static CyclometerGauge cyclometer;
	private static RectangleHalfGauge voltMeter;
	private static RectangleHalfGauge fuelMeter;
	
	private static int screenW;
	private static int screenH;

	private Range[] createVoltRanges()
	{
		Range greenRange = new Range()
		{{
			setMinValue(48);
			setMaxValue(96);
			setStartWidth(new Length(7, LengthType.Relative));
			setEndWidth(new Length(7, LengthType.Relative));
			setFill(Brushes.Green);
			setAlignment(Alignment.OuterOutside);
		}};
		
		Range yellowRange = new Range()
		{{
			setMinValue(30);
			setMaxValue(48);
			setStartWidth(new Length(7, LengthType.Relative));
			setEndWidth(new Length(7, LengthType.Relative));
			setFill(Brushes.Yellow);
			setAlignment(Alignment.OuterOutside);
		}};

		Range redRange = new Range()
		{{
			setMinValue(0);
			setMaxValue(30);
			setStartWidth(new Length(7, LengthType.Relative));
			setEndWidth(new Length(7, LengthType.Relative));
			setFill(Brushes.Red);
			setAlignment(Alignment.OuterOutside);
		}};

		return new Range[] { greenRange, yellowRange, redRange };
	}
	
	private Range[] createFuelRanges()
	{
		Range start = new Range()
		{{
			setMinValue(0);
			setMaxValue(20);
			setStartWidth(new Length(7, LengthType.Relative));
			setEndWidth(new Length(1, LengthType.Relative));
			setFill(Brushes.Red);
			setAlignment(Alignment.OuterOutside);
		}};

		Range end = new Range()
		{{
			setMinValue(76);
			setMaxValue(96);
			setStartWidth(new Length(1, LengthType.Relative));
			setEndWidth(new Length(7, LengthType.Relative));
			setFill(Brushes.Red);
			setAlignment(Alignment.OuterOutside);
		}};

		return new Range[] { start, end };
	}

	public GaugeDemo()
	{
		setTitle("MindFusion.Charting sample: Car Gauges");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		content = new JPanel();
		content.setBackground(new Color(242, 242, 242));
		content.setLayout(new GridLayout(1, 1));
		getContentPane().add(content);

		Dashboard dashboard = new Dashboard();
		dashboard.setBackground(new Color (240, 240, 240));

		GridPanel layoutPanel = new GridPanel()
		{{
			getColumns().add(new GridColumn());
			getRows().add(new GridRow());
			getColumns().get(0).setLengthType(Relative);
			getColumns().get(1).setLengthType(Relative);
			getRows().get(0).setLengthType(Relative);
			getRows().get(1).setLengthType(Relative);
		}};

		speedometer = new SpeedometerGauge(0, 0);
		speedometer.addBaseGaugeListener(new DemoAdapter());
		layoutPanel.getChildren().add(speedometer);

		cyclometer = new CyclometerGauge(1, 0);
		cyclometer.addBaseGaugeListener(new DemoAdapter());
		layoutPanel.getChildren().add(cyclometer);

		voltMeter = new RectangleHalfGauge(0, 1)
		{{
			setRanges(createVoltRanges());
			setShowLabels(true);
			setTextValue("Volt");
			constructScale();
		}};
		layoutPanel.getChildren().add(voltMeter);

		fuelMeter = new RectangleHalfGauge(1, 1)
		{{
			setRanges(createFuelRanges());
			setShowLabels(false);
			setTextValue("Fuel");
			constructScale();
		}};
		layoutPanel.getChildren().add(fuelMeter);
		
		RectangularGaugeSlider volt =
			new RectangularGaugeSlider("Volt", voltMeter, 0);
		RectangularGaugeSlider fuel =
			new RectangularGaugeSlider("Fuel", fuelMeter, 1);

		SpeedMeterSliders sliders = new SpeedMeterSliders();
		CyclometerSlider slider2 = new CyclometerSlider();

		getRootPane().addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				Dimension d = e.getComponent().getSize();

				speedometer.scale(d.height/561.0f);
				cyclometer.scale(d.height/561.0f);
				fuelMeter.scale(d.height/561.0f);
				voltMeter.scale(d.height/561.0f);

				dashboard.removeAll();
				dashboard.add(volt.getComponent(d));
				dashboard.add(fuel.getComponent(d));
				dashboard.add(sliders.getComponent(d));
				dashboard.add(slider2.getComponent(d));
			}
		});
		 
		dashboard.getLayoutPanel().getChildren().add(layoutPanel);
		dashboard.getTheme().setGaugePointerStroke(null);
		dashboard.getTheme().setGaugePointerBackground(null);
		dashboard.setSize(content.getWidth(), content.getHeight());

		content.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				super.componentResized(e);
				dashboard.setSize(content.getWidth(), content.getHeight());
			}
		});
		content.add(dashboard);
	}

	public static class CyclometerSlider
		extends JSlider implements ChangeListener
	{
		private JPanel panel;
		
		private final int baseX=800;
		private final int baseY=340;

		public CyclometerSlider()
		{
			setMaximum(90);
			addChangeListener(this);
		}

		@Override
		public void stateChanged(ChangeEvent arg0)
		{
			cyclometer.speedPointer.setValue(getValue());
		}

		public JPanel getComponent(Dimension d)
		{
			panel=new JPanel();
			JLabel text=new JLabel("RPM:");
			panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
			panel.add(text);
			panel.add(this);
			panel.setBackground(new Color(242, 242, 242));	
			panel.setBounds((int)(baseX*d.getWidth()/1366.0f),(int)(baseY*d.getHeight()/705.0f), (int) (d.getWidth()/784.0f*200), 35);
			
			updateUI();
			return panel;
		}
	}
	
	public static class RectangularGaugeSlider
		extends JSlider implements ChangeListener
	{
		private RectangleHalfGauge gauge;
		private int xPosition;
		private String textS;
		
		private final int baseX = 70;
		private final int baseY = 580;

		public RectangularGaugeSlider(String text, RectangleHalfGauge gauge, int xPosition)
		{
			textS = text;
			this.gauge = gauge;
			this.xPosition = xPosition;
			
			setMaximum(96);
			addChangeListener(this);
		}

		@Override
		public void stateChanged(ChangeEvent arg0)
		{
			gauge.speedPointer.setValue(getValue());
		}

		public JPanel getComponent(Dimension d)
		{
			JPanel out = new JPanel();
			JLabel text = new JLabel(textS + " ");
			out.setLayout(new BoxLayout(out, BoxLayout.X_AXIS));
			out.add(text);
			out.add(this);
			out.setBounds((int)(baseX+(680*xPosition)*d.getWidth()/1366),(int)(d.getHeight() - 45), (int) (200), 35);
			updateUI();
			out.setBackground(new Color(242, 242, 242));
			return out;
		}
	}
	
	public static class SpeedMeterSliders
	{
		private JSlider speed;
		private JSlider fuel;
		
		private final int baseX=120;
		private final int baseY=325;
		
		private JPanel speedPanel;
		private JPanel fuelPanel;
		
		public SpeedMeterSliders()
		{
			speedPanel=new JPanel();
			fuelPanel=new JPanel();
			
			speedPanel.setBackground(new Color(242, 242, 242));
			fuelPanel.setBackground(new Color(242, 242, 242));
			speed=new JSlider();
			fuel=new JSlider();
			
			fuel.setMaximum(100);
			speed.setMaximum(190);
			speed.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent arg0)
				{
					speedometer.speedPointer.setValue(speed.getValue());
				}
			});
			
			fuel.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent arg0)
				{
					speedometer.gasPointer.setValue(fuel.getValue());
				}
			});
			
			JLabel speedText=new JLabel("Speed:");
			JLabel fuelText=new JLabel("Fuel:");
			
			speedPanel.setLayout(new BoxLayout(speedPanel, BoxLayout.X_AXIS));
			speedPanel.add(speedText);
			speedPanel.add(speed);
			
			fuelPanel.setLayout(new BoxLayout(fuelPanel, BoxLayout.X_AXIS));
			fuelPanel.add(fuelText);
			fuelPanel.add(fuel);
		}
		
		public JPanel getComponent(Dimension d)
		{
			JPanel out=new JPanel();
		
			out.setLayout(new BoxLayout(out,BoxLayout.Y_AXIS));
			out.add(speedPanel);
			out.add(fuelPanel);
			out.setBounds((int) (baseX*d.getWidth()/1366), (int)(baseY*d.getHeight()/705), (int) (d.getWidth()/784.0f*200), 35);
			speed.updateUI();
			fuel.updateUI();
			return out;
		}
		
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				GaugeDemo window = null;
				try
				{
					window = new GaugeDemo();
					window.setVisible(true);
				}
				catch (Exception exp)
				{
				}
			}
		});
	}

	protected JPanel content;

	final com.mindfusion.charting.components.LengthType Relative =
		com.mindfusion.charting.components.LengthType.Relative;
}
