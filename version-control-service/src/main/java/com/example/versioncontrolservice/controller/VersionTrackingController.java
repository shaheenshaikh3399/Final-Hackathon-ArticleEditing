package com.example.versioncontrolservice.controller;

import com.example.versioncontrolservice.Dto.Article;
import com.example.versioncontrolservice.Dto.VersionTrackingDto;
import com.example.versioncontrolservice.Dto.VersionTrackingResponse;
import com.example.versioncontrolservice.service.VersionTrackingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class VersionTrackingController {
    private final VersionTrackingService versionTrackingService;



    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<Article>> trackVersionByArticleId(@PathVariable String articleId){
        return ResponseEntity.ok(versionTrackingService.trackVersionByArticleId(articleId));
    }
    @GetMapping("/article/{articleId}/version1/{versionId1}/version2/{versionId2}")
    public ResponseEntity<List<Article>> compareArticle( @PathVariable String articleId ,@PathVariable Integer versionId1, @PathVariable Integer  versionId2 ){
        return ResponseEntity.ok(versionTrackingService.compareArticle(articleId, versionId1, versionId2));
    }

}
