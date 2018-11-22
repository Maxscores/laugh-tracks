package laughtracks;

import org.springframework.data.repository.CrudRepository;

import laughtracks.models.Comedian;

public interface ComedianRepository extends CrudRepository<Comedian, Integer> {

}
