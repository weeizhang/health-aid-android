package none.healthaide.model;

public class Case {
    private String startDate;
    private String endDate;
    private String caseDescribe;
    private String caseType;
    private String therapeuticMethod;
    private String hospital;
    private String doctor;

    public String getStartDate() {
        return startDate;
    }

    public Case setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public Case setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getCaseDescribe() {
        return caseDescribe;
    }

    public Case setCaseDescribe(String caseDescribe) {
        this.caseDescribe = caseDescribe;
        return this;
    }

    public String getCaseType() {
        return caseType;
    }

    public Case setCaseType(String caseType) {
        this.caseType = caseType;
        return this;
    }

    public String getTherapeuticMethod() {
        return therapeuticMethod;
    }

    public Case setTherapeuticMethod(String therapeuticMethod) {
        this.therapeuticMethod = therapeuticMethod;
        return this;
    }

    public String getHospital() {
        return hospital;
    }

    public Case setHospital(String hospital) {
        this.hospital = hospital;
        return this;
    }

    public String getDoctor() {
        return doctor;
    }

    public Case setDoctor(String doctor) {
        this.doctor = doctor;
        return this;
    }
}
