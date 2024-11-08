package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GestionStationSkiApplicationTests {

	@Mock
	IInstructorRepository instRepository;

	@Mock
	ICourseRepository courseRepository;

	@InjectMocks
	InstructorServicesImpl instServices;

	Instructor s = new Instructor(1L, "amir", "boudidah", LocalDate.of(2024, 1, 1));
	Set<Registration> registrations = new HashSet<>();
	Course c = new Course(1L, 2, TypeCourse.COLLECTIVE_CHILDREN, Support.SKI, 100.0f, 5, registrations);
	List<Instructor> lc = new ArrayList<Instructor>() {{
		add(new Instructor(2L, "rayen", "alelmi", LocalDate.of(2024, 1, 1)));
		add(new Instructor(3L, "kkkkk", "vvvvvv", LocalDate.of(2024, 1, 1)));
		add(new Instructor(4L, "zzzzzz", "zzzzzzz", LocalDate.of(2024, 1, 1)));
	}};

	@Test
	void testRetrieveInstructor() {
		when(instRepository.findById(1L)).thenReturn(Optional.of(s));
		Instructor retrievedInstructor = instServices.retrieveInstructor(1L);
		assertNotNull(retrievedInstructor);
		assertEquals(s.getNumInstructor(), retrievedInstructor.getNumInstructor());
		verify(instRepository).findById(1L);
	}

	@Test
	void testAddInstructor() {
		when(instRepository.save(s)).thenReturn(s);
		Instructor addedInstructor = instServices.addInstructor(s);
		assertNotNull(addedInstructor);
		assertEquals(s.getNumInstructor(), addedInstructor.getNumInstructor());
		verify(instRepository).save(s);
	}
	@Test
	void testRetrieveAllInstructors() {
		when(instRepository.findAll()).thenReturn(lc);
		List<Instructor> instructors = instServices.retrieveAllInstructors();
		assertNotNull(instructors);
		assertEquals(3, instructors.size());
		verify(instRepository).findAll();
	}

	@Test
	void testAddInstructorAndAssignToCourse() {
		when(courseRepository.findById(1L)).thenReturn(Optional.of(c));
		when(instRepository.save(s)).thenReturn(s);

		Instructor result = instServices.addInstructorAndAssignToCourse(s, 1L);

		assertNotNull(result);
		assertTrue(result.getCourses().contains(c));
		verify(courseRepository).findById(1L);
		verify(instRepository).save(s);
	}
//test

}
