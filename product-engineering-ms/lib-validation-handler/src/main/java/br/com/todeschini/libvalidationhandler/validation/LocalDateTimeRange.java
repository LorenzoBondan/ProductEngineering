package br.com.todeschini.libvalidationhandler.validation;

import java.time.LocalDateTime;

public class LocalDateTimeRange {

    private final LocalDateTime start;
    private final LocalDateTime end;

    public LocalDateTimeRange(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
