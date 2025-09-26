package dev.edward.airbus.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SatelliteControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void createFetchAndReadParameters() throws Exception {
        String payload = """
            {
                "name": "A-Sat-1",
                "orbitType": "LEO",
                "launchDate": "2023-10-01T10:00:00",
                "parameters": {
                    "alt": 500,
                    "lat": 45.0,
                    "lon": -93.0,
                }
            """;

        var response = mockMvc.perform(post("/api/v1/satellites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.parameters.alt", is(500)))
            .andReturn().getResponse().getContentAsString();

        String id = response.replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        mockMvc.perform(get("/api/v1/satellites" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.orbit").value("LEO"));

        mockMvc.perform(get("/api/v1/satellites/" + id + "/parameters"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.lat", is(45.0)))
            .andExpect(jsonPath("$.lon", is(-93.0)));
    }

}

