import http from 'k6/http';
import { sleep, check } from 'k6';

export let options = {
  scenarios: {
    token_test: {
      exec: 'tokenTest',
      executor: 'per-vu-iterations', // 각 vu가 iteration만큼 활동
      vus: 5000,
      iterations: 1,
      maxDuration: '3m',
    },
  }
}

export function tokenTest() {
  let token = issueToken();
  if (token) {
    pollingToken(token);
  }
}



export function issueToken() {
  let option = {
    headers: {
      Connection: 'keep-alive',
    },
  }
  const res = http.post('http://concert-app:8080/waiting',null,option);

  const issued = check(res, {
    '대기열_토큰_발급': (r) => r.status === 200,
  });

  if (issued) {
    return res.json().token;
  }
  return false;
}

export function pollingToken(token) {
  console.log(token);
  let waiting = true;
  let option = {
    headers: {
      Connection: 'keep-alive',
      'Hh-Waiting-Token': token,
    },
  };


  // 상태 확인 요청 반복
  while (waiting) {
    sleep(1)
    const res = http.get('http://concert-app:8080/waiting', option);

    // 상태 확인
    const success = check(res, {
      '상태_조회_성공': (r) => r.status === 200,
    });

    if (success) {
      const resJson = res.json();
      waiting = resJson.status !== 'ACTIVE';
    } else {
      return ;
    }
  }
}


