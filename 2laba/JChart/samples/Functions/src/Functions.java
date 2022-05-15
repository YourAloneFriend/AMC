import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.beans.*;
import java.util.*;

import javax.swing.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.components.*;
import com.mindfusion.charting.swing.*;
import com.mindfusion.charting.components.gauges.*;
import com.mindfusion.charting.components.gauges.LengthType;
import com.mindfusion.charting.components.gauges.Text;
import com.mindfusion.drawing.*;


public class Functions extends JFrame
{
	private Range startRange = null;
	private Range endRange = null;
	private Pointer pointer = null;
	private MajorTickSettings majorSettings= null;
	private MiddleTickSettings middleSettings = null;
	private Text text = null;
	private OvalScale scale = null;
	private OvalGauge gauge = null;
	private SimplePanel gaugePanel = null;
	private GridPanel layoutPanel = null;
	private Dashboard board = null;
	
	private void initializeStartRange()
	{
		startRange = new Range();
		startRange.setAlignment(Alignment.InnerCenter);
		startRange.setEndWidth(new Length(0.0f, LengthType.Relative));
		startRange.setFill(new SolidBrush(Colors.SteelBlue));
		startRange.setMargin(new Margins(0.0f, 0.0f, 0.0f, 0.0f));
		startRange.setMaxValue(50.0d);
		startRange.setStartWidth(new Length(4.0f, LengthType.Relative));
		startRange.setStrokeInside(false);
		startRange.setStrokeOutside(false);
	}
	
	private void initializeEndRange()
	{
		endRange = new Range();
		endRange.setAlignment(Alignment.InnerCenter);
		endRange.setEndWidth(new Length(4.0f, LengthType.Relative));
		endRange.setFill(new SolidBrush(Colors.SteelBlue));
		endRange.setMargin(new Margins(0.0f, 0.0f, 0.0f, 0.0f));
		endRange.setMaxValue(100.0d);
		endRange.setMinValue(50.0d);
		endRange.setStartWidth(new Length(0.0f, LengthType.Relative));
		endRange.setStrokeInside(false);
		endRange.setStrokeOutside(false);
	}

	private void initializePointer()
	{
		pointer = new Pointer();
		pointer.setIsInteractive(true);
		pointer.setMargin(new Margins(0.0f, 0.0f, 0.4f, 0.925f));
		pointer.setName("ScalePointer");
		pointer.setPointerHeight(new Length(10.0f, LengthType.Relative));
		pointer.setShape(PointerShape.Rectangle);
		pointer.addPointerListener(new PointerListener()
		{
			@Override
			public void valueChanging(PropertyChangeEvent e)
			{
			}
			
			@Override
			public void valueChanged(PropertyChangeEvent e)
			{
				Double newValue = (Double)e.getNewValue();
				text.setContent(String.format("%.1f", newValue));
			}
		});
	}
	
	private void initializeMajorSettings()
	{
		majorSettings = new MajorTickSettings();
		majorSettings.setFill(new SolidBrush(Colors.Black));
		majorSettings.setFontFamily("Tahoma");
		majorSettings.setFontSize(new Length(11.0f, LengthType.Relative));
		majorSettings.setLabelAlignment(Alignment.InnerCenter);
		majorSettings.setLabelOffset(new Length(-10.0f, LengthType.Relative));
		majorSettings.setLabelRotation(LabelRotation.None);
		majorSettings.setStroke(new Pen(Colors.Black, 1.0f));
		majorSettings.setTickAlignment(Alignment.InnerCenter);
		majorSettings.setTickHeight(new Length(1.0f));
		majorSettings.setTickShape(TickShape.Rectangle);
		majorSettings.setTickWidth(new Length(3.0f, LengthType.Relative));
		majorSettings.setLabelFormat("%.1f");
	}

	private void initializeMiddleSettings()
	{
		middleSettings = new MiddleTickSettings();
		middleSettings.setShowLabels(false);
		middleSettings.setShowMaxValueTick(DisplayType.Never);
		middleSettings.setShowTicks(false);
	}
	
	private void initializeText()
	{
		text = new Text();
		text.setFontFamily("Tahoma");
		text.setContent("0.0");
		text.setFontSize(new Length(14.0f, LengthType.Relative));
		text.setFontStyle(FontStyle.BOLD);
		text.setAlignment(LayoutAlignment.Center);
		text.setLineAlignment(LayoutAlignment.Center);
		text.setMargin(new Margins(0.3f, 0.5f, 0.3f, 0.3f));
	}
	
