package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SharedCodeService {
    private final SharedCodeRepository sharedCodeRepository;

    @Autowired
    public SharedCodeService(SharedCodeRepository sharedCodeRepository) {
        this.sharedCodeRepository = sharedCodeRepository;
    }

    public SharedCode save(SharedCode code) {
        return sharedCodeRepository.save(code);
    }

    public SharedCode findSharedCodeById(String uuid) {
        return sharedCodeRepository.findById(uuid).get();
    }

    public List<SharedCode> findTop10ByOrderByDateDesc() {
        List<SharedCode> top10 =
                sharedCodeRepository.findByTimeEqualsAndViewsEqualsOrderByDateDesc((long) 0, (long) 0);
        if (top10.size() < 10) {
            return top10;
        } else {
            return top10.subList(0, 10);
        }
    }
}
