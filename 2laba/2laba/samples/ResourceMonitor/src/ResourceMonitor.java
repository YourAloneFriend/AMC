import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.beans.*;
import java.lang.management.*;
import java.lang.reflect.*;

import javax.swing.*;
import javax.swing.Timer;

import java.util.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.components.LayoutAlignment;
import com.mindfusion.charting.swing.*;
import com.mindfusion.charting.components.gauges.*;
import com.mindfusion.charting.components.gauges.Text;
import com.mindfusion.drawing.*;


public class ResourceMonitor extends JFrame
{
	final static private int roundness = 25;
	private JPanel controls = null;
	private static Timer timer = null; 
			
	//CPU Gauge components
	
	private OvalScale cpuScale = null;
	private Pointer cpuPointer = null;
	private MajorTickSettings cpuMajorTickSettings = null;
	private MiddleTickSettings cpuMiddleSettings = null;
	private OvalGauge cpuGauge = null;
	private Text cpuText = null;
	private JLabel cpuLabel = null;

	//RAM Gauge components
	
	private OvalScale ramScale = null;
	private Pointer ramPointer = null;
	private MajorTickSettings ramMajorTickSettings = null;
	private MiddleTickSettings ramMiddleSettings = null;
	private OvalGauge ramGauge = null;
	private JLabel ramLabel = null;
	private JLabel ramUsed = null;
	private JLabel ramFree = null;
	private JLabel ramUsedMB = null;

	private void inflate(Rectangle2D rect, double dx, double dy)
	{
		rect.setFrame(rect.getX() - dx, rect.getY() - dy, rect.getWidth() + 2 * dx, rect.getHeight() + 2 * dy);
	}

	private void paintCommonForegroundElements(PrepaintEvent e)
	{
		//Layer 1
		
		RoundRectangle rect = new RoundRectangle();
		rect.setMargin(
			new Margins(-0.09f, 0.22f, -0.09f, 0.1f));

		rect.setFill(e.createLinearGradient(45.0f, 
				new float[] { 0, 0.2f, 0.8f, 1.0f }, 
				new Color[] {
						new Color(0xFF, 0xFF, 0xFF, 0x64),
						new Color(0xFF, 0xFF, 0xFF, 0x32),
						new Color(0xFF, 0xFF, 0xFF, 0x10),
						new Color(0xFF, 0xFF, 0xFF, 0x0)
				}));
		rect.setRoundness(roundness);
		
		Dimension renderSize = new Dimension();
		renderSize.setSize(
			e.getElement().getActualWidth(), e.getElement().getActualHeight());
		
		e.paintVisualElement(rect, renderSize);
		
		Rectangle2D.Float bounds = new Rectangle2D.Float(0, 0, 1, 1);
		inflate(bounds, 0, -0.2d);
		
	}
	
	private void paintCommonBackgroundElements(PrepaintEvent e){
		
		//Layer 1
		
		RoundRectangle rect = new RoundRectangle();
		rect.setMargin(
			new Margins(-0.11f, 0.2f, -0.11f, 0.08f));

		rect.setFill(e.createLinearGradient(45.0d, 
				new float[] { 0.0f, 0.2f, 0.8f, 1.0f }, 
				new Color[] {
						new Color(0x32, 0x32, 0x32, 0xFF),
						new Color(0x32, 0x32, 0x32, 0xFF),
						new Color(0x64, 0x64, 0x64, 0xFF),
						new Color(0x64, 0x64, 0x64, 0xFF)
				}));
		rect.setRoundness(roundness);
		
		Dimension renderSize = new Dimension();
		renderSize.setSize(e.getElement().getActualWidth(), e.getElement().getActualHeight());
		
		e.paintVisualElement(rect, renderSize);
		
		//Layer 2
		
		rect.setMargin(
			new Margins(-0.1f, 0.21f, -0.1f, 0.09f));

		rect.setFill(e.createLinearGradient(45.0d, 
				new float[] { 0.0f, 0.2f, 0.8f, 1.0f }, 
				new Color[] {
						new Color(0x64, 0x64, 0x64, 0xFF),
						new Color(0x64, 0x64, 0x64, 0xFF),
						new Color(0x32, 0x32, 0x32, 0xFF),
						new Color(0x32, 0x32, 0x32, 0xFF)
				}));
		rect.setRoundness(roundness);
		e.paintVisualElement(rect, renderSize);
		
		//Layer 3
		
		rect.setMargin(
			new Margins(-0.09f, 0.22f, -0.09f, 0.1f));

		rect.setFill(e.createLinearGradient(45.0d, 
				new float[] { 0.0f, 0.2f, 0.8f, 1.0f }, 
				new Color[] {
						new Color(0x32, 0x32, 0x32, 0xFF),
						new Color(0x32, 0x32, 0x32, 0xFF),
						new Color(0x64, 0x64, 0x64, 0xFF),
						new Color(0x64, 0x64, 0x64, 0xFF)
				}));
		rect.setRoundness(roundness);
		e.paintVisualElement(rect, renderSize);
	}
	
