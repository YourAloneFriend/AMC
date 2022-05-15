import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.EnumSet;

import javax.swing.*;
import javax.swing.event.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.animation.Animation;
import com.mindfusion.charting.animation.AnimationTimeline;
import com.mindfusion.charting.animation.AnimationType;
import com.mindfusion.charting.components.*;
import com.mindfusion.charting.swing.*;
import com.mindfusion.common.ObservableList;
import com.mindfusion.drawing.*;


public class BubbleChartDemo extends JFrame
{	
	BubbleChart bubbleChart;
	
	void initChart()
	{
		bubbleChart = new BubbleChart();
		bubbleChart.setBounds(0,75,1000,575);
		bubbleChart.setShowLegend(true);
		bubbleChart.setLegendTitle("Legend");

		bubbleChart.setSeries(
			createSeries());
		
		// axis titles and ranges
		Axis xAxis = bubbleChart.getXAxis();
		xAxis.setTitle("Average relative annual growth (%)");
		xAxis.setMinValue(-1.0);
		xAxis.setMaxValue(1.0);
		Axis yAxis = bubbleChart.getYAxis();
		yAxis.setTitle("July 1, 2015 projection");
		yAxis.setMinValue(0.0);
		yAxis.setMaxValue(100.0);
		
		// background appearance
		bubbleChart.setShowZoomWidgets(true);
		bubbleChart.setGridType(GridType.Vertical);
		bubbleChart.setBackground(new Color(45, 57, 86));
		bubbleChart.setLegendTitle("");
		//bubbleChart.setGridType(GridType.None);
		Theme theme = bubbleChart.getTheme();
		theme.setGridColor1(new Color(45, 57, 86, 102));
		theme.setGridColor2(new Color(97, 106, 127));
		theme.setLegendBackground(new SolidBrush(new Color (45, 57, 86)));
		
		// series colors
		theme.setCommonSeriesFills(Arrays.asList
		(
			new GradientBrush(Colors.Transparent, new Color (206, 0, 0), 0),
			new GradientBrush(Colors.Transparent, new Color(90, 121, 165), 0)
		));
		theme.setCommonSeriesStrokes(Arrays.asList
		(
			new SolidBrush(new Color(192, 192, 192))
		));

		Brush light = new SolidBrush(new Color (224, 233, 233));
		theme.setUniformSeriesStroke(light);
		theme.setHighlightStroke(light);
		theme.setDataLabelsBrush(light);
		theme.setLegendTitleBrush(light);
		theme.setLegendBorderStroke(light);
		theme.setAxisLabelsBrush(light);
		theme.setAxisTitleBrush(light);
		theme.setAxisStroke(light);
		theme.setAxisLabelsFontSize(12);
		theme.setAxisTitleFontSize(14);
		theme.setDataLabelsFontSize(12);
		theme.setLegendTitleFontSize(14);
		
		theme.setHighlightStrokeDashStyle(DashStyle.Dot);

		// animate the bubbles
		Animation animation = new Animation();
		AnimationTimeline timeline = new AnimationTimeline();
		timeline.addAnimation(
			AnimationType.PerSeriesAnimation,
			3f, (Renderer2D)bubbleChart.getSeriesRenderer());
		animation.addTimeline(timeline);
		animation.runAnimation();
	}

	ObservableList<Series> createSeries()
	{
		PointSeries3D series1 = new PointSeries3D(
			Arrays.asList
			(
				new Point3D(0.32f, 81, 9.5f * 10),
				new Point3D(0.39f, 66, 7.8f * 10),
				new Point3D(0.75f, 65, 7.6f * 10),
				new Point3D(0.49f, 60, 7.1f * 10)
			),
			Arrays.asList("Germany", "France", "UK", "Italy" ));
		series1.setTitle(">50 000 000");
		
		PointSeries3D series2 = new PointSeries3D(
			Arrays.asList(
				new Point3D(-0.28f, 46, 5.4f * 10),
				new Point3D(-0.32f, 42, 5 * 10),
				new Point3D(0.05f, 38, 4.5f * 10),
				new Point3D(-0.4f, 19, 2.3f * 10)),
			Arrays.asList(
				"Spain", "Ukraine", "Poland", "Romania"));
		series2.setTitle("<50 000 000");
		
		return new ObservableList<Series>(
			Arrays.asList(series1, series2));
	}
	
	void initFrame()
	{
		setTitle("MindFusion.Charting sample: Bubble Chart");
		setSize(800, 600);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initChart();
		
		JTabbedPane tabbedPane = constructTabbedPane();
		
		JPanel controls = new JPanel();	
		controls.setLayout(null);
		controls.setBounds(getBounds());
		controls.add(tabbedPane);
		controls.setPreferredSize(new Dimension(800, 90));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(controls, BorderLayout.NORTH);
		getContentPane().add(bubbleChart, BorderLayout.CENTER);
		setVisible(true);
	}

