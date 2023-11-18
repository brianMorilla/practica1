package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.CTTCompaniesJobs;
import uoc.ds.pr.util.QueueLinkedList;
import java.time.LocalDate;


public class JobOffer {
    private String id;
    private String idCompany;
    private Company company;
    private LocalDate startDate;
    private LocalDate endDate;
    private  int workingDays;
    private int NumWorkers;
    private CTTCompaniesJobs.Qualification minQualification;
    private int numSubstitutes;
    private QueueLinkedList<Enrollment> enrollments;
    private QueueLinkedList<Enrollment> enrollmentsSubstitutes;
    private int maxWorquersRequest;
    private LinkedList<Rating> listRatings;
    private double totalRating;
    private double mitjaTotalRating;
    private int numTotalRatings;

    public JobOffer(String id, String idCompany, LocalDate startDate, LocalDate endDate, int workingDays, CTTCompaniesJobs.Qualification minQualification, int maxWorquersRequest) {
        this.id = id;
        this.idCompany = idCompany;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workingDays = calculateWorkingDays(startDate, endDate)-1;
        this.minQualification = minQualification;
        this.enrollments = new QueueLinkedList<>();
        this.listRatings = new LinkedList<>();
        this.enrollmentsSubstitutes = new QueueLinkedList<>();
        this.maxWorquersRequest = maxWorquersRequest;
        this.numSubstitutes =0;
        this.NumWorkers = 0;
        this.totalRating = 0;
        this.numTotalRatings = 0;
    }

    private int calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        int workingDays = 0;
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            workingDays++;
            date = date.plusDays(1);
        }
        return workingDays;
    }


    public boolean isWorkerInEnroolementForJObOffer(String workerid){
        boolean exist = false;
        Iterator<Enrollment> iterator = enrollments.iterator();
        while (iterator.hasNext()) {
            Enrollment enrollment = iterator.next();
            if (enrollment.getWorker().getId().equals(workerid)) {
                exist = true; //el worker id existeix
            }
        }
        if(!exist){
            Iterator<Enrollment> iterator2 = enrollmentsSubstitutes.iterator();
            while (iterator2.hasNext()) {
                Enrollment enrollmentsSubstitutes = iterator2.next();
                if (enrollmentsSubstitutes.getWorker().getId().equals(workerid)) {
                    exist = true; //el worker id existeix a la llista de substitutes
                }
            }
        }
        return exist;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public String getId() {
        return id;
    }


    public LocalDate getStartDate() {
        return startDate;
       //return null;
    }


    public LocalDate getEndDate() {
        return endDate;
    }

    public String getJobOfferId() {
        return id;
    }

    public CTTCompaniesJobs.Qualification getMinQualification() {
        return minQualification;
    }

    public int getNumWorkers() {
        return NumWorkers;
    }

    public int getNumSubstitutes() {
        return numSubstitutes;
    }

    public QueueLinkedList<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void addEnrollement(Enrollment enrollment, boolean isSubstitute){
        //enrollments.add(enrollment);
        if(isSubstitute){
            numSubstitutes++;
            enrollmentsSubstitutes.add(enrollment);
        }else{
            NumWorkers++;
            enrollments.add(enrollment);
        }
    }

    public Iterator<Enrollment> substitutes() {
        return this.enrollmentsSubstitutes.iterator();
    }

    public double getTotalRating() {
        return mitjaTotalRating;
    }

    public Company getCompany() {
        return company;
    }

    public int getMaxWorquersRequest() {
        return maxWorquersRequest;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void addRatingByJobOffer(Rating ratingByJobOffer){
        this.totalRating = this.totalRating + ratingByJobOffer.getValue();
        this.numTotalRatings++;
        this.mitjaTotalRating = totalRating / numTotalRatings;
        listRatings.insertEnd(ratingByJobOffer);
    }

    public double getMitjaTotalRating() {
        return mitjaTotalRating;
    }

    public LinkedList<Rating> getListRatings(){
        return listRatings;
    }


}