	//Initialize CPU Label
	
	private void initializeCpuLabel()
	{
			cpuLabel = new JLabel();
			cpuLabel.setBounds(124, 208, 29, 13);
			cpuLabel.repaint();
			cpuLabel.setName("CPULabel");
			cpuLabel.setText("CPU");
	}
	
	//Initialize CPU text
	
	private void initializeCpuText()
	{
		cpuText = new Text();
		cpuText.setFontFamily("Consolas");
		cpuText.setFontSize(new Length(20.0f, LengthType.Relative));
		cpuText.setFontStyle(FontStyle.BOLD);
		cpuText.setForeground(new Color(0x7F, 0xFF, 0x0, 0xFF));
		cpuText.setLineAlignment(LayoutAlignment.Center);
		cpuText.setAlignment(LayoutAlignment.Center);
		cpuText.setContent("0%");
		cpuText.setMargin(new Margins(0, 0.3f, 0, 0));
	}
	
	//Initialize CPU Major Tick Settings
	
	private void initializeCpuMajorTickSettings()
	{
		cpuMajorTickSettings = new MajorTickSettings();
		cpuMajorTickSettings.setFill(new SolidBrush(Colors.White));
		cpuMajorTickSettings.setFontFamily("Consolas");
		cpuMajorTickSettings.setFontSize(new Length(13.0f, LengthType.Relative));
		//cpuMajorTickSettings.setLabelFormat("0.");
		cpuMajorTickSettings.setLabelAlignment(Alignment.InnerOutside);
		cpuMajorTickSettings.setLabelForeground(Colors.Chartreuse);
		cpuMajorTickSettings.setLabelOffset(new Length(-15.0f));
		cpuMajorTickSettings.setLabelRotation(LabelRotation.None);
		cpuMajorTickSettings.setStroke(new Pen(Colors.White, 1.0f));
		cpuMajorTickSettings.setTickAlignment(Alignment.OuterInside);
		cpuMajorTickSettings.setTickHeight(new Length(1.0f, LengthType.Relative));
		cpuMajorTickSettings.setTickWidth(new Length(10.0f, LengthType.Relative));
		cpuMajorTickSettings.setTickShape(TickShape.Rectangle);
	}
	
	//Initialize CPU Middle Tick Settings
	
	private void initializeCpuMiddleTickSettings()
	{
		cpuMiddleSettings = new MiddleTickSettings();
		cpuMiddleSettings.setShowLabels(false);
		cpuMiddleSettings.setShowTicks(false);
	}
	
	//Initialize CPU Pointer
	
	private void initializeCpuPointer()
	{
		cpuPointer = new Pointer();
		cpuPointer.setMargin(new Margins(0F, 0F, 0.3699999F, 0.8425F));
		cpuPointer.setName("CpuPointer");
		cpuPointer.setPointerHeight(new Length(26.25f, LengthType.Relative));
		cpuPointer.setPointerWidth(new Length(105f, LengthType.Relative));
		cpuPointer.setStroke(new Pen(Colors.White, 1.0f));
		cpuPointer.addPointerListener(new PointerAdapter()
		{
			@Override
			public void valueChanged(PropertyChangeEvent e)
			{
				String procent = new String("%");
				cpuText.setContent(
					String.format("%d%s", (int)e.getNewValue(), procent));
			}
		});
	}
	
	//Initialize the CPU Scale
	
