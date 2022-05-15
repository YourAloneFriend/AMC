import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.animation.*;
import com.mindfusion.charting.components.*;
import com.mindfusion.charting.swing.*;
import com.mindfusion.common.ObservableList;
import com.mindfusion.drawing.*;


public class GroupLabels extends JFrame
{
	BarRenderer barRenderer;
	AnnotationRenderer annotationRenderer;
	Plot2D plot;
	XAxisRenderer xAxisRenderer;
	YAxisRenderer yAxisRenderer;

	public GroupLabels()
	{
		super("MindFusion.Charting sample: Group Labels");
		setSize(800, 600);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private ObservableList<Series> createSeries()
	{
		// motherboard series
		BarSeries series1 = new BarSeries(
			Arrays.asList(20.0, 60.0, 40.0, 55.0),
			Arrays.asList("Acer", "Biostar", "Foxconn", "Supermicro"),
			null /* no top labels */);
		series1.setTitle("Motherboards");
		
		// chipset series
		BarSeries series2 = new BarSeries(
			Arrays.asList(30.0, 70.0, 65.0, 19.0),
			Arrays.asList("Biostar", "Intel", "Nvidia", "VIA Technologies"),
			null /* no top labels */);
		series2.setTitle("Chipsets");

		// CPU series
		BarSeries series3 = new BarSeries(
			Arrays.asList(22.0, 44.0, 33.0, 66.0),
			Arrays.asList("Foxconn", "Nvidia", "Marvell", "NexGen"),
			null);
		series3.setTitle("CPU");

		// HDD series
		BarSeries series4 = new BarSeries(
			Arrays.asList(12.0, 45.0, 77.0, 90.0),
			Arrays.asList("Supermicro", "VIA Technologies", "NexGen", "Toshiba"),
			null /* no top labels */);
		series4.setTitle("HDD");

		return new ObservableList<Series>(
			Arrays.asList(series1, series2, series3, series4));
	}
	
	private ObservableList<Series> createAxisLabels()
	{
		return new ObservableList<Series>(Arrays.asList(
			new SimpleSeries(null, null)
			{
				public String getLabel(int index, LabelKinds kind)
				{
					return axisLabels[index];
				}
				
				public double getValue(int index, int dimension) { return index; }
				
				public int getSize() { return axisLabels.length; }

				public int getDimensions() { return 1; }

				public EnumSet<LabelKinds> getSupportedLabels() {
					return EnumSet.of(LabelKinds.XAxisLabel);
				}
				
				final String[] axisLabels = {
					"Motherboards", "Chipsets", "CPU", "HDD" };
			}
		));
	}

	private List<Brush> fill()
	{
		List<Brush> fills = new ArrayList<Brush>();
		fills.add(new GradientBrush(new Color(224, 233, 233), new Color(102, 154, 204), 0));
		fills.add(new GradientBrush(new Color(224, 233, 233), new Color(0, 52, 102), 0));
		fills.add(new GradientBrush(new Color (224, 233, 233), new Color (97, 106, 127), 0));
		fills.add(new GradientBrush(new Color (192, 192, 192), new Color (206, 0, 0), 0));
		return fills;
	}
	
	private List<Brush> stroke()
	{
		List<Brush> strokes = new ArrayList<Brush>();
		strokes.add(new SolidBrush(Colors.Transparent));
		return strokes;
	}
	
	private Plot2D createPlot()
	{
		Plot2D out = new Plot2D();	
		out.setBackground(new SolidBrush(Colors.Black));
		List<Brush> fills = fill();
		List<Brush> strokes = stroke();
		
		out.setSeriesStyle(new PerSeriesStyle(fills, strokes, null, null));
		out.setHighlightStroke(new SolidBrush(new Color(0, 0, 99)));
		out.setHighlightStrokeDashStyle(DashStyle.Dash);
		out.setGridType(GridType.Crossed);
		out.setGridColor1(Colors.White);
		out.setGridColor2(new Color(232, 232, 232));
		out.setGridLineColor(new Color(156, 170, 198));
		
		out.setXAxis(xAxisRenderer.getAxis());
		out.setYAxis(yAxisRenderer.getAxis());
		
		out.setGridColumn(1);
		out.setGridRow(0);
		return out;
	}
	
	private GridPanel constructPanel()
	{
		GridPanel panel = new GridPanel();
		panel.getColumns().add(new GridColumn());
		panel.getRows().add(new GridRow());
		
		
		panel.getChildren().add(plot);
		panel.getChildren().add(xAxisRenderer);
		panel.getChildren().add(yAxisRenderer);
		return panel;
	}
	
	private void initializeAxis()
	{
		Axis xAxis = new Axis();
		Axis yAxis = new Axis();
		yAxis.setMinValue(0.0);
		yAxis.setMaxValue(100.0);
		
		xAxis.setTitle("");
		yAxis.setTitle("");
		
		xAxisRenderer = new XAxisRenderer(xAxis);
		yAxisRenderer = new YAxisRenderer(yAxis);
		
		
		yAxisRenderer.setGridColumn(0);
		xAxisRenderer.setGridRow(1);
		xAxisRenderer.setGridColumn(1);
		
		xAxisRenderer.setVerticalAlignment(LayoutAlignment.Stretch);
		xAxisRenderer.setHorizontalAlignment(LayoutAlignment.Stretch);
		xAxisRenderer.setAxisStroke(new SolidBrush(Colors.White));
		xAxisRenderer.setLabelBrush(new SolidBrush(Color.white));
		xAxisRenderer.setLabelFontSize(12.0);
		xAxisRenderer.setLabelsSource(annotationRenderer);
		xAxisRenderer.setShowCoordinates(false);
		xAxisRenderer.setAxisStrokeThickness(1.0);

		yAxisRenderer.setAxisStroke(new SolidBrush(Colors.White));
		yAxisRenderer.setLabelBrush(new SolidBrush(Colors.White));
		yAxisRenderer.setLabelFontSize(12.0);
		yAxisRenderer.setPlotLeftSide(true);
		yAxisRenderer.setAxisStrokeThickness(1.0);
	}
	
	private void initGroupLabels()
	{
		Dashboard board = new Dashboard();

		// set up appearance
		Theme theme = board.getTheme();
		theme.setLegendBorderStrokeThickness(0);
		theme.setLegendTitleBrush(new SolidBrush(Color.black));
		theme.setPlotBorderStrokeThickness(0);
		theme.setSubtitleBrush(new SolidBrush(Color.black));
		theme.setTitleBrush(new SolidBrush(Color.black));
		theme.setUniformSeriesFill(new SolidBrush("#90EE90"));
		theme.setUniformSeriesStroke(new SolidBrush(Color.black));
		
		barRenderer = new BarRenderer(createSeries());
		barRenderer.setLabelFontSize(12.0);
		
		annotationRenderer = new AnnotationRenderer(createAxisLabels());
		annotationRenderer.setLabelBrush(new SolidBrush(Colors.White));
		
		initializeAxis();
		
		plot = createPlot();
		
		GridPanel panel = constructPanel();
		
		plot.getSeriesRenderers().add(barRenderer);
		plot.getSeriesRenderers().add(annotationRenderer);
		
		board.getRootPanel().getChildren().add(panel);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(board, BorderLayout.CENTER);
		getContentPane().setBackground(Color.black);

		// animate the bars
		Animation animation = new Animation();
		AnimationTimeline timeline = new AnimationTimeline();
		timeline.addAnimation(
			AnimationType.PerElementAnimation, 3, barRenderer);
		animation.addTimeline(timeline);
		animation.runAnimation();

		this.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		GroupLabels groupLabelsSample = new GroupLabels();
		groupLabelsSample.initGroupLabels();
	}
}