package uoc.ds.pr;
import org.junit.jupiter.api.Test;
import uoc.ds.pr.model.Request;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
public class RequestTEst {
    @Test
    public void testUpdateRequest() {
        // Arrange
        Request request = new Request("1", "job123", "company456", "Description", CTTCompaniesJobs.Qualification.HIGH_SCHOOL, 5, LocalDate.now(), LocalDate.now());

        // Act
        LocalDate dateStatus = LocalDate.now();
        request.updateRequest(CTTCompaniesJobs.Status.ENABLED, dateStatus, "Status updated");

        // Assert
        assertEquals(CTTCompaniesJobs.Status.ENABLED, request.getStatus());
        assertEquals(dateStatus, request.getDateStatus());
        assertEquals("Status updated", request.getDescriptionStatus());
    }

    @Test
    public void testGetJobOfferInitiallyNull() {
        // Arrange
        Request request = new Request("1", "job123", "company456", "Description", CTTCompaniesJobs.Qualification.HIGH_SCHOOL, 5, LocalDate.now(), LocalDate.now());

        // Act & Assert
        assertNull(request.getJobOffer());
    }


}
