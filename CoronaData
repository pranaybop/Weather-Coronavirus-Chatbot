import java.text.NumberFormat;

public class CoronaData {

	private int countrypop;
	private String population;
	private int confirmed;
	private String confirmedCases;
	private int deaths;
	private String deathCases;
	private String location;
	
	public CoronaData(int confirmed, int deaths, int countrypop, String location) {
		setCountrypop(countrypop);
		setConfirmed(confirmed);
		setDeaths(deaths);
		setLocation(location);
	}
	
	public String toString() {
		return String.format("In %s the population is %s and they have %s confirmed cases and %s deaths", location, population, confirmedCases, deathCases);
	}
	
	public int getCountrypop() {
		return countrypop;
	}
	public void setCountrypop(int countrypop) {
		this.population = NumberFormat.getIntegerInstance().format(countrypop);
	}
	
	public int getConfirmed() {
		return confirmed;
	}
	
	public void setConfirmed(int confirmed) {
		
		this.confirmedCases = NumberFormat.getIntegerInstance().format(confirmed);
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public void setDeaths(int deaths) {
		this.deathCases = NumberFormat.getIntegerInstance().format(deaths);
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	

}
