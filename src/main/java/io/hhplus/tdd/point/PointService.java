package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {

    private final UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;

    public PointService(UserPointTable userPointTable, PointHistoryTable pointHistoryTable) {
        this.userPointTable = userPointTable;
        this.pointHistoryTable = pointHistoryTable;
    }

    public UserPoint searchPoint(long id) {

        UserPoint userPoint = userPointTable.selectById(id);

        return userPoint;

    } // searchPoint()

    public List<PointHistory> searchHistoriesPoint(long id) {

        List<PointHistory> pointHistoryList = pointHistoryTable.selectAllByUserId(id);

        return pointHistoryList;

    } // searchHistoriesPoint()

    public UserPoint chargePoint(long id, long amount) {

        validateAmount(amount);

        UserPoint currentUserPoint = userPointTable.selectById(id);

        long curUsrPoint = currentUserPoint.point();

        if(Long.MAX_VALUE < curUsrPoint + amount) {
            throw new IllegalArgumentException("충전 후 포인트는 표현 가능한 최대값을 초과할 수 없습니다.");
        }

        long updatePoint = curUsrPoint + amount;

        UserPoint chargedUserPoint = userPointTable.insertOrUpdate(id, updatePoint);

        PointHistory pointHistory = pointHistoryTable.insert(id, amount, TransactionType.CHARGE, System.currentTimeMillis());

        return chargedUserPoint;

    } // chargePoint()

    public UserPoint usePoint(long id, long amount) {

        validateAmount(amount);

        UserPoint currentUserPoint = userPointTable.selectById(id);

        long curUsrPoint = currentUserPoint.point();

        if(curUsrPoint <= 0L || curUsrPoint < amount) {
            throw new InsufficientPointException();
        }

        long updatePoint = curUsrPoint - amount;

        UserPoint usedUserPoint = userPointTable.insertOrUpdate(id, updatePoint);

        PointHistory pointHistory = pointHistoryTable.insert(id, amount, TransactionType.USE, System.currentTimeMillis());

        return usedUserPoint;

    } // usePoint()

    private void validateAmount(long amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("금액은 0보다 커야 합니다.");
        }

    } // validateAmount()

} // PointService
