package comp3911.cwk2;

public class Record {
  private String surname;
  private String forename;
  private String address;
  private String dateOfBirth;
  private String doctorId;
  private String diagnosis;

  public String getSurname() { return surname; }
  public String getForename() { return forename; }
  public String getAddress() { return address; }
  public String getDateOfBirth() { return dateOfBirth; }
  public String getDoctorId() { return doctorId; }
  public String getDiagnosis() { return diagnosis; }

  public void setSurname(String value) { surname = value; }
  public void setForename(String value) { forename = value; }
  public void setAddress(String value) { address = value; }
  public void setDateOfBirth(String value) { dateOfBirth = value; }
  public void setDoctorId(String value) { doctorId = value; }
  public void setDiagnosis(String value) { diagnosis = value; }
}