	private void initializeScale()
	{
		scale = new OvalScale();
		scale.setEndAngle(352.0d);
		scale.setEndWidth(new Length(18.0f, LengthType.Relative));
		scale.setFill(new SolidBrush(Colors.Transparent));
		initializeMajorSettings();
		scale.setMajorTickSettings(majorSettings);
		initializeMiddleSettings();
		scale.setMiddleTickSettings(middleSettings);
		scale.setName("MainScale");
		initializePointer();
		scale.getPointers().clear();
		scale.getPointers().add(pointer);
		initializeStartRange();
		scale.getRanges().clear();
		scale.getRanges().add(startRange);
		initializeEndRange();
		scale.getRanges().add(endRange);
		initializeText();
		scale.getScaleChildren().clear();
		scale.getScaleChildren().add(text);
		Point2D.Float center = new Point2D.Float(0.5f, 0.8f);
		scale.setScaleRelativeCenter(center);
		scale.setScaleRelativeRadius(0.75d);
		scale.setStartAngle(188.0d);
		scale.setStartWidth(new Length(18.0f, LengthType.Relative));
		scale.setStroke(new Pen(new Color(80, 80, 80, 0), 1.0f));
		scale.setCustomFunction(new FunctionDelegate()
		{
			@Override
			public double invoke(double value, double argument)
			{
				float k = (float) gauge.getScales().get(0).getMinValue();
				float l = (float) gauge.getScales().get(0).getMaxValue();

				float a = (float) ((l - k) / Math.pow(l, 2) - Math.pow(k, 2));
				float b = (float) (k - a * Math.pow(k, 2));

				return a * value * value + b;
			}
		});
		
		scale.setReversedCustomFunction(new FunctionDelegate()
		{
			@Override
			public double invoke(double value, double argument)
			{
				float k = (float) gauge.getScales().get(0).getMinValue();
				float l = (float) gauge.getScales().get(0).getMaxValue();
				
				float a = (float) ((l - k) / Math.pow(l, 2) - Math.pow(k, 2));
				float b = (float) (k - a * Math.pow(k, 2));
				
				return Math.sqrt((value - b) / a);
			}
		});
	}
	
