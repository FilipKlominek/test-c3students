package cz.educanet.students.beans;

import cz.educanet.students.entities.StudentEntity;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Named
@ApplicationScoped
public class StudentBean implements Serializable {

    private String newName;
    private LocalDate newDateOfBirth;
    private double newAverageGrade;
    private int id;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyStudentApp");

    public void addStudent() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        StudentEntity student = new StudentEntity();
        student.setName(newName);
        student.setDateOfBirth(newDateOfBirth);
        student.setAverageGrade(newAverageGrade);
        em.persist(student);

        et.commit();
        em.close();
    }

    public List<StudentEntity> getStudents() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<StudentEntity> query = em.createQuery("SELECT student FROM StudentEntity AS student", StudentEntity.class);
        List<StudentEntity> result = query.getResultList();

        em.close();

        return result;
    }

    public double getAverageAverageGrade() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<StudentEntity> query = em.createQuery("SELECT student FROM StudentEntity AS student", StudentEntity.class);
        List<StudentEntity> result = query.getResultList();

        int sum = 0;

        for (StudentEntity studentEntity : result) {
            sum += studentEntity.getAverageGrade();
        }

        em.close();

        return sum / (double) result.size();
    }

    public void editStudent() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction et = em.getTransaction();
        et.begin();

        Query query = em.createQuery("UPDATE StudentEntity SET name = :name, dateOfBirth = :dateOfBirth, averageGrade = :averageGrade WHERE id = :id");
        query.setParameter("name", newName);
        query.setParameter("dateOfBirth", newDateOfBirth);
        query.setParameter("averageGrade", newAverageGrade);
        query.setParameter("id", id);
        query.executeUpdate();

        et.commit();

        em.close();
    }

    public void deleteStudent() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction et = em.getTransaction();
        et.begin();

        Query query = em.createQuery("DELETE FROM StudentEntity WHERE id = :id");
        query.setParameter("id", id);

        et.commit();

        em.close();
    }

    @PreDestroy
    public void onDestroy() {
        emf.close();
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public LocalDate getNewDateOfBirth() {
        return newDateOfBirth;
    }

    public void setNewDateOfBirth(LocalDate newDateOfBirth) {
        this.newDateOfBirth = newDateOfBirth;
    }

    public double getNewAverageGrade() {
        return newAverageGrade;
    }

    public void setNewAverageGrade(double newAverageGrade) {
        this.newAverageGrade = newAverageGrade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
