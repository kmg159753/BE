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
        updateNews(); // 프로그램 시작 시에 한 번 실행
    }

    @Scheduled(cron = "0 0 9 * * ?")//
    public void updateNews() throws InterruptedException {
        log.info("오늘의 뉴스 업데이트 ");
        // 크롤링 ㄱㄱ

        try {
            // 원하는 뉴스 사이트의 URL을 지정
            String url = "https://www.sedaily.com/";

            List<String> mainCategories = Arrays.asList("증권", "부동산", "경제 · 금융", "산업");

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

            //수집한 카테고리 링크롣 들어가서 상세기사에 접속할것임
            for(Category categoryLinkPair : categoryLinkList) {
                Document category_doc = Jsoup.connect(categoryLinkPair.getLink()).get();

                Element ulNews = category_doc.select("ul.sub_news_list").first();

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


            for(Category newsDetailsLinkPair : todaysNewsLinkList){
                Document newsDetails_doc = Jsoup.connect(newsDetailsLinkPair.getLink()).get();

                String newsTitle = newsDetails_doc.select("#v-left-scroll-in > div.article_head > h1").text();

                Elements imgs = newsDetails_doc.select("div.article_view img");
                Elements texts = newsDetails_doc.select("div.article_view");
                Elements newsInfo = newsDetails_doc.select("div.article_info");

                Element dateSpan = newsInfo.select("span.url_txt").first();
                String date = dateSpan.text().substring(2,12);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate newsDate = LocalDate.parse(date, formatter);

                String firstImageUrl = imgs.first().absUrl("src");  // 첫 번째 이미지 URL을 가져옵니다.

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
                        newsDate, newsDetailsLinkPair.getCategory()));

//                System.out.println("기사 제목 " + newsTitle);
//                System.out.println("기사 작성 일자 : " + newsDate);
//                System.out.println("첫번쨰 이미지 : "  + firstImageUrl );
//                System.out.println("기사 본문 내용 : " + content);
//                System.out.println("카테고리 : " + newsDetailsLinkPair.getCategory());





            }

        } catch (IOException e) {
            e.printStackTrace();
        }



//        newsRepository.save(null); // 크롤링 해온 정보가 들어감

    }
}
