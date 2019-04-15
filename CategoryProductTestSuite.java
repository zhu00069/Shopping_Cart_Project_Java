/**********************************************************************egg*m******a******n********************
 * File: CategoryTestSuite.java
 * Course materials (19W) CST 8277
 * @author (original) Bo Zhu (040684747)
 *
 *Description: CategoryTestSuite test Category C-R-U-D cycle, 
 *1:M between Category and Product, 
 *also test the different attributes.
 *
 *Created Date: April 2019
 *
 */
package com.algonquincollege.cst8277.models;

import static com.algonquincollege.cst8277.models.TestSuiteConstants.attachListAppender;
import static com.algonquincollege.cst8277.models.TestSuiteConstants.buildEntityManagerFactory;
import static com.algonquincollege.cst8277.models.TestSuiteConstants.detachListAppender;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
/**
 * This class is use to do JUnit test on Model Category, Product, 1:M relationship
 * date (modified) 2019 04 12
 * @author Bo Zhu,  040-684-747
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryProductTestSuite implements TestSuiteConstants {
    /**
     * Class<?> _thisClaz, a class object taht represent class and interface in a running java application
     */
    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    /**
     * Logger logger, a logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);
    /**
     * ch.qos.logback.classic.Logger eclipselinkSqlLogger, a logger object for this class
     */
    private static final ch.qos.logback.classic.Logger eclipselinkSqlLogger = (ch.qos.logback.classic.Logger) LoggerFactory
            .getLogger(ECLIPSELINK_LOGGING_SQL);

    /**
     * EntityManagerFactory emf, EMF instance, use to create EntityManager for connecting to same database
     */
    public static EntityManagerFactory emf;
    /**
     * Server server, tcp server
     */
    public static Server server;
    
    /**
     * this method used to set up for testing 
     */
    @BeforeClass
    public static void oneTimeSetUp() {
        try {
            logger.debug("oneTimeSetUp");
            // create in-process H2 server so we can 'see' into database
            // use "jdbc:h2:tcp://localhost:9092/mem:assignment3-testing" in Db Perspective
            // (connection in .dbeaver-data-sources.xml so should be immediately useable
            server = Server.createTcpServer().start();
            emf = buildEntityManagerFactory(_thisClaz.getSimpleName());
        } catch (Exception e) {
            logger.error("something went wrong building EntityManagerFactory", e);
        }
    }

    private static final String SELECT_CAYEGORY = 
            "SELECT ID, CATEGORY_NAME, VERSION, CREATED_DATE, UPDATED_DATE FROM CATEGORY WHERE (ID = ?)";

    /**
     * test no category at start, use CLIPSELINK_LOGGING_SQL to track queries generated
     */
    @Test
    //@Ignore
    public void test_01_no_category_at_start() {
        EntityManager em = emf.createEntityManager();

        ListAppender<ILoggingEvent> listAppender = attachListAppender(eclipselinkSqlLogger, ECLIPSELINK_LOGGING_SQL);
        Category category = em.find(Category.class, Integer.valueOf(1));
        detachListAppender(eclipselinkSqlLogger, listAppender);

        assertNull(category);
        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertEquals(1, loggingEvents.size());
        assertThat(loggingEvents.get(0).getMessage(), startsWith(SELECT_CAYEGORY));

        em.close();
    }
    /**
     * test creating 1 category, and persist them, saved to database
     */
    @Test
    //@Ignore
    public void test_02_create_category() {
        EntityManager em = emf.createEntityManager();
        ListAppender<ILoggingEvent> listAppender = attachListAppender(eclipselinkSqlLogger, ECLIPSELINK_LOGGING_SQL);

        em.getTransaction().begin();
        Category cate1 = new Category();
        cate1.setCategoryName("Category1");

        em.persist(cate1);
        em.getTransaction().commit();
        
        assertEquals("Category1", cate1.getCategoryName());

        detachListAppender(eclipselinkSqlLogger, listAppender);
        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertEquals(2, loggingEvents.size());

        em.close();
    }
    
    /**
     * test creating 2 products that belong to category1, 1:M relationship, and persist them, saved to database
     */
    @Test
    //@Ignore
    public void test_03_create_category_with_product() {
        EntityManager em = emf.createEntityManager();
        ListAppender<ILoggingEvent> listAppender = attachListAppender(eclipselinkSqlLogger, ECLIPSELINK_LOGGING_SQL);

        em.getTransaction().begin();
        Category find_cate = em.find(Category.class, 1);
   
        Product product1 = new Product();
        product1.setProductName("Product1");
        product1.setPrice(2.00);
        product1.setCategory(find_cate);
        
        Product product2 = new Product();
        product2.setProductName("Product2");
        product2.setPrice(4.00);
        product2.setCategory(find_cate);
        
        em.persist(product1);
        em.persist(product2);

        em.getTransaction().commit();
        assertEquals("Category1", product1.getCategory().getCategoryName());
        assertEquals("Category1", product1.getCategory().getCategoryName());

        detachListAppender(eclipselinkSqlLogger, listAppender);
        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertEquals(4, loggingEvents.size());

        em.close();
    }
    
    
    /**
     * test delete a category, use remove() method from EntityManager interface and
     * delete by id
     */
    @Test
    //@Ignore
    public void test_04_delete_category() {
        EntityManager em = emf.createEntityManager();
        ListAppender<ILoggingEvent> listAppender = attachListAppender(eclipselinkSqlLogger, ECLIPSELINK_LOGGING_SQL);

        // start the transaction
        em.getTransaction().begin();
        Category cate2 = new Category();
        cate2.setCategoryName("CategoryWillBeDelete");
        em.persist(cate2);

        int id = cate2.getId();
        em.remove(cate2);
        em.getTransaction().commit();

        // Category was deleted
        cate2 = em.find(Category.class, id);
        assertNull(cate2);

        detachListAppender(eclipselinkSqlLogger, listAppender);
        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertEquals(1, loggingEvents.size());

        em.close();
    }
    
    /**
     * Test update a category
     */

    @Test
    //@Ignore
    public void test_05_update_category() {
        EntityManager em = emf.createEntityManager();
        ListAppender<ILoggingEvent> listAppender = attachListAppender(eclipselinkSqlLogger, ECLIPSELINK_LOGGING_SQL);

        em.getTransaction().begin();
        Category cate3 = new Category();
        cate3.setCategoryName("Category3");
        em.persist(cate3);

        cate3.setCategoryName("Category3_Update");
        em.merge(cate3);

        em.getTransaction().commit();
        assertEquals("Category3_Update", cate3.getCategoryName());

        detachListAppender(eclipselinkSqlLogger, listAppender);
        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertEquals(2, loggingEvents.size());
        em.close();
    }
    
    /**
     * Test sum of categories by using JPQL creatQuery
     */
    @Test
    //@Ignore
    public void test_06_sum_categories_by_query() {
        EntityManager em = emf.createEntityManager();
        ListAppender<ILoggingEvent> listAppender = attachListAppender(eclipselinkSqlLogger, ECLIPSELINK_LOGGING_SQL);

        em.getTransaction().begin();
        Query query = em.createQuery("Select count(c) from Category c");
        long sum = (long) query.getSingleResult();
        em.getTransaction().commit();
        assertEquals(2, sum, 0);

        detachListAppender(eclipselinkSqlLogger, listAppender);
        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertEquals(1, loggingEvents.size());

        em.close();
    }
    /**
     * this method used to tear down for testing 
     */
    @AfterClass
    public static void oneTimeTearDown() {
        logger.debug("oneTimeTearDown");
        if (emf != null) {
            emf.close();
        }
        if (server != null) {
            server.stop();
        }
    }
   
    
}

