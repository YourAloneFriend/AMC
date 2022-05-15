import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mindfusion.charting.BarLayout;
import com.mindfusion.charting.Margins;
import com.mindfusion.charting.Series;
import com.mindfusion.charting.Series2D;
import com.mindfusion.charting.components.ImageAlign;
import com.mindfusion.charting.components.LayoutAlignment;
import com.mindfusion.charting.swing.BarChart3D;
import com.mindfusion.common.ObservableList;
import com.mindfusion.drawing.Brush;
import com.mindfusion.drawing.Colors;
import com.mindfusion.drawing.DashStyle;
import com.mindfusion.drawing.FontStyle;
import com.mindfusion.drawing.SolidBrush;


public class BarChart3DDemo extends JFrame
{
	BarChart3D barChart = null;

	public BarChart3DDemo()
	{
		barChart = new BarChart3D();
		barChart.setTitle("Agricultural produce by type");
		
		initialize();
		
		//create sample data
		barChart.setSeries(createSeries());
		
		//set one color per series
		barChart.getTheme().setCommonSeriesFills(createColorsPerSeries());
		
		//background
		barChart.setBackground(Colors.WhiteSmoke);
		barChart.setPlotImage(bgImage);
		barChart.setPlotImageAlign(ImageAlign.MiddleLeft);
		barChart.setPlotImageAutoSize(false);
		
		//axis appearance
		barChart.getXAxis().setTitle("");
		barChart.getYAxis().setTitle("Quantity in tons");
		barChart.getTheme().setAxisLabelsBrush(new SolidBrush(Colors.Black));
		barChart.getTheme().setAxisLabelsFontName("Verdana");
		barChart.getTheme().setAxisLabelsFontSize(12.0d);
		barChart.getTheme().setAxisLabelsFontStyle(EnumSet.of(FontStyle.REGULAR));
		barChart.getTheme().setAxisStroke(new SolidBrush(Colors.DimGray));
		barChart.getTheme().setAxisStrokeDashStyle(DashStyle.Solid);
		barChart.getTheme().setAxisStrokeThickness(1.0d);
		barChart.getTheme().setAxisTitleBrush(new SolidBrush(Colors.Black));
		barChart.getTheme().setAxisTitleFontName("Verdana");
		barChart.getTheme().setAxisTitleFontSize(13.0d);
		barChart.getTheme().setAxisTitleFontStyle(EnumSet.of(FontStyle.REGULAR));
		
		//data labels
		barChart.getTheme().setDataLabelsBrush(new SolidBrush(Colors.Black));
		barChart.getTheme().setDataLabelsFontName("Arial");
		barChart.getTheme().setDataLabelsFontSize(14.0d);
		barChart.getTheme().setDataLabelsFontStyle(EnumSet.of(FontStyle.REGULAR));
		
		
		//legend appearance
		barChart.setLegendTitle("Agricultural type");
		barChart.getTheme().setLegendBackground(new SolidBrush(Colors.Wheat));
		barChart.getTheme().setLegendBorderStroke(new SolidBrush(Colors.SandyBrown));
		barChart.getTheme().setLegendBorderStrokeThickness(1.0d);
		barChart.getTheme().setLegendTitleBrush(new SolidBrush(Colors.Black));
		barChart.getTheme().setLegendTitleFontName("Verdana");
		barChart.getTheme().setLegendTitleFontSize(12.0d);
		barChart.getTheme().setLegendTitleFontStyle(EnumSet.of(FontStyle.REGULAR));
		barChart.setLegendMargin(new Margins(80, 5, 5, 5));
		barChart.getLayoutPanel().setMargin(new Margins(10));
		
		setupControls();
	}
	
