package de.fh.dortmund.eventApp.requestBody;

import de.fh.dortmund.eventApp.entity.Location;
import de.fh.dortmund.eventApp.entity.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Valid
public class EventBody {


    @NotNull
    private Status status;

    @NotNull
    private LocalDate eventDate;

    @NotNull
    private Location eventLocation;

    @Min(value = 0)
    private int maxParticipant;

    @NotNull
    private String eventPhoto;

    @NotNull
    private String eventDescription;

    @NotNull
    private String eventName;
}
