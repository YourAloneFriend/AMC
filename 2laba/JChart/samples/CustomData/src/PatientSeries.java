import java.util.EnumSet;
import java.util.List;

import com.mindfusion.charting.*;


class PatientSeries implements Series
{
	private List<Patient> values;

	public PatientSeries(List<Patient> patients)
	{
		values = patients;
	}

	@Override
	public double getValue(int index, int dimension)
	{
		if (dimension==0)
			return values.get(index).getAge();
		else
			return values.get(index).getWeight();
	}

	@Override
	public String getLabel(int index, LabelKinds kind)
	{
		if (kind == LabelKinds.InnerLabel)
			return values.get(index).getName();

		if (kind == LabelKinds.ToolTip)
			return values.get(index).getName() + "(" + values.get(index).getBMIndex() + ")";

		return null;
	}

	@Override
	public boolean isSorted(int dimension)
	{
		return false;
	}

	@Override
	public boolean isEmphasized(int index)
	{
		return false;
	}

	@Override
	public int getSize()
	{
		return values.size();
	}

	@Override
	public int getDimensions()
	{
		return 3;
	}

	@Override
	public String getTitle()
	{
		return values.get(0).getBMIndex();
	}

	@Override
	public EnumSet<LabelKinds> getSupportedLabels()
	{
		return EnumSet.of(LabelKinds.InnerLabel, LabelKinds.ToolTip);
	}

	@Override
	public void addDataChangedListener(DataChangedListener listener)
	{
	}

	@Override
	public void removeDataChangedListener(DataChangedListener listener)
	{
	}
}