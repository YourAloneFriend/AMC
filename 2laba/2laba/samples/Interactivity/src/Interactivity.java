import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.animation.*;
import com.mindfusion.charting.swing.*;
import com.mindfusion.common.ObservableList;
import com.mindfusion.drawing.*;


public class Interactivity
{
	BarChart barChart = null;
	PieChart pieChart = null;
	
	BarChart initializeBarChart()
	{		
		BarChart chart = new BarChart()
		{{
			setTitle("Bar Chart");
			setBackground(Colors.WhiteSmoke);
			setSeries(createBarSeries());
			
			setTheme(new Theme());
			getTheme().setCommonSeriesFills(barFill());
			getTheme().setCommonSeriesStrokes(barStroke());
			getTheme().setCommonSeriesStrokeThicknesses(barStrokeThicknesses());

			getTheme().setLegendBackground(
				new SolidBrush(new Color(220, 220, 220)));
			getTheme().setLegendBorderStroke(
				new SolidBrush(new Color(210, 210, 210)));
			getTheme().setHighlightStroke(
				new SolidBrush(new Color(97, 106, 127)));

			getTheme().setHighlightStrokeThickness(5.0);
			getTheme().setGridColor1(Color.white);
			getTheme().setGridColor2(new Color(235, 235, 235));
			getTheme().setGridLineColor(new Color(210, 210, 210));
			getTheme().setDataLabelsBrush(new SolidBrush(Colors.White));
			getTheme().setDataLabelsFontStyle(EnumSet.of(FontStyle.BOLD));
			getTheme().setAxisLabelsFontSize(12.0);
	
			getSeriesRenderer().setLabelFontSize(14.0d);
			setAllowZoom(true);
			setAllowPan(true);
			setGridType(GridType.Horizontal);
			setTitleMargin(new Margins(15.0));
		}};

		Axis xAxis = chart.getXAxis();
		xAxis.setTitle("");
		xAxis.setMinValue(0.0d);
		xAxis.setMaxValue(3.0d);
		
		Axis yAxis = chart.getYAxis();
		yAxis.setTitle("");
		yAxis.setMinValue(0.0d);
		yAxis.setMaxValue(100.0d);

		// animate the bars
		Animation animation = new Animation();
		AnimationTimeline timeline = new AnimationTimeline();
		timeline.addAnimation(
			AnimationType.PerElementAnimation, 4, (Renderer2D)chart.getSeriesRenderer());
		animation.addTimeline(timeline);
		animation.runAnimation();

		return chart;
	}
	
	PieChart initializePieChart()
	{		
		PieChart chart = new PieChart();
		chart.setTitle("Pie Chart");
		chart.setBackground(Colors.WhiteSmoke);
		chart.setAllowRotate(true);
		chart.setAllowZoom(true);
		chart.setShowLegend(false);
		chart.setSeries(createPieSeries());
		chart.setShowDataLabels(EnumSet.of(LabelKinds.OuterLabel));
		chart.getSeriesRenderer().setLabelFontSize(15.0d);
		chart.setTheme(new Theme());
		chart.getTheme().setSeriesFills(pieFill());
		chart.getTheme().setHighlightStroke(new SolidBrush(Colors.White));
		chart.getTheme().setHighlightStrokeThickness(10.0d);
		chart.getTheme().setUniformSeriesStroke(new SolidBrush(Colors.GhostWhite));
		chart.getTheme().setSeriesStrokeThicknesses(toList(toList(new double[]{15})));
		chart.getTheme().setSeriesStrokes(toListBrush(toList(new Brush[]{new SolidBrush(chart.getBackground())})));
		
		PerElementSeriesStyle style = new PerElementSeriesStyle();
		style.setFills(createPieBrushes());
		chart.getPlot().setSeriesStyle(style);

		return chart;
	}
	
	List<List<Brush>> createPieBrushes()
	{
		List<Brush> fills = new ArrayList<Brush>();
		fills.add(new SolidBrush(new Color(224, 233, 233)));
		fills.add(new SolidBrush(new Color(102, 154, 204)));
		fills.add(new SolidBrush(new Color(206, 0, 0)));
		fills.add(new SolidBrush(new Color(45, 57, 86)));
		
		List<List<Brush>> pieBrushes = new ArrayList<List<Brush>>();
		pieBrushes.add(fills);
		
		return pieBrushes;
	}
	
