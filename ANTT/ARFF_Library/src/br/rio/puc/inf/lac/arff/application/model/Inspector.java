package br.rio.puc.inf.lac.arff.application.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Inspector implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private String            name;
  /** the number */
  private int               number;

  public enum Status {
    Free,
    PerformingInspection,
    CheckingComplaint,
    Danger
  };

  private Status                         situation;
  /** */
  private ArrayList<VerificationVehicle> verificationList;

  public Inspector() {
    verificationList = new ArrayList<VerificationVehicle>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public Status getSituation() {
    return situation;
  }

  public void setSituation(Status situation) {
    this.situation = situation;
  }

  public ArrayList<VerificationVehicle> getVehicle() {
    return verificationList;
  }

  public void setVehicle(ArrayList<VerificationVehicle> vehicleList) {
    this.verificationList = vehicleList;
  }
}
