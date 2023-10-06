package com.safetynet.safetynetalerts.controllerTest;

import com.safetynet.safetynetalerts.controller.PersonController;
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
@WebMvcTest(PersonController.class)
@ExtendWith(SpringExtension.class)
@ComponentScan({"com.safetynet.safetynetalerts.model", "com.safetynet.safetynetalerts.repository",
        "com.safetynet.safetynetalerts.service"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Person controller Test ")
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void addNewPersonTest() throws Exception {
        this.mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Sarah\",\"lastName\":\"Conor\",\"address\":\"King str\"," +
                        "\"city\":\"Boston\",\"zip\":\"1235\",\"phone\":\"913-546-7216\"," +
                        "\"email\":\"sarah@conor\"}"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("{\"firstName\":\"Sarah\",\"lastName\":\"Conor\"," +
                        "\"address\":\"King str\",\"city\":\"Boston\",\"zip\":\"1235\"," +
                        "\"phone\":\"913-546-7216\",\"email\":\"sarah@conor\"}"));
    }

    @Test
    public void updatePersonTest() throws Exception {
        this.mockMvc.perform(put("/person")
                .param("firstName", "John")
                .param("lastName", "Boyd")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\"firstName\":\"John\"," +
                        "\"lastName\":\"Boyd\",\"address\":\"123 Test St\",\"city\":\"Culver\",\"zip\":\"97451\"," +
                        "\"phoneNumber\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePersonTest() throws Exception {
        this.mockMvc.perform(delete("/person")
                .param("firstName","Eric")
                .param("lastName","Cadigan"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void listsOfChildrenAndAdultsAtAddressTest() throws Exception {
        this.mockMvc.perform(get("/childAlert")
                .param("address","892 Downing Ct"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("[{\"firstName\":\"Sophia\",\"lastName\":\"Zemicks\"," +
                       "\"age\":\"35\"},{\"firstName\":\"Warren\",\"lastName\":\"Zemicks\"," +
                       "\"age\":\"38\"},{\"firstName\":\"Zach\",\"lastName\":\"Zemicks\",\"age\":\"6\"}]"));
    }

    @Test
    public void personInfoTest() throws Exception {
        this.mockMvc.perform(get("/personInfo")
                .param("firstName", "Eric")
                .param("lastName", "Cadigan"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json( "{\"address\":\"951 LoneTree Rd\",\"email\":\"gramps@email.com\"," +
                        "\"firstName\":\"Eric\", \"lastName\":\"Cadigan\"," +
                        "\"age\":\"78\",\"medications\":[\"tradoxidine:400mg\"],\"allergies\":[]}"));
    }

    @Test
    public void emailAddressesByCityTest() throws Exception {
        this.mockMvc.perform(get("/communityEmail")
                .param("city", "Culver"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json( "[\n" +
                                                 "\"jaboyd@email.com\",\n" +
                                                 "\"drk@email.com\",\n" +
                                                 "\"tenz@email.com\",\n" +
                                                 "\"jaboyd@email.com\",\n" +
                                                 "\"jaboyd@email.com\",\n" +
                                                 "\"drk@email.com\",\n" +
                                                 "\"tenz@email.com\",\n" +
                                                 "\"jaboyd@email.com\",\n" +
                                                 "\"jaboyd@email.com\",\n" +
                                                 "\"tcoop@ymail.com\",\n" +
                                                 "\"lily@email.com\",\n" +
                                                 "\"soph@email.com\",\n" +
                                                 "\"ward@email.com\",\n" +
                                                 "\"zarc@email.com\",\n" +
                                                 "\"reg@email.com\",\n" +
                                                 "\"jpeter@email.com\",\n" +
                                                 "\"jpeter@email.com\",\n" +
                                                 "\"aly@imail.com\",\n" +
                                                 "\"bstel@email.com\",\n" +
                                                 "\"ssanw@email.com\",\n" +
                                                 "\"bstel@email.com\",\n" +
                                                 "\"clivfd@ymail.com\",\n" +
                                                 "\"gramps@email.com\"\n" +
                                                 "]"));
    }

    @Test
    public void findAllPersonsTest() throws Exception {
        this.mockMvc.perform(get("/persons"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("[{\"firstName\":\"John\",\"lastName\":\"Boyd\"," +
                        "\"address\":\"1509 Culver St\"," +
                        "\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\"," +
                        "\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\"," +
                        "\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\"," +
                        "\"phone\":\"841-874-6513\",\"email\":\"drk@email.com\"},{\"firstName\":\"Tenley\"," +
                        "\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\"," +
                        "\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"tenz@email.com\"}," +
                        "{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\"," +
                        "\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\"," +
                        "\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Felicia\"," +
                        "\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\"," +
                        "\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6544\"," +
                        "\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Jonanathan\"," +
                        "\"lastName\":\"Marrack\",\"address\":\"29 15th St\",\"city\":\"Culver\"," +
                        "\"zip\":\"97451\",\"phone\":\"841-874-6513\",\"email\":\"drk@email.com\"}," +
                        "{\"firstName\":\"Tessa\",\"lastName\":\"Carman\",\"address\":\"834 Binoc Ave\"," +
                        "\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\"," +
                        "\"email\":\"tenz@email.com\"},{\"firstName\":\"Peter\",\"lastName\":\"Duncan\"," +
                        "\"address\":\"644 Gershwin Cir\",\"city\":\"Culver\",\"zip\":\"97451\"," +
                        "\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Foster\"," +
                        "\"lastName\":\"Shepard\",\"address\":\"748 Townings Dr\",\"city\":\"Culver\"," +
                        "\"zip\":\"97451\",\"phone\":\"841-874-6544\",\"email\":\"jaboyd@email.com\"}," +
                        "{\"firstName\":\"Tony\",\"lastName\":\"Cooper\",\"address\":\"112 Steppes Pl\"," +
                        "\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6874\"," +
                        "\"email\":\"tcoop@ymail.com\"},{\"firstName\":\"Lily\",\"lastName\":\"Cooper\"," +
                        "\"address\":\"489 Manchester St\",\"city\":\"Culver\",\"zip\":\"97451\"," +
                        "\"phone\":\"841-874-9845\",\"email\":\"lily@email.com\"},{\"firstName\":\"Sophia\"," +
                        "\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\",\"city\":\"Culver\"," +
                        "\"zip\":\"97451\",\"phone\":\"841-874-7878\",\"email\":\"soph@email.com\"}," +
                        "{\"firstName\":\"Warren\",\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\"," +
                        "\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7512\"," +
                        "\"email\":\"ward@email.com\"},{\"firstName\":\"Zach\",\"lastName\":\"Zemicks\"," +
                        "\"address\":\"892 Downing Ct\",\"city\":\"Culver\",\"zip\":\"97451\"," +
                        "\"phone\":\"841-874-7512\",\"email\":\"zarc@email.com\"},{\"firstName\":\"Reginold\"," +
                        "\"lastName\":\"Walker\",\"address\":\"908 73rd St\",\"city\":\"Culver\"," +
                        "\"zip\":\"97451\",\"phone\":\"841-874-8547\",\"email\":\"reg@email.com\"}," +
                        "{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"address\":\"908 73rd St\"," +
                        "\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7462\"," +
                        "\"email\":\"jpeter@email.com\"},{\"firstName\":\"Ron\",\"lastName\":\"Peters\"," +
                        "\"address\":\"112 Steppes Pl\",\"city\":\"Culver\",\"zip\":\"97451\"," +
                        "\"phone\":\"841-874-8888\",\"email\":\"jpeter@email.com\"},{\"firstName\":\"Allison\"," +
                        "\"lastName\":\"Boyd\",\"address\":\"112 Steppes Pl\",\"city\":\"Culver\"," +
                        "\"zip\":\"97451\",\"phone\":\"841-874-9888\",\"email\":\"aly@imail.com\"}," +
                        "{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\"," +
                        "\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7784\"," +
                        "\"email\":\"bstel@email.com\"},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\"," +
                        "\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":\"97451\"," +
                        "\"phone\":\"841-874-7784\",\"email\":\"ssanw@email.com\"},{\"firstName\":\"Kendrik\"," +
                        "\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\"," +
                        "\"zip\":\"97451\",\"phone\":\"841-874-7784\",\"email\":\"bstel@email.com\"}," +
                        "{\"firstName\":\"Clive\",\"lastName\":\"Ferguson\",\"address\":\"748 Townings Dr\"," +
                        "\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6741\"," +
                        "\"email\":\"clivfd@ymail.com\"},{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\"," +
                        "\"address\":\"951 LoneTree Rd\",\"city\":\"Culver\",\"zip\":\"97451\"," +
                        "\"phone\":\"841-874-7458\",\"email\":\"gramps@email.com\"}]"));
    }
}
