package za.co.iherridge0.jpa.hibernate.springjpahibernate.repository;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import za.co.iherridge0.jpa.hibernate.springjpahibernate.entity.Course;
import za.co.iherridge0.jpa.hibernate.springjpahibernate.entity.Passport;
import za.co.iherridge0.jpa.hibernate.springjpahibernate.entity.Student;

@Repository
@Transactional
public class StudentRepository {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	public Student findById(Long id) {
		return em.find(Student.class, id);
	}

	public Student save(Student student) {
		if (student.getId() == null) {
			em.persist(student);
		} else {
			em.merge(student);
		}
		return student;
	}

	public void deleteById(Long id) {
		Student student = findById(id);
		em.remove(student);
	}

	public void saveStudentWithPassport() {

		Passport passport = new Passport("123456789");
		em.persist(passport);
		Student student = new Student("Jameson");
		student.setPassport(passport);
		em.persist(student);

	}

	// EntityManager & Persistence Context
	// Transaction
	// session = persistence context
	public void someOperationToUnderstandPersistenceContext() {
		// Database Operation 1 - Retrieve student
		Student student = em.find(Student.class, 20001L);
		// Persistence Context (student)

		// Database Operation 2 - Retrieve passport
		Passport passport = student.getPassport();
		// Persistence Context (student, passport)

		// Database Operation 3 - update passport
		passport.setNumber("E123457");
		// Persistence Context (student, passport++)

		// Database Operation 4 - update student
		student.setName("Ranga - updated");
		// Persistence Context (student++ , passport++)
	}

	public void insertHardcodedStudentAndCourse() {
		Student student = new Student("Jennifer");
		Course course = new Course("Microservices in 100 Steps");
		student.addCourse(course);
		course.addStudent(student);
		em.persist(student);
		em.persist(course);


//		em.persist(student);

	}

	public void insertStudentAndCourse(Student student, Course course) {
		em.persist(student);
		em.persist(course);

		student.addCourse(course);
		course.addStudent(student);

		em.persist(student);

	}
}
