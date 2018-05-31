/**
 * 
 */
package qcastslgenerator;


/**
 * @author jracer
 *
 */
public class MyManufacturers {

	private String myManufacturers_Name_; 
	private String myManufacturers_Code_;
	private String myManufacturers_ShortName_;
	private String myManufacturers_HistoricName_;
	
	public String getManufacturerName() {
		return myManufacturers_Name_;
	}
	
	public String getManufacturerCode() {
		return myManufacturers_Code_;
	}
	
	public String getManufacturerShortName() {
		return myManufacturers_ShortName_;
	}
	
	
	public String getManufacturerHistoricName() {
		return myManufacturers_HistoricName_;
	}
	
	public String getManufacturerInfo() {
		return "Manufacturer: " + myManufacturers_Name_ + 
					" Code: " + myManufacturers_Code_ +
					" Short Name: " + myManufacturers_ShortName_ +
					" Historic Name: " + myManufacturers_HistoricName_;
	}
	
	MyManufacturers(String str1, String str2, String str3, String str4) {
		myManufacturers_Name_ = str1;
		myManufacturers_Code_ = str2;
		myManufacturers_ShortName_ = str3;
		myManufacturers_HistoricName_ = str4;
	}
	
	// default contructor (nothing). 
	MyManufacturers() {
		// We don't touch this. 
		myManufacturers_Name_ = "";
		myManufacturers_Code_ = "";
		myManufacturers_ShortName_ = "";
		myManufacturers_HistoricName_ = "";
	}
}
