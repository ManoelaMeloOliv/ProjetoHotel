package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.bo.Hospede;

public class HospedeDAO implements InterfaceDAO<Hospede> {
 private static HospedeDAO instance;
    protected EntityManager entityManager;

    public HospedeDAO(){
        entityManager = getEntityManager();
    }
    public static HospedeDao getInstance(){
        if(instance == null){
            instance = new HospedeDao();
        }
        return instance;
    }
    private EntityManager getEntityManager(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU");
        if (entityManager == null){
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }
    
    @Override
    public void Create(Hospede objeto) {
        try {
            entityManager.getTransaction() .begin();
            entityManager.persist(objeto);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
        

    @Override
    public Hospede Retrieve(int id) {
            Hospede hospede = entityManager.find(Hospede.class, id);
            return hospede;
    }

    @Override
    public List<Hospede> Retrieve(String atributo, String valor) {

        List<Hospede> listaHospede = new Arraylist<>();
        listaHospede = entityManager.createQuery(" Select hosp From hospede hosp" +
                                                  " where" + atributo + 
                                                 "like (%" + valor "%)", Hospede.class) .getResultList();
        return listaHospede;
    }
        
       
    @Override
    public void Update(Hospede objeto) {

        try{
            entityManager.getTransaction() .begin();
            entityManager.merge( objeto);
            entityManager.getTransaction() .commit();
        } catch (Exception ex){
            ex.printStackTrace();
            entityManager.getTransaction() .rollback();
        }
    }
        

    @Override
    public void Delete(Hospede objeto) {
        try{
            entityManager.getTransaction() .begin();
            Hospede hospede = new Hospede();
            hospede= entityManager.find(hospede.class, objetogetId());
            if(hospede != null) {
                entityManager.remove(hospede);
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            ex.printStackTrace();
            entityManager.getTransaction() .rollback();
        
        
    }

}
