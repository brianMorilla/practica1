package uoc.ds.pr.model;

public class Enrollment {
   // private String id;
    private JobOffer jobOffer;
    private Worker worker;
    boolean isSubtitute;

    public Enrollment( JobOffer jobOffer, Worker worker, boolean isSubstitute) {
        //this.id = id;
        this.jobOffer = jobOffer;
        this.worker = worker;
        this.isSubtitute = isSubtitute;
    }

    public Worker getWorker() {
        return worker;
    }

}
