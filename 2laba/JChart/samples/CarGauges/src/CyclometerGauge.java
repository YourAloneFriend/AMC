import com.mindfusion.charting.*;
import com.mindfusion.charting.components.gauges.*;
import com.mindfusion.drawing.*;


public class CyclometerGauge extends OvalGauge
{
	private final Pen transparentPen = new Pen(Brushes.Transparent, 0);
	private final int baseSize=270;
	
	private OvalScale mphScale;
	
	public Pointer speedPointer;
	
	private void construct_mphScale()
	{
		mphScale.beginInit();
		mphScale.setName("MphScale");
		mphScale.setFill(Brushes.Transparent);
		mphScale.setStroke(transparentPen);
		mphScale.setMaxValue(90);
		CustomInterval mphInterval = new CustomInterval();
		mphInterval.setMinValue(60);
		mphInterval.setFill(Brushes.Red);
		
		Range mphRange=new Range();
		mphRange.setMinValue(60);
		mphRange.setMaxValue(90);
		mphRange.setStartWidth(new Length(1,LengthType.Relative));
		mphRange.setEndWidth(new Length(10,LengthType.Relative));
		mphRange.setFill(Brushes.DarkRed);
		//mphRange.setVisibility(Visibility.Collapsed);
		mphRange.setAlignment(Alignment.TrueCenter);
		mphScale.getRanges().add(mphRange);
		
		mphScale.setMargin(new Margins(0.075));
		mphScale.getMajorTickSettings().getCustomIntervals().add(mphInterval);	
		mphScale.getMajorTickSettings().setFill(Brushes.White);
		mphScale.getMajorTickSettings().setFontFamily("Verdana");
		mphScale.getMajorTickSettings().setFontSize(new Length(9, LengthType.Relative));
		mphScale.getMajorTickSettings().setLabelAlignment(Alignment.InnerCenter);
		mphScale.getMajorTickSettings().setLabelForeground(Colors.White);
		mphScale.getMajorTickSettings().setLabelOffset(new Length(4, LengthType.Relative));
		mphScale.getMajorTickSettings().setLabelRotation(LabelRotation.Auto);
		mphScale.getMajorTickSettings().setStep(10);
		mphScale.getMajorTickSettings().setStroke(transparentPen);
		mphScale.getMajorTickSettings().setTickAlignment(Alignment.TrueCenter);
		mphScale.getMajorTickSettings().setTickHeight(new Length(2, LengthType.Relative));
		mphScale.getMajorTickSettings().setTickShape(TickShape.Rectangle);
		
		mphScale.getMiddleTickSettings().setCount(5);
		mphScale.getMiddleTickSettings().getCustomIntervals().add(mphInterval);
		mphScale.getMiddleTickSettings().setFill(Brushes.White);
		mphScale.getMiddleTickSettings().setStroke(transparentPen);
		mphScale.getMiddleTickSettings().setShowLabels(false);
		mphScale.getMiddleTickSettings().setTickAlignment(Alignment.TrueCenter);
		mphScale.getMiddleTickSettings().setTickHeight(new Length(2, LengthType.Relative));
		mphScale.getMiddleTickSettings().setTickShape(TickShape.Ellipse);
		mphScale.getMiddleTickSettings().setTickWidth(new Length(2, LengthType.Relative));
		
		speedPointer = new Pointer();
		speedPointer.setName("SpeedPointer");
		speedPointer.setPointerHeight(new Length(20, LengthType.Relative));
		speedPointer.setPointerWidth(new Length(100, LengthType.Relative));
		speedPointer.setShape(PointerShape.Needle);
		
		mphScale.getPointers().add(speedPointer);
		mphScale.endInit();
	}
	
	public CyclometerGauge(int gridX,int gridY)
	{
		setGridColumn(gridX);
		setGridRow(gridY);
		
		setWidth(100+baseSize);
		setHeight(baseSize);
		getScales().clear();
		
		mphScale = new OvalScale();
		construct_mphScale();
		
		getScales().add(mphScale);
	}
	
	public void scale(float factor)
	{
		setWidth(100+baseSize*factor);
		setHeight(baseSize*factor);
	}
}
