package ch.stragiotti.safersafe;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TokenGenerator {

    public static void main(String[] args) {

    }

    public String getToken() {
        Supplier<String> tokenSupplier = () -> {

            StringBuilder token = new StringBuilder();
            long currentTimeInMilisecond = Instant.now().toEpochMilli();
            return token.append(currentTimeInMilisecond).append("-")
                    .append(UUID.randomUUID().toString()).toString();
        };
        return Stream.generate(tokenSupplier).limit(1).toString();
    }
}
