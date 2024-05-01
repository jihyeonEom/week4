package org.mjulikelion.memomanagement.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.dto.MemoCreateDto;
import org.mjulikelion.memomanagement.dto.MemoUpdateDto;
import org.mjulikelion.memomanagement.dto.ResponseDto;
import org.mjulikelion.memomanagement.dto.response.LikeResponseData;
import org.mjulikelion.memomanagement.dto.response.MemoListResponseData;
import org.mjulikelion.memomanagement.dto.response.MemoResponseData;
import org.mjulikelion.memomanagement.service.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping("/memos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto<Void>> createMemo(@RequestBody @Valid MemoCreateDto memoCreateDto, @RequestHeader("userId") String userId) {
        this.memoService.createMemo(memoCreateDto, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "create memo"), HttpStatus.OK);
    }

    @GetMapping("/memos/all")
    public ResponseEntity<ResponseDto<MemoListResponseData>> getAllMemos() {
        MemoListResponseData memoListResponseData = memoService.getAllMemos();
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get all memo", memoListResponseData), HttpStatus.OK);
    }

    @GetMapping("/memos")
    public ResponseEntity<ResponseDto<MemoListResponseData>> getAllMemoByUserId(@RequestHeader("userId") String userId) {
        MemoListResponseData memoListResponseData = memoService.getAllMemoByUserId(userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get memos", memoListResponseData), HttpStatus.OK);
    }

    @GetMapping("/memos/{memoId}")
    public ResponseEntity<ResponseDto<MemoResponseData>> getMemoByMemoId(@PathVariable int memoId, @RequestHeader("userId") String userId) {
        MemoResponseData memoResponseData = memoService.getMemoByMemoId(userId, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get memo", memoResponseData), HttpStatus.OK);
    }

    // 메모에 좋아요를 누르는 기능
    @PostMapping("/memos/{memoId}/likes")
    public ResponseEntity<ResponseDto<MemoResponseData>> addLikeByMemoId(@PathVariable int memoId, @RequestHeader("userId") String userId) {
        this.memoService.addLikeByMemoId(userId, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "add like"), HttpStatus.OK);
    }

    // 특정 메모의 좋아요 리스트 반환
    // 메모의 좋아요 갯수와 좋아요를 누른 유저의 이름을 보여준다.
    @GetMapping("/memos/{memoId}/likes")
    public ResponseEntity<ResponseDto<LikeResponseData>> getLikeByMemoId(@PathVariable int memoId) {
        LikeResponseData likeListResponseData = memoService.getLikeByMemoId(memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get like list", likeListResponseData), HttpStatus.OK);
    }

    @DeleteMapping("/memos/{memoId}")
    public ResponseEntity<ResponseDto<Void>> deleteMemoByMemoId(@PathVariable int memoId, @RequestHeader("userId") String userId) {
        this.memoService.deleteMemoByMemoId(userId, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "delete memo"), HttpStatus.OK);
    }

    @PatchMapping("/memos/{memoId}")
    public ResponseEntity<ResponseDto<Void>> updateMemoByMemoId(@PathVariable int memoId, @RequestBody @Valid MemoUpdateDto memoUpdateDto, @RequestHeader("userId") String userId) {
        this.memoService.updateMemoByMemoId(userId, memoUpdateDto.getContent(), memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "update memo"), HttpStatus.OK);
    }

}