	JButton initializeButton()
	{		
		JButton button = new JButton("Reset Zoom");
		button.setVisible(true);
		button.setPreferredSize(new Dimension(105,25));
		button.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
                barChart.resetZoom();
            }
        });
		
		return button;
	}
	
	JTabbedPane constructJTabbedPane(JFrame frame)
	{
		JPanel barPanel = new JPanel();
		barPanel.setLayout(new BorderLayout());
		
		JPanel pnlButtons = new JPanel();
		pnlButtons.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnlButtons.add(button);
		
		barPanel.add(pnlButtons, BorderLayout.SOUTH);
		barPanel.add(barChart, BorderLayout.CENTER);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Bar Chart", barPanel);
		tabbedPane.addTab("Pie Chart", pieChart);
		return tabbedPane;		
	}
	
	ObservableList<Series> createBarSeries()
	{
		Series2D firstSeries = new Series2D(
			Arrays.asList(0.0, 1.0, 2.0, 3.0),
			Arrays.asList(20.0, 60.0, 40.0, 55.0),
			Arrays.asList("January", "February", "March", "April"));
		firstSeries.setTitle("Series 1");

		Series2D secondSeries = new Series2D(
			Arrays.asList(0.0, 1.0, 2.0, 3.0),
			Arrays.asList(30.0, 70.0, 60.0, 19.0),
			Arrays.asList("May", "June", "July", "August"));
		secondSeries.setTitle("Series 2");

		Series2D thirdSeries = new Series2D(
			Arrays.asList(0.0, 1.0, 2.0, 3.0),
			Arrays.asList(22.0, 44.0, 33.0, 66.0),
			Arrays.asList("September", "Octomber", "November", "December"));
		thirdSeries.setTitle("Series 3");

		ObservableList<Series> ols = new ObservableList<Series>();
		ols.add(firstSeries);
		ols.add(secondSeries);
		ols.add(thirdSeries);
		return ols;
	}
	
	Series createPieSeries()
	{
		PieSeries series = new PieSeries(
			Arrays.asList(20.0, 60.0, 40.0, 55.0),
			Arrays.asList("January", "February", "March", "April"),
			Arrays.asList("January", "February", "March", "April"));
		series.setTitle("Pie Series");
		return series;
	}

	List<Double> toList(double[] arr)
	{
		List<Double> list = new ArrayList<Double>();
		for(int i=0;i<arr.length;i++){
			list.add(arr[i]);
		}
		return list;
	}
	
	List<List<Double>> toList(List<Double> list)
	{
		List<List<Double>> rList = new ArrayList<List<Double>>();
		rList.add(list);
		return rList;
	}
	
	List<Brush> toList(Brush[] arr)
	{
		List<Brush> list = new ArrayList<Brush>();
		for(int i = 0;i<arr.length;i++){
			list.add(arr[i]);
		}
		
		return list;
	}
	
	List<List<Brush>> toListBrush(List<Brush> list)
	{
		List<List<Brush>> rList = new ArrayList<List<Brush>>();
		rList.add(list);
		
		return rList;
	}

	List<Brush> barFill()
	{
		List<Brush> fills = new ArrayList<Brush>();
		fills.add(new SolidBrush(new Color (102, 154, 204)));
		fills.add(new SolidBrush(new Color(206, 0, 0)));
		fills.add(new SolidBrush(new Color(0, 52, 102)));
		return fills;
	}
	
	List<Brush> barStroke()
	{
		List<Brush> strokes = new ArrayList<Brush>();
		strokes.add(new SolidBrush(new Color (210, 210, 210)));
		
		return strokes;
	}
	
	List<Double> barStrokeThicknesses()
	{
		List<Double> thicknesses = new ArrayList<Double>();
		thicknesses.add(3.0);
		
		return thicknesses;
	}
	
	List<List<Brush>> pieFill()
	{
		List<Brush> fills = new ArrayList<Brush>();
		fills.add(new SolidBrush(Colors.RosyBrown));
		fills.add(new SolidBrush(Colors.Coral));
		fills.add(new SolidBrush(Colors.Crimson));
		fills.add(new SolidBrush(Colors.DarkRed));
		
		List<List<Brush>> piefill = new ArrayList<List<Brush>>();
		piefill.add(fills);
		
		return piefill;
	}
	
	void initFrame()
	{
		JFrame frame = new JFrame();
		frame.setTitle("MindFusion.Charting sample: Interactivity");
		frame.setSize(800, 600);
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		barChart = initializeBarChart();
		pieChart = initializePieChart();
		button = initializeButton();
		
		JTabbedPane tabbedPane = constructJTabbedPane(frame);

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		frame.setVisible(true);
	}

	public static void main(String[] args)
	{
		Interactivity interactivity = new Interactivity();
		interactivity.initFrame();		
	}
	
	public JButton button = null;
}
