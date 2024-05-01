package org.mjulikelion.memomanagement.repository;

import org.mjulikelion.memomanagement.domain.Like;
import org.mjulikelion.memomanagement.domain.Memo;
import org.mjulikelion.memomanagement.domain.User;
import org.mjulikelion.memomanagement.errorcode.ErrorCode;
import org.mjulikelion.memomanagement.exception.MemoNotFoundException;
import org.mjulikelion.memomanagement.exception.UserDoesNotHaveAccessException;
import org.mjulikelion.memomanagement.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

@RestController
public class MemoRepository {
    private final WeakHashMap<Integer, Memo> memos = new WeakHashMap<>();

    public void createMemo(Memo memo, List<User> users) {
        if (!isValidUserId(memo.getUserId(), users)) { // 유저가 회원가입한 유저가 아니면 예외처리
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        memos.put(memo.getId(), memo);
    }

    public boolean isValidUserId(String userId, List<User> users) {
        for (User user : users) {
            if (userId.equals(user.getId())) {
                return true;
            }
        }
        return false;
    }

    public List<Memo> getAllMemos() {
        return new ArrayList<>(memos.values());
    }

    public List<Memo> getAllMemoByUserId(String userId, List<User> users) {
        if (!isValidUserId(userId, users)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        List<Memo> result = new ArrayList<>();
        for (Memo memo : memos.values()) {
            if (userId.equals(memo.getUserId())) {
                result.add(memo);
            }
        }
        return result;
    }

    public Memo getMemoByMemoId(String userId, int memoId) {
        for (Memo memo : memos.values()) {
            if (memo.getId() == memoId) {
                if (userId.equals(memo.getUserId())) {
                    return memo;
                }
                throw new UserDoesNotHaveAccessException(ErrorCode.USER_DOES_NOT_HAVE_ACCESS);
            }
        }
        throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "memo with id " + memoId + " not found");
    }

    // 메모에 좋아요 추가
    public void addLikeByMemoId(String userId, int memoId, List<User> users) {
        for (int key : memos.keySet()) {
            if (key == memoId) { // 특정 메모를 찾고
                if (isValidUserId(userId, users)) { // 좋아요를 누른 유저가 유효한 유저인지 검사한 뒤 좋아요를 추가한다.
                    Memo memo = memos.get(key);
                    Like like = new Like(memoId, userId, LocalDateTime.now());
                    memo.getLikes().add(like);
                    return;
                }
                throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
            }
        }
        // 특정 메모가 없는 경우
        throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "memo with id " + memoId + " not found");
    }

    // 특정 메모의 좋아요 리스트 반환
    public List<String> getLikeByMemoId(int memoId, List<User> users) {
        List<String> result = new ArrayList<>(); // 좋아요 리스트를 담을 변수
        for (Memo memo : memos.values()) {
            if (memo.getId() == memoId) { // 특정 메모를 찾아서
                for (Like like : memo.getLikes()) { // 그 메모의 좋아요 리스트를 순회하며
                    // 좋아요를 누른 유저의 이름을 리스트에 추가한다.
                    String name = getUserNameByUserId(like.getUserId(), users);
                    result.add(name);
                }
                return result;
            }
        }
        throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "memo with id " + memoId + " not found");
    }

    public String getUserNameByUserId(String userId, List<User> users) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user.getName();
            }
        }
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    public void deleteMemoByMemoId(String userId, int memoId) {
        for (int key : memos.keySet()) {
            Memo memo = memos.get(key);
            if (memo.getId() == memoId) {
                if (userId.equals(memo.getUserId())) {
                    memos.remove(key);
                    return;
                }
                throw new UserDoesNotHaveAccessException(ErrorCode.USER_DOES_NOT_HAVE_ACCESS);
            }
        }
        throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "memo with id " + memoId + " not found");
    }

    public void updateMemoByMemoId(String userId, int memoId, String content) {
        for (Memo memo : memos.values()) {
            if (memo.getId() == memoId) {
                if (userId.equals(memo.getUserId())) {
                    memo.setContent(content);
                    return;
                }
                throw new UserDoesNotHaveAccessException(ErrorCode.USER_DOES_NOT_HAVE_ACCESS);
            }
        }
        throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "memo with id " + memoId + " not found");
    }
}
