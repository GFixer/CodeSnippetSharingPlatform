package platform;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SharedCodeRepository extends CrudRepository<SharedCode, Integer> {
    Optional<SharedCode> findById(String uuid);

    List<SharedCode> findTop10ByOrderByDateDesc();

    List<SharedCode> findByTimeEqualsAndViewsEqualsOrderByDateDesc(Long time, Long views);

    void deleteById(Integer id);

}
