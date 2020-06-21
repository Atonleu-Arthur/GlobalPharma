package com.example.globalpharma.Model;

public class MedicationForm {
  private String formName;
  private int formImage;

  public MedicationForm(String formName, int formImage) {
    this.formName = formName;
    this.formImage = formImage;
  }

  public String getFormName() {
    return formName;
  }

  public void setFormName(String formName) {
    this.formName = formName;
  }

  public int getFormImage() {
    return formImage;
  }

  public void setFormImage(int formImage) {
    this.formImage = formImage;
  }
}