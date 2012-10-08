package br.rio.puc.inf.lac.arff.application.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Vehicle implements Serializable {

  /**
	 * 
	 */
  private static final long              serialVersionUID = 1L;

  private String                         plate;
  /** the events longitude */
  private double                         longitude;
  /** the events latitude */
  private double                         latitude;
  /** the description */
  private int                            number;
  /** The list of inspections */
  private ArrayList<VerificationVehicle> inspections;

  public Vehicle() {
    inspections = new ArrayList<VerificationVehicle>();
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getPlate() {
    return plate;
  }

  public void setPlate(String placa) {
    this.plate = placa;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public int getNumero() {
    return number;
  }

  public void setNumero(int numero) {
    this.number = numero;
  }

  public ArrayList<VerificationVehicle> getInspections() {
    return inspections;
  }

  public void setInspections(ArrayList<VerificationVehicle> inspections) {
    this.inspections = inspections;
  }

}
