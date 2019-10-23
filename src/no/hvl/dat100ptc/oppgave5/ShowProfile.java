package no.hvl.dat100ptc.oppgave5;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class ShowProfile extends EasyGraphics {

	private static int MARGIN = 50;		// margin on the sides 
	
	//FIXME: use highest point and scale accordingly
	private static int MAXBARHEIGHT = 500; // assume no height above 500 meters
	
	private GPSPoint[] gpspoints;

	public ShowProfile() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		GPSComputer gpscomputer =  new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		int N = gpspoints.length; // number of data points

		makeWindow("Height profile", 2 * MARGIN + 3 * N, 2 * MARGIN + MAXBARHEIGHT);

		// top margin + height of drawing area
		showHeightProfile(MARGIN + MAXBARHEIGHT); 
	}

	public void showHeightProfile(int ybase) {

		// ybase indicates the position on the y-axis where the columns should start
		
		// TODO - START
		int x = MARGIN;
		int RECTANGLE_WIDTH = (2 * MARGIN + 3 * gpspoints.length) / (2*gpspoints.length);
		
		setColor(0, 0, 255);
		for (GPSPoint gps : gpspoints) {
			int height = (int) gps.getElevation();
			if (height < 0) {
				height = 0;
			}
			
			fillRectangle(x, ybase-height, RECTANGLE_WIDTH, height); // height < 0 ? 0 : height
			x += (RECTANGLE_WIDTH+1);
		}
		// TODO - SLUTT
		Path currentRelativePath = Paths.get("");
		System.out.println(currentRelativePath.toAbsolutePath().normalize().toString());
		
	}

}
