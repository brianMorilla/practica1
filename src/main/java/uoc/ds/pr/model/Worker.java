package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import uoc.ds.pr.CTTCompaniesJobs;

import java.time.LocalDate;

public class Worker {
    private final String id;
    private String name;
    private String surname;
    private LocalDate  dateOfBirth;
    private CTTCompaniesJobs.Qualification qualification;
    private final LinkedList<JobOffer> jobOffersByWorker;

    private int workingDays;

    public Worker(String id, String name, String surname, LocalDate dateOfBirth, CTTCompaniesJobs.Qualification qualification) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.qualification = qualification;
        this.jobOffersByWorker = new LinkedList<>();
    }

    public void addOffersByWorker(JobOffer jobOffer){
        jobOffersByWorker.insertEnd(jobOffer);
    }

    public LinkedList<JobOffer> getJobOffersByWorker() {
        return jobOffersByWorker;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public CTTCompaniesJobs.Qualification getQualification() {
        return qualification;
    }

    public void setQualification(CTTCompaniesJobs.Qualification qualification) {
        this.qualification = qualification;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }

    public boolean is(String workId) {
        return id.equals(workId);
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", qualificacio=" + qualification +
                '}';
    }


}
