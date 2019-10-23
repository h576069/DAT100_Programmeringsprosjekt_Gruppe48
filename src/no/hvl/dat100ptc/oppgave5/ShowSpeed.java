package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowSpeed extends EasyGraphics {
			
	private static int MARGIN = 50;
	private static int BARHEIGHT = 200; // assume no speed above 200 km/t

	private GPSComputer gpscomputer;
	private GPSPoint[] gpspoints;
	
	public ShowSpeed() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();
		
	}
	
	// read in the files and draw into using EasyGraphics
	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		int N = gpspoints.length-1; // number of data points
		
		makeWindow("Speed profile", 2*MARGIN + 2 * N, 2 * MARGIN + BARHEIGHT);
		
		showSpeedProfile(MARGIN + BARHEIGHT,N);
	}
	
	public void showSpeedProfile(int ybase, int N) {
		
		System.out.println("Angi tidsskalering i tegnevinduet (i %) ...");
		int timescaling = Integer.parseInt(getText("Tidsskalering"));
				
		// TODO - START
	
		// Finner alle hastighetene:
		double[] speeds = gpscomputer.speeds();
		
		// Vil hente ut fartene som er innenfor tidsskaleringen:
		int n = (int) (speeds.length * (timescaling / 100.0));
		
		// Starter grafen i x = MARGIN:
		int x = MARGIN;
		
		// Bestemmer bredden på rektanglene:
		int RECTANGLE_WIDTH = (2 * N - n)/n;
		
		setColor(0, 0, 255);
		int count = 0;
		while (count < n) {
			fillRectangle(x, (int) (ybase - speeds[count]), RECTANGLE_WIDTH, (int) speeds[count]);
			x+= (RECTANGLE_WIDTH+1);
			count++;
		}
		
		setColor(0, 255, 0);
		drawLine(MARGIN, (int) (ybase - gpscomputer.averageSpeed()), x, (int) (ybase - gpscomputer.averageSpeed()));
		// TODO - SLUTT
	}
}
