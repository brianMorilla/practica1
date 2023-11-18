package uoc.ds.pr.model;

import uoc.ds.pr.CTTCompaniesJobs;
import uoc.ds.pr.CTTCompaniesJobsImpl;

import java.time.LocalDate;
import java.util.Date;

public class Request {
    private String id;
    private String jobOfferId;
    private JobOffer jobOffer;
    private String  companyId;
    private String description;
    private CTTCompaniesJobs.Qualification  minQualification;
    private Integer maxWorkers;
    private LocalDate startDate;
    private LocalDate endDate;
    private CTTCompaniesJobs.Status status;

    private LocalDate dateStatus;
    private String descriptionStatus;


    public Request(String id, String jobOfferId, String companyId, String description, CTTCompaniesJobs.Qualification minQualification, Integer maxWorkers, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.jobOfferId = jobOfferId;
        this.companyId = companyId;
        this.description = description;
        this.minQualification = minQualification;
        this.maxWorkers = maxWorkers;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = CTTCompaniesJobs.Status.PENDING;
    }

    public Request updateRequest(CTTCompaniesJobs.Status status, LocalDate dateStatus, String description ){
        this.status = status;
        this.dateStatus = dateStatus;
        this.descriptionStatus = description;

        return this;
    }


    public JobOffer getJobOffer() {

        return jobOffer;
    }


    public String getCompanyId() {
        return companyId;
    }


    public CTTCompaniesJobs.Qualification getMinQualification() {
        return minQualification;
    }

    public Integer getMaxWorkers() {
        return maxWorkers;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }


    public CTTCompaniesJobs.Status getStatus() {
        return status;
    }

    public LocalDate getDateStatus() {
        return dateStatus;
    }


    public String getDescriptionStatus() {
        return descriptionStatus;
    }

    public String getJobOfferId() {
        return jobOfferId;
    }

    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }
}
