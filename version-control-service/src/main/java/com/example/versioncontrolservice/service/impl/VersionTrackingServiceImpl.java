package com.example.versioncontrolservice.service.impl;

import com.example.versioncontrolservice.Dto.Article;
import com.example.versioncontrolservice.Dto.VersionTrackingResponse;
import com.example.versioncontrolservice.entity.Status;
import com.example.versioncontrolservice.exception.GlobalExceptionHandler;
import com.example.versioncontrolservice.openFeign.ArticleFeignClient;
import com.example.versioncontrolservice.repository.VersionTrackingRepo;
import com.example.versioncontrolservice.service.VersionTrackingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class VersionTrackingServiceImpl implements VersionTrackingService {
    private final VersionTrackingRepo versionTrackingRepo;

    private final ArticleFeignClient articleFeignClient;

    @Override
    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "getDefaultArticle")

    public List<Article> trackVersionByArticleId(String arcticleId) {

        List<Article> articles = articleFeignClient.getArticleById(arcticleId).getBody();
        if(Objects.nonNull(articles)) {
            return articles;
        }
        else{
            throw new GlobalExceptionHandler(String.format("Invalid ID provided!!"));
        }
    }

    @Override
    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "getDefaultCompareVersion")

    public List<Article> compareArticle(String articleId, Integer versionId1, Integer versionId2) {

        List<Article> response = articleFeignClient.compareArticles(articleId, versionId1, versionId2).getBody();
        if (Objects.nonNull(response)) {
            return response;
        } else {

            throw new GlobalExceptionHandler(String.format("Invalid Id!!"));
        }
    }
    public List<Article> getDefaultArticle(String articleId, Exception exception) {

        Article article1 = Article.builder()
                .articleId("Dummy Id")
                .content("Dummy Content")
                .currentVersionId(1)
                .title("Dummy Title")
                .srNo(1L)
                .status(Status.approved)
                .build();

        Article article2 = Article.builder()
                .articleId("Dummy Id2")
                .content("Dummy Content2")
                .currentVersionId(2)
                .title("Dummy Title")
                .srNo(2L)
                .status(Status.approved)
                .build();

        List<Article> articleDummy = Arrays.asList(article1, article2);


        return articleDummy;
    }

    public List<Article> getDefaultCompareVersion(String articleId, Integer versionId1, Integer versionId2, Exception exception){
        Article article1 = Article.builder()
                .articleId("Dummy Id")
                .content("Dummy Content")
                .currentVersionId(versionId1)
                .title("Dummy Title")
                .srNo(1L)
                .status(Status.approved)
                .build();
        Article article2 = Article.builder()
                .articleId("Dummy Id2")
                .content("Dummy Content2")
                .currentVersionId(versionId2)
                .title("Dummy Title")
                .srNo(2L)
                .status(Status.approved)
                .build();

                List<Article> articleList= Arrays.asList(article1, article2);
        return articleList;
    }




}
