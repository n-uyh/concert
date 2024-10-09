### 시퀀스 다이어그램

#### 유저 대기열 토큰 기능
```mermaid
---
title: 유저 대기열 토큰 발급 및 조회
---
sequenceDiagram 
    actor user as 사용자
    participant api as API
    participant waiting as 대기열(Waiting)
    participant db as DB

    user->>api: 토큰 요청(user_id)
    api->>waiting: 토큰 요청(user_id)
    waiting->>db: 기발급건 조회
    db-->>waiting: return 토큰정보
    
    
    alt 기발급건 없는 경우
        waiting->>waiting: 토큰생성
        waiting->>db: 토큰저장
        waiting-->>api: return 토큰정보
    else 기발급건 있는 경우
        db-->>waiting: return 
        alt 토큰상태 이용가능인 경우
            waiting-->>api: return 토큰정보
        else 토큰상태 대기인 경우
            waiting->>db: 대기상태 토큰 개수 요청
            db-->>waiting: 개수 반환
            waiting->>waiting: 토큰상태변환
            waiting-->>api: return 토큰정보
        else 토큰상태 만료인 경우
            waiting->>waiting: 토큰생성
            waiting->>db: 토큰저장
            waiting-->>api: return 토큰정보
        end
    end
    
    api-->>user: return 토큰정보 
```

#### 예약가능 날짜 조회
```mermaid
sequenceDiagram
    actor user as 사용자
    participant interceptor as 인터셉터
    participant api as API
    participant waiting as 대기열(Waiting)
    participant concert as 콘서트(Concert)
    participant db as DB
    
    user ->> api: request(token, from, end)
    api ->> waiting: token
    waiting ->> db: 토큰정보조회
    db -->> waiting: return 토큰정보
    
    break 토큰상태가 이용가능하지 않은 경우
        waiting -->> api: 토큰이용불가 exception
        api -->> user: 토큰이용불가 exception
    end
    
    api ->> concert: 공연정보조회(from,end)
    concert ->> db: 공연정보조회(from,end)
    db -->> concert: return 공연정보리스트
    concert -->> api: return 공연정보리스트
    api -->> user: return 공연정보리스트
```

#### 예약가능 좌석 조회
```mermaid
sequenceDiagram
    actor user as 사용자
    participant api as API
    participant waiting as 대기열(Waiting)
    participant concert as 콘서트(Concert)
    participant db as DB
    
    user ->> api: request(token, 공연정보 id)
    api ->> waiting: token
    waiting ->> db: 토큰정보조회
    db -->> waiting: return 토큰정보

    break 토큰상태가 이용가능하지 않은 경우
        waiting -->> api: 토큰이용불가 exception
        api -->> user: 토큰이용불가 exception
    end
    
    api ->> concert: 좌석정보조회(공연정보id)
    concert ->> db: 좌석정보조회(공연정보id)
    db -->> concert: return 좌석정보 리스트
    concert -->> api: return 좌석정보 리스트
    api -->> user: return 좌석정보 리스트
```

#### 좌석 예약 요청
```mermaid
sequenceDiagram
    actor user as 사용자
    participant api as API
    participant waiting as 대기열(Waiting)
    participant reservation as 좌석예약(Reservation)
    participant concert as 콘서트(Concert)
    participant db as DB
    
    user ->> api: request(token, 좌석id)
    api ->> waiting: token
    waiting ->> db: 토큰정보조회
    db -->> waiting: return 토큰정보
    
    break 토큰상태가 이용가능하지 않은 경우
        waiting -->> api: 토큰이용불가 exception
        api -->> user: 토큰이용불가 exception
    end
    
    api ->> reservation: 좌석예약(token, 좌석id)
    reservation ->> concert: 좌석정보(좌석id)
    concert ->> db: 좌석정보조회(좌석id)
    db -->> concert: return 좌석정보
    concert -->> reservation: return 좌석정보
    break 좌석이 점유된 경우
        reservation -->> api : 이미 선택된 좌석입니다 exception
        api -->> user : 이미 선택된 좌석입니다 exception
    end

    reservation ->> reservation: 예약정보 생성
    reservation ->> db: 예약정보 저장
    reservation ->> concert: 좌석점유정보업데이트
    concert ->> db: 좌석점유정보업데이트
    reservation -->> api: return 예약정보
    api -->> user: return 예약정보
```

