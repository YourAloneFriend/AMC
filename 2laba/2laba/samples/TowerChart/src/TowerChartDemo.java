import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.swing.*;
import com.mindfusion.common.DateTime;
import com.mindfusion.drawing.*;


public class TowerChartDemo extends JFrame
{
	TowerChart towerChart;

	TowerChart initChart()
	{
		towerChart = new TowerChart();

		// if there is no fixed range set, the chart 
		// scales segments to fill all available height
		towerChart.setRange(5.0);

		// sample data for left side
		List<ChartEvent> leftEvents = Arrays.asList(
			createEvent(0, 1, 3, "22'45\"", "Fernandes"),
			createEvent(2, 1, 2, "59'27\"", "Sanchez"));

		EventSeries leftSeries = new EventSeries(
			new ArrayList<>(leftEvents));
		leftSeries.setTitle("Spain");
		towerChart.setLeftSeries(leftSeries);

		// sample data for right side
		List<ChartEvent> rightEvents = Arrays.asList(
			createEvent(1, 1, 2, "38'27\"", "Chiesa"));

		EventSeries rightSeries = new EventSeries(
			new ArrayList<>(rightEvents));
		rightSeries.setTitle("Italy");
		towerChart.setRightSeries(rightSeries);

		// customize appearance
		List<Brush> brushes = Arrays.asList(
			 new SolidBrush(new Color(0x669acc)),
			 new SolidBrush(new Color(0x616a7f)));

		List<Brush> strokes = Arrays.asList(
			new SolidBrush(new Color(0xFFFFFF)));

		towerChart.getPlot().setSeriesStyle(
			new PerSeriesStyle(
				brushes, strokes, null, null));

		towerChart.getTheme().setDataLabelsFontSize(15);
		towerChart.getTheme().setHighlightStroke(
			new SolidBrush(new Color(0xce0000)));

		towerChart.getLegendRenderer().setBackground(
			new SolidBrush(new Color(0xe0e9e9)));
		towerChart.getLegendRenderer().setBorderStroke(
			new SolidBrush(new Color(0xc0c0c0)));
		towerChart.getLegendRenderer().setShowSeriesElements(false);
		towerChart.getLegendRenderer().setTitleFontSize(14.0);

		towerChart.setBackground(Color.white);
		towerChart.setMargins(new Margins(250, 20, 250, 20));

		return towerChart;
	}

	void initFrame()
	{
		setTitle("MindFusion.Charting sample: Tower Chart");
		setSize(900, 800);
		//setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().add(
			initChart(),
			BorderLayout.CENTER);

		getContentPane().add(
			initUI(),
			BorderLayout.SOUTH);

