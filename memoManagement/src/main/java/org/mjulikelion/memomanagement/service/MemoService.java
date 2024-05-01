package org.mjulikelion.memomanagement.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.domain.Like;
import org.mjulikelion.memomanagement.domain.Memo;
import org.mjulikelion.memomanagement.domain.User;
import org.mjulikelion.memomanagement.dto.MemoCreateDto;
import org.mjulikelion.memomanagement.dto.response.LikeResponseData;
import org.mjulikelion.memomanagement.dto.response.MemoListResponseData;
import org.mjulikelion.memomanagement.dto.response.MemoResponseData;
import org.mjulikelion.memomanagement.repository.MemoRepository;
import org.mjulikelion.memomanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    public void createMemo(MemoCreateDto memoCreateDto, String userId) {
        Random random = new Random();
        int memoId = random.nextInt(); // memoId 생성
        List<User> users = userRepository.getAllUsers(); // user list를 받는다.
        List<Like> likes = new ArrayList<>(); // 빈 좋아요 리스트 생성
        Memo memo = new Memo(memoId, memoCreateDto.getTitle(), memoCreateDto.getContent(), userId, likes);
        this.memoRepository.createMemo(memo, users);
    }

    public MemoListResponseData getAllMemos() {
        return new MemoListResponseData(memoRepository.getAllMemos());
    }

    public MemoListResponseData getAllMemoByUserId(String userId) {
        List<User> users = userRepository.getAllUsers();
        return new MemoListResponseData(memoRepository.getAllMemoByUserId(userId, users));
    }

    public MemoResponseData getMemoByMemoId(String userId, int memoId) {
        return new MemoResponseData(memoRepository.getMemoByMemoId(userId, memoId));
    }

    public void addLikeByMemoId(String userId, int memoId) {
        List<User> users = userRepository.getAllUsers();
        this.memoRepository.addLikeByMemoId(userId, memoId, users);
    }

    public LikeResponseData getLikeByMemoId(int memoId) {
        List<User> users = userRepository.getAllUsers(); // 모든 유저의 리스트
        List<String> likeUserList = this.memoRepository.getLikeByMemoId(memoId, users); // 메모에 좋아요를 누른 유저의 아이디 리스트
        int count = likeUserList.size();
        return new LikeResponseData(likeUserList, count);
    }

    public void deleteMemoByMemoId(String userId, int memoId) {
        this.memoRepository.deleteMemoByMemoId(userId, memoId);
    }

    public void updateMemoByMemoId(String userId, String content, int memoId) {
        this.memoRepository.updateMemoByMemoId(userId, memoId, content);
    }
}
