import http from 'k6/http';
import { sleep, check } from 'k6';

export let options = {
  scenarios: {
    reservation: {
      exec: 'reservation',
      executor: 'per-vu-iterations', // 각 vu가 iteration만큼 활동
      // vus: 2500,
      vus: 1,
      iterations: 1,
      maxDuration: '2m',
    },
  }
  // vus: 1
}

export function setup() {
  const res = http.post('http://localhost:8080/waiting');
  let token = res.json().token;
  console.log(token);
  sleep(2)
  let header = {
    headers: {
      'Hh-Waiting-Token': token,
    },
  };
  return { header };
}

function getRandomSeatId() {
  return Math.floor(Math.random() * 2000) + 1;
}

export function reservation(data) {
  console.log('예약 시작..')
  const { header } = data;
  const baseUrl = 'http://localhost:8080'

  // 1. 좌석조회
  const res = http.get(`${baseUrl}/concerts/1`,header);
  let success = check(res, {
    '좌석_조회_성공': (r) => r.status === 200,
  });
  if (!success) return ;


  // 2. 좌석예약 (최대 4번 시도)
  let trial = 0; // 시도 횟수
  const maxTrial = 4; // 최대 시도 횟수
  success = false;

  console.log(__VU);
  while (trial < maxTrial && !success) {
    trial++;
    const reserv_body = {
      seat_id: getRandomSeatId(),
      user_id: __VU,
    }
    console.log(`seat_id = ${reserv_body.seat_id}`)
    header['headers']['Content-Type'] = 'application/json'
    const res2 = http.post(`${baseUrl}/reservation`,JSON.stringify(reserv_body), header);
    let res2Json = JSON.stringify(res2);
    console.log(`result = ${res2Json}`)
    success = check(res2, {
      '좌석_예약_성공': (r) => r.status === 200,
    });
  }
  if (!success) return ;


  // 3. 결제
  const pay_body = {
    reservation_id: res2.json()['reservationId']
  }
  const res3 = http.post(`${baseUrl}/payment`,pay_body, header);
  check(res3, {
    '결제_성공': (r) => r.status === 200,
  });
};
