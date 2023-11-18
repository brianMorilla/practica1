package uoc.ds.pr;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;
import uoc.ds.pr.util.QueueLinkedList;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class CTTCompaniesJobsImpl implements CTTCompaniesJobs {
    //definició dels objectes segons la PAC1
    private final Worker[] workers;
    private int numWorkers;
    private Company[] companies;
    private int numCompanies;
    private JobOffer[] jobOffers;
    private int numJobOffers;
    private QueueLinkedList<Request> requests;
    private int numTotalRequests;
    private int numPendingRequests;
    private int numRejectedRequests;
    /*
        Contructor principal de la classe CTTCompaniesJobsImpl
     */
    public CTTCompaniesJobsImpl() {
        this.workers = new Worker[MAX_NUM_WORKERS];
        this.companies = new Company[MAX_NUM_COMPANIES];
        this.jobOffers =  new JobOffer[MAX_NUM_JOBOFFERS];
        this.requests = new QueueLinkedList<>();
        this.numWorkers = 0;
        this.numCompanies =0;
        this.numJobOffers =0;
        this.numTotalRequests =0;
        this.numPendingRequests =0;
        this.numRejectedRequests =0;
    }

    @Override
    public void addWorker(String id, String name, String surname, LocalDate dateOfBirth, Qualification qualification) throws InsufficientSpaceException {
    // trobar la  primera posició disponible per el nou traballador
        int index = findEmptyWorkerIndex();
        boolean existingWorker = false;
        if(numWorkers > 0){
            //buscar si el worker existe
            for (int i = 0; i < workers.length; i++) {
                if (workers[i] != null && workers[i].getId().equals(id)) {
                    // Si el ID del treballador coincideix, actualitzar dades
                    workers[i].setName(name);
                    workers[i].setSurname(surname);
                    workers[i].setDateOfBirth(dateOfBirth);
                    workers[i].setQualification(qualification);
                    existingWorker = true;
                    break;
                }
            }
        }

        if (!existingWorker) {
            // Verifica si hi ha espai per afegir un trebalaldor
            if (index != -1) {
                // Crear un nou objete Worker y assignar els valors
                Worker newWorker = new Worker(id, name, surname, dateOfBirth, qualification);
                // Afegir el nou treballador a la posició disponible de l'array
                workers[index] = newWorker;
                numWorkers++;
            } else {
                // Si no hi ha espai suficient, torna excepció
                throw new InsufficientSpaceException();
            }
        }
    }

    // Metode pertrobar la primera posició vuida a l'array de treballadors
    private int findEmptyWorkerIndex() {
        for (int i = 0; i < workers.length; i++) {
            if (workers[i] == null) {
                return i;
            }
        }
        return -1; // si no hi ha espai, retorna -1
    }

    // Metode per trobar la companyia pel seu id entre la llista de companyies
    public Company getCompanyById(String id){
        Company company = null;
        for (int i = 0; i < numCompanies; i++) {
            if (companies[i].getId().equals(id)) {
                company = companies[i];
            }
        }
        return company;
    }

    // Mètode per afegir a l'objecte companies la companyia que es crea
    @Override
    public void addCompany(String id, String name, String description) throws InsufficientSpaceException {
        Boolean companyExist = false;
        // Verificar si la compañía ya existe
        for (int i = 0; i < numCompanies; i++) {
            if (companies[i].getId().equals(id)) {
                // Si la companyia ja existeix, s'actualitza
                companies[i].setName(name);
                companies[i].setDescription(description);
                companyExist = true;
            }
        }
        // Si la companyia no existeix, crea una nova i s'afegeix a l'objecte companies
        if (numCompanies < MAX_NUM_COMPANIES ) {
            if(!companyExist){
                Company newCompany = new Company(id, name, description);
                companies[numCompanies] = newCompany;
                numCompanies++;
            }
        } else {
            //si es supera el numero de companyies, retorna excepció
            throw new InsufficientSpaceException();
        }
    }

    // Mètode per afegir a l'objecte requests la request que es crea
    @Override
    public void addRequest(String id, String jobOfferId, String companyId, String description, Qualification minQualification, int maxWorkers, LocalDate startDate, LocalDate endDate) throws CompanyNotFoundException {
        //comprovar que id de la companyia no sigui 0
        if(!Objects.equals(companyId, "0")){
            //comprovar que id existeixi la companyia
            Company company = getCompany(companyId);
            if (company == null){
                //si no es esta la companyia al objecte companies, retorna excepcio
                throw new CompanyNotFoundException();
            }
        }else{
            //si no existeix la copanyia, retorna excepcio
            throw new CompanyNotFoundException();
        }
        requests.add(new Request(id,jobOfferId,companyId,description,minQualification,maxWorkers,startDate,endDate));
        numTotalRequests++;
        numPendingRequests++;
    }

    // Mètode per afegir a l'objecte jobOffers l'oferta de treball
    private void addJobOffer(Request request) throws InsufficientSpaceException {
        Boolean jobOfferExist = false;
        // Verificar que no existeixi el joboffer
        for (int i = 0; i < numJobOffers; i++) {
            if (jobOffers[i].getId().equals(request.getJobOfferId())) {
                jobOfferExist = true;
            }
        }
        if (numJobOffers < MAX_NUM_JOBOFFERS ) {
            if(!jobOfferExist){
                JobOffer newJobOffer = new JobOffer(request.getJobOfferId(), request.getCompanyId(), request.getStartDate(), request.getEndDate(),0, request.getMinQualification(),request.getMaxWorkers());
                newJobOffer.setCompany(getCompanyById(request.getCompanyId()));
                jobOffers[numJobOffers] = newJobOffer;
                request.setJobOffer(newJobOffer);
                numJobOffers++;
            }
        } else {
            throw new InsufficientSpaceException();
        }
    }

    // Mètode per actualitzar una request
    @Override
    public Request updateRequest(Status status, LocalDate date, String description) throws NoRequestException, InsufficientSpaceException {
        Request request = requests.poll();
        if(request == null){
            throw new NoRequestException();
        }
        request.updateRequest(status,date,description);
        //en funció de l'estat de la request, es treballen un totals o altres
        if (request.getStatus().equals(Status.ENABLED)) {
            addJobOffer(request);
            JobOffer jobOffer = request.getJobOffer();
            Company company = jobOffer.getCompany();
            company.setJobOffer(jobOffer);
            company.addOffersByCompany(jobOffer);
            numPendingRequests--;
        }
        else if(request.getStatus().equals(Status.DISABLED)) {
            numRejectedRequests++;
            numJobOffers--;
            addJobOffer(request);
            JobOffer jobOffer = request.getJobOffer();
            Company company = jobOffer.getCompany();
            company.setJobOffer(jobOffer);
            numPendingRequests--;
        }else{
            numPendingRequests++;
            addJobOffer(request);
            JobOffer jobOffer = request.getJobOffer();
            Company company = jobOffer.getCompany();
            company.setJobOffer(jobOffer);
        }
        return request;
    }



    // Mètode per registrsr un treballador a una oferta de feina
    @Override
    public Response signUpJobOffer(String workerId, String jobOfferId) throws JobOfferNotFoundException, WorkerNotFoundException, WorkerAlreadyEnrolledException, NoWorkerException, WorkerNotQuliMinimus {
            //aquests blocs son per determinar els possibles errors i tornar les excepcions corresponents
            Worker worker = getWorker(workerId);
            if(worker == null){
                throw new WorkerNotFoundException();
            }
            JobOffer jobOffer1 = getJobOffer(jobOfferId);
            if(jobOffer1 == null){
                throw new JobOfferNotFoundException();
            }
            if(jobOffer1.isWorkerInEnroolementForJObOffer(worker.getId())){
                throw new WorkerAlreadyEnrolledException();
            }
            if( jobOffer1.getMinQualification().getValue() > worker.getQualification().getValue()  ){
                return Response.REJECTED;
            }
            worker.setWorkingDays(worker.getWorkingDays() + jobOffer1.getWorkingDays());
            if(jobOffer1.getEnrollments().size() < jobOffer1.getMaxWorquersRequest() ){
                //mirar si el treballador ja està assignat
                if(!jobOffer1.isWorkerInEnroolementForJObOffer(worker.getId())){
                    Enrollment enrollment = new Enrollment(jobOffer1,worker,false);
                    worker.addOffersByWorker(jobOffer1);
                    jobOffer1.addEnrollement(enrollment,false);
                    return Response.ACCEPTED;
                }else{
                    throw new WorkerAlreadyEnrolledException();
                }
            }else{
                //suplent
                Enrollment enrollment = new Enrollment(jobOffer1,worker,true);
                jobOffer1.addEnrollement(enrollment,true);
                return Response.SUBSTITUTE;
            }
    }

    @Override
    public double getPercentageRejectedRequests() {
        //calcul del percentatje de requests rebutjades sobre el total
        return (double) numRejectedRequests / numTotalRequests;
    }

    @Override
    public Iterator<JobOffer> getJobOffersByCompany(String companyId) throws NOJobOffersException {
        Company company = getCompany(companyId);
        LinkedList<JobOffer> jobOffersByCompany = company.getJobOffersByCompany();
        // Verificar si la llista està vuida
        if (jobOffersByCompany.isEmpty()) {
            throw new NOJobOffersException();
        }
        return jobOffersByCompany.values();
    }

    @Override
    public Iterator<JobOffer> getAllJobOffers() throws NOJobOffersException {
        if (numJobOffers == 0 || jobOffers == null || jobOffers.length == 0) {
            throw new NOJobOffersException(); // Manejo si no hay ofertas de trabajo
        }
        JobOffer[] allJobOffers = Arrays.copyOf(jobOffers, numJobOffers);
        return new Iterator<JobOffer>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < allJobOffers.length;
            }
            @Override
            public JobOffer next() {
                if (!hasNext()) {
                    try {
                        throw new NOJobOffersException();
                    } catch (NOJobOffersException e) {
                        throw new RuntimeException(e);
                    }
                }
                return allJobOffers[currentIndex++];
            }
        };
    }

    @Override
    public Iterator<JobOffer> getJobOffersByWorker(String workerId) throws NOJobOffersException {
        Worker worker = getWorker(workerId);
        LinkedList<JobOffer> jobOffersByWorkers = worker.getJobOffersByWorker();
        // Verificar si la llista de jobOffersByWorkers està vuida
        if (jobOffersByWorkers.isEmpty()) {
            throw new NOJobOffersException();
        }
        return jobOffersByWorkers.values();
    }

    @Override
    public void addRating(String workerId, String jobOfferId, int value, String message) throws WorkerNotFoundException, JobOfferNotFoundException, WorkerNOEnrolledException {
        Worker worker = getWorker(workerId);
        if(worker == null){
            throw new WorkerNotFoundException();
        }
        JobOffer jobOffer = getJobOffer(jobOfferId);
        if(jobOffer == null){
            throw new JobOfferNotFoundException();
        }
        if(!jobOffer.isWorkerInEnroolementForJObOffer(workerId)){
            throw new WorkerNOEnrolledException();
        }
        jobOffer.addRatingByJobOffer(new Rating(workerId,jobOfferId,value,message));

    }

    @Override
    public Iterator<Rating> getRatingsByJobOffer(String jobOfferId) throws JobOfferNotFoundException, NORatingsException {
        JobOffer jobOffer = getJobOffer(jobOfferId);
        if(jobOffer == null){
            throw new JobOfferNotFoundException();
        }
        LinkedList<Rating> listRatingsByJobOffer = jobOffer.getListRatings();
        if(listRatingsByJobOffer.isEmpty()){
            throw new NORatingsException();
        }
        return jobOffer.getListRatings().values();
    }

    @Override
    public Worker getMostActiveWorker() throws NoWorkerException {
        if(numJobOffers ==0){
            throw new NoWorkerException();
        }
        int numMax=0;
        Worker workerMoreDaysInActivity = null;
        for (Worker comp : workers) {
            if(comp != null){
                if(numMax < comp.getWorkingDays()){
                    numMax=comp.getWorkingDays();
                    workerMoreDaysInActivity = comp;
                }
            }
        }
        return workerMoreDaysInActivity;
    }

    @Override
    public JobOffer getBestJobOffer() throws NOJobOffersException {
        if(numJobOffers ==0){
            throw new NOJobOffersException();
        }
        JobOffer jobOffer = null;
        //recorrer totes les jobOffers
        double maxRating =0;
        JobOffer jobOfferTheBest = null;
        for (JobOffer comp : jobOffers) {
            if(comp != null){
                double comparar = comp.getMitjaTotalRating() ;
                if(maxRating < comparar){
                    maxRating=comparar;
                    jobOfferTheBest = comp;
                }
            }
        }
        return jobOfferTheBest;
    }

    @Override
    public Worker getWorker(String id) {
        for (Worker comp : workers) {
            if (comp == null) {
                return null;
            } else if (comp.is(id)){
                return comp;
            }
        }
        return null;
    }

    @Override
    public Company getCompany(String id) {

        for (Company comp : companies) {
            if (comp == null) {
                return null;
            } else if (comp.is(id)){
                return comp;
            }
        }
        return null;
    }

    @Override
    public JobOffer getJobOffer(String id){
        JobOffer jobOffer = null;
        for (int i = 0; i < numJobOffers; i++) {
            if (jobOffers[i].getId().equals(id)) {
                jobOffer = jobOffers[i];
            }
        }
        return jobOffer;
    }



    @Override
    public int numWorkers() {
        return numWorkers;
    }

    @Override
    public int numCompanies() {
        return numCompanies;
    }

    @Override
    public int numJobOffers() {
        return numJobOffers;
    }

    @Override
    public int numPendingRequests() {
        return numPendingRequests;
    }

    @Override
    public int numTotalRequests() {
        return numTotalRequests;
    }

    @Override
    public int numRejectedRequests() {
        return numRejectedRequests;
    }
}
