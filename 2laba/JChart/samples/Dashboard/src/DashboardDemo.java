import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.JFrame;

import com.mindfusion.charting.*;
import com.mindfusion.charting.animation.*;
import com.mindfusion.charting.components.*;
import com.mindfusion.charting.components.TextComponent;
import com.mindfusion.charting.components.gauges.Length;
import com.mindfusion.charting.components.gauges.LengthType;
import com.mindfusion.charting.components.gauges.OvalGauge;
import com.mindfusion.charting.components.gauges.OvalScale;
import com.mindfusion.charting.components.gauges.Pointer;
import com.mindfusion.charting.components.gauges.Text;
import com.mindfusion.charting.swing.*;
import com.mindfusion.common.ObservableList;
import com.mindfusion.common.Orientation;
import com.mindfusion.drawing.*;


public class DashboardDemo
{
	Dashboard initDashboard()
	{
		Dashboard dashboard = new Dashboard();
		LayoutBuilder builder = new LayoutBuilder(dashboard);
		Animation animation = new Animation();

		TextComponent title = new TextComponent();
		title.setHorizontalAlignment(LayoutAlignment.Center);
		title.setText("What devices and web apps are people using?");
		title.setFontSize(14.0);
		title.setFontStyle(EnumSet.of(FontStyle.BOLD));
		title.setMargin(new Margins(10));
		dashboard.getLayoutPanel().getChildren().add(title);

		// build subtitle as a stack of text components
		StackPanel nameStack = new StackPanel();
		nameStack.setOrientation(Orientation.Horizontal);
		nameStack.setHorizontalAlignment(LayoutAlignment.Center);
		nameStack.setMargin(new Margins(0.0, 0.0, 0.0, 10.0));

		dashboard.getLayoutPanel().getChildren().add(nameStack);

		TextComponent txt2 = new TextComponent();
		txt2.setHorizontalAlignment(LayoutAlignment.Center);
		txt2.setText("Yahoo");
		txt2.setFontStyle(EnumSet.of(FontStyle.BOLD)); txt2.setTextBrush(new SolidBrush(Colors.Purple));
		txt2.setMargin(new Margins(0));
		
		nameStack.getChildren().add(txt2);
		
		TextComponent txt3 = new TextComponent();
		txt3.setText(" vs ");
		nameStack.getChildren().add(txt3);
		
		TextComponent[] google = new TextComponent[6];
		String[] g = "Google".split("");
		Brush[] br = new Brush[] {
			new SolidBrush(new Color(66, 133, 244)), new SolidBrush(new Color(219, 68, 55)),
			new SolidBrush(new Color(244, 180, 0)), new SolidBrush(new Color(66, 133, 244)), 
			new SolidBrush(new Color(15, 157, 88)), new SolidBrush(new Color(219, 68, 55))
		};
		for (int i = 0; i<google.length; i++)
		{
			google[i] = new TextComponent();
			google[i].setText(g[i]);
			google[i].setFontStyle(EnumSet.of(FontStyle.BOLD));
			google[i].setTextBrush(br[i]);
			nameStack.getChildren().add(google[i]);
		}

		txt3 = new TextComponent();
		txt3.setText(" vs ");
		nameStack.getChildren().add(txt3);
		
		TextComponent txt4 = new TextComponent();
		txt4.setText("Facebook");
		txt4.setTextBrush(new SolidBrush(new Color(59, 89, 152)));
		txt4.setFontStyle(EnumSet.of(FontStyle.BOLD));
		txt4.setMargin(new Margins(0));
		
		nameStack.getChildren().add(txt4);
		
		// create bar-chart axis renderers
		Axis barPlotYAxis = new Axis();
		barPlotYAxis.setMinValue(0.0);
		barPlotYAxis.setMaxValue(1010.0);
		barPlotYAxis.setInterval(100.0);
		barPlotYAxis.setTitle("Millions of Users");

		Axis barPlotXAxis = new Axis();
		barPlotXAxis.setMaxValue(10.0);
		barPlotXAxis.setMinValue(1.0);
		barPlotXAxis.setInterval(1.0);
		barPlotXAxis.setTitle("Time of Day");

		YAxisRenderer barPlotYAxisRenderer = new YAxisRenderer(barPlotYAxis);
		barPlotYAxisRenderer.setLabelFontSize(10.0);
		barPlotYAxisRenderer.setTitleFontSize(12.0);
		XAxisRenderer barPlotXAxisRenderer = new XAxisRenderer(barPlotXAxis);
		barPlotXAxisRenderer.setLabelFontSize(10.0);
		barPlotXAxisRenderer.setTitleFontSize(12.0);

		// create bar-chart plot
		Plot2D barPlot = new Plot2D();
		barPlotXAxisRenderer.setLabelsSource(barPlot);
		barPlotXAxisRenderer.setShowCoordinates(false);

		// create sample data
		Series2D smartphones = createSeries(1, 24, 1);
		smartphones.setLabels(new ArrayList<String>());
		for (int i = 0; i < 24; i++)
		{
			smartphones.getLabels().add(String.format("%2d:%2d\n%s", 
					(i/13 == 0) ? i : (i%12), 0, (i / 13 == 0) ? "AM" : "PM"));
		}
		smartphones.setSupportedLabels(LabelKinds.XAxisLabel);
		smartphones.setTitle("Smartphone");

		Series2D tablets = createSeries(1, 24, 1);
		tablets.setTitle("Tablet");
		Series2D computers = createSeries(1, 24, 1);
		computers.setTitle("Computer");

		// render the data series as bars
		List<Series> seriesList = Arrays.asList(smartphones, tablets, computers);
		ObservableList<Series> ols = new ObservableList<Series>(seriesList);
		BarRenderer barRenderer = new BarRenderer(ols);
		
		AnimationTimeline barTimeline = new AnimationTimeline();
		barTimeline.addAnimation(AnimationType.PerSeriesAnimation, 3.0f, barRenderer);
		
		PerSeriesStyle barRendererStyle = new PerSeriesStyle();
		barRendererStyle.setFills(
			Arrays.asList(
				new SolidBrush(new Color(0, 52, 102)),
				new SolidBrush(new Color(102, 154, 204)),
				new SolidBrush(new Color(206, 0, 0))));
		barRendererStyle.setStrokes(
			Arrays.asList(
				new SolidBrush(new Color(0, 52, 102)),
				new SolidBrush(new Color(102, 154, 204)),
				new SolidBrush(new Color(206, 0, 0))));
		barRenderer.setSeriesStyle(barRendererStyle);	

		barPlot.setYAxis(barPlotYAxis);
		barPlot.setXAxis(barPlotXAxis);
		barPlot.setGridType(GridType.Horizontal);
		barPlot.setGridLineStyle(DashStyle.Dash);
		barPlot.setGridColor1(Color.WHITE);
		barPlot.setGridColor2(new Color(232, 232, 232));
		barPlot.setGridLineColor(new Color(192, 192, 192));
		barPlot.setHighlightStroke(new SolidBrush(new Color(192, 192, 192)));

		barRenderer.setYAxis(barPlotYAxis);

		// create line plot
		Plot2D linePlot = new Plot2D();
		linePlot.setBackground(new SolidBrush(Colors.White));
		linePlot.setGridType(GridType.Crossed);
		linePlot.setGridColor1(Color.WHITE);
		linePlot.setGridColor2(Color.WHITE);
		linePlot.setGridLineStyle(DashStyle.Dash);
		linePlot.setGridLineColor(new Color(192, 192, 192));

		// create sample data
		Series2D yahooSeries = new Series2D(
			Arrays.asList(
				0d, 1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d, 11d, 12d, 13d, 14d,
				15d, 16d, 17d, 18d, 19d, 20d, 21d, 22d, 23d, 24d, 25d, 26d, 27d,
				28d, 29d, 30d, 31d, 32d, 33d, 34d, 35d, 36d), 
			Arrays.asList(
				40000d, 38000d, 39000d, 39000d, 41000d, 37500d, 35300d, 37500d,
				34500d, 37800d, 39000d, 37800d, 37800d, 34500d, 41500d, 44000d,
				48000d, 43000d, 44000d, 49000d, 37500d, 42500d, 43500d, 43500d,
				42000d, 42200d, 43500d, 43200d, 40500d, 40500d, 39000d, 40500d,
				38000d, 42000d, 39000d, 38000d, 37000d),
			Arrays.asList(
				"jun 07", " ", " ", " ", "oct 07", " ", " ", " ",
				"feb 08", " ", " ", " ", "jun 08", " ", " ", " ",
				"oct 08", " ", " ", " ", "feb 09", " ", " ", " ",
				"jun 09", " ", " ", " ", "oct 09", " ", " ", " ",
				"feb 10", " ", " ", " ", "jun 10"));

		yahooSeries.setTitle("Yahoo");
		yahooSeries.setSupportedLabels(LabelKinds.XAxisLabel);

		Series2D googleSeries = new Series2D(
			Arrays.asList(
				0d, 1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d, 11d, 12d, 13d, 14d,
				15d, 16d, 17d, 18d, 19d, 20d, 21d, 22d, 23d, 24d, 25d, 26d, 27d,
				28d, 29d, 30d, 31d, 32d, 33d, 34d, 35d, 36d),
			Arrays.asList(
				11500d, 12500d, 12600d, 13500d, 13400d, 14500d, 14500d,
				16500d, 16500d, 16500d, 16600d, 16600d, 17000d, 17000d,
				17100d, 19000d, 19000d, 18500d, 18500d, 21200d, 20700d,
				26500d, 28500d, 34500d, 33500d, 36500d, 36500d, 34500d,
				37000d, 38500d, 41000d, 39000d, 40500d, 40000d, 40500d, 41000d, 41000d),
			null);
		googleSeries.setTitle("Google");
			
		Series2D facebookSeries = new Series2D(
			Arrays.asList(
				0d, 1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d, 11d, 12d, 13d, 14d,
				15d, 16d, 17d, 18d, 19d, 20d, 21d, 22d, 23d, 24d, 25d, 26d, 27d,
				28d, 29d, 30d, 31d, 32d, 33d, 34d, 35d, 36d),
			Arrays.asList(
				5000d, 6150d, 6100d, 5000d, 6050d, 6100d, 6100d, 6150d,
				6100d, 5150d, 6200d, 6200d, 8000d, 8000d, 9500d, 10500d,
				10500d, 11500d, 12000d, 10500d, 12000d, 16000d, 16500d,
				17000d, 17500d, 20000d, 26000d, 27500d, 31500d, 30000d,
				31500d, 32500d, 36000d, 37500d, 39000d, 41000d, 42000d),
			null);
		facebookSeries.setTitle("Facebook");

		// define axis ranges
		Axis yAxis = new Axis();
		yAxis.setMinValue(0d);
		yAxis.setMaxValue(55000d);
		yAxis.setInterval(5000.0);
			
		Axis xAxis = new Axis();
		xAxis.setMinValue(0d);
		xAxis.setMaxValue(36d);
		xAxis.setInterval(1.0);

		linePlot.setXAxis(xAxis);
		linePlot.setYAxis(yAxis);

		// render sample data as line-chart
		List<Series> lineSeriesList = Arrays.asList(
			googleSeries, yahooSeries, facebookSeries);
		ObservableList<Series> ols1 = new ObservableList<Series>(lineSeriesList);
		
		LineRenderer lineRenderer = new LineRenderer(ols1);

		AnimationTimeline lineTimeline = new AnimationTimeline();
		lineTimeline.addAnimation(AnimationType.PerElementAnimation, 3.0f, lineRenderer);
		
		GradientBrush googleBrush = new GradientBrush(
				new Color(66, 133, 244),
				new Color(244, 180, 0), 0);
		googleBrush.setColors(new Color[]{
				new Color(66,133,244),
				new Color(244,180,0),
				new Color(219,68,55),
				new Color(66,133,244)
			});
		googleBrush.setFractions(new float[] { 0.0f, 0.33f, 0.66f, 1.0f });

		lineRenderer.setYAxis(yAxis);
		MixedSeriesStyle lineSeriesStyle = new MixedSeriesStyle();
		lineSeriesStyle.setStrokes(
			Arrays.asList(
				Arrays.asList(
					new SolidBrush(new Color(66,133,244)),						
					new SolidBrush(new Color(219,68,55)),
					new SolidBrush(new Color(244,180,0)),
					new SolidBrush(new Color(66,133,244)),
					new SolidBrush(new Color(15,157,88)),
					new SolidBrush(new Color(219,68,55))
				),
				Arrays.asList(
					new SolidBrush(Colors.Purple)
				),
				Arrays.asList(
					new SolidBrush(new Color(59,89,152)),
					new SolidBrush(new Color(242,242,242)))));
		
		lineSeriesStyle.setStrokeThicknesses(new ArrayList<List<Double>>());
		lineSeriesStyle.getStrokeThicknesses().add(Arrays.asList(5d));
		
		lineSeriesStyle.setFills(
			Arrays.asList(
				Arrays.asList(googleBrush),
				Arrays.asList(new SolidBrush(Colors.Purple)),
				Arrays.asList(new GradientBrush(new Color(59,89,152), new Color(242,242,242), 0))));

		lineRenderer.setSeriesStyle(lineSeriesStyle);

		// show Y axis
		YAxisRenderer linePlotYRenderer = new YAxisRenderer(yAxis);
		linePlotYRenderer.setLabelsSource(linePlot);
		linePlotYRenderer.setLabelFontSize(10.0);
		linePlotYRenderer.setTitleFontSize(12.0);
		linePlotYRenderer.getAxis().setTitle("Millions of Minutes");
		
		// show X axis
		XAxisRenderer linePlotXRenderer = new XAxisRenderer(xAxis);
		linePlotXRenderer.setLabelsSource(linePlot);
		linePlotXRenderer.setLabelFontSize(10.0);
		linePlotXRenderer.setShowCoordinates(false);
		linePlotXRenderer.setShowSeriesLabels(true);
		linePlotXRenderer.getAxis().setTitle(" ");
		
		GridPanel linePanel = builder.createAndAddPlotWithBottomAndLeftAxes
			(linePlot, linePlotXRenderer, linePlotYRenderer);

		// create a gauge
		OvalGauge ovalGauge = new OvalGauge();
		ovalGauge.setPointerStroke(new SolidBrush(Colors.Black));
		ovalGauge.setPointerBackground(new SolidBrush(Colors.Transparent));
		ovalGauge.setTickBackground( 
			new GradientBrush(new Color(192, 192, 192), new Color(206, 0, 0), 0));
		ovalGauge.setHorizontalAlignment(LayoutAlignment.Stretch);
		ovalGauge.setVerticalAlignment(LayoutAlignment.Stretch);
		ovalGauge.setStroke(new Pen(new Color(192, 192, 192)));
		ovalGauge.setMargin(new Margins(25.0));

		OvalScale scale = ovalGauge.getScales().get(0);
		scale.setEndAngle(270);
		scale.setStartAngle(90);
		scale.setMinValue(0);
		scale.setMaxValue(10);

		// gauge definition
		Text text = new Text();
		text.setContent("TB/h\n x 1000");
		text.setFontFamily("Verdana");
		text.setFontSize(new Length(12.0, LengthType.Relative));
		text.setFontStyle(FontStyle.BOLD);
		text.setAlignment(LayoutAlignment.Far);
		text.setLineAlignment(LayoutAlignment.Far);
		text.setForeground(Colors.White);
		text.setMargin(new Margins(0.3f, 0.3f, 0.3f, 0.3f));
		scale.getScaleChildren().add(text);

		Pointer pointer1 = new Pointer();
		pointer1.setValue(1.8);
		pointer1.setFill(new SolidBrush(Colors.LightGreen));
		scale.getPointers().add(pointer1);

		Pointer pointer2 = new Pointer();
		pointer2.setValue(4.63);
		pointer2.setFill(new SolidBrush(new Color(0, 52, 102)));
		scale.getPointers().add(pointer2);

		Pointer ptr = scale.getPointers().get(0); 
		ptr.setIsInteractive(false);
		ptr.setValue(7.3);
		ptr.setFill(new SolidBrush(new Color(206, 0, 0)));

		scale.getMiddleTickSettings().setShowLabels(false);
		
		// grid for second row of components
		GridPanel pnlGrid = new GridPanel();
		pnlGrid.getColumns().add(new GridColumn());
		pnlGrid.getRows().add(new GridRow());

		pnlGrid.getRows().get(1).setLengthType(com.mindfusion.charting.components.LengthType.Relative);
		for (GridColumn col : pnlGrid.getColumns())
			col.setLengthType(com.mindfusion.charting.components.LengthType.Relative);

		barPlot.getSeriesRenderers().add(barRenderer);
		linePlot.getSeriesRenderers().add(lineRenderer);

		GridPanel barPlotWithAxes = builder.createPlotWithBottomAndLeftAxes(barPlot, barPlotXAxisRenderer, barPlotYAxisRenderer);
		TextComponent barText = new TextComponent();
		barText.setGridRow(0);
		barText.setGridColumn(0);
		barText.setText("Device usage during the day");
		barText.setFontStyle(EnumSet.of(FontStyle.BOLD));
		barText.setMargin(new Margins(10));
		barText.setHorizontalAlignment(LayoutAlignment.Stretch);
		
		TextComponent pieText = new TextComponent();
		pieText.setGridRow(0);
		pieText.setGridColumn(1);
		pieText.setText("Data per hour");
		pieText.setFontStyle(EnumSet.of(FontStyle.BOLD));
		pieText.setMargin(new Margins(10));
		pieText.setHorizontalAlignment(LayoutAlignment.Stretch);
		
		barPlotWithAxes.setGridRow(1);
		barPlotWithAxes.setGridColumn(0);
		ovalGauge.setGridRow(1);
		ovalGauge.setGridColumn(1);
			
		pnlGrid.getChildren().add(barText);
		pnlGrid.getChildren().add(pieText);
		pnlGrid.getChildren().add(barPlotWithAxes);
		pnlGrid.getChildren().add(ovalGauge);

		// create a legend for the line serires
		LegendRenderer legendRenderer = new LegendRenderer();
		List<SeriesRenderer> legendSeriesList = new ArrayList<SeriesRenderer>();
		legendSeriesList.add(lineRenderer);
		ObservableList<SeriesRenderer> ols3 = new ObservableList<SeriesRenderer>(legendSeriesList);
		legendRenderer.setContent(ols3);
		legendRenderer.setMargin(new Margins(80, 70, 0, 0));
		legendRenderer.setBackground(new SolidBrush(Color.WHITE));
		legendRenderer.setBorderStroke(new SolidBrush(new Color(192, 192, 192)));
		legendRenderer.setTitle("");
		dashboard.getRootPanel().getChildren().add(legendRenderer);
		
		LegendRenderer legendRenderer2 = new LegendRenderer();
		List<SeriesRenderer> legendSeriesList2 = new ArrayList<SeriesRenderer>();
		legendSeriesList2.add(barRenderer);
		ObservableList<SeriesRenderer> legSeries2 = new ObservableList<SeriesRenderer>(legendSeriesList2);
		legendRenderer2.setContent(legSeries2);
		legendRenderer2.setVerticalAlignment(LayoutAlignment.Far);
		legendRenderer2.setMargin(new Margins(80, 0, 0, 300));
		legendRenderer2.setBackground(new SolidBrush(Color.WHITE));
		legendRenderer2.setBorderStroke(new SolidBrush(new Color(192, 192, 192)));
		legendRenderer2.setTitle("");
		dashboard.getRootPanel().getChildren().add(legendRenderer2);

		pnlGrid.setVerticalAlignment(LayoutAlignment.Stretch);
		barPlotWithAxes.setVerticalAlignment(LayoutAlignment.Stretch);
		ovalGauge.setVerticalAlignment(LayoutAlignment.Stretch);
		dashboard.getLayoutPanel().getChildren().add(pnlGrid);
		
		animation.addTimeline(barTimeline);
		animation.addTimeline(lineTimeline);
		animation.runAnimation();
		
		return dashboard;
	}
	
	void initFrame()
	{
		JFrame f = new JFrame();
		f.setTitle("MindFusion.Charting sample: Dashboard");
		f.setSize(800, 600);
		f.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setLayout(new BorderLayout());
		f.add(initDashboard(), BorderLayout.CENTER);
		f.setVisible(true);
	}
	
	Series2D createSeries(int startX, int endX, int intervalX)
	{
		List<Double> x = new ArrayList<Double>();
		List<Double> y = new ArrayList<Double>();
		List<String> innerLabels = new ArrayList<String>();
		Random rand = new Random();
		for(int i = startX; i<=endX; i+=intervalX)
		{
			x.add((double)i);
			y.add(rand.nextDouble()*1000);
			//innerLabels.Add(string.Format("inner label {0}", i - startX + 1));
		}
		Series2D result = new Series2D(x, y, null);
		return result;
	}
	
	public static void main(String[] args)
	{
		DashboardDemo demo = new DashboardDemo();
		demo.initFrame();
	}
	
	Random rand = new Random();
}
