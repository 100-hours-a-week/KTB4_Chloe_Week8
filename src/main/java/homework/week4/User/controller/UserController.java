package homework.week4.User.controller;


import homework.week4.User.dto.*;

import homework.week4.response.ApiResponse;
import homework.week4.User.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원 가입 -> 사용자 생성
    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody SignUpRequestDto request) {

       userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    //회원 정보 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<ApiResponse<UserGetResponseDto>> lookupUser(@PathVariable Long user_id) {

        UserGetResponseDto result = userService.lookupUser(user_id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("회원정보 조회 완료",result));
    }

    //회원 탈퇴
    @DeleteMapping("/{user_id}")
    public ResponseEntity<ApiResponse<UserDeleteResponseDto>> deleteUser(@PathVariable Long user_id) {

        UserDeleteResponseDto result = userService.deleteUser(user_id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("회원 탈퇴 완료",result));
    }

    //회원 정보 수정
    @PatchMapping("/{user_id}")
    public ResponseEntity<ApiResponse<UserChangeResponseDto>> changeUser(
            @PathVariable Long user_id,
            @Valid @RequestBody UserChangeRequestDto request) {

        UserChangeResponseDto result = userService.changeUser(user_id,request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("회원정보 변경 완료",result));

    }

    //비빌번호 변경
    @PatchMapping("/{user_id}/password")
    public ResponseEntity<Void> changePassword (
            @PathVariable Long user_id,
            @Valid @RequestBody UserPasswordRequestDto request
    ){
        userService.changePassWord(user_id, request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
