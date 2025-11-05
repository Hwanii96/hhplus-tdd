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

    /**
     * 특정 유저의 포인트 조회
     * @param id 포인트를 조회하기 위한 유저 ID
     * @return 조회한 유저의 포인트
     */
    public UserPoint searchPoint(long id) {

        UserPoint userPoint = userPointTable.selectById(id);

        return userPoint;

    } // searchPoint()

    /**
     * 특정 유저의 포인트 충전 또는 이용 내역 조회
     * @param id 포인트 충전 또는 이용 내역을 조회하기 위한 유저 ID
     * @return 조회한 유저 포인트 이용 내역
     */
    public List<PointHistory> searchHistoriesPoint(long id) {

        List<PointHistory> pointHistoryList = pointHistoryTable.selectAllByUserId(id);

        return pointHistoryList;

    } // searchHistoriesPoint()

    /**
     * 특정 유저의 포인트 충전
     * @param id 포인트를 충전하기 위한 유저 ID
     * @param amount 충전할 포인트 금액
     * @return 충전 후 유저 포인트
     */
    public UserPoint chargePoint(long id, long amount) {

        validateAmount(amount);

        UserPoint currentUserPoint = userPointTable.selectById(id);

        long curUsrPoint = currentUserPoint.point();

        // Long.MAX_VALUE < curUsrPoint + amount
        // Long.MAX_VALUE - curUsrPoint < amount
        // amount > Long.MAX_VALUE - curUsrPoint
        if(Long.MAX_VALUE - amount < curUsrPoint) {
            throw new IllegalArgumentException("충전 후 포인트는 표현 가능한 최대값을 초과할 수 없습니다.");
        }

        long updatePoint = curUsrPoint + amount;

        UserPoint chargedUserPoint = userPointTable.insertOrUpdate(id, updatePoint);

        pointHistoryTable.insert(id, amount, TransactionType.CHARGE, System.currentTimeMillis());

        return chargedUserPoint;

    } // chargePoint()

    /**
     * 특정 유저의 포인트 이용
     * @param id 포인트를 이용하기 위한 유저 ID
     * @param amount 이용할 포인트 금액
     * @return 이용 후 유저 포인트
     */
    public UserPoint usePoint(long id, long amount) {

        validateAmount(amount);

        UserPoint currentUserPoint = userPointTable.selectById(id);

        long curUsrPoint = currentUserPoint.point();

        if(curUsrPoint <= 0L || curUsrPoint < amount) {
            throw new InsufficientPointException("잔고가 충분하지 않아 포인트 이용이 불가능합니다.");
        }

        long updatePoint = curUsrPoint - amount;

        UserPoint usedUserPoint = userPointTable.insertOrUpdate(id, updatePoint);

        pointHistoryTable.insert(id, amount, TransactionType.USE, System.currentTimeMillis());

        return usedUserPoint;

    } // usePoint()

    /**
     * 포인트 충전 또는 이용 시 포인트 금액 검증
     * @param amount 충전 또는 이용할 포인트 금액
     */
    private void validateAmount(long amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("금액이 0보다 크지 않아 충전 또는 이용이 불가능합니다.");
        }

    } // validateAmount()

} // PointService
