package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Procession;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class ProcessionServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private ProcessionService processionService;

	// Tests

	@Test
	public void testFindOne() {
		Procession result;

		super.authenticate("brotherhood1");
		result = this.processionService
				.findOne(this.getEntityId("procession1"));

		System.out.println(result);
		super.unauthenticate();
	}

	@Test
	public void testCreateAndSave() {
		Procession procession, saved;
		Collection<Procession> processions;

		super.authenticate("brotherhood1");
		procession = this.processionService.create();
		procession.setTitle("Título 1");
		procession.setDescription("Descripción 1");
		procession.setMoment(new Date(1531526400000L));

		saved = this.processionService.save(procession);
		System.out.println(procession.getTicker());
		processions = processionService.findAll();
		Assert.isTrue(processions.contains(saved));
		super.unauthenticate();
	}
}
