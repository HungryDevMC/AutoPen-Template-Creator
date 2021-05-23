package tech.gesp.image_processing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.gesp.maths.Vector2D;

@AllArgsConstructor
@Getter
public class Pixel {
    private boolean isWhite;
    private Vector2D position;
}
