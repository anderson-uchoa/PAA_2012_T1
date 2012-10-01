package br.rio.puc.inf.lac.arff.application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class VerificationVehicle implements Serializable {

  /**
	 * 
	 */
  private static final long    serialVersionUID = 1L;
  /***/
  private Date                 dtVerification;
  /***/
  private String               description;
  /** */
  private Inspector            inspector;
  /**  */
  private Vehicle              vehicle;
  /** */
  private ArrayList<ItemCheck> itemCheckList    = new ArrayList<ItemCheck>();

  public Date getDtVerification() {
    return dtVerification;
  }

  public void setDtVerification(Date dtVerification) {
    this.dtVerification = dtVerification;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Inspector getInspector() {
    return inspector;
  }

  public void setInspector(Inspector inspector) {
    this.inspector = inspector;
  }

  public ArrayList<ItemCheck> getItemCheckList() {
    return itemCheckList;
  }

  public void setItemCheckList(ArrayList<ItemCheck> itemCheckList) {
    this.itemCheckList = itemCheckList;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }
}