	private void initialize()
	{
		label1 = new Label();
		tabbedPane = new JTabbedPane();
		tabPage2 = new JPanel();
		cbImage = new Checkbox();
		cbShowYTicks = new Checkbox();
		cbShowXTicks = new Checkbox();
		cbShowY = new Checkbox();
		cbShowX = new Checkbox();
		cbShowScatter = new Checkbox();
		label2 = new Label();
		cbLayout = new JComboBox<Object>(new Object[]{"SideBySide", "Stack", "Overlay"});
		tabPage4 = new JPanel();
		btnResetZoom = new JButton();
		cbZoom = new Checkbox();
		cbMoveLegend = new Checkbox();
		cbAllowPan = new Checkbox();
		tabPage5 = new JPanel();
		label7 = new Label();
		label6 = new Label();
		tbMarginLeft = new JSlider();
		tbMarginTop = new JSlider();
		label5 = new Label();
		cbVertAlign = new JComboBox<Object>(new Object[]{"Near", "Center", "Far", "Stretch"});
		label4 = new Label();
		cbHorAlign = new JComboBox<Object>(new Object[]{"Near", "Center", "Far", "Stretch"});
		
		//bgImage
		
		try
		{
		    bgImage = ImageIO.read(new File("bgimage.png"));
		}
		catch (IOException e)
		{
		}
		
		//label1
		
		label1.setBackground(Colors.White);
		label1.setLocation(new Point(0, 683));
		label1.setName("label1");
		label1.setPreferredSize(new Dimension(1008, 47));
		label1.setText(
			"This sample demonstrates various properties of the BarChart3D control. Change pro" +
		    "perty values in tab panels to see their effect in the chart.");
		
		//tabbedPane
		
		tabbedPane.addTab("Appearance", tabPage2);
		tabbedPane.addTab("Behavior", tabPage4);
		tabbedPane.addTab("Legend", tabPage5);
		tabbedPane.setLocation(new Point(0, 0));
		tabbedPane.setName("tabbedPane");
		tabbedPane.setSelectedIndex(0);
		tabbedPane.setPreferredSize(new Dimension(1008, 101));
		
		//tabPage2
		
		tabPage2.setLayout(null);
		tabPage2.add(cbShowYTicks);
		tabPage2.add(cbShowXTicks);
		tabPage2.add(cbShowY);
		tabPage2.add(cbShowX);
		tabPage2.add(cbShowScatter);
		tabPage2.add(label2);
		
		tabPage2.add(cbImage);//chb
		tabPage2.add(cbLayout);//cmb
		tabPage2.setBorder(new EmptyBorder(3, 3, 3, 3));
		tabPage2.setLocation(new Point(4, 22));
		tabPage2.setName("tabPage2");
		tabPage2.setSize(new Dimension(1000, 75));
		tabPage2.repaint();
		
		//cbImage
		
		cbImage.setLocation(new Point(8, 49));
		cbImage.setName("cbImage");
		cbImage.setSize(new Dimension(156, 17));
		cbImage.setLabel("Show Background Image");
		cbImage.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if (cbImage.getState())
					barChart.setPlotImage(bgImage);
				else
					barChart.setPlotImage(null);
			}
		});
		
		// cbShowYTicks
		
		cbShowYTicks.setLocation(new Point(395, 49));
		cbShowYTicks.setName("cbShowYTicks");
		cbShowYTicks.setSize(new Dimension(90, 17));
		cbShowYTicks.setLabel("Show Y Ticks");
		cbShowYTicks.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				barChart.setShowYTicks(cbShowYTicks.getState());
			}
		});
		
		//cbShowXTicks

		cbShowXTicks.setLocation(new Point(395, 26));
		cbShowXTicks.setName("cbShowXTicks");
		cbShowXTicks.setSize(new Dimension(90, 17));
		cbShowXTicks.setLabel("Show X Ticks");
		cbShowXTicks.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				barChart.setShowXTicks(cbShowXTicks.getState());
			}
		});
		
		//cbShowY
		
		cbShowY.setLocation(new Point(260, 26));
		cbShowY.setName("cbShowY");
		cbShowY.setSize(new Dimension(127, 17));
		cbShowY.setLabel("Show Y Coordinates");
		cbShowY.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				barChart.setShowYCoordinates(cbShowY.getState());
			}
		});
		
		//cbShowX
		
		cbShowX.setLocation(new Point(260, 49));
		cbShowX.setName("cbShowX");
		cbShowX.setSize(new Dimension(127, 17));
		cbShowX.setLabel("Show X Coordinates");
		cbShowX.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				barChart.setShowXCoordinates(cbShowX.getState());
			}
		});
		
		//cbShowScatter
		
		cbShowScatter.setLocation(new Point(164, 26));
		cbShowScatter.setName("cbShowScatter");
		cbShowScatter.setSize(new Dimension(89, 17));
		cbShowScatter.setLabel("Show Scatter");
		cbShowScatter.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				barChart.setShowScatter(cbShowScatter.getState());
			}
		});
		
		//label2
		
		label2.setLocation(new Point(3, 10));
		label2.setName("label2");
		label2.setSize(new Dimension(60, 13));
		label2.setText("BarLayout");
		
		//cbLayout
		
		cbLayout.setEnabled(true);
		cbLayout.setSelectedIndex(0);
		cbLayout.setLocation(new Point(6, 26));
		cbLayout.setName("cbLayout");
		cbLayout.setSize(new Dimension(121, 21));
		cbLayout.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				barChart.setBarLayout(
					BarLayout.valueOf((String)cbLayout.getSelectedItem()));
			}
		});
		
		//tabPage4
		
		tabPage4.setLayout(null);
		tabPage4.add(btnResetZoom);
		tabPage4.add(cbZoom);
		tabPage4.add(cbMoveLegend);
		tabPage4.add(cbAllowPan);
		tabPage4.setLocation(new Point(4, 22));
		tabPage4.setName("tabPage4");
		tabPage4.setBorder(new EmptyBorder(3, 3, 3, 3));
		tabPage4.setSize(new Dimension(1000, 75));
		
		//btnResetZoom
		
		btnResetZoom.setLocation(new Point(300, 6));
		btnResetZoom.setName("btnResetZoom");
		btnResetZoom.setSize(new Dimension(125, 23));
		btnResetZoom.setText("Reset Zoom");
		btnResetZoom.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				barChart.resetZoom();
			}
		});
		
		//cbZoom
		
		cbZoom.setLocation(new Point(212, 10));
		cbZoom.setName("cbZoom");
		cbZoom.setSize(new Dimension(79, 17));
		cbZoom.setLabel("Allow Zoom");
		cbZoom.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				barChart.setAllowZoom(cbZoom.getState());
			}
		});
		
		//cbMoveLegend
		
		cbMoveLegend.setLocation(new Point(87, 10));
		cbMoveLegend.setName("cbMoveLegend");
		cbMoveLegend.setSize(new Dimension(118, 17));
		cbMoveLegend.setLabel("Allow MoveLegend");
		cbMoveLegend.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				barChart.setAllowMoveLegend(cbMoveLegend.getState());
			}
		});
		
		//cbAllowPan
		
		cbAllowPan.setLocation(new Point(5, 10));
		cbAllowPan.setName("cbAllowPan");
		cbAllowPan.setSize(new Dimension(70, 17));
		cbAllowPan.setLabel("Allow Pan");
		cbAllowPan.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				barChart.setAllowPan(cbAllowPan.getState());
			}
		});
		
		//tabPage5
		
		tabPage5.setLayout(null);
		tabPage5.add(label7);
		tabPage5.add(label6);
		tabPage5.add(tbMarginLeft);
		tabPage5.add(tbMarginTop);
		tabPage5.add(label5);
		tabPage5.add(cbVertAlign);
		tabPage5.add(label4);
		tabPage5.add(cbHorAlign);
		tabPage5.setLocation(new Point(4, 22));
		tabPage5.setName("tabPage5");
		tabPage5.setBorder(new EmptyBorder(3, 3, 3, 3));
		tabPage5.setSize(new Dimension(1000, 75));

		//label7
		
		label7.setLocation(new Point(389, 9));
		label7.setName("label7");
		label7.setSize(new Dimension(115, 13));
		label7.setText("LegendMargin (Top)");
		
		//label6
		
		label6.setLocation(new Point(267, 9));
		label6.setName("label6");
		label6.setSize(new Dimension(115, 13));
		label6.setText("LegendMargin (Left)");
		
		//tbMarginLeft
		
		tbMarginLeft.setLocation(new Point(272, 16));
		tbMarginLeft.setName("tbMarginLeft");
		tbMarginLeft.setSize(new Dimension(104, 45));
		tbMarginLeft.setMajorTickSpacing(50);
		tbMarginLeft.setValue(75);
		tbMarginLeft.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				barChart.setLegendMargin(
					new Margins(tbMarginLeft.getValue(), barChart.getLegendMargin().getTop(), 0, 0));
			}
		});
		
		//tbMarginTop
		
		tbMarginTop.setLocation(new Point(394, 16));
		tbMarginTop.setName("tbMarginTop");
		tbMarginTop.setSize(new Dimension(104, 45));
		tbMarginTop.setMajorTickSpacing(50);
		tbMarginTop.setValue(25);
		tbMarginTop.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				barChart.setLegendMargin(
					new Margins(barChart.getLegendMargin().getLeft(), tbMarginTop.getValue(), 0, 0));
			}
		});
		
		//label5
		
		label5.setLocation(new Point(132, 9));
		label5.setName("label5");
		label5.setSize(new Dimension(110, 13));
		label5.setText("Vertical Alignment");
		
		//cbVertAlign
		
		cbVertAlign.setEnabled(true);
		cbVertAlign.setLocation(new Point(135, 25));
		cbVertAlign.setName("cbVertAlign");
		cbVertAlign.setSize(new Dimension(121, 21));
		cbVertAlign.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				barChart.setLegendVerticalAlignment(
					LayoutAlignment.valueOf((String)cbVertAlign.getSelectedItem()));
			}
		});
		
		//label4
		
		label4.setLocation(new Point(5, 9));
		label4.setName("label4");
		label4.setSize(new Dimension(117, 13));
		label4.setText("Horizontal Alignment");
		
		//cbHorAlign
		
		cbHorAlign.setEnabled(true);
		cbHorAlign.setLocation(new Point(8, 25));
		cbHorAlign.setName("cbHorAlign");
		cbHorAlign.setSize(new Dimension(121, 21));
		cbHorAlign.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				barChart.setLegendHorizontalAlignment(
					LayoutAlignment.valueOf((String)cbHorAlign.getSelectedItem()));
			}
		});
		
		//barChart
		
		barChart.setLegendTitle("Legend");
		barChart.setLocation(new Point(0, 101));
		barChart.setName("barChart");
		barChart.setBorder(new EmptyBorder(5, 5, 5, 5));
		barChart.setShowLegend(true);
		barChart.setPreferredSize(new Dimension(1008, 582));
		barChart.setSubtitleFontName(null);
		barChart.setSubtitleFontSize(null);
		barChart.setSubtitleFontStyle(null);
		barChart.setTitleFontName(null);
		barChart.setTitleFontSize(null);
		barChart.setTitleFontStyle(null);
	}
	
	public ObservableList<Series> createSeries()
	{
		ObservableList<Series> list = new ObservableList<>();
		
		List<String> labels = Arrays.asList("Tomatoes", "Cucumbers", "Peppers", "Lettuce");

		Series2D zeroSeries = new Series2D(
			Arrays.asList(10.0, 20.0, 30.0, 40.0),
			Arrays.asList(50.0, 40.0, 50.0, 5.0),
			labels);
		zeroSeries.setTitle("Traditional");

		Series2D firstSeries = new Series2D(
			Arrays.asList(10.0, 20.0, 30.0, 40.0),
			Arrays.asList(60.0, 10.0, 20.0, 80.0),
			labels);
		firstSeries.setTitle("Urban");

		Series2D secondSeries = new Series2D(
			Arrays.asList(10.0, 20.0, 30.0, 40.0),
			Arrays.asList(0.0, 60.0, 0.0, 90.0),
			labels);
		secondSeries.setTitle("Hydroponics");

		list.add(zeroSeries);
		list.add(firstSeries);
		list.add(secondSeries);
		
		return list;
	}

	public List<Brush> createColorsPerSeries()
	{
		List<Brush> list = new ArrayList<Brush>();
		list.add(new SolidBrush(Colors.ForestGreen));
		list.add(new SolidBrush(Colors.LemonChiffon));
		list.add(new SolidBrush(Colors.LawnGreen));
		return list;
	}
	
	public void setupControls()
	{
		cbLayout.setSelectedItem(barChart.getBarLayout());
		cbShowScatter.setState(barChart.getShowScatter()); 
		cbImage.setState(true); 
		cbShowX.setState(barChart.getShowXCoordinates());
		cbShowY.setState(barChart.getShowYCoordinates());
		cbShowXTicks.setState(barChart.getShowXTicks()); 
		cbShowYTicks.setState(barChart.getShowYTicks());
		cbAllowPan.setState(barChart.getAllowPan());
		cbMoveLegend.setState(barChart.getAllowMoveLegend());
		cbHorAlign.setSelectedItem(barChart.getLegendHorizontalAlignment());
		cbVertAlign.setSelectedItem(barChart.getLegendVerticalAlignment());
		cbZoom.setState(barChart.getAllowZoom());
		tbMarginLeft.setMinimum(0);
		tbMarginLeft.setMaximum(barChart.getWidth() - 100);
		tbMarginLeft.setValue((int)barChart.getLegendMargin().getLeft());
		tbMarginTop.setMinimum(0);
		tbMarginTop.setMaximum(barChart.getHeight() - 100);
		tbMarginTop.setValue((int)barChart.getLegendMargin().getTop());
	}
	
	public void run()
	{
		setLayout(new BorderLayout());
		setTitle("MindFusion.Charting Sample: Bar Chart 3D");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().add(barChart, BorderLayout.CENTER);
		getContentPane().add(tabbedPane, BorderLayout.PAGE_START);
		getContentPane().add(label1, BorderLayout.PAGE_END);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		BarChart3DDemo frame = new BarChart3DDemo();
		frame.run();
	}

	private static final long serialVersionUID = 1L;
	
	BufferedImage bgImage = null;
	
	private Label label1 = null;
	private JTabbedPane tabbedPane = null;
	private Label label2 = null;
	private JComboBox<Object> cbLayout = null;
	private JPanel tabPage2 = null;
	private Checkbox cbShowY = null;
	private Checkbox cbShowX = null;
	private Checkbox cbShowYTicks = null;
	private Checkbox cbShowXTicks = null;
	private JPanel tabPage4 = null;
	private Checkbox cbAllowPan = null;
	private Checkbox cbShowScatter = null;
	private Checkbox cbMoveLegend = null;
	private JPanel tabPage5 = null;
	private Label label4 = null;
	private JComboBox<Object> cbHorAlign = null;
	private Label label5 = null;
	private JComboBox<Object> cbVertAlign = null;
	private JSlider tbMarginLeft = null;
	private JSlider tbMarginTop = null;
	private Label label6 = null;
	private Label label7 = null;
	private JButton btnResetZoom = null;
	private Checkbox cbZoom = null;
	private Checkbox cbImage = null;
}
