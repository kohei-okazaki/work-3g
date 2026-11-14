package jp.co.ha.common.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * ページングの境界条件と、取得対象ページに対応する表示情報を確認する。
 */
class PagingViewFactoryTest {

    private static final String PATH = "/news/list?page";

    @ParameterizedTest(name = "count={0}, size={1}, page={2}")
    @CsvSource({
            "0, 10, 0, 0, 0, 0, false, false, 0, 0, 0",
            "1, 10, 0, 1, 1, 1, false, false, 0, 0, 0",
            "10, 10, 0, 1, 1, 10, false, false, 0, 0, 0",
            "11, 10, 0, 2, 1, 10, false, true, 1, 0, 1",
            "20, 10, 0, 2, 1, 10, false, true, 1, 0, 1",
            "20, 10, 1, 2, 11, 20, true, false, 1, 0, 1",
            "21, 10, 1, 3, 11, 20, true, true, 2, 0, 2",
            "21, 10, 2, 3, 21, 21, true, false, 2, 1, 2",
            "30, 10, 1, 3, 11, 20, true, true, 2, 0, 2",
            "25, 5, 4, 5, 21, 25, true, false, 4, 3, 4",
            "26, 5, 4, 6, 21, 25, true, true, 5, 3, 5",
            "41, 20, 2, 3, 41, 41, true, false, 2, 1, 2",
            "20, 10, 2, 2, 0, 0, true, false, 1, 1, 1",
            "21, 10, 3, 3, 0, 0, true, false, 2, 2, 2",
            "23, 10, 5, 3, 0, 0, true, false, 2, 2, 2",
            "0, 10, 5, 0, 0, 0, false, false, 0, 0, 0"
    })
    void returnsConsistentPageInformation(long count, int size, int page,
            long totalPages, long from, long to, boolean hasPrevious, boolean hasNext,
            long lastPage, long previousPage, long nextPage) {

        PagingView paging = PagingViewFactory.getPageView(PageRequest.of(page, size),
                PATH, count);

        assertAll(
                () -> assertEquals(count, paging.getTotalRecordNum()),
                () -> assertEquals(totalPages, paging.getTotalPage()),
                () -> assertEquals(page, paging.getCurrentPageNum()),
                () -> assertEquals(size, paging.getRecordPerPage()),
                () -> assertEquals(from, paging.getFromRecordNum()),
                () -> assertEquals(to, paging.getToRecordNum()),
                () -> assertEquals(hasPrevious, paging.isCanGoFirst()),
                () -> assertEquals(hasPrevious, paging.isCanGoPrevious()),
                () -> assertEquals(hasNext, paging.isCanGoNext()),
                () -> assertEquals(hasNext, paging.isCanGoLast()),
                () -> assertEquals(PATH + "=0", paging.getFirstHref()),
                () -> assertEquals(PATH + "=" + lastPage, paging.getLastHref()),
                () -> assertEquals(PATH + "=" + previousPage, paging.getPreviousHref()),
                () -> assertEquals(PATH + "=" + nextPage, paging.getNextHref()));
    }

    @Test
    void keepsRecordPositionsBeyondIntegerRange() {
        PagingView paging = PagingViewFactory.getPageView(
                PageRequest.of(Integer.MAX_VALUE, 2), PATH, 4_294_967_296L);

        assertAll(
                () -> assertEquals(4_294_967_295L, paging.getFromRecordNum()),
                () -> assertEquals(4_294_967_296L, paging.getToRecordNum()),
                () -> assertEquals(2_147_483_648L, paging.getTotalPage()),
                () -> assertEquals(PATH + "=2147483647", paging.getLastHref()),
                () -> assertEquals(PATH + "=2147483647", paging.getNextHref()),
                () -> assertTrue(paging.isCanGoPrevious()),
                () -> assertFalse(paging.isCanGoNext()));
    }

    @Test
    void calculatesPageCountWithoutOverflowingLargeTotals() {
        PagingView paging = PagingViewFactory.getPageView(PageRequest.of(0, 10),
                PATH, Long.MAX_VALUE);

        assertAll(
                () -> assertEquals(922_337_203_685_477_581L, paging.getTotalPage()),
                () -> assertEquals(PATH + "=922337203685477580", paging.getLastHref()),
                () -> assertEquals(1L, paging.getFromRecordNum()),
                () -> assertEquals(10L, paging.getToRecordNum()),
                () -> assertTrue(paging.isCanGoNext()));
    }

    @Test
    void rejectsNegativeRecordCounts() {
        assertThrows(IllegalArgumentException.class, () -> PagingViewFactory
                .getPageView(PageRequest.of(0, 10), PATH, -1));
    }

    @Test
    void rejectsUnpagedRequests() {
        assertThrows(IllegalArgumentException.class, () -> PagingViewFactory
                .getPageView(Pageable.unpaged(), PATH, 20));
    }
}

