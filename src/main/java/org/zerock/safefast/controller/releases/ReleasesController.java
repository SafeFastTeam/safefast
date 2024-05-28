//package org.zerock.safefast.controller.releases;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.zerock.safefast.dto.receive.ReleasesDTO;
//import org.zerock.safefast.entity.ProductionPlan;
//import org.zerock.safefast.service.releases.ReleasesService;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/receive")
//@RequiredArgsConstructor
//@Log4j2
//public class ReleasesController {
//
//    private final ReleasesService releasesService;
//
//    @PostMapping("/add")
//    public ResponseEntity<String> addRelease(@RequestBody ReleasesDTO releasesDTO) {
//        log.info("Received DTO: {}", releasesDTO);
//        try {
//            releasesService.addRelease(releasesDTO);
//            return ResponseEntity.ok("{\"message\": \"출고가 등록되었습니다.\"}");
//        } catch (Exception e) {
//            log.error("Error while adding release: ", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"출고 등록에 실패했습니다.\"}");
//        }
//    }
//}
