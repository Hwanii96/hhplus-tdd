package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;

import static org.mockito.Mockito.mock;

class PointServiceTest {

    private final UserPointTable userPointTable = mock(UserPointTable.class);
    private final PointHistoryTable pointHistoryTable = mock(PointHistoryTable.class);

    private final PointService pointService = new PointService(userPointTable, pointHistoryTable);




} // PointServiceTest
