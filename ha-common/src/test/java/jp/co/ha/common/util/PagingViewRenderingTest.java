package jp.co.ha.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import tools.jackson.databind.json.JsonMapper;

class PagingViewRenderingTest {

    // Dashboard の一覧画面と同じ件数表示・リンク制御を実際の式評価で確認する。
    private static final String TEMPLATE = """
            <div>
              <p id="range" th:text="${paging.totalRecordNum} + '件中 ' + ${paging.fromRecordNum} + '～' + ${paging.toRecordNum} + '件表示'"></p>
              <a id="first" class="link" th:classappend="!${paging.canGoFirst} ? 'disabled'" th:href="${paging.firstHref}">&lt;&lt;</a>
              <a id="previous" class="link" th:classappend="!${paging.canGoPrevious} ? 'disabled'" th:href="${paging.previousHref}">&lt;</a>
              <a id="next" class="link" th:classappend="!${paging.canGoNext} ? 'disabled'" th:href="${paging.nextHref}">&gt;</a>
              <a id="last" class="link" th:classappend="!${paging.canGoLast} ? 'disabled'" th:href="${paging.lastHref}">&gt;&gt;</a>
            </div>
            """;

    @Test
    void firstFullPageRendersForwardLinksAndCorrectRecordRange() {
        PagingView paging = PagingViewFactory.getPageView(
                PageRequest.of(0, 10), "/news?page", 20);

        String html = render(paging);

        assertTrue(html.contains("20件中 1～10件表示"));
        assertLink(html, "first", false, "/news?page=0");
        assertLink(html, "previous", false, "/news?page=0");
        assertLink(html, "next", true, "/news?page=1");
        assertLink(html, "last", true, "/news?page=1");
    }

    @Test
    void lastFullPageRendersBackwardLinksAndCorrectRecordRange() {
        PagingView paging = PagingViewFactory.getPageView(
                PageRequest.of(1, 10), "/news?page", 20);

        String html = render(paging);

        assertTrue(html.contains("20件中 11～20件表示"));
        assertLink(html, "first", true, "/news?page=0");
        assertLink(html, "previous", true, "/news?page=0");
        assertLink(html, "next", false, "/news?page=1");
        assertLink(html, "last", false, "/news?page=1");
    }

    @Test
    void emptyResultsRenderZeroRangeAndNoEnabledNavigation() {
        PagingView paging = PagingViewFactory.getPageView(
                PageRequest.of(4, 10), "/news?page", 0);

        String html = render(paging);

        assertTrue(html.contains("0件中 0～0件表示"));
        assertEquals(4, paging.getCurrentPageNum());
        assertLink(html, "first", false, "/news?page=0");
        assertLink(html, "previous", false, "/news?page=0");
        assertLink(html, "next", false, "/news?page=0");
        assertLink(html, "last", false, "/news?page=0");
    }

    @Test
    void outOfRangeResultsRenderEmptyRangeAndAValidRecoveryLink() {
        PagingView paging = PagingViewFactory.getPageView(
                PageRequest.of(5, 10), "/news?page", 23);

        String html = render(paging);

        assertTrue(html.contains("23件中 0～0件表示"));
        assertEquals(5, paging.getCurrentPageNum());
        assertLink(html, "first", true, "/news?page=0");
        assertLink(html, "previous", true, "/news?page=2");
        assertLink(html, "next", false, "/news?page=2");
        assertLink(html, "last", false, "/news?page=2");
    }

    @Test
    void jsonKeepsNumericPageContractAndLargeRecordOffsets() {
        JsonMapper mapper = JsonMapper.builder().build();
        PagingView normal = PagingViewFactory.getPageView(
                PageRequest.of(1, 10), "/news?page", 20);
        Map<?, ?> normalJson = mapper.readValue(normal.toString(), Map.class);

        assertNumber(normalJson, "current_page_num", 1);
        assertNumber(normalJson, "total_page", 2);
        assertNumber(normalJson, "record_per_page", 10);
        assertNumber(normalJson, "total_record_num", 20);
        assertNumber(normalJson, "from_record_num", 11);
        assertNumber(normalJson, "to_record_num", 20);

        PagingView large = PagingViewFactory.getPageView(
                PageRequest.of(300_000_000, 10), "/news?page", 3_000_000_025L);
        Map<?, ?> largeJson = mapper.readValue(large.toString(), Map.class);

        assertNumber(largeJson, "current_page_num", 300_000_000);
        assertNumber(largeJson, "total_page", 300_000_003);
        assertNumber(largeJson, "record_per_page", 10);
        assertNumber(largeJson, "total_record_num", 3_000_000_025L);
        assertNumber(largeJson, "from_record_num", 3_000_000_001L);
        assertNumber(largeJson, "to_record_num", 3_000_000_010L);
        assertTrue(render(large).contains("3000000025件中 3000000001～3000000010件表示"));
    }

    private static String render(PagingView paging) {
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        TemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(resolver);
        Context context = new Context();
        context.setVariable("paging", paging);
        return engine.process(TEMPLATE, context);
    }

    private static void assertNumber(Map<?, ?> json, String name, long expected) {
        Object value = json.get(name);
        assertTrue(value instanceof Number, name + " must remain a JSON number");
        assertEquals(expected, ((Number) value).longValue(), name);
    }

    private static void assertLink(String html, String id, boolean enabled, String href) {
        String tag = link(html, id);
        assertEquals(!enabled, tag.contains("disabled"), id + " link visibility");
        assertTrue(tag.contains("href=\"" + href + "\""), id + " href: " + tag);
    }

    private static String link(String html, String id) {
        Matcher matcher = Pattern.compile("<a id=\"" + id + "\"[^>]*>").matcher(html);
        assertTrue(matcher.find(), id + " link must be rendered");
        return matcher.group();
    }
}
