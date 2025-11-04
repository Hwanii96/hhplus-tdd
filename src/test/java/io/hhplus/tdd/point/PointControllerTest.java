package io.hhplus.tdd.point;

import io.hhplus.tdd.ApiControllerAdvice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PointController.class)
@Import(ApiControllerAdvice.class)
class PointControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointService pointService;

    /**
     * 특정 유저의 포인트 조회
     * 유저의 포인트가 정상적으로 조회되는지 검증
     */
    @Test
    @DisplayName("GET /point/{id}")
    void 유저_ID로_포인트_조회() throws Exception {

        long id = 1L;
        long forTestCurTime = System.currentTimeMillis();

        when(pointService.searchPoint(id)).thenReturn(new UserPoint(id, 77777, forTestCurTime));

        mockMvc.perform(get("/point/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.point").value(77777))
                .andExpect(jsonPath("$.updateMillis").value(forTestCurTime));

        verify(pointService).searchPoint(id);

    }

    /**
     * 유저 ID 형식 검증
     * 잘못된 형식의 유저 ID로 포인트를 조회할 때 예외처리가 정상적으로 수행되는지를 검증
     */
    @Test
    @DisplayName("GET /point/{id}")
    void 잘못된_형식의_유저_ID로_포인트_조회() throws Exception {

        String forTestWrongFormatId = "test01";

        mockMvc.perform(get("/point/" + forTestWrongFormatId))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verifyNoInteractions(pointService);

    }

    /**
     * 특정 유저의 포인트 충전 또는 이용 내역 조회
     * 유저의 포인트 충전 및 이용 내역이 정상적으로 조회되는지 검증
     */
    @Test
    @DisplayName("GET /point/{id}/histories")
    void 유저_ID로_포인트_충전_및_이용_내역_조회() throws Exception {

        long id = 3L;
        long forTestCurTime1 = System.currentTimeMillis();
        long forTestCurTime2 = System.currentTimeMillis();

        var pointHistory1 = new PointHistory(1, id, 77777, TransactionType.CHARGE, forTestCurTime1);
        var pointHistory2 = new PointHistory(2, id, 7777, TransactionType.USE, forTestCurTime2);

        List<PointHistory> pointHistoryList = new ArrayList<>();
        pointHistoryList.add(pointHistory1);
        pointHistoryList.add(pointHistory2);

        when(pointService.searchHistoriesPoint(id)).thenReturn(pointHistoryList);

        mockMvc.perform(get("/point/" + id + "/histories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].userId").value(id))
                .andExpect(jsonPath("$[0].amount").value(77777))
                .andExpect(jsonPath("$[0].type").value(TransactionType.CHARGE.name()))
                .andExpect(jsonPath("$[0].updateMillis").value(forTestCurTime1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].userId").value(id))
                .andExpect(jsonPath("$[1].amount").value(7777))
                .andExpect(jsonPath("$[1].type").value(TransactionType.USE.name()))
                .andExpect(jsonPath("$[1].updateMillis").value(forTestCurTime2));

        verify(pointService).searchHistoriesPoint(id);

    }

    /**
     * 특정 유저의 포인트 충전
     * 유저의 포인트가 정상적으로 충전되는지 검증
     */
    @Test
    @DisplayName("PATCH /point/{id}/charge")
    void 특정_유저의_포인트_충전() throws Exception {

        long id = 4L;
        long forTestCurTime = System.currentTimeMillis();

        when(pointService.chargePoint(id, 77777)).thenReturn(new UserPoint(id, 77777, forTestCurTime));

        mockMvc.perform(patch("/point/" + id + "/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content("77777")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.point").value(77777))
                .andExpect(jsonPath("$.updateMillis").value(forTestCurTime));

        verify(pointService).chargePoint(id,77777);

    }

    /**
     * 특정 유저가 포인트 충전 시 허용 가능한 금액을 초과한 경우
     * 유저의 충전 금액이 허용 가능한 금액을 초과하는 경우 예외 처리 검증
     */
    @Test
    @DisplayName("")
    void 특정_유저가_포인트_충전_시_허용_가능한_금액을_초과한_경우() throws Exception {

    }
    
    /**
     * 특정 유저의 포인트 이용
     * 유저의 포인트가 정상적으로 이용되는지 검증
     */
    @Test
    @DisplayName("PATCH /point/{id}/use")
    void 특정_유저의_포인트_이용() throws Exception {

        long id = 5L;
        long forTestCurTime = System.currentTimeMillis();

        when(pointService.usePoint(id, 777)).thenReturn(UserPoint.empty(id));

        mockMvc.perform(patch("/point/" + id + "/use")
                .contentType(MediaType.APPLICATION_JSON)
                .content("777")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.point").value(0));
                // .andExpect(jsonPath("$.updateMillis").value(forTestCurTime));

        verify(pointService).usePoint(id,777);

    }

    /**
     * 특정 유저가 포인트 이용 시 금액이 부족한 경우
     * 유저가 보유한 금액이 부족한 경우 예외 처리 검증
     */
    @Test
    @DisplayName("")
    void 특정_유저가_포인트_이용_시_금액이_부족한_경우() throws Exception {

    }

    /**
     * 유저 포인트 동시 충전
     * 유저의 포인트를 동시에 충전할 때 동시성 문제 검증
     */
    
    /**
     * 유저 포인트 동시 이용
     * 유저의 포인트를 동시에 이용할 때 동시성 문제 검증
     */
    
    /**
     * 유저 포인트 동시 충전 및 이용
     * 유저의 포인트를 동시에 충전 및 이용할 때 동시성 문제 검증
     */
    
} // PointControllerTest
