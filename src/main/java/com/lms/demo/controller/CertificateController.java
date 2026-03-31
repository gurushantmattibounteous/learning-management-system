package com.lms.demo.controller;



import com.lms.demo.dto.CertificateDTO;
import com.lms.demo.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping("/my")
    public ResponseEntity<List<CertificateDTO>> getMyCertificates() {
        return ResponseEntity.ok(certificateService.getMyCertificates());
    }

    @PostMapping("/generate/{courseId}")
    public ResponseEntity<CertificateDTO> generate(@PathVariable Long courseId) {
        return ResponseEntity.ok(certificateService.generate(courseId));
    }
}
