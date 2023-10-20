package com.safetynet.safetynetalerts.controllerTest;

import com.safetynet.safetynetalerts.controller.FireStationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(FireStationController.class)
@ExtendWith(SpringExtension.class)
@ComponentScan({"com.safetynet.safetynetalerts.model", "com.safetynet.safetynetalerts.repository",
        "com.safetynet.safetynetalerts.service"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Firestation controller Test ")
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("Add new firestation Test")
    public void addFireStationTest() throws Exception {
        this.mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"address\":\"257 King street\",\"station\":\"15\"}"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("{\"address\":\"257 King street\",\"station\":\"15\"}"));
    }

    @Test
    @DisplayName("Find all firestations Test")
    public void findAllFireStationsTest() throws Exception {
        this.mockMvc.perform(get("/firestations"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("[{\"address\":\"1509 Culver St\",\"station\":\"3\"}," +
                        "{\"address\":\"29 15th St\",\"station\":\"2\"}," +
                        "{\"address\":\"834 Binoc Ave\",\"station\":\"3\"}," +
                        "{\"address\":\"644 Gershwin Cir\",\"station\":\"1\"}," +
                        "{\"address\":\"748 Townings Dr\",\"station\":\"3\"}," +
                        "{\"address\":\"112 Steppes Pl\",\"station\":\"3\"}," +
                        "{\"address\":\"489 Manchester St\",\"station\":\"4\"}," +
                        "{\"address\":\"892 Downing Ct\",\"station\":\"2\"}," +
                        "{\"address\":\"908 73rd St\",\"station\":\"1\"}," +
                        "{\"address\":\"112 Steppes Pl\",\"station\":\"4\"}," +
                        "{\"address\":\"947 E. Rose Dr\",\"station\":\"1\"}," +
                        "{\"address\":\"748 Townings Dr\",\"station\":\"3\"}," +
                        "{\"address\":\"951 LoneTree Rd\",\"station\":\"2\"}]"));
    }

    @Test
    @DisplayName("Update firestation Test")
    public void updateFireStationTest() throws Exception {
        this.mockMvc.perform(put("/firestation")
                .param("address", "951 LoneTree Rd")
                .contentType(MediaType.APPLICATION_JSON).content("{\"address\":\"951 LoneTree Rd\",\"station\":\"7\"}"));
    }

    @Test
    @DisplayName("Delete firestation Test")
    public void deleteFireStationTest() throws Exception {
        this.mockMvc.perform(delete("/firestation")
                .param("address","951 LoneTree Rd"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Retrn list of adults and children serviced by corresponding firestation Test")
    public void listAdultsAndchildrenServicedByCorrespondingFireStationTest() throws Exception {
        this.mockMvc.perform(get("/firestation")
                .param("stationNumber","2"))
                .andExpect(content().json("{\"stationNumber\":\"2\",\"numberOfAdults\":\"4\"," +
                        "\"numberOfChildren\":\"1\"," +
                        "\"personList\":[\"Jonanathan\",\"Marrack\",\"29 15th St\",\"841-874-6513\"," +
                        "\"Sophia\",\"Zemicks\",\"892 Downing Ct\",\"841-874-7878\"," +
                        "\"Warren\",\"Zemicks\",\"892 Downing Ct\",\"841-874-7512\"," +
                        "\"Zach\",\"Zemicks\",\"892 Downing Ct\",\"841-874-7512\"," +
                        "\"Eric\",\"Cadigan\",\"951 LoneTree Rd\",\"841-874-7458\"]}"));
    }

    @Test
    @DisplayName("Find phone numbers each person within firestations jurisdiction Test")
    public void phoneNumbersEachPersonWithinFireStationsJurisdictionTest() throws Exception{
        this.mockMvc.perform(get("/phoneAlert")
        .param("firestation","2"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().json("[\"841-874-6513\",\"841-874-7878\"," +
                "\"841-874-7512\",\"841-874-7512\",\"841-874-7458\"]"))
        .andDo(print());

    }

    @Test
    @DisplayName("Find firestation number that services household address and create list of people in it Test")
    public void findFireStationNumberThatServicesHouseholdAddressAndListOfPeopleInItTest() throws Exception {
        this.mockMvc.perform(get("/fire")
                .param("address","951 LoneTree Rd"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("" +
                        "{\"station number: 2\":[{\"allergies\":[],\"phone\":\"841-874-6513\",\"medications\":[],\"lastName\":\"Marrack\",\"firstName\":\"Jonanathan\",\"age\":\"34\"}," +
                        "{\"allergies\":[\"peanut\",\"shellfish\",\"aznol\"],\"phone\":\"841-874-7878\",\"medications\":[\"aznol:60mg\",\"hydrapermazol:900mg\",\"pharmacol:5000mg\",\"terazine:500mg\"],\"lastName\":\"Zemicks\",\"firstName\":\"Sophia\",\"age\":\"35\"}," +
                        "{\"allergies\":[],\"phone\":\"841-874-7512\",\"medications\":[],\"lastName\":\"Zemicks\",\"firstName\":\"Warren\",\"age\":\"38\"}," +
                        "{\"allergies\":[],\"phone\":\"841-874-7512\",\"medications\":[],\"lastName\":\"Zemicks\",\"firstName\":\"Zach\",\"age\":\"6\"}," +
                        "{\"allergies\":[],\"phone\":\"841-874-7458\",\"medications\":[\"tradoxidine:400mg\"],\"lastName\":\"Cadigan\",\"firstName\":\"Eric\",\"age\":\"78\"}]}"));
    }

    @Test
    @DisplayName("Find all households with list of people in each firestation jurisdiction")
    public void findAllHouseholdsWithListOfPeopleInEachFireStationJurisdictionTest() throws Exception {
        this.mockMvc.perform(get("/flood/stations")
        .param("stations","1,4"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().json("[{\"address\":\"644 Gershwin Cir\",\"personList\":[{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"allergies\":[\"shellfish\"],\"medications\":[],\"phone\":\"841-874-6512\",\"age\":\"23\"}]}," +
                "{\"address\":\"908 73rd St\",\"personList\":[{\"firstName\":\"Reginold\"," +
                "\"lastName\":\"Walker\",\"allergies\":[\"illisoxian\"],\"medications\":[\"thradox:700mg\"]," +
                "\"phone\":\"841-874-8547\",\"age\":\"44\"},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\"," +
                "\"allergies\":[],\"medications\":[],\"phone\":\"841-874-7462\",\"age\":\"41\"}]}," +
                "{\"address\":\"947 E. Rose Dr\",\"personList\":[{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\"," +
                "\"allergies\":[\"nillacilan\"],\"medications\":[\"ibupurin:200mg\",\"hydrapermazol:400mg\"]," +
                "\"phone\":\"841-874-7784\",\"age\":\"47\"},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\"," +
                "\"allergies\":[],\"medications\":[],\"phone\":\"841-874-7784\",\"age\":\"43\"},{\"firstName\":\"Kendrik\"," +
                "\"lastName\":\"Stelzer\",\"allergies\":[],\"medications\":[\"noxidian:100mg\",\"pharmacol:2500mg\"]," +
                "\"phone\":\"841-874-7784\",\"age\":\"9\"}]},{\"address\":\"489 Manchester St\"," +
                "\"personList\":[{\"firstName\":\"Lily\",\"lastName\":\"Cooper\",\"allergies\":[],\"medications\":[]," +
                "\"phone\":\"841-874-9845\",\"age\":\"29\"}]},{\"address\":\"112 Steppes Pl\"," +
                "\"personList\":[{\"firstName\":\"Tony\",\"lastName\":\"Cooper\",\"allergies\":[\"shellfish\"]," +
                "\"medications\":[\"hydrapermazol:300mg\",\"dodoxadin:30mg\"],\"phone\":\"841-874-6874\",\"age\":\"29\"}," +
                "{\"firstName\":\"Ron\",\"lastName\":\"Peters\",\"allergies\":[],\"medications\":[]," +
                "\"phone\":\"841-874-8888\",\"age\":\"58\"},{\"firstName\":\"Allison\",\"lastName\":\"Boyd\"," +
                "\"allergies\":[\"nillacilan\"],\"medications\":[\"aznol:200mg\"],\"phone\":\"841-874-9888\"," +
                "\"age\":\"58\"}]}]"));
    }

}