		setVisible(true);
	}

	JPanel initUI()
	{
		JPanel ui = new JPanel();
		FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		ui.setLayout(flow);

		JPanel col1 = new JPanel();
		col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
		ui.add(col1);

		JPanel col2 = new JPanel();
		col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));
		ui.add(col2);

		JPanel col3 = new JPanel();
		col3.setLayout(new BoxLayout(col3, BoxLayout.Y_AXIS));
		ui.add(col3);

		JPanel col4 = new JPanel();
		col4.setLayout(new BoxLayout(col4, BoxLayout.Y_AXIS));
		ui.add(col4);

		JTextArea description = new JTextArea(
			"This sample demonstrates various properties of the TowerChart control. " +
			"Change property values in this panel to see their effect in chart above.");
		description.setPreferredSize(new Dimension(350, 70));
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		ui.add(description);

		// tower layout UI
		JLabel layoutLabel = new JLabel("Layout:");
		col1.add(layoutLabel);

		JComboBox<String> layoutCombo = new JComboBox<String>(
			new String[] { "Interleave", "Stack", "Timeline" });
		layoutCombo.setSelectedIndex(0);
		layoutCombo.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					towerChart.setTowerLayout(
						TowerLayout.valueOf(layoutCombo.getSelectedItem().toString()));
				}
			});
		layoutCombo.setAlignmentX(0);
		col1.add(layoutCombo);

		col1.add(Box.createRigidArea(new Dimension(0, 5)));

		// add new event to left team
		JButton addLeftBtn = new JButton("Add Left");
		addLeftBtn.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					EventSeries series = (EventSeries)towerChart.getLeftSeries();
					series.getValues().add(createRandomEvent());
					towerChart.repaint();

					if (towerChart.getRange().intValue() < counter)
						towerChart.setRange((double)counter);
				}
			});
		col1.add(addLeftBtn);

		// segment shape UI
		JLabel shapeLabel = new JLabel("Shape:");
		col2.add(shapeLabel);

		JComboBox<String> shapeCombo = new JComboBox<String>(
			new String[] { "Triangle", "Rectangle" });
		shapeCombo.setSelectedIndex(0);
		shapeCombo.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					towerChart.setSegmentShape(
						TowerSegmentShape.valueOf(shapeCombo.getSelectedItem().toString()));
				}
			});
		shapeCombo.setAlignmentX(0);
		col2.add(shapeCombo);

		col2.add(Box.createRigidArea(new Dimension(0, 5)));

		// add new event to left team
		JButton addRightBtn = new JButton("Add Right");
		addRightBtn.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					EventSeries series = (EventSeries)towerChart.getRightSeries();
					series.getValues().add(createRandomEvent());
					towerChart.repaint();

					if (towerChart.getRange().intValue() < counter)
						towerChart.setRange((double)counter);
				}
			});
		col2.add(addRightBtn);

		// toggle label visibility
		JCheckBox showDataLabelsCheck = new JCheckBox("Show Data Labels");
		showDataLabelsCheck.setSelected(true);
		showDataLabelsCheck.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (showDataLabelsCheck.isSelected())
						towerChart.setShowDataLabels(EnumSet.allOf(LabelKinds.class));
					else
						towerChart.setShowDataLabels(EnumSet.noneOf(LabelKinds.class));
				}
			});
		col3.add(showDataLabelsCheck);

		// toggle legend visibility
		JCheckBox showLegendCheck = new JCheckBox("Show Legend");
		showLegendCheck.setSelected(towerChart.getShowLegend());
		showLegendCheck.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					towerChart.setShowLegend(showLegendCheck.isSelected());
				}
			});
		col3.add(showLegendCheck);

		// margins UI
		JLabel marginsLabel = new JLabel("Margins:");
		col4.add(marginsLabel);

		JSlider marginsSlider = new JSlider(200, 400, 250);
		marginsSlider.addChangeListener(
			new ChangeListener()
			{
				@Override
				public void stateChanged(ChangeEvent e)
				{
					towerChart.setMargins(new Margins(
						marginsSlider.getValue(), 20, marginsSlider.getValue(), 20));
				}
			});
		col4.add(marginsSlider);

		col4.add(Box.createRigidArea(new Dimension(0, 5)));

		// padding UI
		JLabel paddingLabel = new JLabel("Padding:");
		col4.add(paddingLabel);

		JSlider paddingSlider = new JSlider(0, 40);
		paddingSlider.addChangeListener(
			new ChangeListener()
			{
				@Override
				public void stateChanged(ChangeEvent e)
				{
					towerChart.setSeriesPadding(
						paddingSlider.getValue());
				}
			});
		col4.add(paddingSlider);

		return ui;
	}

	private ChartEvent createRandomEvent()
	{
		ChartEvent event = new ChartEvent(
			counter++, 1, DateTime.now().getTicks() % 2 + 2);
		event.setInnerLabel(
			Integer.toString(DateTime.now().getSecond()));
		event.setOuterLabel(
			"Player " + Long.toString(DateTime.now().getTicks() % 12));
		return event;
	}

	private ChartEvent createEvent(
		double time, double duration, double value,
		String innerLabel, String outerLabel)
	{
		ChartEvent event =
			new ChartEvent(time, duration, value);
		event.setInnerLabel(innerLabel);
		event.setOuterLabel(outerLabel);
		return event;
	}

	static public void main(String[] args)
	{
		TowerChartDemo frame = new TowerChartDemo();
		frame.initFrame();
	}

	SolidBrush firstBrush;
	SolidBrush secondBrush;
	SolidBrush thirdBrush;

	List<String> labels = Arrays.asList(
		"one", "two", "three", "four", "five", "six",
		"seven", "eight", "nine", "ten", "eleven", "twelve"
	);

	private int counter = 3;

	static private final long serialVersionUID = 1L;
}
