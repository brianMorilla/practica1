
package uoc.ds.pr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uoc.ds.pr.model.Worker;

import java.time.LocalDate;

public class WorkerTest {


    private Worker worker;

    @BeforeEach
    public void setUp() {
        // Crear un trabajador de ejemplo para utilizar en las pruebas
        LocalDate dateOfBirth = LocalDate.of(1990, 5, 15);
        worker = new Worker("1", "John", "Doe", dateOfBirth, CTTCompaniesJobs.Qualification.UNIVERSITY);
    }

    @Test
    public void testSetName() {
        // Verificar que el método setName establece correctamente el nombre
        worker.setName("Jane");
        Assertions.assertEquals("Jane", worker.getName());
    }

    @Test
    public void testSetSurname() {
        // Verificar que el método setSurname establece correctamente el apellido
        worker.setSurname("Smith");
        Assertions.assertEquals("Smith", worker.getSurname());
    }

    @Test
    public void testSetDateOfBirth() {
        // Verificar que el método setDateOfBirth establece correctamente la fecha de nacimiento
        LocalDate newDateOfBirth = LocalDate.of(1985, 10, 20);
        worker.setDateOfBirth(newDateOfBirth);
        Assertions.assertEquals(newDateOfBirth, worker.getDateOfBirth());
    }

}
