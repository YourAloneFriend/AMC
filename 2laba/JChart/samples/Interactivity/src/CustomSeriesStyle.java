import com.mindfusion.charting.*;
import com.mindfusion.common.ObservableList;
import com.mindfusion.drawing.Brush;
import com.mindfusion.drawing.DashStyle;
import com.mindfusion.drawing.SolidBrush;

import java.awt.Color;


public class CustomSeriesStyle implements SeriesStyle {

	private ObservableList<Series> series;
	
	public CustomSeriesStyle(ObservableList<Series> series)
	{
		this.series = series;		
	}

	@Override
	public Brush fill(int seriesIndex, int dataIndex)
	{
		Series serie = series.get(seriesIndex);
		
		double number = serie.getValue(dataIndex, 1);
		if (number > 35)
			return new SolidBrush(Color.GREEN);

		return new SolidBrush(Color.RED);		
	}

	@Override
	public Brush stroke(int seriesIndex, int dataIndex)
	{
		return new SolidBrush(Color.GRAY);
	}

	@Override
	public DashStyle strokeDashStyle(int seriesIndex, int dataIndex)
	{
		return null;
	}

	@Override
	public Double strokeThickness(int seriesIndex, int dataIndex)
	{
		return 1.5;
	}
}
