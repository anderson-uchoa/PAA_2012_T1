package contextnet.sddl;

import java.io.Serializable;
import java.util.UUID;

public class VehicleInformation implements Serializable {

	  private static final long serialVersionUID = 1L;

	  /** the vehicle unique id */
	  private UUID id;
	  
	  /** the vehicle tag */
	  private String tag;
	  
	  /** the current form info related to this vehicle **/
	  private String formInfo;

	  /**
	   * Creates a information for the vehicle identified by the id.
	   * 
	   * @param id
	   */
	  public VehicleInformation(UUID id) {
	    this.id = id;
	  }

	  /**
	   * Creates a information for the vehicle identified by the id and tag.
	   * 
	   * @param id
	   */
	  public VehicleInformation(UUID id, String formInfo, String tag) {
		   this.id = id;
		   this.formInfo = formInfo;
		   this.tag = tag;
	  }

	  /**
	   * Retrieves the vehicle unique id.
	   * 
	   * @return the id
	   */
	  public UUID getId() {
		  return id;
	  }

	  /**
	   * Retrieves the vehicle tag.
	   * 
	   * @return the tag
	   */
	  public String getTag() {
		  return tag;
	  }

	  /**
	   * Retrieves the current form info.
	   * 
	   * @return the tag
	   */
	  public String getFormInfo() {
		  return formInfo;
	  }

	public void setFormString(String s) {
		formInfo = s;		
	}
}
