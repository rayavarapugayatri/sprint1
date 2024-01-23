package com.anp.project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class DepartmentDAO {

    private final EntityManagerFactory emf;

    public DepartmentDAO() {
        this.emf = Persistence.createEntityManagerFactory("employee-jpa");
    }

    public void saveDepartment(Department department) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            if (department.getDeptId() == 0) {
                em.persist(department);
            } else {
                em.merge(department);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Department getDepartmentById(int deptId) {
        EntityManager em = emf.createEntityManager();
        Department department = null;

        try {
            department = em.find(Department.class, deptId);
        } finally {
            em.close();
        }

        return department;
    }

    public List<Department> getAllDepartments() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("FROM Department");
        return query.getResultList();
    }

    public void updateDepartment(Department department) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(department);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void deleteDepartment(int deptId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Department department = em.find(Department.class, deptId);
            if (department != null) {
                em.remove(department);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
