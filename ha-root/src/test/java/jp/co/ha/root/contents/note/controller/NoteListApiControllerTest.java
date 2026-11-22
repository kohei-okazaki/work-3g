package jp.co.ha.root.contents.note.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;

import jp.co.ha.business.db.crud.read.RootUserNoteInfoSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.root.config.ApplicationProperties;
import jp.co.ha.root.contents.note.request.NoteListApiRequest;

/**
 * メモ一覧とページングの総件数が、同じ管理者ユーザの有効なメモを対象とすることを確認する。
 */
class NoteListApiControllerTest {

    @ParameterizedTest
    @CsvSource({ "42,0,0", "42,7,2", "99,3,1" })
    void pagingUsesTheSameOwnerAsTheList(Long loginId, long activeCount,
            long expectedPages) throws Exception {
        RootUserNoteInfoSearchService search = mock(RootUserNoteInfoSearchService.class);
        when(search.findBySeqLoginId(eq(loginId), any(SelectOption.class)))
                .thenReturn(List.of());
        when(search.countBySeqLoginId(loginId)).thenReturn(activeCount);
        // 他ユーザや削除済みを含む総件数を使うと、応答のページ数が変わる。
        when(search.countBySeqLoginId(isNull())).thenReturn(99L);

        ApplicationProperties properties = new ApplicationProperties();
        properties.setNotePage(5);
        NoteListApiController controller = new NoteListApiController();
        ReflectionTestUtils.setField(controller, "searchService", search);
        ReflectionTestUtils.setField(controller, "applicationProperties", properties);

        var response = controller.index(new NoteListApiRequest(), loginId, 0);
        var body = response.getBody();
        assertNotNull(body);
        assertAll(
                () -> assertEquals(200, response.getStatusCode().value()),
                () -> assertTrue(body.getNoteList().isEmpty()),
                () -> assertEquals(activeCount, body.getPaging().getTotalRecordNum()),
                () -> assertEquals(expectedPages, body.getPaging().getTotalPage()),
                () -> assertEquals(5, body.getPaging().getRecordPerPage()));

        ArgumentCaptor<SelectOption> selection = ArgumentCaptor.forClass(SelectOption.class);
        verify(search).findBySeqLoginId(eq(loginId), selection.capture());
        assertEquals("SEQ_ROOT_USER_NOTE_INFO_ID DESC", selection.getValue().getOrderBy());
        assertEquals(5, selection.getValue().getPageable().getPageSize());
        verify(search).countBySeqLoginId(loginId);
        verify(search, never()).countBySeqLoginId(isNull());
    }
}