	private void initializeCpuScale()
	{
		cpuScale = new OvalScale();
		cpuScale.setStartAngle(210.0d);
		cpuScale.setEndAngle(330.0d);
		cpuScale.setFill(new SolidBrush(Colors.Black));
		cpuScale.setMargin(new Margins(0.1F, 0.1F, 0.1F, 0.1F));
		cpuScale.setName("CpuScale");
		Point2D.Float center = new Point2D.Float(0.5f, 0.9f);
		cpuScale.setScaleRelativeCenter(center);
		cpuScale.setScaleRelativeRadius(0.6d);
		cpuScale.setStroke(new Pen(Colors.White, 1.0f));
		initializeCpuMajorTickSettings();
		cpuScale.setMajorTickSettings(cpuMajorTickSettings);
		initializeCpuMiddleTickSettings();
		cpuScale.setMiddleTickSettings(cpuMiddleSettings);
		initializeCpuPointer();
		cpuScale.getPointers().clear();
		cpuScale.getPointers().add(cpuPointer);
		initializeCpuText();
		cpuScale.getScaleChildren().add(cpuText);
		cpuScale.addBaseScaleListener(new BaseScaleAdapter()
		{
			@Override
			public void queryLabelValue(QueryLabelValueEvent e)
			{
				e.setNewValue(String.format("%d%%%", (int)e.getCalculatedLabelValue()));
			}
		});
	}
	
	//Initialize CPU Gauge
	
	private void initializeCpuGauge()
	{
		cpuGauge = new OvalGauge();
		cpuGauge.setStyle(OvalGaugeStyle.SemicircleN);
		cpuGauge.setMargin(new Margins(10.0d));
		cpuGauge.setName("cpuGauge");
		initializeCpuScale();
		cpuGauge.getScales().clear();
		cpuGauge.getScales().add(cpuScale);
		cpuGauge.setWidth(250.0d);
		cpuGauge.setHeight(200.0d);
		cpuGauge.addBaseGaugeListener(new BaseGaugeAdapter()
		{
			@Override
			public void prepaintForeground(PrepaintEvent e)
			{
				e.setCancelDefaultPainting(true);
				paintCommonForegroundElements(e);
			}
			
			@Override
			public void prepaintBackground(PrepaintEvent e)
			{
				e.setCancelDefaultPainting(true);
				paintCommonBackgroundElements(e);
				
				//CPU usage label background
				
				RoundRectangle textRect = new RoundRectangle();
				textRect.setFill(e.createLinearGradient(45, 
						new float[] { 0.0f, 0.33f, 0.66f, 1.0f }, 
						new Color[] {
								new Color(0xC0, 0xC0, 0xC0, 0x80),
								new Color(0x64, 0x64, 0x64, 0x9A),
								new Color(0x0, 0x0, 0x0, 0xBE),
								new Color(0x0, 0x0, 0x0, 0xFF)
					}));

				GradientBrush strokeBrush = new GradientBrush(
					new Color(0xA7, 0xA7, 0xA7, 0xFF), new Color(0xFE, 0xFE, 0xFE, 0xFF), 130);
				textRect.setStroke(new Pen(strokeBrush, 0.01f));
				textRect.setMargin(new Margins(0.37f, 0.55f, 0.37f, 0.32f));
				
				Dimension renderSize = new Dimension();
				renderSize.setSize(e.getElement().getActualWidth(), e.getElement().getActualHeight());
				
				e.paintVisualElement(textRect, renderSize);
			}
		});
	}
	

	//Initialize RAM Labels
	
	private void initializeRamLabels()
	{
		// Initialize RAM Label
		ramLabel = new JLabel();
		ramLabel.setName("RAM Label");
		ramLabel.setBackground(Colors.Transparent);
		//ramLabel.setForeground(Colors.Chartreuse);
		ramLabel.setBounds(373, 208, 61, 13);
		ramLabel.repaint();
		ramLabel.setText("Memory");
		
		// Initialize RAM Used MB Label
		ramUsedMB = new JLabel();
		ramUsedMB.setBackground(Colors.Transparent);
		ramUsedMB.setFont(new Font("Consolas", 0, 11));
		ramUsedMB.setForeground(Colors.Chartreuse);
		ramUsedMB.setBounds(286, 168, 81, 13);
		ramUsedMB.repaint();
		ramUsedMB.setText("Used: ");
		
		// Initialize RAM Used Label
		ramUsed = new JLabel();
		ramUsed.setBackground(Colors.Transparent);
		ramUsed.setFont(new Font("Consolas", 0, 11));
		ramUsed.setForeground(Colors.Chartreuse);
		ramUsed.setBounds(430, 155, 91, 13);
		ramUsed.repaint();
		ramUsed.setText("RAM Used: ");
		
		// Initialize RAM Free Label
		ramFree = new JLabel();
		ramFree.setBackground(Colors.Transparent);
		ramFree.setFont(new Font("Consolas", 0, 11));
		ramFree.setForeground(Colors.Chartreuse);
		ramFree.setBounds(286, 147, 81, 13);
		ramFree.repaint();
		ramFree.setText("Free: ");
	}
	
