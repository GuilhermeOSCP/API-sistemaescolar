package com.guilhermeoscp.apisistemaescolar;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guilhermeoscp.apisistemaescolar.model.Student;
import com.guilhermeoscp.apisistemaescolar.repository.StudentRepository;
import com.guilhermeoscp.apisistemaescolar.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentEndpointTokenTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private UserRepository userRepository;

    private HttpEntity<Void> protectedHeader;
    private HttpEntity<Student> adminHeader;
    private HttpEntity<Void> wrongHeader;

    @BeforeEach
    public void configProtectedHeaders() {

        String str = "{\"username\": \"kars\", \"password\": \"jojofag\"}";

        HttpHeaders headers = restTemplate.postForEntity("http://localhost:8080/login", str, String.class).getHeaders();

        this.protectedHeader = new HttpEntity<>(headers);
    }

    @BeforeEach
    public void configAdminHeaders() {
        String str = "{\"username\": \"Wamuu\", \"password\": \"jojofag\"}";
        HttpHeaders headers = restTemplate.postForEntity("http://localhost:8080/login", str, String.class).getHeaders();
        this.adminHeader = new HttpEntity<>(headers);
    }

    @BeforeEach
    public void configWrongHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "11111");
        this.wrongHeader = new HttpEntity<>(headers);
    }

    @BeforeEach
    public void setup() {
        Student student = new Student(3L, "Rohan", "rohan@gmail.com");

        when(studentRepository.findById(3L)).thenReturn(java.util.Optional.of(student));
    }


    @Test
    public void whenListStudentUsingIncorrectToken_thenReturnStatusCode403Forbidden () {

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/protected/students/", GET, wrongHeader, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void whenGetStudentByIdUsingIncorrectToken_thenReturnStatusCode403Forbidden () {

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/protected/students/1", GET, wrongHeader, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void whenListStudentUsingCorrectToken_thenReturnStatusCode200OK () {
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/protected/students/", GET, protectedHeader, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void whenFindStudentByNameUsingIncorrectToken_thenReturnStatusCode403Forbidden () {
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/protected/students/findByName/Kars", GET, wrongHeader, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void whenGetStudentByIdUsingCorrectTokenAndStudentDontExist_thenReturnStatusCode404NotFound () {

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/protected/students/-1", GET, protectedHeader, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void whenSaveStudentUsingIncorrectToken_thenReturnStatusCode403Forbidden() {
        Student student = new Student(1L, "Rohan", "rohan@gmail.com");

        adminHeader = new HttpEntity<>(student, adminHeader.getHeaders());


        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/", POST, wrongHeader, String.class);


        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void whenSaveStudentUsingCorrectToken_thenReturn201Created () {
        Student student = new Student(1L, "Rohan", "rohan@gmail.com");

        adminHeader = new HttpEntity<>(student, adminHeader.getHeaders());

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/", POST, adminHeader, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void whenDeleteStudentUsingIncorrectToken_thenReturnStatusCode403Forbidden () {

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/{id}", DELETE, wrongHeader, String.class, 1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void whenDeleteStudentUsingCorrectToken_thenReturnStatusCode200Ok () throws JSONException {
        Student student = new Student("Rohan", "rohan@gmail.com");

        adminHeader = new HttpEntity<>(student, adminHeader.getHeaders());

        ResponseEntity<String> response1 = restTemplate.exchange("http://localhost:8080/v1/admin/students/", POST, adminHeader, String.class);

        JSONObject studentJson = new JSONObject(response1.getBody());

        doNothing().when(studentRepository).deleteById(studentJson.getLong("id"));

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/{id}", DELETE, adminHeader, String.class, studentJson.getLong("id"));

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void whenUpdateStudentUsingCorrectToken_thenReturnStatusCode200Ok () throws JSONException {

        Student student = new Student(999L, "Rohan", "rohan@gmail.com");

        adminHeader = new HttpEntity<>(student, adminHeader.getHeaders());

        ResponseEntity<String> response1 = restTemplate.exchange("http://localhost:8080/v1/admin/students/", POST, adminHeader, String.class);

        JSONObject studentJson = new JSONObject(response1.getBody());

        doNothing().when(studentRepository).deleteById(studentJson.getLong("id"));

        student = new Student(studentJson.getLong("id"), "Rohan", "rohan@gmail.com");

        adminHeader = new HttpEntity<>(student, adminHeader.getHeaders());

        restTemplate.exchange("http://localhost:8080/v1/admin/students/", POST, adminHeader, String.class);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/", PUT, adminHeader, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void whenUpdateStudentUsingIncorrectToken_thenReturnStatusCode403Forbidden () {


        Student student = new Student(1L, "Rohan", "rohan@gmail.com");

        studentRepository.save(student);

        adminHeader = new HttpEntity<>(student, adminHeader.getHeaders());

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/", PUT, wrongHeader, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }


    @Test
    @WithMockUser(username = "xxx", password = "xxx", roles = {"USER"})
    public void whenListAllStudentsUsingCorrectRole_thenReturnStatusCode200 () throws Exception {
        List<Student> students = asList(new Student(1L, "Rohan", "rohan@gmail.com"),
                new Student(2L, "Koichi", "koichi@gmail.com"));

        Page<Student> pagedStudents = new PageImpl<Student>(students);

        when(studentRepository.findAll(isA(Pageable.class))).thenReturn(pagedStudents);

        mockMvc.perform(get("http://localhost:8080/v1/protected/students/"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(studentRepository).findAll(isA(Pageable.class));
    }

    @Test
    @WithMockUser(username = "xxx", password = "xxx", roles = {"USER"})
    public void whenGetStudentByIdUsingCorrectRoleAndStudentDoesntExist_thenReturnStatusCode404 () throws Exception {
        Student student = new Student(3L, "Rohan", "rohan@gmail.com");

        when(studentRepository.findById(3L)).thenReturn(java.util.Optional.of(student));

        mockMvc.perform(get("http://localhost:8080/v1/protected/students/{id}", 6))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(studentRepository).findById(6L);
    }

    @Test
    @WithMockUser(username = "xxx", password = "xxx", roles = {"USER"})
    public void whenFindStudentsByNameUsingCorrectRole_thenReturnStatusCode200 () throws Exception {
        List<Student> students = asList(new Student(1L, "Rohan", "rohan@gmail.com"),
                new Student(2L, "Koichi", "koichi@gmail.com"),
                new Student(3L, "rohan kishibe", "rohan.kishibe@gmail.com"));

        when(studentRepository.findByNameIgnoreCaseContaining("rohan")).thenReturn(students);

        mockMvc.perform(get("http://localhost:8080/v1/protected/students/findByName/rohan"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(studentRepository).findByNameIgnoreCaseContaining("rohan");
    }

    @Test
    @WithMockUser(username = "xxx", password = "xxx", roles = {"ADMIN"})
    public void whenDeleteUsingCorrectRole_thenReturnStatusCode200 () throws Exception {

        Student student = new Student(3L, "Rohan", "rohan@gmail.com");

        when(studentRepository.findById(3L)).thenReturn(java.util.Optional.of(student));

        doNothing().when(studentRepository).deleteById(3L);

        mockMvc.perform(delete("http://localhost:8080/v1/admin/students/{id}", 3))
                .andExpect(status().isOk())
                .andDo(print());

        verify(studentRepository).deleteById(3L);
    }

    @Test
    @WithMockUser(username = "xxx", password = "xxx", roles = {"ADMIN"})
    public void whenDeleteHasRoleAdminAndStudentDontExist_thenReturnStatusCode404 () throws Exception {

        doNothing().when(studentRepository).deleteById(1L);

        mockMvc.perform(delete("http://localhost:8080/v1/admin/students/{id}", 1))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(studentRepository, atLeast(1)).findById(1L);
    }

    @Test
    @WithMockUser(username = "xxx", password = "xxx", roles = {"USER"})
    public void whenDeleteHasRoleUser_thenReturnStatusCode403 () throws Exception {
        doNothing().when(studentRepository).deleteById(1L);

        mockMvc.perform(delete("http://localhost:8080/v1/admin/students/{id}", 1))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "xxx", password = "xxx", roles = {"ADMIN"})
    public void whenSaveHasRoleAdminAndStudentIsNull_thenReturnStatusCode404 () throws Exception {

        Student student = new Student(3L, "", "rohan@gmail.com");

        when(studentRepository.save(student)).thenReturn(student);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(student);


        mockMvc.perform(post("http://localhost:8080/v1/admin/students/").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @WithMockUser(username = "xx", password = "xx", roles = "USER")
    public void whenListAllStudentsUsingCorrectRole_thenReturnCorrectData () throws Exception {
        List<Student> students = asList(new Student(1L, "Rohan", "rohan@gmail.com"),
                new Student(2L, "Koichi", "koichi@gmail.com"));

        Page<Student> pagedStudents = new PageImpl<Student>(students);

        when(studentRepository.findAll(isA(Pageable.class))).thenReturn(pagedStudents);

        mockMvc.perform(get("http://localhost:8080/v1/protected/students/"))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].name").value("Rohan"))
                .andExpect(jsonPath("$.content[0].email").value("rohan@gmail.com"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].name").value("Koichi"))
                .andExpect(jsonPath("$.content[1].email").value("koichi@gmail.com"));

        verify(studentRepository).findAll(isA(Pageable.class));
    }

}