#### 잔액 충전
```mermaid
sequenceDiagram
    actor user as 사용자
    participant api as API
    participant waiting as 대기열(Waiting)
    participant userDomain as 사용자(User)
    participant db as DB
    
    user ->> api: request(token, 충전금액)
    api ->> waiting: token
    waiting ->> db: 토큰정보조회
    db -->> waiting: return 토큰정보
    
    break 토큰상태가 이용가능하지 않은 경우
        waiting -->> api: 토큰이용불가 exception
        api -->> user: 토큰이용불가 exception
    end
    
    api ->> userDomain: 사용자조회(token)
    userDomain ->> db: 사용자조회(token)
    db -->> userDomain: 사용자정보 반환
    userDomain ->> userDomain: 포인트충전
    userDomain ->> db: 포인트 이력저장
    userDomain ->> db: 사용자 가용포인트 업데이트
    userDomain -->> api: return 사용자포인트 정보
    api -->> user: return 사용자포인트 정보
```

#### 잔액 조회
```mermaid
sequenceDiagram
    actor user as 사용자
    participant api as API
    participant waiting as 대기열(Waiting)
    participant userDomain as 사용자(User)
    participant db as DB

    user ->> api: request(token)
    api ->> waiting: token
    waiting ->> db: 토큰정보조회
    db -->> waiting: return 토큰정보
    
    break 토큰상태가 이용가능하지 않은 경우
        waiting -->> api: 토큰이용불가 exception
        api -->> user: 토큰이용불가 exception
    end
    
    api ->> userDomain: 포인트조회(token)
    userDomain ->> db: 사용자 포인트조회(token)
    db -->> userDomain: return 사용자포인트정보
    userDomain -->> api: return 사용자포인트정보
    api -->> user: return 사용자포인트정보
```

#### 결제
```mermaid
sequenceDiagram
    actor user as 사용자
    participant api as API
    participant waiting as 대기열(Waiting)
    participant reservation as 예약(Reservation)
    participant userDomain as 사용자(User)
    participant db as DB

    user ->> api: request(token, 좌석예약 id)
    api ->> waiting: token
    waiting ->> db: 토큰정보조회
    db -->> waiting: return 토큰정보

    break 토큰상태가 이용가능하지 않은 경우
        waiting -->> api: 토큰이용불가 exception
        api -->> user: 토큰이용불가 exception
    end
    
    api ->> reservation: 결제(token, 좌석예약 id)
    reservation ->> db: 예약내역조회(좌석예약 id)
    db -->> reservation: return 예약정보
    
    
    alt 예약내역의 상태가 취소인 경우
        reservation -->> api: 결제불가능 exception
        api -->> user: 결제불가능 exception
    else 예약내역의 상태가 완료인 경우
        reservation -->> api: 이미 결제 완료 exception
        api -->> user: 이미 결제 완료 exception
    else 예약내역의 상태가 대기인 경우
        reservation ->> userDomain: 포인트차감(token, 결제금액)
        userDomain ->> db: 사용자조회(token)
        db -->> userDomain: return 사용자정보
        
        break reservation의 사용자 id와 토큰으로 조회한 사용자 id가 불일치
            userDomain -->> api: 사용자 불일치 exception
            api -->> user: 사용자 불일치 exception
        end
        
        break 포인트잔액이 모자라는 경우
            userDomain -->> api: 포인트부족 exception
            api -->> user: 포인트부족 exception
        end
        
        userDomain ->> db: 포인트 차감이력 생성
        userDomain ->> db: 포인트 잔액 업데이트
        
        reservation ->> db: 결제내역 생성
        reservation ->> db: 예약정보를 결제완료상태로 업데이트
        
        waiting ->> db: 대기열 만료 업데이트
        
        reservation -->> api: return 예약정보
        api -->> user: return 예약정보
    end
```
