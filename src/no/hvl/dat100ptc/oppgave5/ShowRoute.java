package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN   = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);

		playRoute(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 
		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
	
		double ystep;
		
		// TODO - START
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		ystep = (MAPYSIZE-2*MARGIN) / (Math.abs(maxlat - minlat));
		return ystep;
		// TODO - SLUTT
		
	}

	public void showRouteMap(int ybase) {

		// TODO - START
		setColor(0, 255, 0);
		double[] latitudes  = GPSUtils.getLatitudes(gpspoints);
		double[] longitudes = GPSUtils.getLongitudes(gpspoints);
		
		for (int i = 0; i < gpspoints.length-1; i++) {
			// Finner forskjellen på nåværende long og den minste long, ganger med antall pixler per long
			int x1 = (int) ((longitudes[i]-GPSUtils.findMin(longitudes))*xstep());
			int x2 = (int) ((longitudes[i+1]-GPSUtils.findMin(longitudes))*xstep());
			
			// Finner forskjellen på nåværende lat og den største lat, ganger med antall pixler per lat
			int y1 = (int) ((GPSUtils.findMax(latitudes)-latitudes[i])*ystep());
			int y2 = (int) ((GPSUtils.findMax(latitudes)-latitudes[i+1])*ystep());
			
			
			fillCircle(x1, MARGIN + y1, 2);
			
			// Tegn linje mellom punktene
			drawLine(x1, MARGIN + y1, x2, MARGIN + y2);
			
		}
		setColor(0, 0, 255);
		int x = (int) ((longitudes[longitudes.length-1]-GPSUtils.findMin(longitudes))*xstep());
		int y = (int) ((GPSUtils.findMax(latitudes) - latitudes[latitudes.length-1])*ystep());
		fillCircle(x, MARGIN + y, 4);
		// TODO - SLUTT
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		
		// TODO - START
		String[] str = gpscomputer.displayStatistics();
		int y = 50;
		for (String s : str) {
			drawString(s, 50, y);
			y += TEXTDISTANCE;
		}
		// TODO - SLUTT;
	}

	public void playRoute(int ybase) {

		// TODO - START
		System.out.println("what");
		// TODO - SLUTT
	}

}
