package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PointServiceTest {

    private final UserPointTable userPointTable = mock(UserPointTable.class);
    private final PointHistoryTable pointHistoryTable = mock(PointHistoryTable.class);

    private final PointService pointService = new PointService(userPointTable, pointHistoryTable);

    @Test
    @DisplayName("")
    void 유저_포인트_조회_시_포인트가_없는_경우_기본_값으로_0포인트_반환() {

        long id = 1L;

        when(userPointTable.selectById(id)).thenReturn(UserPoint.empty(id));

        UserPoint userPoint = pointService.searchPoint(id);

        assertEquals(id, userPoint.id());
        assertEquals(0, userPoint.point());

        verify(userPointTable).selectById(id);
        verifyNoInteractions(pointHistoryTable);

    }



} // PointServiceTest
