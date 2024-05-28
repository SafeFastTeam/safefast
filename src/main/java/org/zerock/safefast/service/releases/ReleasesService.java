package org.zerock.safefast.service.releases;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.receive.ReleasesDTO;
import org.zerock.safefast.entity.*;
import org.zerock.safefast.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReleasesService {
    private final ReleasesRepository releasesRepository;
    private final ItemRepository itemRepository;
    private final QuantityRepository quantityRepository;
    private final ProductionPlanRepository productionPlanRepository;
    private final ProcurementPlanRepository procurementPlanRepository;

    public void registerReleases(Releases releases) {
        log.info("Registering new releases: {}", releases);

        releases.setReleaseDate(LocalDate.now());

        // Release 저장
        releasesRepository.save(releases);
        log.info("Release entity saved successfully: {}", releases);

        // Quantity 엔티티 업데이트
        updateQuantity(releases.getItem(), releases.getReleaseQuantity());
    }

    // Quantity 엔티티 업데이트 메서드 추가
    private void updateQuantity(Item item, int quantity) {
        Quantity quantityEntity = quantityRepository.findByItem(item);
        quantityEntity.setAllQuantity(quantityEntity.getAllQuantity() - quantity);
        quantityRepository.save(quantityEntity);
        log.info("Quantity entity updated successfully: {}", quantityEntity);
    }

//    public List<Quantity> getAllQuantity() {
//        return quantityRepository.findAll();
//    }

}
