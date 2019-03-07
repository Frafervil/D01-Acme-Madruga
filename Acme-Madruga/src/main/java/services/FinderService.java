
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;
import domain.Member;
import domain.Procession;

@Service
@Transactional
public class FinderService {

	// Managed Repository

	@Autowired
	FinderRepository		finderRepository;

	// Supporting services

	@Autowired
	MemberService			memberService;

	@Autowired
	ProcessionService		processionService;

	@Autowired
	CustomisationService	customisationService;


	// Simple CRUD methods

	public Finder create(final Member member) {
		Finder result;

		result = new Finder();
		result.setMember(member);
		result.setResults(new ArrayList<Procession>());

		return result;
	}

	public Finder findOne(final int finderId) {
		Finder result;
		Member principal;

		principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);

		result = this.finderRepository.findOne(finderId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public void save(final Finder finder) {
		Finder result;
		final Collection<Procession> searchResults;
		Date currentMoment;

		currentMoment = new Date(System.currentTimeMillis() - 1);
		Assert.notNull(finder);

		searchResults = this.searchProcessions(finder);
		finder.setResults(searchResults);
		finder.setLastRefreshed(currentMoment);

		result = this.finderRepository.save(finder);
		this.finderRepository.flush();
		Assert.notNull(result);
	}
	// Business methods

	public Collection<Procession> searchProcessions(final Finder finder) {
		Collection<Procession> result;
		Member principal;
		Date dateMin;
		Date dateMax;
		String area;
		String keyWord;
		principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);

		dateMin = finder.getDateMin();

		dateMax = finder.getDateMax();

		area = finder.getArea();

		keyWord = finder.getKeyWord();

		result = this.finderRepository.searchProcessions(keyWord, area, dateMin, dateMax);
		Assert.notNull(result);

		return result;
	}

	// ---------------------------

	public Finder findByPrincipal() {
		Finder finder;
		Member principal;
		Date currentMoment;
		final Collection<Procession> searchResults;
		final Date limit;
		Integer cacheTime;

		principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);

		finder = principal.getFinder();

		currentMoment = new Date();
		cacheTime = this.customisationService.find().getFinderCacheTime();
		limit = DateUtils.addHours(currentMoment, -cacheTime);

		if (finder.getLastRefreshed().before(limit)) {
			searchResults = this.searchProcessions(finder);
			finder.setResults(searchResults);
		}
		this.finderRepository.save(finder);
		this.finderRepository.flush();

		return finder;
	}

	public Finder clear(final Finder finder) {
		Member principal;
		final Date dateMin = null;
		final Date dateMax = null;
		String area = "";
		final String keyWord = "";
		Date currentMoment;
		final Collection<Procession> all;

		currentMoment = new Date(System.currentTimeMillis() - 1);

		principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);

		all = this.processionService.findAll();

		finder.setDateMin(dateMin);
		finder.setDateMax(dateMax);
		finder.setArea(area);
		finder.setKeyWord(keyWord);
		area = finder.getArea();
		finder.setLastRefreshed(currentMoment);
		finder.setResults(all);

		this.finderRepository.save(finder);
		this.finderRepository.flush();

		return finder;
	}
	public Integer countEmptyFinders() {
		Integer result;
		result = this.finderRepository.emptyFinders();

		return result;
	}
}
