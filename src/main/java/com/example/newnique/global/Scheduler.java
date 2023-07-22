package com.example.newnique.global;

import com.example.newnique.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final NewsRepository newsRepository;

    @Scheduled(cron = "*/10 * * * * *") // 매일 새벽 1시+
    public void updateNews() throws InterruptedException {
        log.info("오늘의 뉴스 업데이트 ");
        // 크롤링 ㄱㄱ

        try {
            // 원하는 뉴스 사이트의 URL을 지정
            String url = "https://www.sedaily.com/NewsMain/GA/1";

            // Jsoup을 이용하여 웹 페이지에 접속하고 데이터를 가져옴
            Document doc = Jsoup.connect(url).get();

            // 뉴스 기사를 선택할 CSS 선택자를 사용하여 가져오기
            Elements newsHeadlines = doc.select(".news-article");

            // 가져온 뉴스 기사를 원하는 형태로 처리 또는 저장
            for (Element headline : newsHeadlines) {
                String title = headline.select(".title").text();
                String content = headline.select(".content").text();

                // 여기서는 간단히 콘솔에 출력하는 것으로 예시
                System.out.println("Title: " + title);
                System.out.println("Content: " + content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



        newsRepository.save(null); // 크롤링 해온 정보가 들어감

    }
}
