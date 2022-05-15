import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.components.*;
import com.mindfusion.charting.swing.*;
import com.mindfusion.drawing.*;


public class FunnelChartDemo extends JFrame
{
	FunnelChart chart;

	void initChart()
	{
		chart = new FunnelChart()
		{{
			setBounds(200,0,800,700);
	
			setSeries(createSeries());
			
			getTheme().setLegendBackground(new SolidBrush(new Color(224, 233, 233)));
			getTheme().setLegendBorderStroke(new SolidBrush(new Color(192, 192, 192)));
			setLegendTitle("");
			setLegendElementLabelKind(LabelKinds.OuterLabel);
			getTheme().setLegendTitleFontSize(14.0);
			getTheme().setDataLabelsFontSize(12);
			
			getTheme().setSeriesFills(fill());
			getTheme().setSeriesStrokes(stroke());
			getTheme().setSeriesStrokeThicknesses(strokeThickness());
		}};
		
		JTabbedPane tabbedPane = constructTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(220, 800));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tabbedPane, BorderLayout.WEST);
		getContentPane().add(chart, BorderLayout.CENTER);
		
		setVisible(true);
	}

	Series createSeries()
	{
		List<Double> values = Arrays.asList(70.0, 60.0, 50.0, 30.0, 20.0, 15.0, 10.0, 4.0);

		List<String> funnelLabels = Arrays.asList(
			"Unqualified prospects",
			"Leads",
			"Initial communication",
			"Customer evaluation",
			"Negotiation",
			"Purchase order received",
			"Delivery",
			"Payment");
		
		List<String> legendLabels = Arrays.asList(
			"Unqualified prospects=70K, 100%",
			"Leads=60K, 85.7%",
			"Initial communication=50K, 71.4%",
			"Customer evaluation=30K, 42.9%",
			"Negotiation=20K, 28.6%",
			"Purchase order received=15K, 21.4%",
			"Delivery=10K, 14.3%",
			"Payment=4K, 5.7%");

		funnelSeries = new PieSeries(values, funnelLabels, legendLabels);
		return funnelSeries;
	}

	void initFrame()
	{
		setTitle("MindFusion.Charting sample: Funnel Chart");
		setSize(800, 600);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initChart();
	}

	List<List<Brush>> fill()
	{
		List<Brush> fills = Arrays.asList(
			new SolidBrush(new Color(224, 233, 233)),
			new SolidBrush(new Color(192, 192, 192)),
			new SolidBrush(new Color(156, 170, 198)),
			new SolidBrush(new Color(102, 154, 204)),
			new SolidBrush(new Color(90, 121, 165)),
			new SolidBrush(new Color(97, 106, 127)),
			new SolidBrush(new Color(45, 57, 86)),
			new SolidBrush(new Color(206, 0, 0)));

		List<List<Brush>> perSeries = new ArrayList<List<Brush>>();
		perSeries.add(fills);
		return perSeries;
	}

	List<List<Brush>> stroke()
	{
		List<Brush> strokes = Arrays.asList(
			new SolidBrush(new Color(255, 255, 255)));

		List<List<Brush>> perSeries = new ArrayList<List<Brush>>();
		perSeries.add(strokes);
		return perSeries;
	}
	
	List<List<Double>> strokeThickness()
	{
		List<Double> thicknesses = Arrays.asList(
			10.0);

		List<List<Double>> perSeries = new ArrayList<List<Double>>();
		perSeries.add(thicknesses);
		return perSeries;
	}

	
	static public void main(String[] args)
	{
		FunnelChartDemo frame = new FunnelChartDemo();
		frame.initFrame();
	}
	
	JTabbedPane constructTabbedPane()
	{
        JPanel pane1 = new JPanel();
		pane1.setLayout(null);
        pane1.setBounds(0, 0, 200, 700);
        firstRow();
        pane1=addComponent(pane1,new LabelCheckbox());
		newRow();
        pane1=addComponent(pane1,new JLabel("Segment Spacing", JLabel.LEFT));
        pane1=addComponent(pane1,new SegmentSpacingSlider());
		newRow();
        pane1=addComponent(pane1,new JLabel("Stem Width", JLabel.LEFT));
        pane1=addComponent(pane1,new StemWidthSlider());

        JPanel pane2 = new JPanel();
		pane2.setLayout(null);
        pane2.setBounds(0, 0, 200, 700);
        firstRow();
        pane2=addComponent(pane2, new ShowLegendCheckbox());
        pane2=addComponent(pane2, new AllowMoveLegendCheckbox());
		newRow();
        pane2=addComponent(pane2,new JLabel("Horizontal Alignment", JLabel.LEFT));
        pane2=addComponent(pane2, new AlignmentComboBox(true));
        newRow();
        pane2=addComponent(pane2,new JLabel("Vertical Alignment", JLabel.LEFT));
        pane2=addComponent(pane2, new AlignmentComboBox(false));
        newRow();
        pane2=addComponent(pane2,new JLabel("LegendMargin(Left)", JLabel.LEFT));
		pane2=addComponent(pane2, new LegendMarginSlider(true));
        newRow();
        pane2=addComponent(pane2,new JLabel("LegendMargin(Top)", JLabel.LEFT));
		pane2=addComponent(pane2, new LegendMarginSlider(false));

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(0, 0, 200, 700);
		tabbedPane.addTab("Funnel", null, pane1);
		tabbedPane.addTab("Legend", null, pane2);
		return tabbedPane;
	}

	private class LabelCheckbox extends JCheckBox implements ActionListener
	{
		private EnumSet<LabelKinds> unchecked;
		private EnumSet<LabelKinds> checked;
		
		public LabelCheckbox()
		{
			super("Show Data Labels",true);
			addActionListener(this);
			checked=EnumSet.allOf(LabelKinds.class);
			unchecked=EnumSet.of(LabelKinds.ToolTip,LabelKinds.XAxisLabel,LabelKinds.YAxisLabel);
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(isSelected())
			{
				chart.setShowDataLabels(checked);
			}
			else
			{
				chart.setShowDataLabels(unchecked);
			}
			chart.repaint();
		}
	}
	
	private class StemWidthSlider extends JSlider implements ChangeListener
	{
		public StemWidthSlider()
		{
	        this.setMinimum(0);
			this.setMaximum(33);
	        this.setValue(0);
			addChangeListener(this);
		}
		
		@Override
		public void stateChanged(ChangeEvent arg0)
		{
			chart.setStemWidth(this.getValue() / 100f);
		}
	}
	
	private class SegmentSpacingSlider extends JSlider implements ChangeListener
	{
		public SegmentSpacingSlider()
		{
	        this.setMinimum(0);
			this.setMaximum(25);
	        this.setValue(0);
			addChangeListener(this);
		}
		
		@Override
		public void stateChanged(ChangeEvent arg0)
		{
			chart.setSegmentSpacing(this.getValue());
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
						chart.setLegendVerticalAlignment(LayoutAlignment.Near);
						break;
					case 1:
						chart.setLegendVerticalAlignment(LayoutAlignment.Center);
						break;
					case 2:
						chart.setLegendVerticalAlignment(LayoutAlignment.Far);
						break;
					case 3:
						chart.setLegendVerticalAlignment(LayoutAlignment.Stretch);
						break;
				}
			}
			else
			{
				switch(index)
				{
					case 0:
						chart.setLegendHorizontalAlignment(LayoutAlignment.Near);
						break;
					case 1:
						chart.setLegendHorizontalAlignment(LayoutAlignment.Center);
						break;
					case 2:
						chart.setLegendHorizontalAlignment(LayoutAlignment.Far);
						break;
					case 3:
						chart.setLegendHorizontalAlignment(LayoutAlignment.Stretch);
						break;
				}
			}
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
			chart.setAllowMoveLegend(
				!chart.getAllowMoveLegend());
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
			if (chart.getShowLegend())
			{
				chart.setShowLegend(false);
			}
			else
			{
				chart.setShowLegend(true);
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
			this.xAxis = xAxis;  
			if (xAxis)
			{
				this.setMaximum(800);
			}
			else{
				this.setMaximum(700);
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
			chart.setLegendMargin(new Margins(xLegStart,yLegStart,0,0));
		}
	}

	private JPanel addComponent(JPanel panel,Container element)
	{
		if (element != null)
		{
			element.setBounds(xStart, yStart, 200, 20);
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
		xStart=0;
		yStart+=25;
	}
	
	private int xStart=0;
	private int yStart=0;
	private int xFunnelStart=0;
	private int yFunnelStart=0;
	private int xLegStart=0;
	private int yLegStart=0;
	private int funnelWidth=500;
	private int funnelHeight=700;
	
	public Series funnelSeries;
}