	static public void main(String[] args)
	{
		BubbleChartDemo frame = new BubbleChartDemo();
		frame.initFrame();
	}

	private int xStart = 0;
	private int yStart = 0;
	private int xLegStart = 0;
	private int yLegStart = 0;

	private JTabbedPane constructTabbedPane()
	{
        JPanel pane1 = new JPanel();
		pane1.setLayout(null);
        pane1.setBounds(0, 0, 1000, 75);

        addComponent(pane1, new ShowScatterCheckbox());
        addComponent(pane1, new LabelCheckbox());
		newRow();
		addComponent(pane1, new ShowXCoordinatesCheckbox());
		addComponent(pane1, new ShowYCoordinatesCheckbox());
		newRow();
		addComponent(pane1, new ShowXTicksCheckbox());
		addComponent(pane1, new ShowYTicksCheckbox());

        JPanel pane2 = new JPanel();
		pane2.setLayout(null);
        pane2.setBounds(0, 0, 1000, 75);
        firstRow();
        addComponent(pane2, new JLabel("Grid Type", JLabel.LEFT));
        addShortComponent(pane2, new GridComboBox());
        newRow();
        addComponent(pane2,new PinGridCheckbox());

        JPanel pane3 = new JPanel();
		pane3.setLayout(null);
        pane3.setBounds(0, 0, 1000, 75);
        firstRow();
        addComponent(pane3, new AllowPanCheckbox());
        addComponent(pane3, new AllowMoveLegendCheckbox());
        
        JPanel pane4 = new JPanel();
		pane4.setLayout(null);
        pane4.setBounds(0, 0, 1000, 75);
        firstRow();
        addComponent(pane4,
        	new JLabel("HorizontalAlignment", JLabel.LEFT));
        addShortComponent(pane4,
        	new AlignmentComboBox(true));
        newRow();
        addComponent(pane4,
        	new JLabel("VerticalAlignment", JLabel.LEFT));
        addShortComponent(pane4,
        	new AlignmentComboBox(false));
        newRow();
        addComponent(pane4,
        	new JLabel("LegendMargin(Left)", JLabel.LEFT));
		addShortComponent(pane4,
			new LegendMarginSlider(true));
        newRow();
        addComponent(pane4,
        	new JLabel("LegendMargin(Top)", JLabel.LEFT));
		addShortComponent(pane4,
			new LegendMarginSlider(false));
		newRow();
        addComponent(pane4,
        	new ShowLegendCheckbox());

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(0, 0, 1000, 75);
		tabbedPane.addTab("Appearance", null, pane1);
		tabbedPane.addTab("Grid", null, pane2);
		tabbedPane.addTab("Behavior", null, pane3);
		tabbedPane.addTab("Legend", null, pane4);
		return tabbedPane;
	}
	
	private class GridComboBox extends JComboBox implements ActionListener
	{
		public GridComboBox()
		{
			super(new String[] { "None", "Vertical", "Horisontal", "Crossed" });
			this.setSelectedIndex(0);
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int index = getSelectedIndex();
			
			switch (index)
			{
			case 0:
				bubbleChart.setGridType(GridType.None);
				break;
			case 1:
				bubbleChart.setGridType(GridType.Vertical);
				break;
			case 2:
				bubbleChart.setGridType(GridType.Horizontal);
				break;
			case 3:
				bubbleChart.setGridType(GridType.Crossed);
				break;
			}
			//bubbleChart.setGridColor1(Colors.Black);		
			//bubbleChart.setGridColor2(Colors.Black);
			//bubbleChart.setGridLineColor(Colors.White);
		}
	}
	
	private class AlignmentComboBox
		extends JComboBox implements ActionListener
	{
		private boolean xAxis;
		public AlignmentComboBox(boolean xAxis)
		{
			super(new String[] { "Near", "Center", "Far", "Stretch" });
			this.xAxis = xAxis;
			this.setSelectedIndex(0);
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int index = getSelectedIndex();
			
			if (!xAxis)
			{
				switch(index)
				{
				case 0:
					bubbleChart.setLegendVerticalAlignment(LayoutAlignment.Near);
					break;
				case 1:
					bubbleChart.setLegendVerticalAlignment(LayoutAlignment.Center);
					break;
				case 2:
					bubbleChart.setLegendVerticalAlignment(LayoutAlignment.Far);
					break;
				case 3:
					bubbleChart.setLegendVerticalAlignment(LayoutAlignment.Stretch);
					break;
				}
			}
			else
			{
				switch (index)
				{
				case 0:
					bubbleChart.setLegendHorizontalAlignment(LayoutAlignment.Near);
					break;
				case 1:
					bubbleChart.setLegendHorizontalAlignment(LayoutAlignment.Center);
					break;
				case 2:
					bubbleChart.setLegendHorizontalAlignment(LayoutAlignment.Far);
					break;
				case 3:
					bubbleChart.setLegendHorizontalAlignment(LayoutAlignment.Stretch);
					break;
				}
			}
		}
	}

