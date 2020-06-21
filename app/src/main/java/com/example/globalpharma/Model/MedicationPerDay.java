package com.example.globalpharma.Model;

import java.util.List;

public class MedicationPerDay {
  private  String date;
  private List<Medication> medicationsOfDay;

  public MedicationPerDay() {
  }

  public MedicationPerDay(String date, List<Medication> medicationsOfDay) {
    this.date = date;
    this.medicationsOfDay = medicationsOfDay;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public List<Medication> getMedicationsOfDay() {
    return medicationsOfDay;
  }

  public void setMedicationsOfDay(List<Medication> medicationsOfDay) {
    this.medicationsOfDay = medicationsOfDay;
  }

}