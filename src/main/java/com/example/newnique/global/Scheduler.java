package com.example.newnique.global;

import com.example.newnique.news.entity.News;
import com.example.newnique.news.repository.NewsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final NewsRepository newsRepository;

    public class Category {
        private String category;
        private String link;

        public Category(String category, String link) {
            this.category = category;
            this.link = link;
        }

        public String getCategory() {
            return category;
        }

        public String getLink() {
            return link;
        }
    }



    @PostConstruct
    public void init() throws InterruptedException {
        updateNews(); // 프로그램 시작 시에 한 번 실행(테스트 용도)
    }

    @Scheduled(cron = "0 0 9,21 * * ?")
    public void updateNews() throws InterruptedException {
        log.info("오늘의 뉴스 업데이트 ");
        // 크롤링 ㄱㄱ
        try {
            // 원하는 뉴스 사이트의 URL을 지정
            String url = "https://www.sedaily.com/";

            List<String> mainCategories = Arrays.asList("증권", "부동산", "경제 · 금융", "산업","정치","사회","국제","오피니언","문화 · 스포츠","서경");

            Document doc = Jsoup.connect(url).get();

            // 원하는 카테고리 목록을 감싸고 있는 ul 태그 선택
            Element ulCategory = doc.select("ul.dep1").first();

            List<Category>categoryLinkList = new ArrayList<>();

            if (ulCategory != null) {
                // li 태그들 선택
                Elements categoryList = ulCategory.select("li");


                // 각 li 태그에 대해 a 태그의 href 속성 값을 가져와 출력
                for (Element category : categoryList) {
                    Element aTag = category.selectFirst("a");
                    if (aTag != null) {
                        String categoryName = aTag.text();

                        // 대표적인 카테고리 목록에 해당하는 경우에만 출력
                        if (mainCategories.contains(categoryName)) {
                            String categoryLink = aTag.attr("href");
                            categoryLinkList.add(new Category(categoryName,categoryLink));
                            // 각 카테고리 링크 리스트에 추가

                            System.out.println("Category: " + categoryName);
                            System.out.println("Link: " + categoryLink);

                            System.out.println();
                        }
                    }
                }
                // 카테고리들 링크 수집
            }

            //오늘 뉴스 기사 링크
            List<Category> todaysNewsLinkList = new ArrayList<>();

            //수집한 카테고리 링크로 들어가서 오늘 올라온 기사 링크 수집
            for(Category categoryLinkPair : categoryLinkList) {
                Document category_doc = Jsoup.connect(categoryLinkPair.getLink()).get();

                Element ulNews = category_doc.select("ul.sub_news_list").first();
                System.out.println("지금 카테고리는 "+ categoryLinkPair.getCategory());

                Elements newsList = ulNews.select("li");

                //각 카테고리 중 오늘 기사만 뽑아서 링크 뽑자.
                if (newsList != null) {
                    for (Element news : newsList) {
                        Element textAreaDiv = news.select("div.text_area").first();
                        if (textAreaDiv != null) {
                            Element aTag = textAreaDiv.select("a").first();
                            Element dateSpan = textAreaDiv.select("span.date").first();

                            if (aTag != null && dateSpan != null) {
                                String date = dateSpan.text();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                                LocalDate newsDate = LocalDate.parse(date, formatter);

                                // If the news was posted today
                                if (newsDate.equals(LocalDate.now())) {
                                    String newsLink = aTag.attr("href");
                                    todaysNewsLinkList.add(new Category(categoryLinkPair.getCategory(),newsLink));
                                    System.out.println(newsLink);
                                }else{
                                    break;
                                }
                            }
                        }
                    }
                }
            }


            //오늘 올라온 기사 상세정보 저장
            for(Category newsDetailsLinkPair : todaysNewsLinkList){

                Document newsDetails_doc = Jsoup.connect(newsDetailsLinkPair.getLink()).get();

                String newsTitle = newsDetails_doc.select("#v-left-scroll-in > div.article_head > h1").text();
                String newsSummary = newsDetails_doc.select("#v-left-scroll-in > div.article_con > div.con_left > div.article_summary").text();

                Elements imgs = newsDetails_doc.select("div.article_view img");
                Elements texts = newsDetails_doc.select("div.article_view");
                Elements newsInfo = newsDetails_doc.select("div.article_info");

                Element dateSpan = newsInfo.select("span.url_txt").get(1);
                String date = dateSpan.text().substring(3,13);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                LocalDate newsDate = LocalDate.parse(date, formatter);

                String firstImageUrl = "";
                if(imgs.first() != null) {
                    firstImageUrl = imgs.first().absUrl("src");  // 첫 번째 이미지 URL을 가져옵니다.
                }

                // 이미지와 관련된 모든 요소를 제거합니다.
                Elements figureElements = newsDetails_doc.select("figure");
                Elements imgElements = newsDetails_doc.select("img");
                Elements scriptElements = newsDetails_doc.select("script");

                for (Element element : figureElements) {
                    element.remove();
                }
                for (Element element : imgElements) {
                    element.remove();
                }
                for (Element element : scriptElements) {
                    element.remove();
                }

                String content = texts.text();  // 본문 내용을 가져옵니다.

                newsRepository.save(new News(newsTitle, content, firstImageUrl,
                        newsDate, newsDetailsLinkPair.getCategory(),newsSummary));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }



//        newsRepository.save(null); // 크롤링 해온 정보가 들어감

    }
}
