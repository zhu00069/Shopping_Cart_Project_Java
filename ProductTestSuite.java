/**********************************************************************egg*m******a******n********************
 * File: ProdcutTestSuite.java
 * Course materials (19W) CST 8277
 * @author (original) Bo Zhu (040684747)
 *
 *Description: ProdcutTestSuite test Product C-R-U-D cycle, also test the different attributes.
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
 * This class is use to do JUnit test on Model Product
 * date (modified) 2019 04 12
 * @author Bo Zhu,  040-684-747
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductTestSuite implements TestSuiteConstants {
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

    private static final String SELECT_PRODUCT = 
            "SELECT ID, PRICE, PRODUCT_NAME, VERSION, CREATED_DATE, UPDATED_DATE, CATEGORY_ID FROM PRODUCT WHERE (ID = ?)";

    /**
     * test no product at start, use CLIPSELINK_LOGGING_SQL to track queries generated
     */
    @Test
    public void test_01_no_prodcut_at_start() {
        EntityManager em = emf.createEntityManager();

        ListAppender<ILoggingEvent> listAppender = attachListAppender(eclipselinkSqlLogger, ECLIPSELINK_LOGGING_SQL);
        Product product = em.find(Product.class, Integer.valueOf(1));
        detachListAppender(eclipselinkSqlLogger, listAppender);

        assertNull(product);
        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertEquals(1, loggingEvents.size());
        assertThat(loggingEvents.get(0).getMessage(), startsWith(SELECT_PRODUCT));

        em.close();
    }
    /**
     * test creating 2 products and persist them, saved to database
     */
    @Test
    public void test_02_create_product() {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Product product1 = new Product();
        product1.setProductName("Product_1");
        product1.setPrice(8.00);

        em.persist(product1);
        em.getTransaction().commit();
        assertEquals("Product_1", product1.getProductName());

        em.close();
    }

    /**
     * test delete a product, use remove() method from EntityManager interface and
     * delete by id
     */
    @Test
    //@Ignore
    public void test_03_delete_product() {
        EntityManager em = emf.createEntityManager();
        ListAppender<ILoggingEvent> listAppender = attachListAppender(eclipselinkSqlLogger, ECLIPSELINK_LOGGING_SQL);

        // start the transaction
        em.getTransaction().begin();
        Product product2 = new Product();
        product2.setProductName("Product_2");
        product2.setPrice(10.00);

        em.persist(product2);

        int id = product2.getId();
        em.remove(product2);
        em.getTransaction().commit();


        product2 = em.find(Product.class, id);
        assertNull(product2);
        detachListAppender(eclipselinkSqlLogger, listAppender);
        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertEquals(1, loggingEvents.size());
        em.close();

    }

    /**
     * Test update a product
     */

    @Test
    //@Ignore
    public void test_04_update_product() {
        EntityManager em = emf.createEntityManager();
        ListAppender<ILoggingEvent> listAppender = attachListAppender(eclipselinkSqlLogger, ECLIPSELINK_LOGGING_SQL);

        em.getTransaction().begin();
        
        Product product3 = new Product();
        product3.setProductName("Product_3");
        product3.setPrice(10.00);
        em.persist(product3);

        product3.setProductName("Product3_Update");
        em.merge(product3);

        em.getTransaction().commit();
        assertEquals("Product3_Update", product3.getProductName());

        detachListAppender(eclipselinkSqlLogger, listAppender);
        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertEquals(2, loggingEvents.size());
        em.close();
    }

    /**
     * Test sum of products by using JPQL creatQuery
     */
    @Test
    //@Ignore
    public void test_05_sum_products_by_query() {
        EntityManager em = emf.createEntityManager();
        ListAppender<ILoggingEvent> listAppender = attachListAppender(eclipselinkSqlLogger, ECLIPSELINK_LOGGING_SQL);

        em.getTransaction().begin();
        Query query = em.createQuery("Select count(p) from Product p");
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

