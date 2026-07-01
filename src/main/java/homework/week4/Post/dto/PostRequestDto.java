package homework.week4.Post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    @Size(max=26,message = "제목 글자 수는 최대 26자입니다.")
    @NotBlank(message ="제목은 필수값 입니다.")
    private String title;

    @NotBlank(message ="게시글 본문은 필수값 입니다.")
    private String content;

    private MultipartFile postImage;


}