	//Initialize RAM Major Tick Settings
	
	private void initializeRamMajorTickSettings()
	{
		ramMajorTickSettings = new MajorTickSettings();
		ramMajorTickSettings.setCount(5);
		ramMajorTickSettings.setFill(new SolidBrush(Colors.White));
		ramMajorTickSettings.setFontFamily("Consolas");
		ramMajorTickSettings.setFontSize(new Length(13.0f, LengthType.Relative));
		ramMajorTickSettings.setLabelAlignment(Alignment.InnerOutside);
		ramMajorTickSettings.setLabelForeground(Colors.Chartreuse);
		ramMajorTickSettings.setLabelOffset(new Length(-10.0f));
		ramMajorTickSettings.setLabelRotation(LabelRotation.None);
		ramMajorTickSettings.setStroke(new Pen(Colors.White, 1.0f));
		ramMajorTickSettings.setTickAlignment(Alignment.OuterInside);
		ramMajorTickSettings.setTickHeight(new Length(1.0f, LengthType.Relative));
		ramMajorTickSettings.setTickShape(TickShape.Rectangle);
		ramMajorTickSettings.setTickWidth(new Length(10.0f, LengthType.Relative));
	}
	
	//Initialize RAM Middle Tick Settings
	
	private void initializeRamMiddleTickSettings()
	{
		ramMiddleSettings = new MiddleTickSettings();
		ramMiddleSettings.setShowLabels(false);
		ramMiddleSettings.setShowTicks(false);
		ramMiddleSettings.setStroke(new Pen(Colors.Chartreuse, 1.0f));
	}

	//Initialize RAM Pointer
	
	private void initializeRamPointer()
	{
		ramPointer= new Pointer();
		ramPointer.setIsInteractive(false);
		ramPointer.setMargin(new Margins(0F, 0F, 0.3699999F, 0.8425F));
		ramPointer.setName("RAM Pointer");
		ramPointer.setPointerHeight(new Length(26.25f, LengthType.Relative));
		ramPointer.setPointerWidth(new Length(105f, LengthType.Relative));
		ramPointer.setStroke(new Pen(Colors.White, 1.0f));
		//ramPointer.setValue(value);
	}
	
	//Initialize RAM Scale
	
	private void initializeRamScale()
	{
		ramScale = new OvalScale();
		ramScale.setEndAngle(330.0d);
		ramScale.setFill(new SolidBrush(Colors.Black));
		initializeRamMajorTickSettings();
		ramScale.setMajorTickSettings(ramMajorTickSettings);
		ramScale.setMargin(new Margins(0.1F, 0.1F, 0.1F, 0.1F));
		initializeRamMiddleTickSettings();
		ramScale.setMiddleTickSettings(ramMiddleSettings);
		ramScale.setName("RAM Scale");
		initializeRamPointer();
		ramScale.getPointers().add(ramPointer);
		ramScale.setScaleRelativeCenter(new Point2D.Float(0.5f, 0.9f));
		ramScale.setScaleRelativeRadius(0.6d);
		ramScale.setStartAngle(210.0d);
		ramScale.setStroke(new Pen(Colors.White, 1.0f));
		ramScale.addBaseScaleListener(new BaseScaleAdapter()
		{
			@Override
			public void queryLabelValue(QueryLabelValueEvent e)
			{
				e.setNewValue(String.format("%dMB", (int)e.getCalculatedLabelValue()));
			}
		});
		ramScale.setMinValue(0);
		
		com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean)
		java.lang.management.ManagementFactory.getOperatingSystemMXBean();
		Long value = os.getTotalPhysicalMemorySize();
		
