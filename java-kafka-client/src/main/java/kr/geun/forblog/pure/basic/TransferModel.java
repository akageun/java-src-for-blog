package kr.geun.forblog.pure.basic;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * TransferModel
 *
 * @author akageun
 * @since 2020-10-27
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferModel {
    private String id;
    private String name;
    private LocalDateTime date;

    @Builder
    public TransferModel(String id, String name, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
}
