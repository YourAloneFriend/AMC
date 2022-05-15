import java.awt.*;
import java.beans.*;

import com.mindfusion.charting.Margins;
import com.mindfusion.charting.components.gauges.*;
import com.mindfusion.drawing.*;


public class SpeedometerGauge extends OvalGauge
{
	private final Pen transparentPen = new Pen(Brushes.Transparent, 0);
	private final Pen whitePen = new Pen(Brushes.White, 0);
	
	private final int baseSize=250;
	
	private OvalScale kphScale;
	private OvalScale fuelScale;
	private OvalScale outerTickScale;
	private OvalScale mphScale;
	
	public Pointer gasPointer;
	public Pointer speedPointer;
	
	public void scale(float factor)
	{

		setWidth(100+baseSize*factor);
		setHeight(baseSize*factor);
	}
	private void construct_fuelRanges()
	{
		Range range1 = new Range();
		range1.setStartWidth(new Length(4, LengthType.Relative));
		range1.setEndWidth(new Length(4, LengthType.Relative));
		range1.setOffset(new Length(6, LengthType.Relative));
		range1.setMaxValue(15);
		range1.setFill(Brushes.Red);
		range1.setStroke(transparentPen);
		fuelScale.getRanges().add(range1);
		Range range2 = new Range();
		range2.setStartWidth(new Length(4, LengthType.Relative));
		range2.setEndWidth(new Length(4, LengthType.Relative));
		range2.setOffset(new Length(6, LengthType.Relative));
		range2.setMinValue(15);
		range2.setMaxValue(20);
		range2.setFill(Brushes.Yellow);
		range2.setStroke(transparentPen);
		fuelScale.getRanges().add(range2);
	}
	
	private void construct_fuelManagement()
	{
		CustomInterval state = new CustomInterval();
		state.setMinValue(0);
		state.setMaxValue(20);
		state.setFill(Brushes.Red);
		state.setStroke(whitePen);
		state.setForeground(Colors.Red);
		
		Indicator fuelIndicator = new Indicator();
		fuelIndicator.setMargin(new Margins(0.47, 0.85, 0.47, 0.09));
		fuelIndicator.getStates().add(state);
		
		gasPointer = new Pointer();
		gasPointer.setName("GasPointer");
		gasPointer.setIsInteractive(true);
		gasPointer.setPointerHeight(new Length(20, LengthType.Relative));
		gasPointer.setPointerWidth(new Length(100, LengthType.Relative));
		gasPointer.setShape(PointerShape.Needle2);
		gasPointer.addPointerListener(new PointerAdapter()
		{
			@Override
			public void valueChanged(PropertyChangeEvent e)
			{
				fuelIndicator.setValue((Double)e.getNewValue());
			}
		});

		gasPointer.setValue(35);
		fuelIndicator.setValue(35);
		
		fuelScale.getScaleChildren().add(fuelIndicator);
		fuelScale.getPointers().add(gasPointer);
	}
	
	private void construct_kphScale()
	{
		kphScale.setName("KphScale");
		kphScale.beginInit();
		kphScale.getMajorTickSettings().setFill(Brushes.White);
		kphScale.getMajorTickSettings().setFontFamily("Verdana");
		kphScale.getMajorTickSettings().setFontSize(new Length(9, LengthType.Relative));
		kphScale.getMajorTickSettings().setLabelAlignment(Alignment.InnerCenter);
		kphScale.getMajorTickSettings().setLabelForeground(Colors.White);
		kphScale.getMajorTickSettings().setLabelOffset(new Length(8, LengthType.Relative));
		kphScale.getMajorTickSettings().setLabelRotation(LabelRotation.Auto);
		kphScale.getMajorTickSettings().setShowMaxValueTick(DisplayType.Never);
		kphScale.getMajorTickSettings().setStep(20);
		kphScale.getMajorTickSettings().setStroke(transparentPen);
		kphScale.getMajorTickSettings().setTickAlignment(Alignment.InnerCenter);
		kphScale.getMajorTickSettings().setTickHeight(new Length(4, LengthType.Relative));
		kphScale.getMajorTickSettings().setTickWidth(new Length(4, LengthType.Relative));
		kphScale.getMajorTickSettings().setTickShape(TickShape.Rectangle);
		kphScale.setMaxValue(305.77);
		kphScale.getMiddleTickSettings().setShowLabels(false);
		kphScale.getMiddleTickSettings().setShowTicks(false);
		kphScale.setFill(Brushes.Transparent);
		kphScale.setMargin(new Margins(0.15, 0.15, 0.15, 0.15));
		kphScale.setStroke(transparentPen);
		kphScale.endInit();
	}
	
