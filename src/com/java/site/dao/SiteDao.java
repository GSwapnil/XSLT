package com.java.site.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.java.site.model.Equipment;
import com.java.site.model.Site;
import com.java.site.model.SiteList;
import com.java.site.model.Tower;

public class SiteDao {
	
	EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Assignment");
	EntityManager entityManager = null;

	/*
	 * Returns an instance of Site for the given siteId
	 */
	public Site findSite(int siteId)
	{
		entityManager = emFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Site site = entityManager.find(Site.class, siteId);
		entityManager.getTransaction().commit();
		entityManager.close();
		return site;
	}
	
	/*
	 * Returns a list of Site instances
	 */
	@SuppressWarnings("unchecked")
	public List<Site> findAllSites()
	{
		entityManager = emFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createNamedQuery("Site.findAll");
		List<Site> sites = query.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return sites;
	}
	
	/*
	 * Marshals database parameter into a file called xmlFileName
	 */
	public void exportSiteDatabaseToXmlFile(SiteList siteList, String xmlFileName)
	{
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(xmlFileName);
			JAXBContext jaxb = JAXBContext.newInstance(SiteList.class);
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(siteList, fos);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Transforms a file called inputXmlFileName into a file called outputXmlFileName using XSLT file called xsltFileName
	 */
	public void convertXmlFileToOutputFile(String inputXmlFileName, String outputXmlFileName, String xsltFileName)
	{
		File inputXmlFile = new File(inputXmlFileName);
		File outputXmlFile = new File(outputXmlFileName);
		File xsltFile = new File(xsltFileName);
		
		StreamSource source = new StreamSource(inputXmlFile);
		StreamSource xslt = new StreamSource(xsltFile);
		StreamResult output = new StreamResult(outputXmlFile);
		
		TransformerFactory emFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = emFactory.newTransformer(xslt);
			transformer.transform(source, output);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Assignment");
		EntityManager entityManager = null;
		SiteDao dao = new SiteDao();
		
		Site site = new Site();
		site.setName("site1");
		site.setLatitude("0");
		site.setLongitude("0");
		
		Tower tower = new Tower();
		tower.setHeight(10.00);
		tower.setName("tower a");
		tower.setSides(5);
		
		Equipment e = new Equipment();
		e.setName("equipment1");
		e.setDescription("awesome equipment");
		e.setPrice(10.00);
		e.setBrand("homemade");

		tower.addEquipment(e);
		site.addTower(tower);
		entityManager = emFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		entityManager.persist(site);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		List<Site> sites = dao.findAllSites();
		
		SiteList siteList = new SiteList();
		siteList.setSites(sites);
		
		dao.exportSiteDatabaseToXmlFile(siteList, "xml/sites.xml");
		
		dao.convertXmlFileToOutputFile("xml/sites.xml", "xml/sites.html", "xml/sites2html.xslt");
		dao.convertXmlFileToOutputFile("xml/sites.xml", "xml/equipments.html", "xml/sites2equipment.xslt");
	}

}
