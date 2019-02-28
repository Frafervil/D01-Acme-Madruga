package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import domain.Brotherhood;
import domain.Procession;
import domain.Request;

@Service
@Transactional
public class ProcessionService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ProcessionRepository processionRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;

	@Autowired
	private RequestService requestService;

	// Additional functions
	private String generateTicker() {
		String result;
		final Calendar now = Calendar.getInstance();
		String year = String.valueOf(now.get(Calendar.YEAR));
		year = year.substring(year.length() - 2, year.length());
		String date = String.valueOf(now.get(Calendar.DATE));
		date = date.length() == 1 ? "0".concat(date) : date;
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		month = month.length() == 1 ? "0".concat(month) : month;
		final Random r = new Random();
		final char a = (char) (r.nextInt(26) + 'a');
		final char b = (char) (r.nextInt(26) + 'a');
		final char c = (char) (r.nextInt(26) + 'a');
		final char d = (char) (r.nextInt(26) + 'a');
		final char f = (char) (r.nextInt(26) + 'a');
		final char g = (char) (r.nextInt(26) + 'a');
		String code = String.valueOf(a) + String.valueOf(b) + String.valueOf(c)
				+ String.valueOf(d) + String.valueOf(f) + String.valueOf(g);
		code = code.toUpperCase();
		result = year + month + date + "-" + code;
		return result;
	}

	// Simple CRUD Methods
	public Procession create() {
		Procession result;
		final Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		result = new Procession();
		result.setTicker(this.generateTicker());
		result.setIsDraft(true);
		return result;
	}

	public Procession findOne(final int processionId) {
		Procession result;

		result = this.processionRepository.findOne(processionId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Procession> findAll() {
		Collection<Procession> result;

		result = this.processionRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Procession save(final Procession procession) {
		Brotherhood principal;
		Procession result;

		Assert.notNull(procession);

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		procession.setIsDraft(false);

		result = this.processionRepository.save(procession);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Procession procession) {
		Brotherhood principal;
		Collection<Request> requests;

		Assert.notNull(procession);
		Assert.isTrue(procession.getId() != 0);

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		this.brotherhoodService.save(principal);

		requests = this.requestService.findAll();
		for (final Request r : requests)
			if (r.getProcession().getId() == procession.getId())
				this.requestService.delete(r);

		this.processionRepository.delete(procession);
	}

	// Business Methods
	public Collection<Procession> findAllProcessionsOfOneBrotherhood(
			final int brotherhoodId) {
		Collection<Procession> result;

		result = this.processionRepository
				.findAllProcessionsOfOneBrotherhood(brotherhoodId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Procession> findAllFinal() {
		Collection<Procession> result;

		result = this.processionRepository.findAllProcessionsFinal();
		Assert.notNull(result);
		return result;
	}

	public Collection<Procession> startingSoonProcessions() {
		final Collection<Procession> result;
		final Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, 30);
		final Date dateMax = c.getTime();

		result = this.processionRepository.findSoonProcessions(dateMax);
		Assert.notNull(result);

		return result;

	}

	public Collection<Procession> findVisibleProcessions() {
		Collection<Procession> result = this.findAllFinal();
		Collection<Procession> allProcessions;
		String userNameOfPrincipal = this.brotherhoodService.findByPrincipal()
				.getUserAccount().getUsername();

		allProcessions = this.findAll();

		for (Procession p : allProcessions) {
			if (p.getIsDraft() == true
					&& (userNameOfPrincipal.equals(p.getBrotherhood()
							.getUserAccount().getUsername()))) {
				result.add(p);
			}
		}
		return result;
	}

	public Procession saveAsDraft(final Procession procession) {
		Procession result;
		Brotherhood principal;

		Assert.notNull(procession);
		Assert.isTrue(!procession.getIsDraft());

		principal = this.brotherhoodService.findByPrincipal();

		Assert.notNull(principal);

		procession.setIsDraft(true);
		result = this.processionRepository.save(procession);
		Assert.notNull(result);
		return result;
	}

}
