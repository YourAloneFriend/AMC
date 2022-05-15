package amo_lab2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.mindfusion.charting.FunctionSeries;
import com.mindfusion.charting.GridType;
import com.mindfusion.charting.swing.LineChart;
import com.mindfusion.drawing.SolidBrush;

public class Main extends JFrame{

    Main()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 1024);
        setTitle("Lab2: Function chart drawing");
        getContentPane().add(drawChart(), BorderLayout.CENTER);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    new Main().setVisible(true);
                }
                catch (Exception exp)
                {
                }
            }
        });
    }

    private LineChart drawChart()
    {
        LineChart lineChart= new LineChart();

        lineChart.getXAxis().setMinValue(-15.0);
        lineChart.getXAxis().setMaxValue(15.0);
        lineChart.getXAxis().setOrigin(0.0);
        lineChart.getXAxis().setTitle("X");


        lineChart.getYAxis().setMinValue(-15.0);
        lineChart.getYAxis().setMaxValue(15.0);
        lineChart.getYAxis().setOrigin(0.0);
        lineChart.getYAxis().setTitle("Y");

        lineChart.setGridType(GridType.Crossed);

        lineChart.getTheme().setCommonSeriesStrokes(
                Arrays.asList(
                        new SolidBrush(new Color(19, 130, 147)),
                        new SolidBrush(new Color(102, 190, 223)),
                        new SolidBrush(new Color(246, 187, 50))
                )
        );


        lineChart.getTheme().setCommonSeriesFills(
                Arrays.asList(
                        new SolidBrush(new Color(19, 130, 147)),
                        new SolidBrush(new Color(102, 190, 223)),
                        new SolidBrush(new Color(246, 187, 50))
                )
        );


        FunctionSeries f1, f2, f3;

        try
        {
            f1 = new FunctionSeries("Exp(x)/Pow(x, 3)", 1000, -15, 15);
            f1.setTitle("y=(e^x)/x^3");
            lineChart.getSeries().add(f1);

            f2 = new FunctionSeries("Exp(x)/Pow(x, 2)", 1000, -15, 15);
            f2.setTitle("y=(e^x)/x^2");
            lineChart.getSeries().add(f2);

            f3 = new FunctionSeries("Exp(x)/x", 1000, -15, 15);
            f3.setTitle("y=(e^x)/x");
            lineChart.getSeries().add(f3);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return lineChart;
    }

}















