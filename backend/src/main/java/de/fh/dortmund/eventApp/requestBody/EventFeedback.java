package de.fh.dortmund.eventApp.requestBody;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Valid
@Data
public class EventFeedback {

    @NotNull
    private long eventId;

    @Min(value = 1)
    @Max(value = 5)
    private int rating;

    @NotNull
    private String comment;

}
