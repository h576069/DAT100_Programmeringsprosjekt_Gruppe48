package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		// TODO - START
		for (int i = 0; i < gpspoints.length-1; i++) {
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i+1]);
		}
		
		return distance;
		// TODO - SLUTT

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		// TODO - START
		for (int i = 0; i < gpspoints.length-1; i++) {
			if (gpspoints[i+1].getElevation() > gpspoints[i].getElevation()) {
				elevation += (gpspoints[i+1].getElevation() - gpspoints[i].getElevation());
			}
		}
		return elevation;
		// TODO - SLUTT

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {
		
		return this.gpspoints[this.gpspoints.length-1].getTime() - this.gpspoints[0].getTime();

	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		
		// TODO - START		// OPPGAVE - START
		double[] speeds = new double[this.gpspoints.length-1];
		for (int i = 0; i < speeds.length; i++) {
			speeds[i] = GPSUtils.speed(this.gpspoints[i], this.gpspoints[i+1]);
		}
		
		return speeds;
		// TODO - SLUTT

	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		
		// TODO - START
		double[] all_speeds = speeds();
		maxspeed = GPSUtils.findMax(all_speeds);
		
		return maxspeed;
		// TODO - SLUTT
		
	}

	public double averageSpeed() {

		double average = 0;
		
		// TODO - START
		
		// Total avstand i km:
		double totDist = totalDistance() / 1000.0;
		
		// Total tid i timer:
		double totTime = totalTime() / 3600.0;
		
		
		average = totDist / totTime;
		return average;
		// TODO - SLUTT
		
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double speedmph = speed * MS;

		// TODO - START
		if (speedmph <= 10) {
			met = 4.0;
		} else if (speedmph > 10 && speedmph <= 12) {
			met = 6.0;
		} else if (speedmph > 12 && speedmph <= 14) {
			met = 8.0;
		} else if (speedmph > 14 && speedmph <= 16) {
			met = 10.0;
		} else if (speedmph > 16 && speedmph <= 20) {
			met = 12.0;
		} else {
			met = 16.0;
		}
		
		// Gjør om secs til timer:
		double hours = secs / 3600.0;
		// Skriver om ligningen for MET:
		kcal = met * weight * hours;
		
		return kcal;
		// TODO - SLUTT
		
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;

		// TODO - START
		for (int i = 0; i < this.gpspoints.length-1; i++) {
			int secs     = this.gpspoints[i+1].getTime() - this.gpspoints[i].getTime();
			
			double speed = GPSUtils.speed(this.gpspoints[i], this.gpspoints[i+1]);
			
			totalkcal += kcal(weight, secs, speed);
		}
		
		return totalkcal;
		// TODO - SLUTT
		
	}
	
	private static double WEIGHT = 80.0;
	
	public String[] displayStatistics() {

		System.out.println("==============================================");
		
		// TODO - START	
		String[] str = {(String.format("%-16s", "Total Time") + ":" + String.format("%-12s", GPSUtils.formatTime(totalTime())) + "\n"),
				(String.format("%-16s", "Total distance") + ":" + String.format("%-12s", GPSUtils.formatDouble(totalDistance())) + "km" + "\n"),
				(String.format("%-16s", "Total elevation") + ":" + String.format("%-12s", GPSUtils.formatDouble(totalElevation())) + "m" + "\n"),
				(String.format("%-16s", "Max speed") + ":" + String.format("%-12s", GPSUtils.formatDouble(GPSUtils.findMax(speeds()))) + "km/t" + "\n"),
				(String.format("%-16s", "Average speed") + ":" + String.format("%-12s", GPSUtils.formatDouble(averageSpeed())) + "km/t" + "\n"),
				(String.format("%-16s", "Energy") + ":" + String.format("%-12s", GPSUtils.formatDouble(totalKcal(WEIGHT))) + "kcal" + "\n"),
		};
		
		for (String s : str) {
			System.out.print(s);
		}
		
		System.out.println("==============================================");
		return str;
		// TODO - SLUTT
		
	}

}
