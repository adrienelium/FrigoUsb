package fr.exia.pmf.vue;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleEdge;

@SuppressWarnings("serial")
public class LineChart extends ApplicationFrame{
	private JFreeChart lineChart;
	@SuppressWarnings("unused")
	private DefaultCategoryDataset dataset;
	private XYSeries tempOut;
	private XYSeries tempIn;
	
	private int compteur;
	public ValueMarker mark;
	
	public LineChart(String applicationTitle , String chartTitle)
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
	      
	      setTitle(null);
	      getJChart().setTitle((String)null);
		  getJChart().getLegend().setPosition(RectangleEdge.RIGHT);
	      
	      ChartPanel chartPanel = new ChartPanel( lineChart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      setContentPane( chartPanel );
	      compteur = 0;
	      
	      XYPlot plot = lineChart.getXYPlot();
		  mark = new ValueMarker(0, Color.ORANGE, new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 12.0f, new float[] {5.0f}, 0.0f));
		  plot.addRangeMarker(mark, Layer.BACKGROUND);
		  
		  plot.setBackgroundPaint(new Color(45,45,45));
		  plot.setRangeGridlinePaint(new Color(10,10,10));
		  plot.setDomainGridlinePaint(new Color(10,10,10));
		  
		  plot.getDomainAxis().setLabelPaint(new Color(200,200,200));
		  plot.getRangeAxis().setLabelPaint(new Color(200,200,200));
		  
		  XYItemRenderer renderer = plot.getRenderer();
		  
		  renderer.setSeriesItemLabelPaint(0, new Color(241, 61, 7));
		  renderer.setSeriesPaint(0, new Color(241, 61, 7));
		  renderer.setSeriesStroke(0,  new BasicStroke(2));
		  
		  renderer.setSeriesItemLabelPaint(1, new Color(66, 255, 66));
		  renderer.setSeriesPaint(1, new Color(66, 255, 66));
		  renderer.setSeriesStroke(1,  new BasicStroke(2));
		  
		  plot.getRangeAxis().setRange(new Range(5, 28));
		  plot.getRangeAxis().setTickLabelPaint(new Color(200,200,200));
	   }

	private XYSeriesCollection createDataset( )
	{
		//dataset = new DefaultCategoryDataset( );
		XYSeriesCollection dataset = new XYSeriesCollection();
		tempOut = new XYSeries("T° Out");
		tempIn = new XYSeries("T° In");

		dataset.addSeries(tempOut);
		dataset.addSeries(tempIn);
		
		this.tempIn.setMaximumItemCount(100);
		this.tempOut.setMaximumItemCount(100);
		
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
