package uoc.ds.pr;

import uoc.ds.pr.model.JobOffer;

import java.time.LocalDate;
import java.time.LocalDate;
import static org.junit.Assert.*;
import org.junit.Test;
public class JObOfferTest {
    @Test
    public void testJObOffers() {
        // Crear una oferta de trabajo para pruebas
        LocalDate startDate = LocalDate.of(2023, 11, 1);
        LocalDate endDate = LocalDate.of(2023, 11, 15);
        JobOffer jobOffer = new JobOffer("1", "CompanyID", startDate, endDate, 10, CTTCompaniesJobs.Qualification.HIGH_SCHOOL, 5);
        assertEquals("1", jobOffer.getId());
        assertEquals(startDate, jobOffer.getStartDate());
        assertEquals(endDate, jobOffer.getEndDate());
        assertEquals(14, jobOffer.getWorkingDays());
        assertEquals(CTTCompaniesJobs.Qualification.HIGH_SCHOOL, jobOffer.getMinQualification());
        assertEquals(5, jobOffer.getMaxWorquersRequest());
    }

}
