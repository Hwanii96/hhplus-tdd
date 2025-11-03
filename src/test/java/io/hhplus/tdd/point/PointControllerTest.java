package io.hhplus.tdd.point;

import io.hhplus.tdd.ApiControllerAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;

@WebMvcTest(PointController.class)
@Import(ApiControllerAdvice.class)
class PointControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointService pointService;

/**
 * 유저 ID 형식 검증
 * 잘못된 형식의 유저 ID로 포인트를 조회할 때 예외처리가 정상적으로 수행되는지를 검증
 */

/**
 * 특정 유저의 포인트 조회
 * 유저의 포인트가 정상적으로 조회되는지 검증
 */

/**
 * 특정 유저의 포인트 충전 또는 이용 내역 조회
 * 유저의 포인트 충전 및 이용 내역이 정상적으로 조회되는지 검증
 */

/**
 * 특정 유저의 포인트 충전
 * 유저의 포인트가 정상적으로 충전되는지 검증
 */

/**
 * 특정 유저의 포인트 이용
 * 유저의 포인트가 정상적으로 이용되는지 검증
 */

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