	private JPanel initializeRadioButtonsPanel()
	{
		JPanel radioButtonsPanel = new JPanel();
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(140, 70));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JRadioButton linear = new JRadioButton("Linear");
		linear.setSelected(true);
		linear.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	gauge.getScales().get(0).setFunctionType(FunctionType.Linear);
            }
        });
		
		JRadioButton logarithmic = new JRadioButton("Logarithmic");
		logarithmic.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	gauge.getScales().get(0).setFunctionType(FunctionType.Logarithmic);
            }
        });

		JRadioButton custom = new JRadioButton("Custom (Quadratic)");
		custom.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	gauge.getScales().get(0).setFunctionType(FunctionType.Custom);
            }
        });

		ButtonGroup group = new ButtonGroup();
		group.add(linear);
		group.add(logarithmic);
		group.add(custom);
		panel.add(linear);
		panel.add(logarithmic);
		panel.add(custom);
		radioButtonsPanel.add(panel, BorderLayout.EAST);
		
		return radioButtonsPanel;
	}
	
	public void initializeGridPanel()
	{
		layoutPanel = new GridPanel();
		layoutPanel.getChildren().add(gaugePanel);
	}
	
	public void initializeBoard()
	{
		board = new Dashboard();
		board.setEnabled(true);
	}
	
	public void initializeGauge()
	{
		gauge = new OvalGauge();
		gauge.setName("Gauge");
		gauge.setStyle(OvalGaugeStyle.SemicircleN);
		gauge.setWidth(356.0d);
		gauge.setHeight(256.0d);
		initializeScale();
		gauge.getScales().clear();
		gauge.getScales().add(scale);
		gauge.addBaseGaugeListener(new BaseGaugeListener()
		{
			void inflate(Rectangle2D rect, double dx, double dy)
			{
				rect.setFrame(rect.getX() - dx, rect.getY() - dy, rect.getWidth() + 2 * dx, rect.getHeight() + 2 * dy);
			}
			
			public void prepaintBackground(PrepaintEvent e)
			{
				e.setCancelDefaultPainting(true);
				
				float widthExtent = 0.0f;
				if (gauge.getWidth() > gauge.getHeight())
				{
					widthExtent = (float) ((gauge.getWidth() - gauge.getHeight()) / (gauge.getHeight() * 2));
				}
				
				String figurePath = new String(
					"M0.5,0 C0.75,0 1,0.3 1,0.75 C1,0.8 1,0.81 1,0.8 " +
					"L0.57,0.9 C0.57,0.95 0.535,1 0.5,1 C0.465,1 0.43,0.95 " +
					"0.43,0.9 L0,0.8 C0,0.3 0.25,0 0.5,0 Z");
				
				//Background base rectangle
				
				RoundRectangle rect = new RoundRectangle();
				rect.setRoundness(10.0d);
				rect.setMargin(new Margins(-widthExtent, 0.05f, -widthExtent + 0.01f, 0.05f));
				rect.setFill(e.createLinearGradient(45.0f, 
						new float[]{0.2f, 0.8f}, 
						new Color[]{
								new Color(224, 233, 233 /*0xA1, 0xAC, 0xBE*/),
								new Color(102, 154, 204 /*0x71, 0x7C, 0x9E*/)
								}));
				
				rect.setStroke(new Pen(new Color(0x5C, 0x69, 0x7A), 0.0f));
				
				Dimension size = new Dimension();
				size.setSize(e.getElement().getActualWidth(), e.getElement().getActualHeight());
				
				e.paintVisualElement(rect, size);
				
				//Shape of the gauge
				//e.getGraphics().setTransform(AffineTransform.getScaleInstance(0.5, 0.5));
				PathFigure baseShape = new PathFigure(figurePath);
				baseShape.setMargin(new Margins(-widthExtent + 0.035f, 0.095f, -widthExtent + 0.035f, 0.095f));
				baseShape.setFill(
					e.createLinearGradient(45.0f, 
						new float[]{0.0f, 0.2f, 0.8f, 1.0f}, 
						new Color[]{
								new Color(0x90, 0x90, 0x90, 0xFF),
								new Color(0x90, 0x90, 0x90, 0xFF),
								new Color(0x30, 0x30, 0x30, 0xFF),
								new Color(0x30, 0x30, 0x30, 0xFF)
								}));
				e.paintVisualElement(baseShape, size);
				
				baseShape = new PathFigure(figurePath);
				baseShape.setMargin(new Margins(-widthExtent + 0.045f, 0.105f, -widthExtent + 0.045f, 0.105f));
				baseShape.setFill(
					e.createLinearGradient(45.0f, 
						new float[]{0.0f, 0.2f, 0.8f, 1.0f}, 
						new Color[]{
								new Color(0x30, 0x30, 0x30, 0xFF),
								new Color(0x30, 0x30, 0x30, 0xFF),
								new Color(0x90, 0x90, 0x90, 0xFF),
								new Color(0x90, 0x90, 0x90, 0xFF)
								}));
				e.paintVisualElement(baseShape, size);
				
				baseShape = new PathFigure(figurePath);
				baseShape.setMargin(new Margins(-widthExtent + 0.05f, 0.11f, -widthExtent + 0.05f, 0.11f));
				baseShape.setFill(
					e.createRadialGradient(new Point2D.Float(0.4f, 0.4f), 
						new float[]{0.15f, 0.7f}, 
						new Color[]{
								new Color(0xEF, 0xF8, 0xFF),
								new Color(0xB0, 0xC4, 0xDE)
						}));
				e.paintVisualElement(baseShape, size);
				
				//Background layer
				
				baseShape.setFill(
					e.createRadialGradient(new Point2D.Float(0.5f, 0.85f), 
						new float[]{0.7f, 1.0f}, 
						new Color[]{
								new Color(0xFF, 0xFF, 0xFF, 0x32),
								new Color(0xFF, 0xFF, 0xFF, 0x00)//Transparent
						}));
				e.paintVisualElement(baseShape, size);
		
				//Text scale element background
				
				Dimension rs = new Dimension();
				rs.setSize(e.getElement().getActualWidth(), e.getElement().getActualHeight());
				
				Rectangle2D.Float bounds = new Rectangle2D.Float(
					(float)(0.4f * rs.getWidth()), 
					(float)(0.5f * rs.getHeight()), 
					(float)(0.2f * rs.getWidth()),
					(float)(0.15f * rs.getHeight()));
				
				GeneralPath textPath = new GeneralPath(bounds);
			}
			
			public void prepaintForeground(PrepaintEvent e)
			{
				e.setCancelDefaultPainting(true);
				
				float widthExtent = 0.0f;
				if (gauge.getWidth() > gauge.getHeight())
				{
					widthExtent = (float) ((gauge.getWidth()- gauge.getHeight()) / (gauge.getHeight() * 2));
				}
				
				//Reflection 1
				
				PathFigure figure1 = new PathFigure(
					"M0.51,0 C0.25,0 0.03,0.31 0.03,0.63 " +
		            "C0.03,0.69 0.09,0.71 0.12,0.71 " + 
		            "C0.16,0.71 0.21,0.68 0.21,0.64 " + 
		            "C0.21,0.59 0.24,0.47 0.32,0.41 " +
		            "C0.44,0.31 0.64,0.57 0.64,0.05 " + 
		            "C0.64,0.03 0.55,0 0.51,0 Z");
				figure1.setFill(
					e.createLinearGradient(90.0f, 
						new float[]{0.05f, 0.3f, 0.7f, 0.95f}, 
						new Color[]{
								new Color(0xFF, 0xFF, 0xFF, 0x00),//Transparent
								new Color(0xFF, 0xFF, 0xFF, 0x60),
								new Color(0xFF, 0xFF, 0xFF, 0x60),
								new Color(0xFF, 0xFF, 0xFF, 0x00)//Transparent
						}));
				figure1.setMargin(new Margins(-widthExtent + 0.05f, 0.11f, -widthExtent + 0.05f, 0.11f));
				
				Dimension size = new Dimension();
				size.setSize(e.getElement().getActualWidth(), e.getElement().getActualHeight());
				
				e.paintVisualElement(figure1, size);
				
				//Reflection 2
	            PathFigure figure2 = new PathFigure(
	            	"M0.8,0.32 L0.71,0.34 C0.795,0.37 0.89,0.44 0.95,0.54 " +
	            	"C0.95,0.47 0.9,0.36 0.8,0.32 Z");
	            
	            
	            figure2.setFill(
	            	e.createLinearGradient(90.0f, 
	            		new float[]{0.05f, 0.3f, 0.7f, 0.95f}, 
	            		new Color[]{
	            				new Color(0xFF, 0xFF, 0xFF, 0x00),//Transparent
	            				new Color(0xFF, 0xFF, 0xFF, 0x60),
								new Color(0xFF, 0xFF, 0xFF, 0x60),
	            				new Color(0xFF, 0xFF, 0xFF, 0x00)//Transparent
	            		}));
	            figure2.setMargin(new Margins(-widthExtent + 0.05f, 0, -widthExtent + 0.05f, 0));
	            e.paintVisualElement(figure2, size);
			}

			@Override
			public void paintBackground(CustomPaintEvent e)
			{
			}

			@Override
			public void paintForeground(CustomPaintEvent e)
			{
			}

			@Override
			public void prepaintScale(PrepaintEvent e)
			{
			}

			@Override
			public void paintScale(CustomPaintEvent e)
			{
			}

			@Override
			public void prepaintPointer(PrepaintEvent e)
			{
				if (e.getElement().getName() == "ScalePointer")
				{
					e.setCancelDefaultPainting(true);
					
					Dimension size = new Dimension();
					size.setSize(e.getElement().getActualWidth(), e.getElement().getActualHeight());
					Rectangle2D bounds =  Utils.toRectangleF(size);
					
					inflate(bounds, 0, -0.4f * bounds.getHeight());
					
					GradientBrush brush = e.createLinearGradient(90.0d, 
							new float[]{0.0f, 0.25f, 0.75f, 1.0f}, 
							new Color[]{
									new Color(0x81, 0x81, 0x81),
									new Color(0x60, 0x60, 0x60),
									new Color(0x32, 0x32, 0x32),
									new Color(0x0, 0x0, 0x0)
							});
					e.getGraphics().setStroke(new Stroke()
					{
						@Override
						public Shape createStrokedShape(Shape p)
						{
							return bounds;
						}
					});
					brush.applyTo(e.getGraphics(), bounds);
					e.getGraphics().draw(bounds);
					e.getGraphics().fill(bounds);
					
					//Base of the pointer
					/*
					Rectangle2D bounds1 =  Utils.toRectangleF(size);
					
					GeneralPath p = new GeneralPath(bounds1);
					
					GradientBrush b = e.createRadialGradient(new Point2D.Float(0.25f, 0.25f), 
							new float[]{1.0f, 0.6f}, 
							new Color[]{
									new Color(0xFFFFFF),
									new Color(0x000000)
							});
					
					b.applyTo(e.getGraphics(), bounds1);
					e.getGraphics().draw(bounds1);
					e.getGraphics().fill(bounds1);
					*/
				}
				
			}

			@Override
			public void paintPointer(CustomPaintEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void prepaintTick(PrepaintEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void paintTick(CustomPaintEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void invalidated(EventObject e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		gauge.setHorizontalAlignment(LayoutAlignment.Center);
		gauge.setVerticalAlignment(LayoutAlignment.Center);
		
		gaugePanel = new SimplePanel();
		gaugePanel.setGridRow(0);
		gaugePanel.setGridColumn(0);
		gaugePanel.getChildren().add(gauge);
	}
	
	public void run()
	{
		initializeGauge();
		initializeGridPanel();
		initializeBoard();
		
		board.getRootPanel().getChildren().add(layoutPanel);
		board.invalidate();
		board.setBounds(this.getBounds());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(initializeRadioButtonsPanel(), BorderLayout.SOUTH);
		panel.add(board, BorderLayout.CENTER);
		
		this.getContentPane().add(panel);
		board.repaint();
		this.setVisible(true);
	}
	
	public Functions()
	{
		setTitle("MindFusion.Charting sample: Functions");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args)
	{
		Functions functions = new Functions();
		functions.run();
	}
}
