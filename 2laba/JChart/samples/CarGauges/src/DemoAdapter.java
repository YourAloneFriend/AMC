import java.awt.*;
import java.awt.geom.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.components.gauges.*;
import com.mindfusion.common.*;
import com.mindfusion.drawing.*;


public class DemoAdapter extends BaseGaugeAdapter
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
		Rectangle2D bounds = new Rectangle2D.Double(0, 0, width, height);

		// ---------------------- Ellipse #1 ----------------------
		GradientBrush fill1 = new GradientBrush(new float[] { 0, 0.2f, 0.8f, 1 },
			new Color[] { new Color(0x90, 0x90, 0x90), new Color(0x90, 0x90, 0x90),
				new Color(0x30, 0x30, 0x30), new Color(0x30, 0x30, 0x30) }, 45);

		fill1.applyTo(e.getGraphics(), bounds);
		e.getGraphics().fill(
			new Ellipse2D.Double(
				bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));

		// Apply margin of 0.015
		inflate(bounds, -0.015 * width, -0.015 * height);

		// ---------------------- Ellipse #2 ----------------------
		GradientBrush fill2 = new GradientBrush(new float[] { 0, 0.2f, 0.8f, 1 },
			new Color[] {
				new Color(0x30, 0x30, 0x30), new Color(0x30, 0x30, 0x30),
				new Color(0x90, 0x90, 0x90), new Color(0x90, 0x90, 0x90) }, 45);

		fill2.applyTo(e.getGraphics(), bounds);
		e.getGraphics().fill(
			new Ellipse2D.Double(
				bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));

		// Apply margin of 0.015
		inflate(bounds, -0.015 * width, -0.015 * height);

		// ---------------------- Ellipse #3 ----------------------
		RadialGradientBrush fill3 = new RadialGradientBrush();
		fill3.setX(0.5);
		fill3.setY(0.3);
		fill3.setFractions(new float[] { 0, 1 });
		fill3.setColors(new Color[] { new Color(0x20, 0x20, 0x20), new Color(0xBB, 0xBB, 0xBB) });

		fill3.applyTo(e.getGraphics(), bounds);
		e.getGraphics().fill(
			new Ellipse2D.Double(
				bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));
	}
	
	@Override
	public void prepaintScale(PrepaintEvent e)
	{
		OvalScale scale = (OvalScale)e.getElement();
		if (scale.getName() == "FuelScale")
		{
			// Paint the gas station icon
			PathFigure figure = new PathFigure("M0,0.08 C0,0.03 0.05,0 0.1,0 L0.45,0 C0.5,0 0.55,0.03 " +
				"0.55,0.08 L0.55,0.41 L0.68,0.41 C0.74,0.41 0.78,0.46 0.78,0.52 L0.78,0.89 C0.78,0.96 " +
				"0.925,0.96 0.925,0.89 L0.86,0.6 L0.86,0.29 C0.86,0.27 0.86,0.26 0.84,0.24 L0.64,0.08 " +
				"L0.7,0.05 L0.9,0.21 C0.93,0.24 0.93,0.26 0.93,0.28 L0.93,0.58 L1,0.9 C0.98,1.04 0.72,1.04 " +
				"0.7,0.9 L0.7,0.5 C0.7,0.47 0.67,0.47 0.66,0.47 L0.55,0.47 L0.55,1 L0,1 L0,0.08 M0.11,0.06 " +
				"L0.44,0.06 C0.46,0.06 0.47,0.07 0.47,0.09 L0.47,0.29 C0.47,0.3 0.46,0.32 0.44,0.32 L0.11,0.32 " +
				"C0.09,0.32 0.08,0.31 0.08,0.29 L0.08,0.09 C0.08,0.07 0.09,0.06 0.11,0.06 z");

			figure.setStroke(new Pen(Colors.Transparent, 0));
			figure.setFill(Brushes.White);
			figure.setMargin(new Margins(0.46, 0.73, 0.46, 0.17));

            e.paintVisualElement(figure, new XDimension2D.Double(
            	e.getElement().getActualWidth(), e.getElement().getActualHeight()));
		}
	}

	@Override
	public void prepaintForeground(PrepaintEvent e)
	{
		e.setCancelDefaultPainting(true);

		double width = e.getElement().getActualWidth();
		double height = e.getElement().getActualHeight();
		Rectangle2D.Double bounds = new Rectangle2D.Double(0, 0, width, height);

		RadialGradientBrush area1Fill = new RadialGradientBrush();
		area1Fill.setFractions(new float[] { 0, 1 });
		area1Fill.setColors(new Color[] { new Color(255, 255, 255, 153), Colors.Transparent });

		RadialGradientBrush area2Fill = new RadialGradientBrush();
		area2Fill.setFractions(new float[] { 0, 1 });
		area2Fill.setColors(new Color[] { new Color(255, 255, 255, 32), Colors.Transparent });

		ArcArea area1 = new ArcArea();
		area1.RelativeCoordinates = false;
		area1.setStartAngle(160);
		area1.setEndAngle(-20);
		//area1.setMargin(new Margins(0.02));
		area1.setFill(area1Fill);

		ArcArea area2 = new ArcArea();
		area2.RelativeCoordinates = false;
		area2.setStartAngle(140);
		area2.setEndAngle(-40);
		area2.setMargin(new Margins(0.02));
		area2.setFill(area2Fill);

		e.paintVisualElement(area1, new XDimension2D.Double(
			e.getElement().getActualWidth(), e.getElement().getActualHeight()));
		e.paintVisualElement(area2, new XDimension2D.Double(
			e.getElement().getActualWidth(), e.getElement().getActualHeight()));
	}
}
