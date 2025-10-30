package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PointServiceTest {

    private final UserPointTable userPointTable = mock(UserPointTable.class);
    private final PointHistoryTable pointHistoryTable = mock(PointHistoryTable.class);

    private final PointService pointService = new PointService(userPointTable, pointHistoryTable);

    /**
     * 잘못된 형식의 유저 ID로 포인트를 조회할 때 예외처리가 정상적으로 수행되는지를 검증하기 위함
     * 이건 ControllerTest에서 수행해야할듯 ?
     */
    /*
    @Test
    @DisplayName("유저 ID가 Long Type의 데이터가 아닌 경우 예외 반환")
    void 잘못된_유저_ID로_포인트_조회_시_예외_반환() {

        String id = "testId001";

        when(userPointTable.selectById(id)).thenReturn();

    }
    */

    /**
     * 유저의 포인트 데이터가 존재하지 않는 경우 기본 값으로 0포인트가 반환되는지를 검증하기 위함
     */
    @Test
    @DisplayName("포인트 데이터가 없는 경우 0포인트 반환")
    void 유저_포인트_조회_시_포인트가_없는_경우_기본_값으로_0포인트_반환() {
        
        long id = 1L;

        // selectById() 호출 시, empty(id)가 반환되도록 세팅
        when(userPointTable.selectById(id)).thenReturn(UserPoint.empty(id));

        // selectById() 를 호출
        UserPoint userPoint = pointService.searchPoint(id);

        assertEquals(id, userPoint.id());
        assertEquals(0, userPoint.point());

        verify(userPointTable).selectById(id);
        verifyNoInteractions(pointHistoryTable);

    }

    /**
     * 유저의 포인트 데이터가 존재하는 경우 정상적으로 포인트가 조회되는지를 검증하기 위함
     */
    @Test
    @DisplayName("해당 유저의 포인트 조회")
    void 유저_포인트_조회_시_해당_유저의_포인트_반환() {

        long id = 2L;

        // selectById() 호출 시, 사용자가 보유한 포인트가 반환되도록 세팅
        when(userPointTable.selectById(id)).thenReturn(new UserPoint(id, 77777L, System.currentTimeMillis()));

        // selectById() 를 호출
        UserPoint userPoint = pointService.searchPoint(id);

        assertEquals(77777L, userPoint.point());

        verify(userPointTable).selectById(id);
        verifyNoInteractions(pointHistoryTable);

    }

    /**
     * 유저의 포인트 충전 또는 이용 내역 조회 시 내역이 정상적으로 반환되는지를 검증하기 위함
     */
    @Test
    @DisplayName("해당 유저의 포인트 이력 반환")
    void 유저_포인트_내역_조회_시_해당_유저의_포인트_이력_반환() {

        long id = 3L;

        // 유저의 포인트 이력이 반환되는지를 검증하는 목적이므로, 포인트 이력 테이블에 데이터를 세팅하는게 아니라 단순하게 포인트 이력을 세팅해야 함
        var pointHistory1 = new PointHistory(1, id, 7777, TransactionType.CHARGE, System.currentTimeMillis());
        var pointHistory2 = new PointHistory(2, id, 777, TransactionType.USE, System.currentTimeMillis());

        List<PointHistory> pointHistoryList = new ArrayList<>();
        pointHistoryList.add(pointHistory1);
        pointHistoryList.add(pointHistory2);

        // selectAllByUserId() 호출 시, 사용자의 포인트 이력 데이터가 반환되도록 세팅
        when(pointHistoryTable.selectAllByUserId(id)).thenReturn(pointHistoryList);

        // selectAllByUserId() 를 호출
        List<PointHistory> resultPointHistoryList = pointService.searchHistoriesPoint(id);

        assertEquals(2, resultPointHistoryList.size());
        assertEquals(pointHistory1, resultPointHistoryList.get(0));
        assertEquals(pointHistory2, resultPointHistoryList.get(1));

        verify(pointHistoryTable).selectAllByUserId(id);
        verifyNoInteractions(userPointTable);

    }

    /**
     * 유저의 포인트 충전 또는 이용 내역 조회 시 데이터가 없을 경우 빈 결과가 정상적으로 반환되는지를 검증하기 위함
     */
    @Test
    @DisplayName("해당 유저의 포인트 이력이 없을 경우 빈 결과 반환")
    void 유저_포인트_내역이_없을_경우_빈_결과_반환() {

        long id = 4L;

        List<PointHistory> pointHistoryList = new ArrayList<>();
        
        // selectAllByUserId() 호출 시, 사용자의 포인트 이력 데이터가 없는 경우 빈 결과가 반환되도록 세팅
        when(pointHistoryTable.selectAllByUserId(id)).thenReturn(pointHistoryList);

        // selectAllByUserId() 를 호출
        List<PointHistory> resultPointHistoryList = pointService.searchHistoriesPoint(id);

        assertTrue(resultPointHistoryList.isEmpty());

        verify(pointHistoryTable).selectAllByUserId(id);
        verifyNoInteractions(userPointTable);

    }

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
