package kr.geun.forblog.pure;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ListTest
 *
 * @author akageun
 * @since 2020-05-13
 */
@Slf4j
public class ListTest {

    private void run() {
        List<String> userIds = new ArrayList<>();
        userIds.add("test_1");
        userIds.add("test_2");
        userIds.add("test_3");
        userIds.add("test_4");
        userIds.add("test_5");
        userIds.add("test_6");
        userIds.add("test_7");
        userIds.add("test_8");


        List<TmpClz> target = new ArrayList<>();
        target.add(TmpClz.builder().userId("test_1").key("1").build());
        target.add(TmpClz.builder().userId("test_1").key("4").build());
        target.add(TmpClz.builder().key("2").build());
        target.add(TmpClz.builder().key("3").build());

        target.add(TmpClz.builder().userId("test_2").key("5").build());
        target.add(TmpClz.builder().key("6").build());
        target.add(TmpClz.builder().key("7").build());
        target.add(TmpClz.builder().key("8").build());

        List<String> blockedList = blockedUserIds(target.stream()
            .filter(info -> StringUtils.isNotBlank(info.getUserId()))
            .map(TmpClz::getUserId)
            .collect(Collectors.toSet()));


        target.stream()
            .map(info -> {
                if (StringUtils.isNotBlank(info.getUserId()) && isBlockedUserId(info.getUserId(), blockedList)) {
                    return null;
                }

                return info;
            })
            .filter(Objects::nonNull)
            .forEach(info -> log.info(info.toString()));
    }

    private boolean isBlockedUserId(String userId, List<String> blockedList) {
        return blockedList.contains(userId);
    }

    private List<String> blockedUserIds(Set<String> targetList) {
        List<String> blackListUserIds = new ArrayList<>();
        blackListUserIds.add("test_2");
        blackListUserIds.add("test_3");
        blackListUserIds.add("test_4");
        blackListUserIds.add("test_5");
        blackListUserIds.add("test_6");
        blackListUserIds.add("test_7");
        blackListUserIds.add("test_8");
        blackListUserIds.add("test_9");
        blackListUserIds.add("test_10");
        blackListUserIds.add("test_11");
        blackListUserIds.add("test_12");
        blackListUserIds.add("test_13");
        blackListUserIds.add("test_14");
        blackListUserIds.add("test_15");
        blackListUserIds.add("test_16");
        blackListUserIds.add("test_17");


        List<String> rtnList = new ArrayList<>();

        for (String target : targetList) {
            for (String blackListUserId : blackListUserIds) {
                if (StringUtils.equals(target, blackListUserId)) {
                    rtnList.add(blackListUserId);
                }
            }
        }

        return rtnList;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class TmpClz {
        private String userId;
        private String key;
        private String value;

        @Builder
        public TmpClz(String userId, String key, String value) {
            this.userId = userId;
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "TmpClz{" +
                "userId='" + userId + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
        }
    }

    public static void main(String[] args) {
        new ListTest().run();
    }
}