	    Double value2 = value.doubleValue()/1024/1024;
		ramScale.setMaxValue(value2);
	}
	
	//Initialize RAM Gauge
	
	private void initializeRamGauge()
	{
		ramGauge = new OvalGauge();
		ramGauge.setStyle(OvalGaugeStyle.SemicircleN);
		ramGauge.setName("RAM Gauge");
		ramGauge.setMargin(new Margins(10.0d));
		initializeRamScale();
		ramGauge.getScales().clear();
		ramGauge.getScales().add(ramScale);
		ramGauge.setWidth(250.0d);
		ramGauge.setHeight(200.0d);
		ramGauge.addBaseGaugeListener(new BaseGaugeAdapter()
		{
			@Override
			public void prepaintForeground(PrepaintEvent e)
			{
				e.setCancelDefaultPainting(true);
				paintCommonForegroundElements(e);
			}
			
			@Override
			public void prepaintBackground(PrepaintEvent e)
			{
				e.setCancelDefaultPainting(true);
				paintCommonBackgroundElements(e);
			}
		});
	}
	
	//Initialize JSplitPane
	
	private void initializeWhole()
	{
		controls = new JPanel();
		controls.setBackground(Color.white);
		controls.setLayout(null);
		controls.setSize(521, 221);
	
		
		//CPU Gauge
		
		initializeCpuGauge();
		initializeCpuLabel();
		
		Dashboard cpuBoard = new Dashboard();
		cpuBoard.getTheme().setGaugePointerStroke(null);
		cpuBoard.getTheme().setGaugePointerBackground(
			new GradientBrush(
				new float[] { 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f }, 
				new Color[] {
						new Color(0x00, 0x00, 0x00, 0x80),
						new Color(0x00, 0x00, 0x00, 0x80),
						new Color(0x64, 0x64, 0x64, 0xFF),
						new Color(0x64, 0x64, 0x64, 0xFF),
						new Color(0x32, 0x32, 0x32, 0xFF),
						new Color(0x32, 0x32, 0x32, 0xFF)
				}, 
				90));
		cpuBoard.setEnabled(true);
		cpuBoard.setBounds(0, 0, 260, 200);
		cpuBoard.getRootPanel().getChildren().add(cpuGauge);
		cpuBoard.repaint();
		
		controls.add(cpuBoard);
		controls.add(cpuLabel);
		controls.repaint();

		//RAM Gauge
		
		initializeRamGauge();
		initializeRamLabels();
		
		Dashboard ramBoard = new Dashboard();
		ramBoard.getTheme().setGaugePointerStroke(null);
		ramBoard.getTheme().setGaugePointerBackground(
			new GradientBrush(
				new float[] { 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f }, 
				new Color[] {
						new Color(0x00, 0x00, 0x00, 0x80),
						new Color(0x00, 0x00, 0x00, 0x80),
						new Color(0x64, 0x64, 0x64, 0xFF),
						new Color(0x64, 0x64, 0x64, 0xFF),
						new Color(0x32, 0x32, 0x32, 0xFF),
						new Color(0x32, 0x32, 0x32, 0xFF)
				}, 
				90));
		ramBoard.setEnabled(true);
		ramBoard.setBounds(261, 0, 260, 200);
		ramBoard.getRootPanel().getChildren().add(ramGauge);
		ramBoard.repaint();
		
		controls.add(ramLabel);
		controls.add(ramFree);
		controls.add(ramUsed);
		controls.add(ramUsedMB);
		controls.add(ramBoard);
		controls.repaint();
		
	}
	
	public void run()
	{
		initializeWhole();
		this.getContentPane().add(controls);
		timer = new Timer(500, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Long total;
				Long free;
				Double cpuLoad;
				com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean)
				java.lang.management.ManagementFactory.getOperatingSystemMXBean();
				
				total = os.getTotalPhysicalMemorySize();
				free = os.getFreePhysicalMemorySize();
				cpuLoad = os.getSystemCpuLoad();
			    
			    Double total2 = total.doubleValue()/1024/1024;
			    
			    Double free2 = free.doubleValue()/1024/1024;
			    
			    Double used = total2 - free2;
			    
				cpuPointer.setValue(cpuLoad * 100);
				ramPointer.setValue(free2);
				
				Double percent =  (used/total2)*100;
				
				int intPercent = percent.intValue();
				int intFree = free2.intValue();
				int intUsed = used.intValue();
				
				ramUsed.setText(String.format("RAM used: %d%%", intPercent));
				ramFree.setText(String.format("Free: %dMB", intFree));
				ramUsedMB.setText(String.format("Used: %dMB", intUsed));
				cpuText.setContent(String.format("%d%%",(int)cpuPointer.getValue()));
			}
		});
	}

	public ResourceMonitor()
	{
		setTitle("MindFusion.Charting sample: Resource Monitor");
		setSize(575, 280);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args)
	{
		ResourceMonitor resources = new ResourceMonitor();
		resources.run();
		resources.setVisible(true);
		
		timer.setRepeats(true);
		timer.start();	
	}
}