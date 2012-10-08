package br.rio.puc.inf.lac.arff.application.model;

import java.io.Serializable;
import java.util.Date;

public class DriverLicense implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  /** the number */
  private int               number;
  /** the expiration date */
  private Date              dataExpiration;

  /** the possible types Driver's License */
  public enum type {
    A,
    B,
    C,
    D,
    E
  };

  /** the type Driver license */
  private type tipoDriverlicense;

  public int getNumero() {
    return number;
  }

  public void setNumero(int numero) {
    this.number = numero;
  }

  public Date getDataVencimento() {
    return dataExpiration;
  }

  public void setDataVencimento(Date dataVencimento) {
    this.dataExpiration = dataVencimento;
  }

  public type getTipoCnh() {
    return tipoDriverlicense;
  }

  public void setTipoCnh(type tipoCnh) {
    this.tipoDriverlicense = tipoCnh;
  }
}
