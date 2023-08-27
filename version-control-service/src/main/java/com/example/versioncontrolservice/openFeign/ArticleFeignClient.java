package com.example.versioncontrolservice.openFeign;

import com.example.versioncontrolservice.Dto.Article;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="ARTICLE-SERVICE", path="/api/article")
public interface ArticleFeignClient {
    @GetMapping("/{articleId}")
    public ResponseEntity<List<Article>> getArticleById(@PathVariable String articleId);

    @GetMapping("/{articleId}/version1/{versionId1}/version2/{versionId2}")
    public ResponseEntity<List<Article>> compareArticles(@PathVariable String articleId, @PathVariable int versionId1, @PathVariable int versionId2);

}
