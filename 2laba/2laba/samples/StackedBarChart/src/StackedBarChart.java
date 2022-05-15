
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


public class StackedBarChart extends JFrame
{
	BarStackRenderer barRenderer;
	AnnotationRenderer annotationRenderer;
	AnnotationRenderer annotationRenderer1;
	YAxisRenderer yAxisRenderer;
	XAxisRenderer xAxisRenderer;
	Plot2D plot;
	Axis yAxis;
	Axis xAxis;
	
	public StackedBarChart()
	{
		super("MindFusion.Charting sample: Stacked Bar Chart");
		setSize(800, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private ObservableList<Series> createSeries()
	{
		// Important series
		BarSeries series1 = new BarSeries(
			Arrays.asList(40.0, 30.0, 52.0, 62.0),
			Arrays.asList("20%", "15%", "26%", "31%"),
			null /* no top labels */);
			
		
		// Somewhat important
		BarSeries series2 = new BarSeries(
			Arrays.asList(60.0, 70.0, 44.0, 45.0),
			Arrays.asList("30%", "35%", "22%", "23%"),
			null /* no top labels */);
		series2.setTitle("Chipsets");

		// Slightly important series
		BarSeries series3 = new BarSeries(
			Arrays.asList(20.0, 65.0, 33.0, 50.0),
			Arrays.asList("10%", "33%", "17%", "25%"),
			null);
		series3.setTitle("CPU");

		// Not very important series
		BarSeries series4 = new BarSeries(
			Arrays.asList(80.0, 35.0, 71.0, 43.0),
			Arrays.asList("40%", "17%", "35%", "21%"),
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
					return EnumSet.of(LabelKinds.YAxisLabel);
				}
				
				final String[] axisLabels = {
						"Accomodation", "University\nLocation", "Tuition\nPrice", "Quality of\nEducation" };
			}
		));
	}
	
	private List<Brush> fill()
	{
		List<Brush> fills = new ArrayList<Brush>();
		fills.add(new SolidBrush(new Color (90, 116, 68)));
		fills.add(new SolidBrush(new Color(163, 198, 134)));
		fills.add(new SolidBrush(new Color(161, 208, 216)));
		fills.add(new SolidBrush(new Color (103, 139, 153)));
		
		return fills;
	}
	
	private List<Brush> stroke()
	{
		List<Brush> strokes = new ArrayList<Brush>();
		strokes.add(new SolidBrush(Colors.White));
		return strokes;
	}
	
	private Plot2D createPlot()
	{
		// create the Plot2D
		Plot2D out = new Plot2D();	
		out.setAllowPan(true);
		out.setHighlightStrokeDashStyle(DashStyle.Dash);
		out.setHorizontalAlignment(LayoutAlignment.Stretch);
		out.setVerticalAlignment(LayoutAlignment.Stretch);
		out.setGridType(GridType.Vertical);

		// styling options
		List<Brush> fills = fill();
		List<Brush> strokes = stroke();
		out.setBackground(new SolidBrush(Color.white));
		out.setVerticalScroll(true);
		out.setSeriesStyle(new PerSeriesStyle(fills, strokes, Arrays.asList(5.0), null));		
		out.setHighlightStroke(new SolidBrush(new Color(0, 0, 99)));
	
		// position in the grid
		out.setGridColumn(1);
		out.setGridRow(1);
		return out;
	}
	
	private GridPanel constructPanel()
	{
		GridPanel panel = new GridPanel();
		panel.getColumns().add(new GridColumn());
		panel.getRows().add(new GridRow());	
		
		panel.getChildren().add(plot);						
		panel.getChildren().add(yAxisRenderer);
		panel.getChildren().add(xAxisRenderer);
	
		
		return panel;
	}
	
	private void initializeAxis()
	{
		// setup of the X-axis
		xAxis = new Axis();
		xAxis.setMinValue(0.0);
		xAxis.setMaxValue(220.0);
		xAxis.setInterval(50.0);		
	    xAxis.setTitle("");		
		
	    // the xAxisRenderer is bound to the xAxis
		xAxisRenderer = new XAxisRenderer(xAxis);			
		xAxisRenderer.setAxisStroke(new SolidBrush(Colors.Black));
		xAxisRenderer.setAxisStrokeThickness(1.0);
		xAxisRenderer.setLabelFontSize(12.0);
		xAxisRenderer.setLabelBrush(new SolidBrush(Colors.Black));
		
		// axis labels will be rendered by an AnnotationRenderer
		xAxisRenderer.setLabelsSource(annotationRenderer1);
		xAxisRenderer.setShowCoordinates(false);		
		xAxisRenderer.setPlotBottomSide(false);

		// stretch this horizontal axis
    	xAxisRenderer.setHorizontalAlignment(LayoutAlignment.Stretch);
    	
    	// position in the Grid
    	xAxisRenderer.setGridColumn(1);
		xAxisRenderer.setGridRow(0);
		
		// Y
		yAxis = new Axis();
		yAxis.setMinValue(-1.0);
		yAxis.setMaxValue(4.0);
		yAxis.setInterval(1.0);
		
	    yAxis.setTitle("");			
		yAxisRenderer = new YAxisRenderer(yAxis);		
		
		yAxisRenderer.setGridColumn(0);
		yAxisRenderer.setGridRow(1);
		
		yAxisRenderer.setAxisStroke(new SolidBrush(Colors.Transparent));
		yAxisRenderer.setLabelBrush(new SolidBrush(Colors.Black));
		yAxisRenderer.setLabelsSource(annotationRenderer);
		yAxisRenderer.setShowCoordinates(false);
		yAxisRenderer.setLabelFontSize(12.0);
		yAxisRenderer.setPlotLeftSide(true);
		yAxisRenderer.setToolTip("Criteria");
		yAxisRenderer.setAxisStrokeThickness(1.0);
		yAxisRenderer.setShowTicks(false);
		yAxisRenderer.setVerticalAlignment(LayoutAlignment.Stretch);
		yAxisRenderer.setHorizontalAlignment(LayoutAlignment.Stretch);	
		yAxisRenderer.setLabelPadding(20);
		
	}
	
	private void createDashboard()
	{
		Dashboard board = new Dashboard();
						
		// set up appearance
		Theme theme = board.getTheme();
		theme.setPlotBorderStrokeThickness(0);
		theme.setTitleBrush(new SolidBrush(Color.black));
		theme.setGridColor1(Color.white);
		theme.setGridColor2(new Color(240, 240, 240));
		theme.setGridLineColor(new Color(255, 255, 255));
			
		barRenderer = new BarStackRenderer (createSeries());
		barRenderer.setHorizontalBars(true);
		barRenderer.setBarSpacingRatio(0.3);
		barRenderer.setYAxis(yAxis);
		barRenderer.setXAxis(xAxis);
		barRenderer.setShowDataLabels(EnumSet.of(LabelKinds.InnerLabel));
	    barRenderer.setLabelFontSize(12.0);

	    plot = createPlot();
	    
		annotationRenderer = new AnnotationRenderer(createAxisLabels());
		annotationRenderer.setLabelBrush(new SolidBrush(Colors.White));
		
		List<Series> sl = new ArrayList<Series>();
		sl.add(new CustomBarSeries(
				Arrays.asList(25.0, 50.0, 50.0, 50.0),
				null,
				null));

		ObservableList<Series> olss = new ObservableList<Series>(sl);
		annotationRenderer1 = new AnnotationRenderer(olss);
		annotationRenderer1.setSeries(olss);
		
		
		initializeAxis();		
		
		annotationRenderer1.setXAxis(xAxis);
		annotationRenderer1.setShowDataLabels(EnumSet.of(LabelKinds.XAxisLabel));
		
		
		plot.setXAxis(xAxis);
		plot.setYAxis(yAxis);
			
		GridPanel panel = constructPanel();
		panel.setVerticalAlignment(LayoutAlignment.Stretch);
		
		plot.getSeriesRenderers().add(barRenderer);
		plot.getSeriesRenderers().add(annotationRenderer1);
		plot.getSeriesRenderers().add(annotationRenderer);
		
		com.mindfusion.charting.components.TextComponent title = 
				new com.mindfusion.charting.components.TextComponent();
		title.setText("What's Important When You Choose High School?");
		title.setHorizontalAlignment(LayoutAlignment.Stretch);
		title.setMargin(new Margins(20));
		
		title.setFontStyle(EnumSet.of(FontStyle.BOLD));
		
		board.getLayoutPanel().getChildren().add(title);
		board.getLayoutPanel().getChildren().add(panel);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(board, BorderLayout.CENTER);
	
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
		StackedBarChart barChart = new StackedBarChart();
		barChart.createDashboard();
	}
}
