package br.rio.puc.inf.lac.arff.application.model;

import java.io.Serializable;

public class Driver implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private String            name;
  private String            rntc;
  private DriverLicense     driverlicense;

  public String getName() {
    return name;
  }

  public void setName(String nome) {
    this.name = nome;
  }

  public DriverLicense getDriverlicense() {
    return driverlicense;
  }

  public void setDriverlicense(DriverLicense cnh) {
    this.driverlicense = cnh;
  }

  public String getRntc() {
    return rntc;
  }

  public void setRntc(String rntc) {
    this.rntc = rntc;
  }
}
