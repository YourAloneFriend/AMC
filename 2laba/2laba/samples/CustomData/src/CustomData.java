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



public class CustomData extends JFrame
{
	BubbleChart bubbleChart;

	void initChart()
	{
		bubbleChart = new BubbleChart();
		bubbleChart.setSeries(
			createSeries());
	
		bubbleChart.setTitle("Patient BM Index by Age");
		bubbleChart.setBackground(new Color(224, 233, 233));

		bubbleChart.getXAxis().setMinValue(0.0);
		bubbleChart.getXAxis().setMaxValue(80.0);
		bubbleChart.getXAxis().setTitle("Age");
		bubbleChart.getYAxis().setMinValue(0.0);
		bubbleChart.getYAxis().setMaxValue(250.0);
		bubbleChart.getYAxis().setTitle("Weight");
		bubbleChart.setTitleMargin(new Margins(10.0));

		Theme theme = bubbleChart.getTheme(); 
		theme.setCommonSeriesFills(Arrays.asList(
			new GradientBrush(new Color(224, 233, 233), new Color(45, 57, 86), 0),
			new GradientBrush(new Color(224, 233, 233), new Color(206, 0, 0), 0)
		));
		theme.setCommonSeriesStrokes(Arrays.asList(
				new SolidBrush(new Color(156, 170, 198)),
				new SolidBrush(new Color(206, 0, 0))
			));
		theme.setCommonSeriesStrokeThicknesses(Arrays.asList(
				0.5
			));
	//	theme.setCommonSeriesStrokes(theme.getCommonSeriesFills());
		theme.setLegendBackground(new SolidBrush(Color.white));
		theme.setHighlightStroke(new SolidBrush(Color.white));
		theme.setGridColor1(new Color(245, 245, 245));
		theme.setGridColor2(new Color(255, 255, 255));
		theme.setGridLineColor(new Color(210, 210, 210));
		theme.setTitleFontSize(16);
		theme.setTitleFontStyle(EnumSet.of(FontStyle.BOLD, FontStyle.ITALIC));
		theme.setAxisTitleFontSize(14);
		theme.setDataLabelsFontSize(12);
	
		bubbleChart.setLegendHorizontalAlignment(LayoutAlignment.Far);
		bubbleChart.setLegendTitle("");
		bubbleChart.setGridType(GridType.Horizontal);
		bubbleChart.setAllowMoveLegend(false);

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
		List<Patient> orangePatients = new ArrayList<Patient>();
		
				orangePatients.add(new Patient("John",15,70,"BMI > 25"));
				orangePatients.add(new Patient("Elizabeth",4,20,"BMI > 25"));
				orangePatients.add(new Patient("Daniel",25,95,"BMI > 25"));
				orangePatients.add(new Patient("Robert",32,108,"BMI > 25"));
				orangePatients.add(new Patient("Irina",51,83,"BMI > 25"));
				
		List<Patient> redPatients = new ArrayList<Patient>();
			
				redPatients.add(new Patient("George",35,180,"BMI > 30"));
				redPatients.add(new Patient("Elena",14,77,"BMI > 30"));
				redPatients.add(new Patient("Viktor",7,65,"BMI > 30"));
				redPatients.add(new Patient("Jordan",48,125,"BMI > 30"));
				redPatients.add(new Patient("Rafal",67,98,"BMI > 30"));
		
		PatientSeries orangeSeries = new PatientSeries(orangePatients);
		PatientSeries redSeries = new PatientSeries(redPatients);

		ObservableList<Series> ols = new ObservableList<Series>();
		ols.add(orangeSeries);
		ols.add(redSeries);
		return ols;
	}

	void initFrame()
	{
		initChart();

		setTitle("MindFusion.Charting sample: Custom Data");
		setSize(800, 600);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(bubbleChart, BorderLayout.CENTER);
		setVisible(true);
	}

	public static void main(String[] args)
	{
		CustomData customData =  new CustomData();
		customData.initFrame();
	}
}