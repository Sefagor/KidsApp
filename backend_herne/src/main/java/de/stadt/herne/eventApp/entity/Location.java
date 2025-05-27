package de.stadt.herne.eventApp.entity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Valid
public class Location {

    @NotNull
    private String city;
    
    @NotNull

    private String street;

    @NotNull

    private int houseNumber;

    @NotNull
    private int zip;

}



