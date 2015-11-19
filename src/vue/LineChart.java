package vue;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

@SuppressWarnings("serial")
public class LineChart extends ApplicationFrame{
	private JFreeChart lineChart;
	@SuppressWarnings("unused")
	private DefaultCategoryDataset dataset;
	private XYSeries tempOut;
	private XYSeries tempIn;
	
	private int compteur;
	
	public LineChart( String applicationTitle , String chartTitle )
	   {
	      super(applicationTitle);
	      lineChart = ChartFactory.createXYLineChart(
	         chartTitle,
	         "Temps","°C",
	         createDataset(),
	         PlotOrientation.VERTICAL,
	         true,true,false);
	         
	      lineChart.setBackgroundPaint(new Color(77,77,77));
	      lineChart.getTitle().setPaint(new Color(255, 255, 255));
	      
	      
	      ChartPanel chartPanel = new ChartPanel( lineChart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      setContentPane( chartPanel );
	      compteur = 0;
	   }

	private XYSeriesCollection createDataset( )
	{
		//dataset = new DefaultCategoryDataset( );
		XYSeriesCollection dataset = new XYSeriesCollection();
		tempOut = new XYSeries("Extérieur");
		tempIn = new XYSeries("Intérieur");

		dataset.addSeries(tempOut);
		dataset.addSeries(tempIn);
		
		this.tempIn.add(0,0);
		this.tempOut.add(0,0);
		//dataset.addValue( 0, "°C" , String.valueOf(compteur));
		/*dataset.addValue( 15 , "schools" , "1970" );
	      dataset.addValue( 30 , "schools" , "1980" );
	      dataset.addValue( 60 , "schools" ,  "1990" );
	      dataset.addValue( 120 , "schools" , "2000" );
	      dataset.addValue( 240 , "schools" , "2010" );
	      dataset.addValue( 300 , "schools" , "2014" );*/

		//dataset.

		return dataset;
	}

	public JFreeChart getJChart() {
		
		
		return lineChart;
	}
	
	public void addData(float tempIn,float tempOut)
	{
		compteur++;
		this.tempIn.add(compteur,tempIn);
		this.tempOut.add(compteur,tempOut);
	}
}
