
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PlaceRepository;
import domain.Place;
import domain.Procession;
import domain.Request;

@Service
@Transactional
public class PlaceService {

	// Managed Repository

	@Autowired
	PlaceRepository		placeRepository;

	// Services
	@Autowired
	RequestService		requestService;
	@Autowired
	ProcessionService	processionService;


	public void delete(final Place place) {
		this.placeRepository.delete(place);
	}
	public Place create(final int processionId) {
		final Place result = new Place();
		int rowMax;
		int columMax;
		int r = 1;
		int c = 1;
		Collection<Request> requestOfProccesion;
		Procession procession;

		procession = this.processionService.findOne(processionId);
		rowMax = procession.getMaxRow();
		columMax = procession.getMaxColumn();

		requestOfProccesion = this.requestService.findAllByProcession(processionId);

		outerloop: for (r = 1; r <= rowMax; r++)
			for (c = 1; c <= columMax; c++)
				for (final Request rq : requestOfProccesion)
					if ((rq.getPlace().getcolumnP() != c) || (rq.getPlace().getrowP() != r))
						break outerloop;

		result.setcolumnP(c);
		result.setrowP(r);
		return result;
	}
	public void save(final Place place) {
		Place result;
		result = this.placeRepository.save(place);
		Assert.notNull(result);

	}
}
