# KTB4_Chloe_Week4
## 구현상의 한계

### Spring security는 사용하지 않고 진행

- JWT 방식을 사용하지 않았기 때문에 `user_id`로 대신 JWT 토큰의 역할을 대신 하도록 함.
- 회원 가입 시에 `user_id`를 반환하지 않고, 로그인 시에 반환 하는것으로 변경
- 거의 모든 API의 URL에`user_id`가 pathvariable로 포함되어 있음.

### DB 없이 구현

- DB가 존재하지 않아서, 생성되는 데이터를 `HashMap`을 이용해서 저장하고 관리
- JPA 의존성도 받지 않았기 때문에, `@Entity` `@Transactional` 사용하지 않음