	private void construct_fuelScale()
	{
		fuelScale.beginInit();
		fuelScale.setName("FuelScale");
		fuelScale.setEndAngle(60.0);
		fuelScale.setStartWidth(new Length(0, LengthType.Relative));
		fuelScale.setEndWidth(new Length(0, LengthType.Relative));
		fuelScale.getMajorTickSettings().setCount(1);
		fuelScale.getMajorTickSettings().setFill(Brushes.White);
		fuelScale.getMajorTickSettings().setFontFamily("Verdana");
		fuelScale.getMajorTickSettings().setLabelAlignment(Alignment.InnerCenter);
		fuelScale.getMajorTickSettings().setLabelForeground(Colors.White);
		fuelScale.getMajorTickSettings().setLabelOffset(new Length(18, LengthType.Relative));
		fuelScale.getMajorTickSettings().setLabelRotation(LabelRotation.None);
		fuelScale.getMajorTickSettings().setStroke(transparentPen);
		fuelScale.getMajorTickSettings().setTickAlignment(Alignment.InnerInside);
		fuelScale.getMajorTickSettings().setTickHeight(new Length(3, LengthType.Relative));
		fuelScale.getMajorTickSettings().setTickShape(TickShape.Rectangle);
		fuelScale.getMiddleTickSettings().setCount(3);
		fuelScale.getMiddleTickSettings().setFill(Brushes.White);
		fuelScale.getMiddleTickSettings().setShowLabels(false);
		fuelScale.getMiddleTickSettings().setStroke(transparentPen);
		fuelScale.getMiddleTickSettings().setTickAlignment(Alignment.InnerInside);
		fuelScale.getMiddleTickSettings().setTickHeight(new Length(3, LengthType.Relative));
		fuelScale.getMiddleTickSettings().setTickWidth(new Length(8, LengthType.Relative));
		fuelScale.getMiddleTickSettings().setTickShape(TickShape.Rectangle);
		fuelScale.getMinorTickSettings().setCount(6);
		fuelScale.getMinorTickSettings().setFill(Brushes.White);
		fuelScale.getMinorTickSettings().setShowTicks(true);
		fuelScale.getMinorTickSettings().setStroke(transparentPen);
		fuelScale.getMinorTickSettings().setTickAlignment(Alignment.InnerInside);
		fuelScale.getMinorTickSettings().setTickHeight(new Length(1.5, LengthType.Relative));
		fuelScale.getMinorTickSettings().setTickWidth(new Length(5, LengthType.Relative));
		fuelScale.getMinorTickSettings().setTickShape(TickShape.Rectangle);
		fuelScale.setMargin(new Margins(0.25, 0.4, 0.25, 0.1));
		fuelScale.setStroke(whitePen);
		
		fuelScale.addBaseScaleListener(new BaseScaleAdapter()
		{
			@Override
			public void queryLabelValue(QueryLabelValueEvent e)
			{
				if (e.getSettings().getTickType() == TickType.Major)
				{
					if (e.getCalculatedLabelValue() == 0)
						e.setNewValue("E");
					if (e.getCalculatedLabelValue() == 100)
						e.setNewValue("F");
				}
			}
		});
		fuelScale.endInit();
	
	}
	
	private void construct_outerTickScale()
	{
		outerTickScale.beginInit();
		outerTickScale.setName("OuterTickScale");
		outerTickScale.setFill(Brushes.Transparent);
		outerTickScale.setStroke(transparentPen);
		outerTickScale.setMaxValue(190);
		outerTickScale.setMargin(new Margins(0.075));
		outerTickScale.getMajorTickSettings().setFill(new SolidBrush(new Color(0xCC, 0xCC, 0xCC)));
		outerTickScale.getMajorTickSettings().setShowLabels(false);
		outerTickScale.getMajorTickSettings().setStep(20);
		outerTickScale.getMajorTickSettings().setStroke(transparentPen);
		outerTickScale.getMajorTickSettings().setTickAlignment(Alignment.OuterOutside);
		outerTickScale.getMajorTickSettings().setTickHeight(new Length(3, LengthType.Relative));
		outerTickScale.getMajorTickSettings().setTickWidth(new Length(5, LengthType.Relative));
		outerTickScale.getMajorTickSettings().setTickOffset(new Length(-0.5, LengthType.Relative));
		outerTickScale.getMajorTickSettings().setTickShape(TickShape.Rectangle);
		outerTickScale.getMiddleTickSettings().setFill(new SolidBrush(new Color(0x99, 0x99, 0x99)));
		outerTickScale.getMiddleTickSettings().setShowLabels(false);
		outerTickScale.getMiddleTickSettings().setStroke(transparentPen);
		outerTickScale.getMiddleTickSettings().setTickAlignment(Alignment.OuterOutside);
		outerTickScale.getMiddleTickSettings().setTickHeight(new Length(3, LengthType.Relative));
		outerTickScale.getMiddleTickSettings().setTickWidth(new Length(5, LengthType.Relative));
		outerTickScale.getMiddleTickSettings().setTickOffset(new Length(-0.5, LengthType.Relative));
		outerTickScale.getMiddleTickSettings().setTickShape(TickShape.Rectangle);
		outerTickScale.endInit();
	}
	
