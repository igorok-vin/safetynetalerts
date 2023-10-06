package com.safetynet.safetynetalerts.controllerTest;

import com.safetynet.safetynetalerts.controller.MedicalRecordsController;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(MedicalRecordsController.class)
@ExtendWith(SpringExtension.class)
@ComponentScan({"com.safetynet.safetynetalerts.model", "com.safetynet.safetynetalerts.repository",
        "com.safetynet.safetynetalerts.service"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Medical record controller Test ")
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("Find all medical records Test")
    public void findAllMedicalRecordsTest() throws Exception {
        this.mockMvc.perform(get("/medicalRecords"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("[{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"birthdate\":\"03/06/1984\",\"medications\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"birthdate\":\"03/06/1989\",\"medications\":[\"pharmacol:5000mg\",\"terazine:10mg\",\"noznazol:250mg\"],\"allergies\":[]},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"birthdate\":\"02/18/2012\",\"medications\":[],\"allergies\":[\"peanut\"]},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"birthdate\":\"09/06/2017\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\",\"birthdate\":\"01/08/1986\",\"medications\":[\"tetracyclaz:650mg\"],\"allergies\":[\"xilliathal\"]},{\"firstName\":\"Jonanathan\",\"lastName\":\"Marrack\",\"birthdate\":\"01/03/1989\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Tessa\",\"lastName\":\"Carman\",\"birthdate\":\"02/18/2012\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"birthdate\":\"09/06/2000\",\"medications\":[],\"allergies\":[\"shellfish\"]},{\"firstName\":\"Foster\",\"lastName\":\"Shepard\",\"birthdate\":\"01/08/1980\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Tony\",\"lastName\":\"Cooper\",\"birthdate\":\"03/06/1994\",\"medications\":[\"hydrapermazol:300mg\",\"dodoxadin:30mg\"],\"allergies\":[\"shellfish\"]},{\"firstName\":\"Lily\",\"lastName\":\"Cooper\",\"birthdate\":\"03/06/1994\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Sophia\",\"lastName\":\"Zemicks\",\"birthdate\":\"03/06/1988\",\"medications\":[\"aznol:60mg\",\"hydrapermazol:900mg\",\"pharmacol:5000mg\",\"terazine:500mg\"],\"allergies\":[\"peanut\",\"shellfish\",\"aznol\"]},{\"firstName\":\"Warren\",\"lastName\":\"Zemicks\",\"birthdate\":\"03/06/1985\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Zach\",\"lastName\":\"Zemicks\",\"birthdate\":\"03/06/2017\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Reginold\",\"lastName\":\"Walker\",\"birthdate\":\"08/30/1979\",\"medications\":[\"thradox:700mg\"],\"allergies\":[\"illisoxian\"]},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"birthdate\":\"03/06/1982\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Ron\",\"lastName\":\"Peters\",\"birthdate\":\"04/06/1965\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Allison\",\"lastName\":\"Boyd\",\"birthdate\":\"03/15/1965\",\"medications\":[\"aznol:200mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"birthdate\":\"12/06/1975\",\"medications\":[\"ibupurin:200mg\",\"hydrapermazol:400mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\",\"birthdate\":\"07/08/1980\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"birthdate\":\"03/06/2014\",\"medications\":[\"noxidian:100mg\",\"pharmacol:2500mg\"],\"allergies\":[]},{\"firstName\":\"Clive\",\"lastName\":\"Ferguson\",\"birthdate\":\"03/06/1994\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\",\"birthdate\":\"08/06/1945\",\"medications\":[\"tradoxidine:400mg\"],\"allergies\":[]}]"));
    }
    @Test
    @DisplayName("Add new medical record Test")
    public void addNewMedicalRecordTest() throws Exception {
        this.mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("{ \"firstName\":\"John\", \"lastName\":\"Walker\", " +
                        "\"birthdate\":\"03/08/1960\", \"medications\":[\"medication1\"], " +
                        "\"allergies\":[\"fallergie1\"]}"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("{ \"firstName\":\"John\", \"lastName\":\"Walker\", " +
                        "\"birthdate\":\"03/08/1960\", \"medications\":[\"medication1\"]," +
                        " \"allergies\":[\"fallergie1\"] }"));
    }

    @Test
    @DisplayName("Find medical records by first name and last name Test")
    public void findMedicalRecordsByFirstAndLastNameTest() throws Exception {
        this.mockMvc.perform(get("/medicalRecord")
                .param("firstName","Eric")
                .param("lastName","Cadigan"))
                .andExpect(content().json("{ \"firstName\":\"Eric\", \"lastName\":\"Cadigan\"," +
                        "\"birthdate\":\"08/06/1945\",\"medications\":[\"tradoxidine:400mg\"],\"allergies\":[]}"));
    }

    @Test
    @DisplayName("Update medical rercord Test")
    public void updateMedicalRercordTest() throws Exception {
        this.mockMvc.perform(put("/medicalRecord")
                .param("firstName", "Eric")
                .param("lastName","Cadigan")
                .contentType(MediaType.APPLICATION_JSON).content("{ \"firstName\":\"Eric\", \"lastName\":\"Cadigan\"," +
                        "\"birthdate\":\"08/06/1945\",\"medications\":[\"none\"],\"allergies\":[\"none\"]}"));
    }

    @Test
    @DisplayName("Delete medical record Test")
    public void deleteMedicalRecordTest() throws Exception {
        this.mockMvc.perform(delete("/medicalRecord")
                .param("firstName","Eric")
                .param("lastName","Cadigan"))
                .andExpect(status().is2xxSuccessful());
    }
}
