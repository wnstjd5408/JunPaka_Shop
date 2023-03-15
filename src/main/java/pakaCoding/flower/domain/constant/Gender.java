package pakaCoding.flower.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MAN("m","남"),
    WOMAN("w","여");

    private final String key;
    private final String value;
}
