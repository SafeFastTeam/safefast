package org.zerock.safefast.service.releases;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.page.PageRequestDTO;
import org.zerock.safefast.dto.page.PageResultDTO;
import org.zerock.safefast.dto.receive.ReleasesDTO;
import org.zerock.safefast.entity.*;
import org.zerock.safefast.repository.*;

import java.time.LocalDate;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReleasesService {
    private final ReleasesRepository releasesRepository;
    private final ItemRepository itemRepository;
    private final QuantityRepository quantityRepository;
    private final ProductionPlanRepository productionPlanRepository;
    private final ProcurementPlanRepository procurementPlanRepository;

//    public void registerReleases(Releases releases) {
//        log.info("Registering new releases: {}", releases);
//
//        releases.setReleaseDate(LocalDate.now());
//
//        // Release 저장
//        releasesRepository.save(releases);
//        log.info("Release entity saved successfully: {}", releases);
//
//        // Quantity 엔티티 업데이트
//        updateQuantity(releases.getItem(), releases.getReleaseQuantity());
//    }

    public void addRelease(ReleasesDTO releasesDTO) {
        log.info("Adding new release: {}", releasesDTO);

        Item item = itemRepository.findById(releasesDTO.getItemCode())
                .orElseThrow(() -> new EntityNotFoundException("번호를 찾을 수 없습니다."));

        Quantity quantity = quantityRepository.findById(releasesDTO.getQuantityId())
                .orElseThrow(() -> new EntityNotFoundException("번호를 찾을 수 없습니다."));

        Releases releases = new Releases();
        releases.setReleaseDate(LocalDate.now());
        releases.setReleaseNumber(releasesDTO.getReleaseNumber());
        releases.setReleaseQuantity(releasesDTO.getReleaseQuantity());
        releases.setQuantity(quantity);
        releases.setItem(item);

        try {
            releasesRepository.save(releases);
            log.info("Release entity saved successfully: {}", releases);
            updateQuantity(releases.getItem(), releases.getReleaseQuantity());
        } catch (Exception e) {
            log.error("Error while saving release or updating quantity", e);
            throw new RuntimeException("릴리스 저장 또는 수량 업데이트 중 오류가 발생했습니다.", e);
        }
    }

    // Quantity 엔티티 업데이트 메서드 추가
    private void updateQuantity(Item item, int quantity) {
        Quantity quantityEntity = quantityRepository.findByItem(item);
        quantityEntity.setAllQuantity(quantityEntity.getAllQuantity() - quantity);
        quantityRepository.save(quantityEntity);
        log.info("Quantity entity updated successfully: {}", quantityEntity);
    }

    public PageResultDTO<Quantity, Quantity> getListA(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("quantityId").descending());
        Page<Quantity> result = quantityRepository.findAll(pageable);
        Function<Quantity, Quantity> fn = Function.identity();
        return new PageResultDTO<>(result, fn);
    }


    public PageResultDTO<Releases, Releases> getListB(PageRequestDTO pageRequestDTO) {
        // 내림차순으로 정렬된 페이지 정보를 가져옵니다.
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("releaseNumber").descending());
        // 모든 항목을 가져오도록 수정합니다.
        Page<Releases> result = releasesRepository.findAll(pageable);
        Function<Releases, Releases> fn = Function.identity();
        return new PageResultDTO<>(result, fn);
    }



}
