package homework.week4.User.controller;


import homework.week4.Auth.dto.LoginResponseDto;
import homework.week4.User.dto.*;

import homework.week4.response.ApiResponse;
import homework.week4.User.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins="http://127.0.0.1:5500")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원 가입 -> 사용자 생성
    @PostMapping (consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SignUpResponseDto>> createUser(@Valid @ModelAttribute SignUpRequestDto request) {
       SignUpResponseDto result = userService.createUser(request);
        SignUpResponseDto response = new SignUpResponseDto(
                result.getUser_id(),
                "http://127.0.0.1:5500/Login/login.html"
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of("회원 가입 완료",response));
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
        UserDeleteResponseDto response = new UserDeleteResponseDto(
                result.getNickname(),
                result.getIs_member(),
                "http://127.0.0.1:5500/Login/login.html"
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("회원 탈퇴 완료",response));
    }

    //회원 정보 수정
    @PatchMapping(
            value = "/{user_id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<UserChangeResponseDto>> changeUser(
            @PathVariable Long user_id,
            @Valid  @ModelAttribute UserChangeRequestDto request) {

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
