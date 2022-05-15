import java.awt.*;
import java.awt.geom.*;
import java.time.*;

import javax.swing.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.components.LayoutAlignment;
import com.mindfusion.charting.components.gauges.*;
import com.mindfusion.charting.swing.*;
import com.mindfusion.common.XDimension2D;
import com.mindfusion.drawing.*;


public class Clock extends JFrame
{	
	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		f.setTitle("MindFusion.Charting sample: Clock");
		f.setSize(400, 420);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dashboard board = new Dashboard();

		OvalGauge clock = new OvalGauge();
		clock.addBaseGaugeListener(new BaseGaugeAdapter()
		{
			@Override
			public void prepaintPointer(PrepaintEvent e)
			{
				e.setCancelDefaultPainting(true);
				Pointer timePointer;
				LocalDateTime now = LocalDateTime.now();
            
				Brush fill = new SolidBrush(new Color(94, 98, 99));

				if (e.getElement().getName() == "timePointer1")
				{
					timePointer = (Pointer)clock.getElementByName("timePointer1");
					timePointer.setValue((now.getHour() % 12) * 3600 + now.getMinute() * 60 + now.getSecond());
				}
				else
				{
					if (e.getElement().getName() == "timePointer2")
					{
						timePointer = (Pointer)clock.getElementByName("timePointer2");
						timePointer.setValue(now.getMinute() * 720 + now.getSecond() * 12);
					}
					else
					{
						timePointer = (Pointer)clock.getElementByName("timePointer3");
						timePointer.setValue(now.getSecond() * 720);
					}
				}
				timePointer.invalidate();

    	        Rectangle2D.Double psize = new Rectangle2D.Double(
    	        	0, 0, timePointer.getActualWidth(), timePointer.getActualHeight());
    	        		
				int[] x = new int[]
				{
					0, (int)psize.getWidth(), (int)psize.getWidth(), 0
				};
				int[] y = new int[]
				{
					0, 0, (int)psize.getHeight(), (int)psize.getHeight()
				};

				fill.applyTo(e.getGraphics(), psize);
				e.getGraphics().fillPolygon(x, y, x.length);
			}

			@Override
			public void prepaintBackground(PrepaintEvent e)
			{
				e.setCancelDefaultPainting(true);
				
				Rectangle2D bounds = new Rectangle2D.Double(
					0, 0, e.getElement().getActualWidth(), e.getElement().getActualHeight());

				// ---------------------- Ellipse #1 ----------------------
				Brush fill1 = new SolidBrush(new Color(245, 245, 245));
				Pen stroke1 = new Pen(new Color(122, 123, 124), 0.01f * bounds.getWidth());
				inflate(bounds, -stroke1.getWidth() / 2, -stroke1.getWidth() / 2);

				fill1.applyTo(e.getGraphics(), bounds);
				e.getGraphics().fill(
					new Ellipse2D.Double(
						bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));

				stroke1.applyTo(e.getGraphics(), bounds);
				e.getGraphics().draw(
					new Ellipse2D.Double(
						bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));

				inflate(bounds, -0.025 * bounds.getWidth(), -0.025 * bounds.getHeight());

				// ---------------------- Ellipse #2 ----------------------
				RadialGradientBrush fill2 = new RadialGradientBrush();
				fill2.setX(1);
				fill2.setY(1);
				fill2.setFractions(new float[] { 0, 0.3f, 0.7f, 1 });
				fill2.setColors(
					new Color[] {
						new Color(225, 236, 238), new Color(225, 236, 235), Colors.White, Colors.White
					});
				fill2.applyTo(e.getGraphics(), bounds);
				e.getGraphics().fill(
					new Ellipse2D.Double(
						bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));
				
				// ---------------------- Ellipse #3 ----------------------
				RadialGradientBrush fill3 = new RadialGradientBrush();
				fill3.setX(0.5);
				fill3.setY(0.5);
				fill3.setFractions(new float[] { 0, 0.03f, 0.09f, 0.13f, 1 });
				fill3.setColors(
					new Color[] {
						new Color(0, 0, 0, 0),
						new Color(0, 0, 0, 0),
						new Color(0, 0, 0, 32),
						new Color(0, 0, 0, 0),
						new Color(0, 0, 0, 0)
					});
				fill3.applyTo(e.getGraphics(), bounds);
				e.getGraphics().fill(
					new Ellipse2D.Double(
						bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));

				// ---------------------- Ellipse #4 ----------------------
				Pen stroke4 = new Pen(new Color(124, 137, 154), 0.01f);
				inflate(bounds, -stroke4.getWidth() / 2, -stroke4.getWidth() / 2);
				stroke4.applyTo(e.getGraphics(), bounds);
				e.getGraphics().draw(
					new Ellipse2D.Double(
						bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));
				inflate(bounds, -0.49 * bounds.getWidth(), -0.49 * bounds.getHeight());
				
				// ---------------------- Ellipse #5 ----------------------
				Brush fill5 = new SolidBrush(new Color(94, 98, 99));
				fill5.applyTo(e.getGraphics(), bounds);
				e.getGraphics().fill(
					new Ellipse2D.Double(
						bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));
			}

			@Override
			public void prepaintForeground(PrepaintEvent e)
			{
				e.setCancelDefaultPainting(true);

				RadialGradientBrush area1Fill = new RadialGradientBrush();
				area1Fill.setFractions(new float[] { 0, 1 });//
				area1Fill.setColors(new Color[] { new Color(255, 255, 255, 80), Colors.Transparent });

				RadialGradientBrush area2Fill = new RadialGradientBrush();
				area2Fill.setFractions(new float[] { 0, 1 });//
				area2Fill.setColors(new Color[] { new Color(255, 255, 255, 80), Colors.Transparent });

				ArcArea area1 = new ArcArea();
				area1.RelativeCoordinates = false;
				area1.setStartAngle(160);
				area1.setEndAngle(-20);
				area1.setMargin(new Margins(0.03f));
				area1.setFill(area1Fill);

				ArcArea area2 = new ArcArea();
				area2.RelativeCoordinates = false;
				area2.setStartAngle(140);
				area2.setEndAngle(-40);
				area2.setMargin(new Margins(0.03f));
				area2.setFill(area2Fill);

				e.paintVisualElement(area1, new XDimension2D.Double(
					e.getElement().getActualWidth(), e.getElement().getActualHeight()));
				e.paintVisualElement(area2, new XDimension2D.Double(
					e.getElement().getActualWidth(), e.getElement().getActualHeight()));
			}
			
			void inflate(Rectangle2D rect, double dx, double dy)
			{
				rect.setFrame(rect.getX() - dx, rect.getY() - dy, rect.getWidth() + 2 * dx, rect.getHeight() + 2 * dy);
			}
		});

