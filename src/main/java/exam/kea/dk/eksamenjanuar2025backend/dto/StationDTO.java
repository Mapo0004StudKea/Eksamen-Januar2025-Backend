package exam.kea.dk.eksamenjanuar2025backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StationDTO {

    private Long id;
    private double latitude;
    private double longitude;

    public StationDTO(Long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
