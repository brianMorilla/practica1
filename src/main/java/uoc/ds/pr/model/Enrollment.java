package uoc.ds.pr.model;

public class Enrollment {
    private final Worker worker;
    boolean isSubtitute;

    public Enrollment( Worker worker, boolean isSubstitute) {
        this.worker = worker;
        this.isSubtitute = isSubtitute;
    }

    public Worker getWorker() {
        return worker;
    }

}