	private void construct_mphScale()
	{
		mphScale.beginInit();
		mphScale.setName("MphScale");
		mphScale.setFill(Brushes.Transparent);
		mphScale.setStroke(transparentPen);
		mphScale.setMaxValue(190);
		CustomInterval mphInterval = new CustomInterval();
		mphInterval.setMinValue(160);
		mphInterval.setFill(Brushes.Red);
		mphScale.setMargin(new Margins(0.075));
		mphScale.getMajorTickSettings().getCustomIntervals().add(mphInterval);
		mphScale.getMajorTickSettings().setFill(Brushes.White);
		mphScale.getMajorTickSettings().setFontFamily("Verdana");
		mphScale.getMajorTickSettings().setFontSize(new Length(9, LengthType.Relative));
		mphScale.getMajorTickSettings().setLabelAlignment(Alignment.InnerCenter);
		mphScale.getMajorTickSettings().setLabelForeground(Colors.White);
		mphScale.getMajorTickSettings().setLabelOffset(new Length(4, LengthType.Relative));
		mphScale.getMajorTickSettings().setLabelRotation(LabelRotation.Auto);
		mphScale.getMajorTickSettings().setStep(20);
		mphScale.getMajorTickSettings().setStroke(transparentPen);
		mphScale.getMajorTickSettings().setTickAlignment(Alignment.OuterInside);
		mphScale.getMajorTickSettings().setTickHeight(new Length(2, LengthType.Relative));
		mphScale.getMajorTickSettings().setTickShape(TickShape.Rectangle);
		mphScale.getMiddleTickSettings().getCustomIntervals().add(mphInterval);
		mphScale.getMiddleTickSettings().setFill(Brushes.White);
		mphScale.getMiddleTickSettings().setStroke(transparentPen);
		mphScale.getMiddleTickSettings().setShowLabels(false);
		mphScale.getMiddleTickSettings().setTickAlignment(Alignment.OuterInside);
		mphScale.getMiddleTickSettings().setTickHeight(new Length(2, LengthType.Relative));
		mphScale.getMiddleTickSettings().setTickShape(TickShape.Rectangle);
		mphScale.getMiddleTickSettings().setTickWidth(new Length(8, LengthType.Relative));
		mphScale.getMinorTickSettings().setCount(5);
		mphScale.getMinorTickSettings().getCustomIntervals().add(mphInterval);
		mphScale.getMinorTickSettings().setFill(Brushes.White);
		mphScale.getMinorTickSettings().setShowTicks(true);
		mphScale.getMinorTickSettings().setStroke(transparentPen);
		mphScale.getMinorTickSettings().setTickAlignment(Alignment.OuterInside);
		mphScale.getMinorTickSettings().setTickHeight(new Length(1, LengthType.Relative));
		mphScale.getMinorTickSettings().setTickShape(TickShape.Rectangle);
		mphScale.getMinorTickSettings().setTickWidth(new Length(6, LengthType.Relative));
		
		speedPointer = new Pointer();
		speedPointer.setName("SpeedPointer");
		speedPointer.setPointerHeight(new Length(20, LengthType.Relative));
		speedPointer.setPointerWidth(new Length(100, LengthType.Relative));
		speedPointer.setShape(PointerShape.Needle);
		
		mphScale.getPointers().add(speedPointer);
		mphScale.endInit();
	}
	
	public SpeedometerGauge(int gridX,int gridY)
	{
		setGridColumn(gridX);
		setGridRow(gridY);
		
		setWidth(100+baseSize);
		setHeight(baseSize);
		getScales().clear();
		
		//System.out.println(this.getYInParent());
		
		kphScale = new OvalScale();	
		construct_kphScale();
		
		getScales().add(kphScale);
		
		fuelScale = new OvalScale();
		construct_fuelScale();		
		construct_fuelManagement();
		construct_fuelRanges();
		
		getScales().add(fuelScale);
		
		outerTickScale = new OvalScale();
		construct_outerTickScale();
		
		getScales().add(outerTickScale);

		mphScale = new OvalScale();
		construct_mphScale();
		
		getScales().add(mphScale);
	}
}