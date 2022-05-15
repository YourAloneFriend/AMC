import java.awt.*;
import java.awt.geom.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.components.LayoutAlignment;
import com.mindfusion.charting.components.gauges.*;
import com.mindfusion.charting.components.gauges.Text;
import com.mindfusion.drawing.*;


public class RectangleHalfGauge extends OvalGauge
{
	private final Pen transparentPen = new Pen(Brushes.Transparent, 0);
	
	private final int baseSize=245;
	
	private OvalScale scale;
	
	public Pointer speedPointer;
	
	private boolean showLabels;
	private String textValue;
	private Range[] ranges;
	
	public void scale(float factor)
	{
		setWidth(100+baseSize*factor);
		setHeight(baseSize*factor);
	}
	
	public void setShowLabels(boolean showLabels) {
		this.showLabels = showLabels;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public void setRanges(Range[] ranges) {
		this.ranges = ranges;
	}

	public void constructScale()
	{
		
		this.setStyle(OvalGaugeStyle.SemicircleN);
		
		setWidth(100+baseSize);
		setHeight(baseSize);
		getScales().clear();
		
		scale = new OvalScale();
		
		Text text= new Text();
		text.setContent(textValue);
		text.setFontFamily("Vardana");
		text.setFontSize(new Length(20, LengthType.Relative));
		text.setFontStyle(FontStyle.BOLD);
		text.setAlignment(LayoutAlignment.Center);
		text.setLineAlignment(LayoutAlignment.Center);
		text.setForeground(Color.white);
		//text.setMargin(new Thickness(0.3f, 0.3f, 0.3f, 0.3f));
		
		
		scale.beginInit();
		scale.setName("scale");
		scale.setFill(Brushes.Transparent);
		scale.setStroke(transparentPen);
		scale.setMaxValue(96);
//		CustomInterval mphInterval = new CustomInterval();
//		mphInterval.setMinValue(48);
//		mphInterval.setFill(Brushes.Red);
		
		for(int i=0;i<ranges.length;i++)
		{
			scale.getRanges().add(ranges[i]);
		}

	/*	
		*/
		Range mphRange=new Range();
		mphRange.setMinValue(0);
		mphRange.setMaxValue(96);
		mphRange.setStartWidth(new Length(7,LengthType.Relative));
		mphRange.setEndWidth(new Length(7,LengthType.Relative));
		mphRange.setFill(Brushes.White);
		//mphRange.setVisibility(Visibility.Collapsed);
		mphRange.setAlignment(Alignment.CenterOutside);
		scale.getRanges().add(mphRange);
		
		
		scale.setStartAngle(16+180);
		scale.setEndAngle(164+180);
		
		scale.setMargin(new Margins(0.075));
		//scale.getMajorTickSettings().getCustomIntervals().add(mphInterval);	
		scale.getMajorTickSettings().setFill(Brushes.White);
		scale.getMajorTickSettings().setFontFamily("Verdana");
		scale.getMajorTickSettings().setFontSize(new Length(10, LengthType.Relative));
		scale.getMajorTickSettings().setLabelAlignment(Alignment.InnerCenter);
		scale.getMajorTickSettings().setLabelForeground(Colors.White);
		scale.getMajorTickSettings().setLabelOffset(new Length(10, LengthType.Relative));
		scale.getMajorTickSettings().setLabelRotation(LabelRotation.Auto);
		scale.getMajorTickSettings().setShowLabels(showLabels);
		scale.getMajorTickSettings().setStep(24);
		scale.getMajorTickSettings().setStroke(transparentPen);
		scale.getMajorTickSettings().setTickAlignment(Alignment.TrueCenter);
		scale.getMajorTickSettings().setTickHeight(new Length(7, LengthType.Relative));
		scale.getMajorTickSettings().setTickWidth(new Length(10, LengthType.Relative));
		scale.getMajorTickSettings().setTickShape(TickShape.Rectangle);

		scale.getMiddleTickSettings().setShowTicks(false);
		scale.getMiddleTickSettings().setShowLabels(false);
		
		speedPointer = new Pointer();
		speedPointer.setName("SpeedPointer");
		speedPointer.setPointerHeight(new Length(20, LengthType.Relative));
		speedPointer.setPointerWidth(new Length(100, LengthType.Relative));
		speedPointer.setShape(PointerShape.Needle);
		
		scale.getPointers().add(speedPointer);
		scale.endInit();
		
		scale.getScaleChildren().add(text);
		
		getScales().add(scale);
		
		
		addBaseGaugeListener(new BaseGaugeAdapter()
		{
			void inflate(Rectangle2D rect, double dx, double dy)
			{
				rect.setFrame(rect.getX() - dx, rect.getY() - dy, rect.getWidth() + 2 * dx, rect.getHeight() + 2 * dy);
			}
			
			@Override
			public void prepaintBackground(PrepaintEvent e)
			{
				e.setCancelDefaultPainting(true);
				double width = e.getElement().getActualWidth();
				double height = e.getElement().getActualHeight();
				Rectangle2D bounds = new Rectangle2D.Double(0,60, width, height-110);

				// ---------------------- Ellipse #1 ----------------------
				GradientBrush fill1 = new GradientBrush(new float[] { 0, 0.2f, 0.8f, 1 },
					new Color[] { new Color(0x90, 0x90, 0x90), new Color(0x90, 0x90, 0x90),
						new Color(0x30, 0x30, 0x30), new Color(0x30, 0x30, 0x30) }, 45);

				fill1.applyTo(e.getGraphics(), bounds);
				e.getGraphics().fill(new Rectangle2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));

				// Apply margin of 0.015
				inflate(bounds, -0.015 * width, -0.015 * height);

				// ---------------------- Ellipse #2 ----------------------
				GradientBrush fill2 = new GradientBrush(new float[] { 0, 0.2f, 0.8f, 1 },
					new Color[] {
						new Color(0x30, 0x30, 0x30), new Color(0x30, 0x30, 0x30),
						new Color(0x90, 0x90, 0x90), new Color(0x90, 0x90, 0x90) }, 45);

				fill2.applyTo(e.getGraphics(), bounds);
				e.getGraphics().fill(new Rectangle2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));

				// Apply margin of 0.015
				inflate(bounds, -0.015 * width, -0.015 * height);

				// ---------------------- Ellipse #3 ----------------------
				RadialGradientBrush fill3 = new RadialGradientBrush();
				fill3.setX(0.5);
				fill3.setY(0.9);
				fill3.setFractions(new float[] { 0, 1 });
				fill3.setColors(new Color[] { new Color(0x20, 0x20, 0x20), new Color(0xBB, 0xBB, 0xBB) });

				fill3.applyTo(e.getGraphics(), bounds);
				e.getGraphics().fill(new Rectangle2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));
			
			}
			
			public void prepaintForeground(PrepaintEvent e)
			{
				e.setCancelDefaultPainting(true);

			}
		});
		
	}
	
	public RectangleHalfGauge(int gridX,int gridY)
	{
		setGridColumn(gridX);
		setGridRow(gridY);
	}
}