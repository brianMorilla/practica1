package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;

public class Company {
    private final String id;
    private String name;
    private String description;
    private final LinkedList<JobOffer> jobOffersByCompany;

    public Company(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.jobOffersByCompany = new LinkedList<>();
    }

    public void addOffersByCompany(JobOffer jobOffer){
        jobOffersByCompany.insertEnd(jobOffer);
    }

    public LinkedList<JobOffer> getJobOffersByCompany() {
        return jobOffersByCompany;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public boolean is(String companiId) {
        return id.equals(companiId);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


}
