import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.mindfusion.charting.*;

public class CustomBarSeries extends BarSeries
{
	List<Double> _values;
	List<Double> _stackedValues;
	List<String> _innerLabels;

	public CustomBarSeries(List<Double> values, List<String> innerLabels, List<String> topLabels)
	{
		super(values, innerLabels, topLabels);
		
		this._values = values;
		
		double sum = 0;
		
		_stackedValues = new ArrayList<Double>(values.size());
		
		for(double val : values)
		{
			sum += val;
			
			_stackedValues.add(sum);
		}
		
		this._innerLabels = innerLabels;
		
	}
	
	public double getValue(int index, int dimension)
	{
		if (dimension == 0)
			return _stackedValues.get(index);

		return _values.get(index);			
	}
	
	public String getLabel(int index, LabelKinds kind)
	{
		if (kind.equals(LabelKinds.InnerLabel))
			return _innerLabels.get(index);

		//else return the labels
		return axisLabels[index];
	}
	
	public int getDimensions() { return 2; }
	
	public EnumSet<LabelKinds> getSupportedLabels()
	{
		return EnumSet.of(
			LabelKinds.XAxisLabel, LabelKinds.InnerLabel);
	}
	
	final String[] axisLabels = {
			"Very Important", 
			"Somewhat\nImportant",
			"Slightly\nImportant", 
			"Not\nImportant" };
}