		clock.getScales().clear();
		
		OvalScale timeScale = new OvalScale();
		timeScale.beginInit();
		timeScale.setMargin(new Margins(0.075, 0.075, 0.075, 0.075));
		timeScale.setFill(new SolidBrush(Colors.Transparent));
		timeScale.setMinValue(0);
		timeScale.setMaxValue(43200);
		timeScale.setStartAngle(-90);
		timeScale.setEndAngle(270);
		
		timeScale.getMajorTickSettings().setTickShape(TickShape.Rectangle);
		timeScale.getMajorTickSettings().setShowLabels(false);
		timeScale.getMajorTickSettings().setCount(12);
		timeScale.getMajorTickSettings().setFill(new SolidBrush(new Color(0x5E6263)));
		timeScale.getMajorTickSettings().setStroke(new Pen(new Color(0x5E6263), 1));
		timeScale.getMajorTickSettings().setTickAlignment(Alignment.OuterInside);
		timeScale.getMajorTickSettings().setTickHeight(new Length(1, LengthType.Relative));
		
		timeScale.getMiddleTickSettings().setShowLabels(false);
		timeScale.getMiddleTickSettings().setCount(5);
		timeScale.getMiddleTickSettings().setFill(new GradientBrush(new Color(0xFBFCFC), new Color(0x8D9BA5), 0));
		timeScale.getMiddleTickSettings().setStroke(new Pen(Colors.Transparent, 1));
		timeScale.getMiddleTickSettings().setTickAlignment(Alignment.OuterInside);
		timeScale.getMiddleTickSettings().setTickHeight(new Length(3, LengthType.Relative));
		timeScale.getMiddleTickSettings().setTickWidth(new Length(3, LengthType.Relative));
		
		Pointer timePointer1 = new Pointer();
		timePointer1.setName("timePointer1");
		timePointer1.setMargin(new Margins(0, 0, 0.75f, 0.9f));
		timePointer1.setPointerHeight(new Length(2, LengthType.Relative));
		timePointer1.setPointerWidth(new Length(50, LengthType.Relative));
		timePointer1.setShape(PointerShape.Rectangle);
		
		Pointer timePointer2 = new Pointer();
		timePointer2.setName("timePointer2");
		timePointer2.setMargin(new Margins(0, 0, 0.6f, 0.98f));
		timePointer2.setPointerHeight(new Length(2, LengthType.Relative));
		timePointer2.setPointerWidth(new Length(80, LengthType.Relative));
		timePointer2.setShape(PointerShape.Rectangle);
		
		Pointer timePointer3 = new Pointer();
		timePointer3.setName("timePointer3");
		timePointer3.setMargin(new Margins(0, 0, 0.55f, 0.98f));
		timePointer3.setPointerHeight(new Length(1, LengthType.Relative));
		timePointer3.setPointerWidth(new Length(90, LengthType.Relative));
		timePointer3.setShape(PointerShape.Rectangle);
 
		timeScale.getPointers().add(timePointer1);
		timeScale.getPointers().add(timePointer2);
		timeScale.getPointers().add(timePointer3);

		timeScale.setStroke(new Pen(Colors.Transparent, 1));
		timeScale.endInit();
        
		clock.getScales().add(timeScale);

		clock.setHorizontalAlignment(LayoutAlignment.Stretch);
		clock.setVerticalAlignment(LayoutAlignment.Stretch);
		board.getRootPanel().getChildren().add(clock);

		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(board, BorderLayout.CENTER);

		f.setVisible(true);
	}
}
