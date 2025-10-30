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

    /**
     * 유저의 포인트 데이터가 존재하지 않는 경우 기본 값으로 0포인트가 반환되는지를 검증하기 위함
     */
    @Test
    @DisplayName("포인트 데이터가 없는 경우 0포인트 반환")
    void 유저_포인트_조회_시_포인트가_없는_경우_기본_값으로_0포인트_반환() {

        long id = 1L;

        when(userPointTable.selectById(id)).thenReturn(UserPoint.empty(id));

        UserPoint userPoint = pointService.searchPoint(id);

        assertEquals(id, userPoint.id());
        assertEquals(0, userPoint.point());

        verify(userPointTable).selectById(id);
        verifyNoInteractions(pointHistoryTable);

    }

    /**
     * 유저의 포인트 데이터가 존재하는 경우 정상적으로 포인트가 반환되는지를 검증하기 위함
     */

    /**
     * 유저의 포인트 충전 또는 이용 내역 조회 시 내역이 정상적으로 반환되는지를 검증하기 위함
     */

    /**
     * 유저의 포인트 충전 또는 이용 내역 조회 시 데이터가 없을 경우 빈 결과가 정상적으로 반환되는지를 검증하기 위함
     */

    /**
     * 유저가 포인트 충전 시 충전 금액이 0원보다 같거나 작은 경우 충전이 되지 않는지를 검증하기 위함
     */

    /**
     * 유저가 포인트 충전 시 금액이 허용 가능한 금액을 초과하는 경우 충전이 되지 않는지를 검증하기 위함
     */

    /**
     * 유저가 포인트 충전 시 정상적으로 충전되는지를 검증하기 위함
     */

    /**
     * 유저가 포인트 이용 시 사용 금액이 0원보다 같거나 작은 경우 이용이 되지 않는지를 검증하기 위함
     */

    /**
     * 유저가 포인트 이용 시 잔고가 부족한 경우 금액 이용이 되지 않는지를 검증하기 위함
     */

    /**
     * 유저가 포인트 이용 시 정상적으로 사용되는지를 검증하기 위함
     */

} // PointServiceTest