	private class PinGridCheckbox
		extends JCheckBox implements ActionListener
	{
		public PinGridCheckbox()
		{
			super("PinGrid",false);
			addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			bubbleChart.setPinGrid(!bubbleChart.getPinGrid());
		}
	}

	private class ShowScatterCheckbox
		extends JCheckBox implements ActionListener
	{
		public ShowScatterCheckbox()
		{
			super("ShowScatter",false);
			addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			/*if (bubbleChart.getScatter Shape() == ScatterType.None){
				pieR1.setShape(ScatterType.Circle);
			}
			else{
				pieR1.setShape(ScatterType.None);
			}
			plot.invalidate();*/
		}
	}
	
	private class AllowPanCheckbox
		extends JCheckBox implements ActionListener
	{
		public AllowPanCheckbox()
		{
			super("AllowPan",true);
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			bubbleChart.setAllowPan(
				!bubbleChart.getAllowPan());
		}
	}

	private class AllowMoveLegendCheckbox
		extends JCheckBox implements ActionListener
	{
		public AllowMoveLegendCheckbox()
		{
			super("AllowMoveLegend",true);
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			bubbleChart.setAllowMoveLegend(
				!bubbleChart.getAllowMoveLegend());
		}
	}
	
	private class ShowXTicksCheckbox
		extends JCheckBox implements ActionListener
	{
		public ShowXTicksCheckbox()
		{
			super("Show X Ticks",true);
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			bubbleChart.setShowXTicks(isSelected());
		}
	}	
	
	private class ShowYTicksCheckbox
		extends JCheckBox implements ActionListener
	{
		public ShowYTicksCheckbox()
		{
			super("Show Y Ticks",true);
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			bubbleChart.setShowYTicks(isSelected());
		}
	}
	
	private class ShowXCoordinatesCheckbox
		extends JCheckBox implements ActionListener
	{
		public ShowXCoordinatesCheckbox()
		{
			super("Show X Coordinates",true);
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			bubbleChart.setShowXCoordinates(isSelected());
		}
	}
	
	private class ShowYCoordinatesCheckbox
		extends JCheckBox implements ActionListener
	{
		public ShowYCoordinatesCheckbox()
		{
			super("Show Y Coordinates",true);
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			bubbleChart.setShowYCoordinates(isSelected());
		}
	}
	
	private class LabelCheckbox
		extends JCheckBox implements ActionListener
	{
		private EnumSet<LabelKinds> unchecked;
		private EnumSet<LabelKinds> checked;
		
		public LabelCheckbox()
		{
			super("Show Data Labels", true);
			addActionListener(this);
			checked = EnumSet.allOf(LabelKinds.class);
			unchecked = EnumSet.of(
				LabelKinds.ToolTip,
				LabelKinds.XAxisLabel,
				LabelKinds.YAxisLabel);
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (isSelected())
			{
				bubbleChart.setShowDataLabels(checked);
			}
			else
			{
				bubbleChart.setShowDataLabels(unchecked);
			}
			bubbleChart.repaint();
		}
	}

	private class ShowLegendCheckbox
		extends JCheckBox implements ActionListener
	{
		public ShowLegendCheckbox()
		{
			super("ShowLegend",true);
			addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (bubbleChart.getShowLegend())
			{
				bubbleChart.setShowLegend(false);
			}
			else
			{
				bubbleChart.setShowLegend(true);
			}
		}
	}
	
	private class LegendMarginSlider
		extends JSlider implements ChangeListener
	{
		private boolean xAxis;
		public LegendMarginSlider(boolean xAxis)
		{
	        this.setAlignmentX(CENTER_ALIGNMENT);
	        this.setMinimum(0);
			this.xAxis=xAxis;  
			if(xAxis)
			{
				this.setMaximum(1100);
			}
			else
			{
				this.setMaximum(625);
			}
	        this.setValue(0);
			addChangeListener(this);
		}
		
		@Override
		public void stateChanged(ChangeEvent arg0)
		{
			if (xAxis)
			{
				xLegStart = getValue();
			}
			else
			{
				yLegStart = getValue();
			}
			bubbleChart.setLegendMargin(
				new Margins(xLegStart, yLegStart, 0, 0));
		}
	}

	private JPanel addComponent(JPanel panel, Container element)
	{
		if (element != null)
		{
			element.setBounds(xStart, yStart, 200, 20);
			panel.add(element);
		}
		yStart += 25;
		return panel;
	}

	private JPanel addShortComponent(JPanel panel, Container element)
	{
		if (element!=null)
		{
			element.setBounds(xStart, yStart, 150, 20);
			panel.add(element);
		}
		yStart += 25;
		return panel;
	}

	private void firstRow()
	{
		xStart=0;
		yStart=0;
	}
	
	private void newRow()
	{
		xStart+=200;
		yStart=0;
	}
}
